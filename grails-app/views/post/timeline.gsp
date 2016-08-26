<!doctype html>
<html lang=en>
<head>
    <title>
        Timeline for ${user.profile ? user.profile.fullName : user.loginId}
    </title>
    <meta name= "layout" content="main"/>
</head>
<body>
    <h1>Timeline for ${user.profile ? user.profile.fullName : user.loginId}</h1>
    <div id="all-posts">
        <g:each in="${user.posts}" var="post">
            <div class="post-entry">
                <div class="post-text">${post.content}</div>
                <div class="post-date">${post.dateCreated}</div>
            </div>
        </g:each>
    </div>
</body>
</html>
