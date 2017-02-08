databaseChangeLog = {

    /**
     * An example of a custom changeset that can be used to arbitrary changes
     * to a database using Groovy and Grails code.
     * NB Using Grails domain classes is not recommended.
     */
    changeSet(author: "anthony (generated)", id: "randomise-passwords") {
        grailsChange {      // marks this migration as containing Grails code
            init {
                //arbitrary initialisation code
            }
            validate {
                // can call warn(String message) to log a warning
                // or error(String message) to stop processing 
            }
            change {
                println "Changing all passwords..."
            
                def allUsers = sql.rows("select * from users")
                println "Resetting passwords for ${allUsers.size} users"
            
                Random random = new Random(System.currentTimeMillis())
                def passwordChars = [ 'A'..'Z', 'a'..'z', '0'..'9' ].flatten()
            
                allUsers.each { user ->
                    StringBuilder randomPassword = new StringBuilder()
                    1.upto(8) { randomPassord.append(
                        passwordChars.get(random.nextIn(passwordChars.size())))
                    }
                    println "Random password is ${randomPassword} for user ${user.login_id}"
                    sql.execute "update user set password = ? where id = ?",
                            [ randomPassword.toString(), user.id ]
                }
                confirm "Finished changing passwords."
            }
            rollback {
                error "Cannot rollback password changes"
            }

            confirm 'randomise passwords changeset complete'
        }
    }
}
