<html>
<head>
    <title>Register New User</title>
    <meta name="layout" content="main"/>
</head>
<body>
    <h1>Register new User</h1>
    <g:hasErrors>
        <div class="errors">
            <g:renderErrors bean="${user}" as="list" />
        </div>
    </g:hasErrors>
    <g:if test="${flash.message}">
        <div class="flash">${flash.message}</div>
    </g:if>
<!-- Initial, simple form without file upload
    <g:form action="register">
        <fieldset class="form">
            <div class="input required">
                <label for="loginId">Login ID</label>
                <g:textField name="loginId" value="${user?.loginId}"/>
            </div>
            <div class="input required">
                <label for="password">Password</label>
                <g:textField name="password"/>
            </div>
            <div class="input required">
                <label for="profile.fullName">Full Name</label>
                <g:textField name="profile.fullName" value="${user?.profile?.fullName}"/>
            </div>
            <div class="input required">
                <label for="profile.bio">Bio</label>
                <g:textField name="profile.bio" value="${user?.profile?.bio}"/>
            </div>
            <div class="input required">
                <label for="profile.email">Email</label>
                <g:textField name="profile.email" value="${user?.profile?.email}"/>
            </div>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="register" value="Register"/>
        </fieldset>
    </g:form>
-->
    <!-- Listing 8.3 New registration form demonstrating core form tags -->
    <g:uploadForm action="register2">
        <fieldset class="form">
            <div class="input required">
                <label for="loginId">Login ID</label>
                <g:textField name="loginId"/>
            </div>
            <div class="input required">
                <label for="password">Password</label>
                <g:passwordField name="password"/>
            </div>
            <div class="input required">
                <label for="passwordRepeat">Password (repeat)</label>
                <g:passwordField name="passwordRepeat"/>
            </div>
            <div class="input required">
                <label for="profile.fullName">Full Name</label>
                <g:textField name="profile.fullName" value="${user?.profile?.fullName}"/>
            </div>
            <div class="input required">
                <label for="profile.bio">Biography</label>
                <g:textArea name="profile.bio" value="${user?.profile?.bio}"/>
            </div>
            <div class="input required">
                <label for="profile.email">Email</label>
                <g:textField name="profile.email" value="${user?.profile?.email}"/>
            </div>
            <div class="input required">
                <label for="country">Country</label>
                <g:countrySelect name="country"
                    noSelection="['':'Choose your country...']"/>
            </div>
            <div class="input required">
                <label for="timezone">Timezone</label>
                <g:timeZoneSelect name="timezone"/>
            </div>
            <div class="input required">
                <label for="photo">Photo</label>
                <input type="file" name="photo"/>
            </div>
            <div class="input required">
                <label for="referrer">Who introduced you to Hubbub?</label>
                <g:select name="referrer"
                    from="${com.grailsinaction.Profile.list()}"
                    optionKey="id"
                    optionValue="fullName"
                    noSelection="${['null':'Please choose...']}"/>
            </div>
            <div class="input required">
                <label for="spamMe">Spam me forever?</label>
                <g:checkBox name="spamMe" checked="true"/>
            </div>
            <div class="input required">
                <label for="emailFormat">Email Format</label>
                <g:radioGroup name="emailFormat"
                    labels="['Plain','HTML']"
                    values="['P', 'H']"
                    value="H">
                        ${it.label} ${it.radio}
                </g:radioGroup>
            </div>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="register" value="Register" />
            <g:link controller="post">Back to Hubbub</g:link>
        </fieldset>
    </g:uploadForm>
</body>
</html>
