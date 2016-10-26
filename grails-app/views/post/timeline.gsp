<!doctype html>
<html lang=en>
<head>
    <title>
        Timeline for ${user.profile ? user.profile.fullName : user.loginId}
    </title>
    <meta name= "layout" content="main"/>
    <g:if test="${user.profile?.skin}">
        <g:external dir="css" file="${user.profile.skin}.css"/>
    </g:if>
</head>
<body>
    <div id="new-post">
        <h3>What is ${user.profile.fullName} hacking on right now?</h3>
        <g:if test="${flash.message}">
            <div class="flash">${flash.message}</div>
        </g:if>

        <p>
            <g:form action="addPost" id="${params.id}">
                <g:textArea id='post-content' name="content" rows="3" cols="50" />
                <br/>
                <g:submitButton name="post" value="Post"/>
            </g:form>
        </p>
    </div>

    <div id="all-posts">
        <g:render template="postEntry" collection="${user.posts}" var="post"/>
    </div>
</body>
</html>
