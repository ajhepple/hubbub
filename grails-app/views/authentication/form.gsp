<html>
<head>
    <title>Sign into Hubbub</title>
    <meta name="layout" content="main">
</head>
<body>
    <formset>
        <legend>Sign in</legend>
        <g:form uri="/j_spring_security_check" method="POST">
            <fieldset class="form">
                <div class="fieldcontain required">
                    <label for="j_username">Login ID</label>
                    <g:textField name="j_username" class="required" value="${loginId}"/>
                </div>
                <div class="fieldcontain required">
                    <label for="j_password">Password</label>
                    <g:passwordField name="j_password" class="required"/>
                </div>
                <div class="fieldcontain required">
                    <label for="_spring_security_remember_me">Remember me</label>
                    <g:checkBox name="_spring_security_remember_me"/>
                </div>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="signIn" value="Sign In"/>
            </fieldset>
        </g:form>
    </formset>
</body>
</html>
            
