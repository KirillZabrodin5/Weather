CREATE TABLE Users (
                       id          SERIAL PRIMARY KEY,
                       login       VARCHAR(128) NOT NULL,
                       password    VARCHAR(128) NOT NULL
);

CREATE TABLE Locations (
                           id          SERIAL PRIMARY KEY,
                           Name        VARCHAR(128) NOT NULL,
                           UserId      INT REFERENCES Users (id) NOT NULL,
                           Latitude    DECIMAL(4, 2) NOT NULL,
                           Longitude   DECIMAL(4, 2) NOT NULL
);

CREATE TABLE Sessions (
                          id          UUID PRIMARY KEY,
                          UserId      INT REFERENCES Users(id) NOT NULL ,
                          ExpiresAt   TIMESTAMP WITHOUT TIME ZONE NOT NULL
);