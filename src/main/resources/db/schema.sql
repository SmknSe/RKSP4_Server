
CREATE TABLE task
(
    id          SERIAL PRIMARY KEY NOT NULL ,
    description VARCHAR(255),
    created_at  TIMESTAMP,
    completed   BOOLEAN NOT NULL
);