package com.inv.portfolio.config;

import com.inv.portfolio.enums.MergeTolerance;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 集中管理與 portfolio 相關的自訂應用程式屬性。
 * 對應 application.yml 中的 portfolio.* 設定。
 */
@Configuration
@ConfigurationProperties(prefix = "portfolio")
public class PortfolioProperties {

    private Trade trade = new Trade();

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public static class Trade {
        /**
         * 部位合併的容忍度設定，對應 portfolio.trade.merge-tolerance
         * 這個「巢狀結構」其實不是為了好看，是為了對應設定檔的結構 + 管理複雜設定
         * 爲了跟property一層對一層，完全對齊
         */
        private MergeTolerance mergeTolerance = MergeTolerance.DEFAULT;

        public MergeTolerance getMergeTolerance() {
            return mergeTolerance;
        }

        public void setMergeTolerance(MergeTolerance mergeTolerance) {
            this.mergeTolerance = mergeTolerance;
        }
    }
}
