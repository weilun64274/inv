from pydantic import BaseModel, Field

class StockQuoteResponse(BaseModel):
    symbol: str = Field(..., description="The stock ticker symbol")
    price: float = Field(..., description="Current stock price")
    change: float = Field(..., description="Price change in absolute value")
    change_percent: float = Field(..., description="Price change in percentage")
    source: str = Field(default="MOCK", description="Data source provider")

class CompanyProfileResponse(BaseModel):
    symbol: str = Field(..., description="The stock ticker symbol")
    name: str = Field(..., description="Company legal name")
    sector: str = Field(..., description="Sector the company belongs to")
    industry: str = Field(..., description="Industry the company belongs to")
    market_cap: int = Field(..., description="Market capitalization in USD")
