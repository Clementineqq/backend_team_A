CREATE TABLE IF NOT EXISTS lodger(
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at              TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at              TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    id_address              int UNIQUE REFERENCES address(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS member(
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at              TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at              TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    id_role                 int UNIQUE REFERENCES role(id) ON DELETE SET NULL
);

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
    id_member       INT UNIQUE REFERENCES member(id) ON DELETE SET NULL,
    id_lodger       INT UNIQUE REFERENCES lodger(id) ON DELETE SET NULL,
    id_counter      INT UNIQUE REFERENCES counter(id) ON DELETE SET NULL,
    id_company      INT REFERENCES company(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS serviceTask(
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    title           varchar(255) NOT NULL,
    description     varchar(512) NOT NULL,
    status          varchar(255) NOT NULL,
    id_creator      INT UNIQUE REFERENCES users(id) ON DELETE SET NULL,
    id_assignee     INT UNIQUE REFERENCES users(id) ON DELETE SET NULL,
    completed_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);