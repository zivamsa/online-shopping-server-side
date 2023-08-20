-- Create admin user if doesn't exist
INSERT INTO users (id, role, email, password, first_name, last_name, address)
    SELECT nextval( 'user_sequence' ),
           'ADMIN',
           'admin@gmail.com',
           '$2a$10$v9g6kxYhZ/HM8YK1MUvvJeLaQyC8.2DCxST2aJah4OUPVtNMhmfSO', -- hashed "admin"
           'משתמש',
           'מנהל',
           'ראשון לציון'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE role = 'ADMIN');