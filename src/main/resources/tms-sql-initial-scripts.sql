CREATE DATABASE IF NOT EXISTS tms CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS tms.user_role
(
  id                          INT(11) PRIMARY KEY AUTO_INCREMENT,
  `name`                      VARCHAR(256) NOT NULL,
  description                 VARCHAR(256),
  is_deleted                  TINYINT(1) DEFAULT 0 NOT NULL,
  created_timestamp           TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
  last_updated_timestamp      TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL
);
INSERT INTO tms.user_role(id, `name`, `description`) VALUES (1, 'ADMIN', 'super administrator');

CREATE TABLE IF NOT EXISTS tms.system_user
(
  id                          INT(11) PRIMARY KEY AUTO_INCREMENT,
  username                    VARCHAR(256) NOT NULL,
  `password`                  VARCHAR(256) NOT NULL,
  email                       VARCHAR(256),
  firstname                   VARCHAR(256),
  lastname                    VARCHAR(256),
  role_id                     INT(11) NOT NULL,
  remark                      VARCHAR(256),
  is_deleted                  TINYINT(1) DEFAULT 0 NOT NULL,
  created_timestamp           TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
  last_updated_timestamp      TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL
);
-- Default password: 123456
INSERT INTO tms.system_user(id, username, `password`, email, firstname, lastname, role_id, remark)
  VALUES (1, 'admin', '$2a$10$trT3.R/Nfey62eczbKEnueTcIbJXW.u1ffAo/XfyLpofwNDbEB86O', 'admin@bonbon.com', 'administrator', '', 1, 'super administrator');
