databaseChangeLog = {

	changeSet(author: "anthony (generated)", id: "1486484508070-1") {
		addColumn(tableName: "profile") {
			column(name: "twitter_id", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

        changeSet(author: "Anthony (by hand)", id: "1486484508070-2") {
                renameColumn(tableName: "profile",
                        oldColumnName: 'twitter_id', newColumnName: 'twitter_name')
        }
}
