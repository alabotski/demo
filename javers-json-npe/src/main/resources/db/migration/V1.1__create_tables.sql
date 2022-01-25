DROP TABLE IF EXISTS limitation;
DROP TABLE IF EXISTS limitation_range;
DROP TABLE IF EXISTS limitation_signature;

CREATE TABLE limitation
(
    id      UUID PRIMARY KEY,
    caption varchar(256)
);

CREATE table limitation_range
(
    id       UUID PRIMARY KEY,
    caption  varchar(256),
    limit_id UUID NOT NULL,
    FOREIGN KEY (limit_id) REFERENCES limitation
);

CREATE table limitation_signature
(
    id             UUID NOT NULL,
    caption        varchar(256),
    limit_range_id UUID NOT NULL,
    FOREIGN KEY (limit_range_id) REFERENCES limitation_range
);