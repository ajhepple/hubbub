<!doctype html>
<html lang=en>
<head>
    <title>
        Timeline for ${user.profile ? user.profile.fullName : user.loginId}
    </title>
    <meta name= "layout" content="main"/>
    <g:javascript library="jquery"/>
    <g:if test="${user.profile?.skin}">
        <g:external dir="css" file="${user.profile.skin}.css"/>
    </g:if>
</head>
<body>
    <div id="new-post">
        <h3>What is ${user.profile.fullName} hacking on right now?</h3>

        <g:if test="${flash.message}">
           <p class="flash"> ${flash.message} </p>
        </g:if>

        <p>
            Synchronous form 
            <g:form action="addPost" id="${params.id}">
                <g:textArea id='post-content' name="content" rows="3" cols="50" />
                <br/>
                <g:submitButton name="post" value="Post"/>
            </g:form>
        </p>

        <p>
            AJAX form
            <g:form id="ajax-${params.id}">
                <g:textArea id="ajax-post-content" name="content" rows="3" cols="50"/><br/>
                <g:submitToRemote value="Post" id="ajax-post"
                    url="[controller: 'post', action: 'addPostAjax']"
                    update="all-posts"
                    onSuccess="clearElement('ajax-post-content')"
                    onLoading="showSpinner(true)"
                    onComplete="showSpinner(false)"/>
                <g:img id="spinner" style="display:none" uri="/images/spinner.gif"/>

                <br/>
                <a href="#" id="show-hide-url" onclick="$('#tiny-url').slideToggle(300); return true;">
                    Show/Hide Tiny URL bar
                </a>

            </g:form>

            <div id="tiny-url" style="display:none;">
                <g:formRemote name="tinyUrlForm" url="[action: 'tinyUrl']" onSuccess="addTinyUrl(data);">
                    Tiny URL: <g:textField name="fullUrl"/>
                    <g:submitButton name="submit" value="Make Tiny"/>
                </g:formRemote>
            </div>

        </p>
    </div>

    <div id="all-posts">
        <g:render template="postEntry" collection="${user.posts}" var="post"/>
    </div>
    <g:javascript>
        function clearElement(e) { $('#' + e).val(''); }
        function showSpinner(visible) {
            if (visible) $('#spinner').show();
            else $('#spinner').hide();
        }
        function addTinyUrl(data) {
            var post = $("#ajax-post-content");
            post.val(post.val() + data.urls.small);
            $("#tiny-url input[name='fullUrl']").val('');
        }
    </g:javascript>
</body>
</html>
