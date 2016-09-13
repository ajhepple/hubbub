<html>
<head>
    <title>Search Results</title>
    <meta name="layout" content="main"/>
</head>
<body>
    <h1>Results</h1>
    <p>
        Searched ${totalUsers} records for items containing the term <em>${term}</em>.
        Found <strong>${users.size()}</strong> results.
    </p>
    <ul>
        <g:each var="user" in="${users}">
            <li>
                <g:link action="show" id="${user.id}">${user.loginId}</g:link>
                <g:if test="${user.profile}">
                    (<g:link action="profile" id="${user.loginId}">profile</g:link>)
                </g:if>
            </li>
        </g:each>
    </ul>
    <g:link action='search'>Search Again</g:link>
</body>
</html>
