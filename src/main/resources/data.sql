CREATE TABLE Users (
    id BIGINT NOT NULL PRIMARY KEY,
    username VARCHAR(255),
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    update_at TIMESTAMP
);

CREATE TABLE Posts (
    id BIGINT NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    update_at TIMESTAMP,
    CONSTRAINT FK_Post_User FOREIGN KEY (user_id)
      REFERENCES Users(id)
);
