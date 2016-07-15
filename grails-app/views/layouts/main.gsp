<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title><g:layoutTitle default="Grails"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <g:external dir="images" file="apple-touch-icon-retina.png" rel="apple-touch-icon" sizes="114x114"/>
        <g:external dir="images" file="favicon.ico"/>
        <g:external dir="images" file="apple-touch-icon.png" rel="apple-touch-icon"/>
        <g:external dir="css" file="mobile.css"/>
        <g:external dir="css" file="main.css"/>
        <g:external dir="css" file="hubbub.css"/>
        <g:layoutHead/>
        <g:javascript library="application"/>		
        <r:layoutResources />
    </head>
    <body>
        <div id="hd">
            <g:link uri="/">
                <g:img id="logo" uri="/images/header-logo.png" alt="Hubbub logo"/>
            </g:link>
        </div>
        <div id="bd">
            <g:layoutBody/>
        </div>
        <div id="ft">
            <div id="footerText">Hubbub - Social Networking on Grails</div>
        </div>
        <r:layoutResources />
    </body>
</html>
