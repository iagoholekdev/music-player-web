CREATE SEQUENCE seq_file_upload START WITH 1 INCREMENT BY 1;

CREATE TABLE fileupload (
                            id BIGINT PRIMARY KEY,
                            uuid UUID DEFAULT gen_random_uuid() NOT NULL,
                            artist_name VARCHAR(1000),
                            song_name VARCHAR(1000),
                            path_file VARCHAR(1000),
                            path_song VARCHAR(1000)
);
