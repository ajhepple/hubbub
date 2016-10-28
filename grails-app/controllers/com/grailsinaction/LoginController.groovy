package com.grailsinaction

class LoginController {

    static defaultAction = 'logIn'
    
    def form(String id) {
        [loginId: id]
    }
    
    def signIn (String loginId, String password) {
        def user = User.findByLoginId(loginId)
        if (user && user.password == password) {
            session.user = user
            redirect url: "/"
        } else {
            flash.error = "Unknown username or password"
            redirect action: "form"
        }
    }
}
