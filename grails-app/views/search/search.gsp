<html>
    <head>
        <title>Find a Post</title>
        <r:require modules="search"/>
        <meta name="layout" content="main"/>
    </head>
    <body>
        <h1>Search</h1>
        <g:form>
            <g:textField name="q" value="${params.q}"/>
            <g:select name="max" from="${[1,5,10,50]}" value="${params.max ?: 10}"/>
            <g:submitButton name="search" value="Search"/>
        </g:form>
        <hr/>
        <g:if test="${searchResult}">
            Displaying results
            <b>${searchResult.offset+1}-${Math.min(searchResult.offset + searchResult.max, searchResult.total)}</b> of <b>${searchResult.total}</b>:
            <g:set var ="totalPages" value="${Math.ceil(searchResult.total/searchResult.max)}"/>
            <g:if test="${totalPages == 1}">
                <span class="currentStep">1</span>
            </g:if>
            <g:else>
                %{-- <g:paginate controller="search" action="search" 
                        params="[q: params.q]" 
                        total="${searchResult.total}" 
                        prev="&lt; previous" next="next &gt;"/> --}%
            </g:else>
            <p/>
        </g:if>
        <g:if test="${searchResult?.results}">
            <g:each var="result" in="${searchResult.results}">
                <div class="search-post">
                    <div class="search-from">
                        From
                        <g:link controller="users"
                                acion="${result.user.loginId}">
                            ${result.user.loginId}
                        </g:link>
                    </div>
                    <div class="search-content">
                        ${result.content}
                    </div> 
                </div>
            </g:each>
        </g:if>
    </body>
</html>
