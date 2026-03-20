CREATE TABLE IF NOT EXISTS address(
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at              TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at              TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    street                  varchar(255) NOT NULL,
    house                   varchar(255) NOT NULL,
    flat                    varchar(255) NOT NULL,
    city                    varchar(255) NOT NULL,
    region                  varchar(255) NOT NULL,
    totalArea               varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS company (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at              TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at              TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    name                    varchar(255) NOT NULL,
    INN                     varchar(10) NOT NULL,
    KPP                     varchar(9) NOT NULL,
    id_address              INT UNIQUE REFERENCES address(id) ON DELETE SET NULL,
    email                   varchar(255) UNIQUE NOT NULL,
    resolution_comment      varchar(1024),
    description             varchar(1024),
    password                VARCHAR(255) NOT NULL
);

