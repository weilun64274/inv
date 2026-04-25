package com.inv.portfolio.service;

import com.inv.portfolio.config.PortfolioProperties;
import com.inv.portfolio.dto.PositionSummary;
import com.inv.portfolio.dto.TradeRequest;
import com.inv.portfolio.enums.TradeType;
import com.inv.portfolio.model.Position;
import com.inv.portfolio.model.Transaction;
import com.inv.portfolio.repository.PositionRepository;
import com.inv.portfolio.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 掌管核心商業邏輯的內部領域服務 (Domain Service)。
 * 封裝了股票買賣、成本均價攤提與紀錄寫入等強型別處理作業，並不直接被 Controller 依賴。
 */
@Service
public class TradeService {

    private final PositionRepository positionRepository;
    private final TransactionRepository transactionRepository;
    private final PortfolioProperties portfolioProperties;

    /**
     * 建構子。
     *
     * @param positionRepository 部位資料庫存取層
     * @param transactionRepository 交易明細資料庫存取層
     * @param portfolioProperties 集中管理的系統設定屬性
     */
    public TradeService(PositionRepository positionRepository, 
                        TransactionRepository transactionRepository,
                        PortfolioProperties portfolioProperties) {
        this.positionRepository = positionRepository;
        this.transactionRepository = transactionRepository;
        this.portfolioProperties = portfolioProperties;
    }

    /**
     * 取得原始所有分離部位的陣列清單 API。
     *
     * @return 部位實體列表
     */
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    /**
     * 執行一次證券交易。
     *
     * @param request 夾帶前端傳入資料的「交易請求物件」
     */
    @Transactional
    public void processTrade(TradeRequest request) {
        if (request.type() == TradeType.BUY) {
            handleBuy(request);
        } else if (request.type() == TradeType.SELL) {
            handleSell(request);
        } else {
            throw new IllegalArgumentException("Unsupported trade type: " + request.type());
        }
    }

    private void handleBuy(TradeRequest request) {
        List<Position> existingPositions = positionRepository.findBySymbol(request.symbol());
        
        Position targetPosition = null;
        for (Position pos : existingPositions) {
            BigDecimal diffRatio = pos.averageCost().subtract(request.price()).abs()
                    .divide(pos.averageCost(), 4, RoundingMode.HALF_UP);
            if (diffRatio.compareTo(portfolioProperties.getTrade().getMergeTolerance().getRatio()) <= 0) {
                targetPosition = pos;
                break;
            }
        }

        Long positionId;
        if (targetPosition != null) {
            BigDecimal totalValue = targetPosition.shares().multiply(targetPosition.averageCost())
                    .add(request.shares().multiply(request.price()));
            BigDecimal newShares = targetPosition.shares().add(request.shares());
            BigDecimal newAvgCost = totalValue.divide(newShares, 4, RoundingMode.HALF_UP);
            
            targetPosition = new Position(targetPosition.id(), targetPosition.symbol(), newShares, newAvgCost, targetPosition.createdAt(), LocalDateTime.now());
            positionRepository.update(targetPosition);
            positionId = targetPosition.id();
        } else {
            Position newPos = new Position(null, request.symbol(), request.shares(), request.price(), LocalDateTime.now(), LocalDateTime.now());
            positionId = positionRepository.insert(newPos);
        }

        Transaction txn = new Transaction(null, positionId, request.type(), request.shares(), request.price(), request.exchangeRate(), request.taxAmount(), LocalDateTime.now());
        transactionRepository.insert(txn);
    }

    private void handleSell(TradeRequest request) {
        List<Position> existingPositions = positionRepository.findBySymbol(request.symbol());
        Position targetPosition = existingPositions.stream()
            .filter(p -> p.shares().compareTo(request.shares()) >= 0)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Not enough shares to sell in a single position"));

        BigDecimal newShares = targetPosition.shares().subtract(request.shares());
        Position updatedPos = new Position(targetPosition.id(), targetPosition.symbol(), newShares, targetPosition.averageCost(), targetPosition.createdAt(), LocalDateTime.now());
        positionRepository.update(updatedPos);

        Transaction txn = new Transaction(null, targetPosition.id(), request.type(), request.shares(), request.price(), request.exchangeRate(), request.taxAmount(), LocalDateTime.now());
        transactionRepository.insert(txn);
    }

    /**
     * 提供統整視角之彙整資料。
     *
     * @param symbol 美股代碼
     * @return 總計匯總之 DTO 結構
     */
    public PositionSummary calculateSymbolSummary(String symbol) {
        List<Position> positions = positionRepository.findBySymbol(symbol);
        if (positions.isEmpty()) {
            return new PositionSummary(symbol, BigDecimal.ZERO, BigDecimal.ZERO);
        }
        BigDecimal totalShares = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;

        for (Position p : positions) {
            totalShares = totalShares.add(p.shares());
            totalCost = totalCost.add(p.shares().multiply(p.averageCost()));
        }

        BigDecimal avgCost = totalShares.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO 
                : totalCost.divide(totalShares, 4, RoundingMode.HALF_UP);

        return new PositionSummary(symbol, totalShares, avgCost);
    }
}
