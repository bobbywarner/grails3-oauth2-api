drop database if exists demo_test;
drop user if exists demo_test;
drop database if exists demo_dev;
drop user if exists demo_dev;
create database demo_test;
create user demo_test;
\c demo_test
GRANT ALL PRIVILEGES ON schema public to demo_test;
create database demo_dev;
create user demo_dev;
\c demo_dev
GRANT ALL PRIVILEGES ON schema public to demo_dev;
