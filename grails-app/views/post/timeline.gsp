<!doctype html>
<html lang=en>
<head>
    <title>
        Timeline for ${user.profile ? user.profile.fullName : user.loginId}
    </title>
    <meta name= "layout" content="main"/>
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
        <g:each in="${user.posts}" var="post">
            <div class="post-entry">
                <div class="post-text">${post.content}</div>
                <hub:dateFromNow class="post-date" date="${post.dateCreated}"/>
            </div>
        </g:each>
    </div>
</body>
</html>
