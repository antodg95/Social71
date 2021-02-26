CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email varchar (255) UNIQUE NOT NULL,
    created_on TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE whispers (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT NOT NULL,
    text varchar(255) NOT NULL,
    created_on TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);