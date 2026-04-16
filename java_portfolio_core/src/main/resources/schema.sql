CREATE TABLE IF NOT EXISTS position (
    id SERIAL PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL,
    shares NUMERIC(15, 6) NOT NULL DEFAULT 0,
    average_cost NUMERIC(15, 4) NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transaction (
    id SERIAL PRIMARY KEY,
    position_id INTEGER REFERENCES position(id),
    type VARCHAR(20) NOT NULL, -- BUY, SELL, DIVIDEND, DEPOSIT, WITHDRAWAL
    shares NUMERIC(15, 6) NOT NULL DEFAULT 0,
    price NUMERIC(15, 4) NOT NULL DEFAULT 0,
    exchange_rate NUMERIC(10, 4) DEFAULT 1.0, -- USD/Local currency exchange rate at the time
    tax_amount NUMERIC(15, 4) DEFAULT 0, -- Primarily for 30% dividend withholding tax
    transaction_date TIMESTAMP NOT NULL
);
