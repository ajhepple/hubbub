<div class="post-entry">
    <img class="profile-picture" src="${createLink(controller: 'image', action: 'renderImage', 
                id: post.user.loginId)}"/>
    <g:link action="timeline" id="${post.user.loginId}">${post.user.loginId}</g:link>
    <div class="post-text">${post.content}</div>
    <hub:dateFromNow class="post-date" date="${post.dateCreated}"/>
</div>
