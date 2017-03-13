package com.grailsinaction.pages

class LoginPage extends geb.Page {

    static url = "login"

    static content = {
        loginIdInputField { $("input[name='j_username']") }
        passwordInputField { $("input[name='j_password']") }
        submitButton { $("input[type='submit']") }
    }

    static at = {
        title.contains("Sign into Hubbub")
        $("input#signIn")
    }
}
