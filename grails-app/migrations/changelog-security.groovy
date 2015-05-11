databaseChangeLog = {
    changeSet(author: "bobbywarner", id: "1430253322325-1") {
        createSequence(sequenceName: "hibernate_sequence")
    }

    changeSet(author: "bobbywarner", id: "1430253322325-2") {
        createTable(tableName: "person") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "personPK")
            }
            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }
            column(name: "full_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
            column(name: "email", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
            column(name: "password", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
            column(name: "username", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bobbywarner", id: "1430253322325-3") {
        createTable(tableName: "person_security_role") {
            column(name: "security_role_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
            column(name: "person_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bobbywarner", id: "1430253322325-4") {
        createTable(tableName: "security_role") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "security_rolePK")
            }
            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }
            column(name: "authority", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bobbywarner", id: "1430253322325-5") {
        addPrimaryKey(columnNames: "security_role_id, person_id", constraintName: "person_security_rolePK", tableName: "person_security_role")
    }

    changeSet(author: "bobbywarner", id: "1430253322325-6") {
        addUniqueConstraint(columnNames: "username", constraintName: "UC_PERSONUSERNAME_COL", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "person")
        addUniqueConstraint(columnNames: "email", constraintName: "UC_PERSONEMAIL_COL", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "person")
    }

    changeSet(author: "bobbywarner", id: "1430253322325-7") {
        addUniqueConstraint(columnNames: "authority", constraintName: "UC_SECURITY_ROLEAUTHORITY_COL", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "security_role")
    }

    changeSet(author: "bobbywarner", id: "1430253322325-8") {
        addForeignKeyConstraint(baseColumnNames: "person_id", baseTableName: "person_security_role", constraintName: "FK_a4m7f8l95pxepnu3b5uv4hpkt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person")
    }

    changeSet(author: "bobbywarner", id: "1430253322325-9") {
        addForeignKeyConstraint(baseColumnNames: "security_role_id", baseTableName: "person_security_role", constraintName: "FK_k2fmduslrakqpgc7pcinl9xxx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "security_role")
    }

    changeSet(author: "bobbywarner", id: "1430253322325-10") {
        createTable(tableName: "oauth_access_token") {
            column(name: "token_id", type: "VARCHAR(256)")
            column(name: "token", type: "BYTEA")
            column(name: "authentication_id", type: "VARCHAR(256)")
            column(name: "user_name", type: "VARCHAR(256)")
            column(name: "client_id", type: "VARCHAR(256)")
            column(name: "authentication", type: "BYTEA")
            column(name: "refresh_token", type: "VARCHAR(256)")
        }
    }

    changeSet(author: "bobbywarner", id: "1430253322325-11") {
        createTable(tableName: "oauth_approvals") {
            column(name: "userid", type: "VARCHAR(256)")
            column(name: "clientid", type: "VARCHAR(256)")
            column(name: "scope", type: "VARCHAR(256)")
            column(name: "status", type: "VARCHAR(10)")
            column(name: "expiresat", type: "TIMESTAMP WITHOUT TIME ZONE")
            column(name: "lastmodifiedat", type: "TIMESTAMP WITHOUT TIME ZONE")
        }
    }

    changeSet(author: "bobbywarner", id: "1430253322325-12") {
        createTable(tableName: "oauth_client_details") {
            column(name: "client_id", type: "VARCHAR(256)") {
                constraints(nullable: "false")
            }
            column(name: "resource_ids", type: "VARCHAR(256)")
            column(name: "client_secret", type: "VARCHAR(256)")
            column(name: "scope", type: "VARCHAR(256)")
            column(name: "authorized_grant_types", type: "VARCHAR(256)")
            column(name: "web_server_redirect_uri", type: "VARCHAR(256)")
            column(name: "authorities", type: "VARCHAR(256)")
            column(name: "access_token_validity", type: "INT4")
            column(name: "refresh_token_validity", type: "INT4")
            column(name: "additional_information", type: "VARCHAR(4096)")
            column(name: "autoapprove", type: "VARCHAR(256)")
        }
    }

    changeSet(author: "bobbywarner", id: "1430253322325-13") {
        createTable(tableName: "oauth_client_token") {
            column(name: "token_id", type: "VARCHAR(256)")
            column(name: "token", type: "BYTEA")
            column(name: "authentication_id", type: "VARCHAR(256)")
            column(name: "user_name", type: "VARCHAR(256)")
            column(name: "client_id", type: "VARCHAR(256)")
        }
    }

    changeSet(author: "bobbywarner", id: "1430253322325-14") {
        createTable(tableName: "oauth_code") {
            column(name: "code", type: "VARCHAR(256)")
            column(name: "authentication", type: "BYTEA")
        }
    }

    changeSet(author: "bobbywarner", id: "1430253322325-15") {
        createTable(tableName: "oauth_refresh_token") {
            column(name: "token_id", type: "VARCHAR(256)")
            column(name: "token", type: "BYTEA")
            column(name: "authentication", type: "BYTEA")
        }
    }

    changeSet(author: "bobbywarner", id: "1430253322325-16") {
        addPrimaryKey(columnNames: "client_id", constraintName: "oauth_client_details_pkey", tableName: "oauth_client_details")
    }

    changeSet(author: "bobbywarner", id: "1430253322325-17") {
        insert(tableName: 'oauth_client_details') {
            column(name: "client_id", value: "demo-client")
            column(name: "resource_ids", value: "demo")
            column(name: "client_secret", value: "123456")
            column(name: "scope", value: "read,write")
            column(name: "authorized_grant_types", value: "password,refresh_token,client_credentials")
            column(name: "web_server_redirect_uri", value: "")
            column(name: "authorities", value: "ROLE_CLIENT,ROLE_GUEST")
            column(name: "access_token_validity", value: 43200)
            column(name: "refresh_token_validity", value: 2592000)
            column(name: "additional_information", value: "")
            column(name: "autoapprove", value: false)
        }
    }
}
