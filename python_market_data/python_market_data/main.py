from fastapi import FastAPI
from python_market_data.routers import market

app = FastAPI(title="Python Market Data API")

app.include_router(market.router)

@app.get("/")
def read_root():
    return {"message": "Welcome to Python Market Data API"}

@app.get("/health")
def health_check():
    return {"status": "ok"}

