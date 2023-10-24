CREATE TABLE drivers
(
    id     INTEGER PRIMARY KEY,
    human_id   INTEGER UNIQUE REFERENCES human (id),
    car_id INTEGER REFERENCES cars (id)
);
CREATE TABLE human
(
    id                 INTEGER PRIMARY KEY,
    name               TEXT    NOT NULL,
    age                INTEGER NOT NULL,
    haveDriversLicense BOOLEAN default false
);
CREATE TABLE cars
(
    id    INTEGER PRIMARY KEY,
    brand TEXT  NOT NULL,
    model TEXT  NOT NULL,
    cost  MONEY NOT NULL
)