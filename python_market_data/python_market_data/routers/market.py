from fastapi import APIRouter

router = APIRouter(prefix="/api/market", tags=["Market Data"])

@router.get("/quotes/{symbol}")
def get_stock_quote(symbol: str):
    """
    Mock implementation of fetching a stock quote.
    Ultimately this will hit Finnhub or Yahoo Finance.
    """
    return {
        "symbol": symbol.upper(),
        "price": 150.25,
        "change": 2.50,
        "change_percent": 1.69,
        "source": "MOCK"
    }

@router.get("/profile/{symbol}")
def get_company_profile(symbol: str):
    """
    Mock implementation of fetching company profile.
    """
    return {
        "symbol": symbol.upper(),
        "name": f"{symbol.upper()} Corp.",
        "sector": "Technology",
        "industry": "Software",
        "market_cap": 2500000000000
    }
