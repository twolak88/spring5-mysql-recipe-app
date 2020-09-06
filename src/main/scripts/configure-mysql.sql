#Created in docker
#docker run --name tw-mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -v ~/docker/dockerdata/mysql/spring5-mysql-recipe-app:/var/lib/mysql -p 3306:3306 --cap-add sys_nice -d mysql

#Create dbs
CREATE DATABASE tw_dev;
CREATE DATABASE tw_prod;

#Create users
CREATE USER 'tw_dev_user'@'localhost' IDENTIFIED BY 'pass';
CREATE USER 'tw_prod_user'@'localhost' IDENTIFIED BY 'pass';
CREATE USER 'tw_dev_user'@'%' IDENTIFIED BY 'pass';
CREATE USER 'tw_prod_user'@'%' IDENTIFIED BY 'pass';

#DB Grants
GRANT SELECT ON tw_dev.* to 'tw_dev_user'@'localhost';
GRANT INSERT ON tw_dev.* to 'tw_dev_user'@'localhost';
GRANT UPDATE ON tw_dev.* to 'tw_dev_user'@'localhost';
GRANT DELETE ON tw_dev.* to 'tw_dev_user'@'localhost';
GRANT SELECT ON tw_prod.* to 'tw_prod_user'@'localhost';
GRANT INSERT ON tw_prod.* to 'tw_prod_user'@'localhost';
GRANT UPDATE ON tw_prod.* to 'tw_prod_user'@'localhost';
GRANT DELETE ON tw_prod.* to 'tw_prod_user'@'localhost';
GRANT SELECT ON tw_dev.* to 'tw_dev_user'@'%';
GRANT INSERT ON tw_dev.* to 'tw_dev_user'@'%';
GRANT UPDATE ON tw_dev.* to 'tw_dev_user'@'%';
GRANT DELETE ON tw_dev.* to 'tw_dev_user'@'%';
GRANT SELECT ON tw_prod.* to 'tw_prod_user'@'%';
GRANT INSERT ON tw_prod.* to 'tw_prod_user'@'%';
GRANT UPDATE ON tw_prod.* to 'tw_prod_user'@'%';
GRANT DELETE ON tw_prod.* to 'tw_prod_user'@'%';