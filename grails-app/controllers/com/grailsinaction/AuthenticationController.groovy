package com.grailsinaction

class AuthenticationController {

//    static defaultAction = 'form'
    
    def form(String id) {
        [loginId: id]
    }
    
//    def signIn (String loginId, String password) {
//        def user = User.findByLoginId(loginId)
//        if (user && user.password == password) {
//            session.user = user
//            redirect url: "/"
//        } else {
//            flash.error = "Unknown username or password"
//            redirect action: "form"
//        }
//    }

    def signOut () {
        redirect(uri: '/j_spring_security_logout')
    }
}
