<%@ page import="com.grailsinaction.Profile"%>
<html>
<head>
    <title>Search Hubbub</title>
    <meta name="layout" content="main"/>
</head>

<body>
    <formset>
        <g:form action="advancedSearchResults">
            <table>
                <tr><th colspan=2><legend>Advanced Search for Friends</legend></th</tr>
<!--
                <g:each var="field" in="${Profile.metaClass.properties*.name}">
                    <tr>
                        <td><label for="${field}">${field}</label</td>
                        <td><g:textField name="${field}"/></td>
                    </tr>
                </g:each>
-->
                <tr>
                    <td><label for="name">Name</label></td>
                    <td><g:textField name="fullName"/></td>
                </tr>
                <tr>
                    <td><label for="email">Email</label></td>
                    <td><g:textField name="email"/></td>
                </tr>
                <tr>
                    <td><label for="homepage">Homepage</label></td>
                    <td><g:textField name="homepage"/></td>
                </tr>
                <tr>
                    <td><label for="queryType">Query Type</label></td>
                    <td>
                        <g:radioGroup name="queryType"
                                labels="['And', 'Or', 'Not']"
                                values="['and', 'or', 'not']"
                                value="and">
                            ${it.radio} ${it.label}
                        </g:radioGroup>
                    </td>
                </tr>
                <tr>
                    <td/>
                    <td><g:submitButton name="search" value="Search"/></td>
                </tr>
            </table>
        </g:form>
    </formset>
</body>
</html>
