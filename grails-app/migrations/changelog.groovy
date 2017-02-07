databaseChangeLog = {

	changeSet(author: "anthony (generated)", id: "changelog") {
		// TODO add changes and preconditions here
	}

	include file: 'baseline-schema.groovy'

	include file: 'add-twitter-to-Profile.groovy'

	include file: 'drop-twitter-from-profile.groovy'
}
