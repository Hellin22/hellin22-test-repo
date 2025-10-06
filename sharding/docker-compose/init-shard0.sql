CREATE TABLE IF NOT EXISTS users (
     id BIGINT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     email VARCHAR(100) NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (id, name, email) VALUES
    (0, 'User from Shard0', 'shard0@example.com');