databaseChangeLog = {

	changeSet(author: "anthony (generated)", id: "1486481218350-1") {
		createTable(tableName: "APPLICATION_USER") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_1")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "API_KEY", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "APPLICATION_NAME", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PASSWORD", type: "VARCHAR(8)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-2") {
		createTable(tableName: "POST") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_2")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CONTENT", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "DATE_CREATED", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "USER_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-3") {
		createTable(tableName: "POST_TAGS") {
			column(name: "POST_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "TAG_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-4") {
		createTable(tableName: "PROFILE") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_18")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "BIO", type: "VARCHAR(1000)")

			column(name: "COUNTRY", type: "VARCHAR(255)")

			column(name: "EMAIL", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "FULL_NAME", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "HOMEPAGE", type: "VARCHAR(255)")

			column(name: "JABBER_ADDRESS", type: "VARCHAR(255)")

			column(name: "PHOTO", type: "VARBINARY(2097152)")

			column(name: "SKIN", type: "VARCHAR(9)")

			column(name: "TIMEZONE", type: "VARCHAR(255)")

			column(name: "USER_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-5") {
		createTable(tableName: "TAG") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_14")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "NAME", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "USER_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-6") {
		createTable(tableName: "USER") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_27")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DATE_CREATED", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "HOMEPAGE", type: "VARCHAR(255)")

			column(name: "LOGIN_ID", type: "VARCHAR(20)") {
				constraints(nullable: "false")
			}

			column(name: "PASSWORD", type: "VARCHAR(8)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-7") {
		createTable(tableName: "USER_USER") {
			column(name: "USER_FOLLOWING_ID", type: "BIGINT")

			column(name: "USER_ID", type: "BIGINT")
		}
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-8") {
		addPrimaryKey(columnNames: "POST_ID, TAG_ID", constraintName: "CONSTRAINT_1F", tableName: "POST_TAGS")
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-16") {
		createIndex(indexName: "CONSTRAINT_INDEX_1", tableName: "APPLICATION_USER", unique: "true") {
			column(name: "APPLICATION_NAME")
		}
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-17") {
		createIndex(indexName: "CONSTRAINT_INDEX_2", tableName: "USER", unique: "true") {
			column(name: "LOGIN_ID")
		}
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-9") {
		addForeignKeyConstraint(baseColumnNames: "USER_ID", baseTableName: "POST", baseTableSchemaName: "PUBLIC", constraintName: "FK3498A03339FBA6", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "USER", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-10") {
		addForeignKeyConstraint(baseColumnNames: "POST_ID", baseTableName: "POST_TAGS", baseTableSchemaName: "PUBLIC", constraintName: "FK7762B85824AB4F86", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "POST", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-11") {
		addForeignKeyConstraint(baseColumnNames: "TAG_ID", baseTableName: "POST_TAGS", baseTableSchemaName: "PUBLIC", constraintName: "FK7762B8583081882E", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "TAG", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-12") {
		addForeignKeyConstraint(baseColumnNames: "USER_ID", baseTableName: "PROFILE", baseTableSchemaName: "PUBLIC", constraintName: "FKED8E89A93339FBA6", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "USER", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-13") {
		addForeignKeyConstraint(baseColumnNames: "USER_ID", baseTableName: "TAG", baseTableSchemaName: "PUBLIC", constraintName: "FK1BF9A3339FBA6", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "USER", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-14") {
		addForeignKeyConstraint(baseColumnNames: "USER_FOLLOWING_ID", baseTableName: "USER_USER", baseTableSchemaName: "PUBLIC", constraintName: "FK143D5FBF8FFB7714", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "USER", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "anthony (generated)", id: "1486481218350-15") {
		addForeignKeyConstraint(baseColumnNames: "USER_ID", baseTableName: "USER_USER", baseTableSchemaName: "PUBLIC", constraintName: "FK143D5FBF3339FBA6", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "USER", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}
}
