UPDATE position
SET shares = :shares,
    average_cost = :averageCost,
    updated_at = CURRENT_TIMESTAMP
WHERE id = :id
