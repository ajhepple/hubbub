<html>
<head>
    <title>User Profile</title>
    <meta name="layout" content="main">
</head>
<body>
    <div class="profile">
        <img class="profile-picture" src="${createLink(controller: 'image', action: 'renderImage',
                    id: profile.user.loginId)}"/>
        <p>Profile for <strong>${profile.fullName}</strong></p>
        <p>Bio: ${profile.bio}</p>
    <div>
</body>
</html>
