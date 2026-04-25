INSERT INTO position (symbol, shares, average_cost, created_at, updated_at)
VALUES (:symbol, :shares, :averageCost, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
RETURNING id
