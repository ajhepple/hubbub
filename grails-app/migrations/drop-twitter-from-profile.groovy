databaseChangeLog = {

	changeSet(author: "anthony (generated)", id: "1486485791370-1") {
		dropColumn(columnName: "twitter_name", tableName: "profile")
	}
}
