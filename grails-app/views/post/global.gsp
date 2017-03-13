<!doctype html>
<html lang=en>
<head>
    <title>
        All Posts
    </title>
    <meta name= "layout" content="main"/>
</head>
<body>
    <h1>Global Timeline !</h1>

    <g:if test="${flash.message}">
        <div class="alert">${flash.message}</div>
    </g:if>

    <%-- The sec:ifLoggedIn tag is provided by the Spring Security Plugin --%>
    <sec:ifLoggedIn>
        <div id="new-post">
            <h3>What is ${currentUser.profile ? currentUser.profile.fullName : currentUser.loginId}
                hacking on right now?</h3>
            ... TODO ...
        </div>
    </sec:ifLoggedIn>

    <div id="all-posts">
        <g:render template="postEntry" collection="${posts}" var="post"/>
    </div>

    <div class="pagination">
        <g:paginate action="global" total="${postCount}" max="8" />
    </div>
</body>
</html>
