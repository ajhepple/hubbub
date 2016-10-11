<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Hubbub &raquo; <g:layoutTitle default="Welcome"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <g:external dir="images" file="apple-touch-icon-retina.png" rel="apple-touch-icon" sizes="114x114"/>
        <g:external dir="images" file="favicon.ico"/>
        <g:external dir="images" file="apple-touch-icon.png" rel="apple-touch-icon"/>
        <g:external dir="css" file="mobile.css"/>
        <g:external dir="css" file="main.css"/>
        <g:external dir="css" file="hubbub.css"/>
        <g:layoutHead/>
        <g:javascript library="application"/>		
        <r:require modules="bootstrap"/>
        <r:layoutResources />
    </head>
    <body>
        <div id="hd">
            <g:link uri="/">
                <g:img id="logo" uri="/images/header-logo.png" alt="Hubbub logo"/>
            </g:link>
        </div>

        <div class="masthead">
            <div class="container">
                <nav class="masthead-nav">
                    <a class="nav-item active" href="#">My Timeline</a>
                    <a class="nav-item" href="#">Global Timeline</a>
                    <a class="nav-item" href="#">Search</a>
                    <a class="nav-item" href="#">Advanced Search</a>
                    <a class="nav-item" href="#">Register</a>
                </nav>
            </div>
        </div>

        <div id="bd">
            <div class="row">
                <div class="col-sm-8">
                    <g:layoutBody/>
                </div>
                <div class="col-sm-3 col-sm-offset-1 sidebar">
                    <div class="sidebar-module-inset">
                        Sidebar 1 (inset)
                    </div>
                    <div class="sidebar-module">
                        Sidebar 2
                    </div>
                    <div class="sidebar-module">
                        Sidebar 3
                    </div>
                </div>
            </div>
        </div>

        <div id="ft">
            <div id="footerText">Hubbub - Social Networking on Grails
                <p><small>
                    Version <g:meta name="app.version"/>
                    on Grails <g:meta name="app.grails.version"/>
                    <!-- change app version using $> grail set-version <version> -->
                </small></p>
            </div>
        </div>
        <r:layoutResources />
    </body>
</html>
