CREATE TABLE IF NOT EXISTS users (
     id BIGINT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     email VARCHAR(100) NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (id, name, email) VALUES
    (1, 'User from Shard1', 'shard1@example.com');