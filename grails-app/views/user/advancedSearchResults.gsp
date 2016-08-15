<html>
<head>
    <title>Advanced Search Results</title>
    <meta name="layout" content="main"/>
</head>
<body>
    <h1>Results</h1>
    <p>
        Searched for items matching <em>${term}</em>. 
        Found <strong>${profiles.size()}</strong> matches.
    </p>
    <ul>
        <g:each var="profile" in="${profiles}">
            <li>
                <g:link action="show" id="${profile.user.id}">
                    ${profile.fullName}
                </g:link>
            </li>
        </g:each>
    </ul>
    <g:link action='advancedSearch'>Search Again</g:link>
</body>
</html>
