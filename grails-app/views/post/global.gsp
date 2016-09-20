<!doctype html>
<html lang=en>
<head>
    <title>
        All Posts
    </title>
    <meta name= "layout" content="main"/>
</head>
<body>

    <div id="all-posts">
        <g:each in="${posts}" var="post">
            <div class="post-entry">
                <img class="profile-picture" src="${createLink(controller: 'image', action: 'renderImage', 
                        id: post.user.loginId)}"/>
                <g:link action="timeline" id="${post.user.loginId}">${post.user.loginId}</g:link>
                <div class="post-text">${post.content}</div>
                <div class="post-date">${post.dateCreated}</div>
            </div>
        </g:each>
    </div>
    <div class="pagination">
        <g:paginate action="global" total="${postCount}" max="8" />
    </div>
</body>
</html>
