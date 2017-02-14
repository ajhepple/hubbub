<html>
    <head>
        <title>Find a Post</title>
        <r:require modules="search"/>
        <meta name="layout" content="main"/>
    </head>
    <body>
        <h1>Search</h1>
        
        <!-- Input form -->
        <g:form>
            <g:textField name="q" value="${params.q}"/>
            <g:select name="max" from="${[1,5,10,50]}" value="${params.max ?: 10}"/>
            <g:submitButton name="search" value="Search"/>
        </g:form>
        <hr/>

        <!-- Searchable suggest-query -->
        <g:if test="${searchResult?.suggestedQuery}">
        <%@ page import="grails.plugin.searchable.internal.util.StringQueryUtils" %>
        <p>
            Did you mean
            <g:link controller="search" action="search"
                    params="[q: searchResult.suggestedQuery]">
                ${raw(StringQueryUtils.highlightTermDiffs(params.q.trim(),
                        searchResult.suggestedQuery))}
            </g:link>?
        </p>
        </g:if>

        <!-- Search results -->
        <g:if test="${searchResult}">
            Displaying results
            <b>${searchResult.offset+1}-${Math.min(searchResult.offset + searchResult.max, searchResult.total)}</b> of <b>${searchResult.total}</b>:
        </g:if>
        <g:if test="${searchResult?.results}">
            <g:each var="result" in="${searchResult.results}" status="hitNum">
                <div class="search-post">
                    <div class="search-from">
                        From
                        <g:link controller="users"
                                acion="${result.user.loginId}">
                            ${result.user.loginId}
                        </g:link>
                    </div>
                    <div class="search-content">
                        ${raw(searchResult.highlights[hitNum])}
                    </div> 
                </div>
            </g:each>
        </g:if>

        <!-- Pagination of search results -->
        <g:if test="${searchResult}">
            <g:set var ="totalPages" value="${Math.ceil(searchResult.total/searchResult.max)}"/>
            <g:if test="${totalPages == 1}">
                <span class="currentStep">Page 1 of 1</span>
            </g:if>
            <g:else>
                <g:paginate controller="search" action="search" 
                        params="[q: params.q]" 
                        total="${searchResult.total}" 
                        prev="&lt; previous" next="next &gt;"/>
            </g:else>
            <p/>
        </g:if>
    </body>
</html>
