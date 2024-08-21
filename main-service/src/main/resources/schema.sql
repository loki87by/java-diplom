CREATE TABLE IF NOT EXISTS users (
    id long generated by default as identity PRIMARY KEY,
    name VARCHAR(255) DEFAULT 'noname user',
    email VARCHAR(255) NOT NULL,
    is_admin boolean default 'false'
    );

CREATE TABLE IF NOT EXISTS categoryes (
    id long generated by default as identity PRIMARY KEY,
    name VARCHAR(255) DEFAULT 'noname user'
    );

CREATE TABLE IF NOT EXISTS compilations (
    id long generated by default as identity PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    pinned BOOLEAN DEFAULT false
    );

CREATE TABLE IF NOT EXISTS coordinates (
    id long generated by default as identity PRIMARY KEY,
    lat float not null,
    lon float not null
);

CREATE TABLE IF NOT EXISTS events (
    id long generated by default as identity PRIMARY KEY,
    annotation VARCHAR(512) NOT NULL,
    category_id BIGINT REFERENCES categoryes(id),
    confirmed_requests INT DEFAULT 0,
    event_date TIMESTAMP NOT NULL,
    user_id BIGINT REFERENCES users(id),
    paid BOOLEAN DEFAULT false,
    title VARCHAR(255) NOT NULL,
    views INT DEFAULT 0,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    compilation_id BIGINT REFERENCES compilations(id),
    description varchar(512) not null,
    location_id BIGINT REFERENCES coordinates(id),
    participant_limit int,
    published_on TIMESTAMP,
    state VARCHAR(255) default 'PUBLISHED',
    request_moderation BOOLEAN
    );

CREATE TABLE IF NOT EXISTS requests
(
    id long generated by default as identity PRIMARY KEY,
    published TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    event BIGINT REFERENCES events(id),
    requester BIGINT REFERENCES users(id),
    state VARCHAR(255) default 'PENDING'
)