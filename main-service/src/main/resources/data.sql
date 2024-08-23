insert into users (id, name, email, is_admin)
select 33, ' ', ' ', true
    where not exists (select 1 from users where id = 33);