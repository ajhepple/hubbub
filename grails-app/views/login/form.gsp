<html>
<head>
    <title>Sign into Hubbub</title>
    <meta name="layout" content="main">
</head>
<body>
    <formset>
        <legend>Sign in</legend>
        <g:form action="signIn">
            <fieldset>
                <label for="loginId">Login ID</label>
                <g:textField name="loginId" class="required" value="${loginId}"/>
                <label for="password">Password</label>
                <g:passwordField name="password" class="required"/>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="signIn" value="Sign In"/>
            </fieldset>
        </g:form>
    </formset>
</body>
</html>
            
