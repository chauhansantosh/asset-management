<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#list-person" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="list-person" class="col-12 content scaffold-list" role="main">
                    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
                    <g:form action="search" method="post">
                        <g:textField id="mytext" class="input-xxlarge" name="q" placeholder="Search" />
                        <button id="submit-values" class="btn btn-small btn-primary" type="submit">
                            <i class="icon-ok"></i>
                            Search
                        </button>
                    </g:form>
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <p>
                        Searched "${personInstanceTotal}" records for items matching <em>"${searchText}"</em>. Found <strong>"${personInstanceList.size()}"</strong>.
                    </p>
                    <f:table collection="${personInstanceList}" />

                    <g:if test="${personCount > params.int('max')}">
                    <div class="pagination">
                        <g:paginate total="${personCount ?: 0}" />
                    </div>
                    </g:if>
                </div>
            </section>
        </div>
    </div>
    </body>
</html>