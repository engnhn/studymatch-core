CREATE TABLE admins (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

INSERT INTO admins (id, email, password, role)
VALUES (gen_random_uuid(), 'admin@studymatch.com', 'admin123', 'SUPER_ADMIN');
