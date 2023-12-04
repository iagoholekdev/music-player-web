CREATE SEQUENCE seq_music;

CREATE TABLE music (
                       id SERIAL PRIMARY KEY,
                       trackname VARCHAR(255),
                       artistname VARCHAR(255),
                       datelistened DATE
);