CREATE TABLE IF NOT EXISTS song_model (
    id SERIAL PRIMARY KEY,
    artist VARCHAR(255) NOT NULL,
    album VARCHAR(255) NOT NULL,
    duration VARCHAR(10) NOT NULL,
    resource_id INTEGER NOT NULL,
    year VARCHAR(4) NOT NULL,
    title VARCHAR(255) NOT NULL
);