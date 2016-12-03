package com.grailsinaction.pages

class LoginFormPage extends geb.Page {

    static url = "login/form"

    static content = {
        loginIdInputField { $("input[name='loginId']") }
        passwordInputField { $("input[name='password']") }
        submitButton { $("input[type='submit']") }
    }

    static at = {
        title.contains("Sign into Hubbub")
        $("input#signIn")
    }
}
