CREATE TABLE IF NOT EXISTS users (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    phone_number    varchar(12) not null unique,
    email           varchar(255) unique,
    password        varchar(255),
    name            varchar(255),
    last_name       varchar(255),
    fathers_name    varchar(255),
    role            varchar(255),
    id_address      INT REFERENCES address(id) ON DELETE SET NULL,
    id_company      INT REFERENCES company(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS serviceTask(
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at          TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    title               varchar(255) NOT NULL,
    description         varchar(512) NOT NULL,
    status              varchar(255) NOT NULL,
    id_creator          INT REFERENCES users(id) ON DELETE SET NULL,
    id_assignee         INT REFERENCES users(id) ON DELETE SET NULL,
    completed_at        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    resolution_comment  varchar(1024) NOT NULL
);


CREATE TABLE IF NOT EXISTS counter (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at              TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at              TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    name                    varchar(255) NOT NULL,
    counter_value           float NOT NULL,
    id_user                 INT REFERENCES users(id) ON DELETE SET NULL,
    is_approved             boolean DEFAULT false
);
