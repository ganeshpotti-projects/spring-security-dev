CREATE TABLE employees (
    id UUID PRIMARY KEY,

    name VARCHAR(100) NOT NULL,

    email VARCHAR(255) NOT NULL UNIQUE,

    phone_number VARCHAR(10) NOT NULL UNIQUE,

    age INTEGER,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    status VARCHAR(50) NOT NULL
);
