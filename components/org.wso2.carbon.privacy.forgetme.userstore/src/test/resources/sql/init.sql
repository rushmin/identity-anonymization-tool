CREATE TABLE IF NOT EXISTS UM_USER(
                        UM_ID INTEGER NOT NULL AUTO_INCREMENT,
                        UM_USER_NAME VARCHAR(255) NOT NULL,
                        UM_USER_PASSWORD VARCHAR(255) NOT NULL,
                        UM_SALT_VALUE VARCHAR(31),
                        UM_REQUIRE_CHANGE BOOLEAN DEFAULT FALSE,
                        UM_CHANGED_TIME TIMESTAMP NOT NULL,
                        UM_TENANT_ID INTEGER DEFAULT 0,
                        PRIMARY KEY (UM_ID, UM_TENANT_ID),
                        UNIQUE(UM_USER_NAME, UM_TENANT_ID));

INSERT INTO UM_USER(UM_USER_NAME, UM_USER_PASSWORD, UM_SALT_VALUE, UM_CHANGED_TIME, UM_TENANT_ID) VALUES('user1',
'password', '123', '2018-09-17 18:47:52', -1234);

INSERT INTO UM_USER(UM_USER_NAME, UM_USER_PASSWORD, UM_SALT_VALUE, UM_CHANGED_TIME, UM_TENANT_ID) VALUES('user1',
'password', '123', '2018-09-17 18:47:52', 1);
