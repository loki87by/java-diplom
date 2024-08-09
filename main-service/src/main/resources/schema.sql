CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) DEFAULT 'noname user',
    email VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS categoryes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) DEFAULT 'noname user'
    );

CREATE TABLE IF NOT EXISTS compilations (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    pinned BOOLEAN DEFAULT false
    );

CREATE TABLE IF NOT EXISTS coordinates (
    id SERIAL PRIMARY KEY,
    lat float not null,
    lon float not null
);

CREATE TABLE IF NOT EXISTS events (
    id SERIAL PRIMARY KEY,
    annotation VARCHAR(512) NOT NULL,
    category_id BIGINT REFERENCES categoryes(id),
    confirmed_requests INT DEFAULT 0,
    event_date TIMESTAMPTZ NOT NULL,
    user_id BIGINT REFERENCES users(id),
    paid BOOLEAN DEFAULT false,
    title VARCHAR(255) NOT NULL,
    views INT DEFAULT 0,
    created_on TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    compilation_id BIGINT REFERENCES compilations(id),
    description varchar(512) not null
    location_id BIGINT REFERENCES coordinates(id),
    participant_limit int,
    published_on TIMESTAMPTZ,
    state VARCHAR(255) Default "PUBLISHED",
    request_moderation BOOLEAN
    );

CREATE TABLE IF NOT EXISTS requests
(
    id SERIAL PRIMARY KEY,
    published TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    event BIGINT REFERENCES events(id),
    requester BIGINT REFERENCES users(id),
    state VARCHAR(255) Default "PENDING",
)