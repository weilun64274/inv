from fastapi import FastAPI

app = FastAPI(title="Python Market Data API")

@app.get("/")
def read_root():
    return {"message": "Welcome to Python Market Data API"}

@app.get("/health")
def health_check():
    return {"status": "ok"}
