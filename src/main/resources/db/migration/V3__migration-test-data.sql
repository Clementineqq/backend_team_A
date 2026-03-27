DO $$
DECLARE
    v_company_id INTEGER;
    v_company_address_id INTEGER;
    v_user_address_id INTEGER;
    v_user_id INTEGER;
BEGIN

    INSERT INTO address (street, house, flat, city, region, totalArea, created_at, updated_at)
    VALUES ('Ленина', '15', '42', 'Москва', 'Московская область', 120.5, NOW(), NOW())
    RETURNING id INTO v_company_address_id;

    INSERT INTO company (name, INN, KPP, id_address, email, description, created_at, updated_at)
    VALUES ('ООО Ромашка', '1234567890', '123456789', v_company_address_id, 'info@romashka.ru', 'Компания по производству', NOW(), NOW())
    RETURNING id INTO v_company_id;

    INSERT INTO address (street, house, flat, city, region, totalArea, created_at, updated_at)
    VALUES ('Тверская', '10', '5', 'Москва', 'Московская область', 65.3, NOW(), NOW())
    RETURNING id INTO v_user_address_id;

    INSERT INTO users (phone_number, email, password, name, last_name, fathers_name, role, id_address, id_company, created_at, updated_at)
        VALUES (
            '+79991234567',
            'admin@exmaple.ru',
            '$2a$10$OENEHXXBdNaJzrvldYLvEODamvsamoKD0yLf28dS4APf2FpmGMCyu',
            'Админ',
            'Админов',
            'Админович',
            'Admin',
            v_user_address_id,
            v_company_id,
            NOW(),
            NOW()
        )
    RETURNING id INTO v_user_id;

END $$;