use test;

-- delete from roles;
-- delete from users;

CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  dob datetime DEFAULT NULL,
  email varchar(255) NOT NULL,
  name varchar(255) DEFAULT NULL,
  password varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_avh1b2ec82audum2lyjx2p1ws (email)
);

CREATE TABLE roles (
  role_id int(11) NOT NULL AUTO_INCREMENT,
  role_name varchar(255) NOT NULL,
  user_id int(11) DEFAULT NULL,
  PRIMARY KEY (role_id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO users (id,dob,email,name,password) VALUES 
 (1,NULL,'admin@gmail.com','Administrator','admin'),
 (2,NULL,'siva@gmail.com','Siva','siva');

INSERT INTO roles (role_id,role_name,user_id) VALUES 
 (1,'ROLE_ADMIN',1),
 (2,'ROLE_USER',1),
 (3,'ROLE_USER',2);
