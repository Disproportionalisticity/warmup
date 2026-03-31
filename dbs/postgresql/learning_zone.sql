SET search_path TO learning_zone, public;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username TEXT NOT NULL,
    email TEXT UNIQUE,
    active BOOLEAN DEFAULT true
);

select * from users;

INSERT INTO users (username, email) VALUES ('dev_user', 'dev@example.com');
INSERT INTO users (username, email) VALUES ('tester', 'test@example.com');



CREATE TABLE data_type_showcase (
    -- 1. IDENTIFIERS
    id SERIAL PRIMARY KEY,              -- Auto-incrementing 4-byte integer (Oracle uses sequences/identity)
    uuid_col UUID DEFAULT gen_random_uuid(), -- native UUID type (huge for modern apps, better than RAW(16))

    -- 2. STRINGS (Postgres handles these better than Oracle)
    short_text VARCHAR(50),             -- Same as Oracle
    long_text TEXT,                     -- *** USE THIS instead of CLOB. No 4000 char limit, no performance penalty.
    fixed_char CHAR(10),                -- Same as Oracle

    -- 3. NUMBERS
    small_num SMALLINT,                 -- 2-byte int (saves space)
    normal_int INTEGER,                 -- 4-byte int (standard)
    big_int BIGINT,                     -- 8-byte int
    precise_dec NUMERIC(10,2),          -- Same as Oracle's NUMBER
    real_num REAL,                      -- Variable-precision, inexact

    -- 4. DATES & TIMES (Be careful here!)
    only_date DATE,                     -- Only YYYY-MM-DD (unlike Oracle's DATE which includes time)
    timestamp_no_tz TIMESTAMP,          -- Date + Time
    timestamp_with_tz TIMESTAMPTZ,      -- *** RECOMMENDED. Best practice for global apps.
    duration INTERVAL,                  -- Represents '1 day' or '2 hours 30 mins' (very powerful in PG)

    -- 5. THE "POSTGRES SPECIALS" (Not in standard Oracle)
    is_active BOOLEAN,                  -- Actual TRUE/FALSE (Oracle usually uses CHAR(1) 'Y'/'N')
    metadata JSONB,                     -- *** BINARY JSON. Indexed, searchable, and extremely fast.
    tags TEXT[],                        -- NATIVE ARRAYS. You can store a list of strings in one column.
    ip_address INET,                    -- Specialized type for IPv4/IPv6 (validates format automatically)
    raw_data BYTEA                      -- Binary data (equivalent to BLOB)
);

-- Adding comments to the columns (Oracle style, but in PG)
COMMENT ON COLUMN data_type_showcase.long_text IS 'In PG, TEXT is just as fast as VARCHAR and has no size limit (up to 1GB).';
COMMENT ON COLUMN data_type_showcase.jsonb IS 'JSONB is the star of Postgres. It stores JSON in a binary format that can be indexed.';
COMMENT ON COLUMN data_type_showcase.tags IS 'Arrays are built-in. Use them for simple lists instead of creating a mapping table.';