<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="AssetManagement"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
    <asset:stylesheet src="application.css"/>
    <g:layoutHead/>
</head>
<header>
<! comment !>
<% comment %>
%{ comment }%
%{ style="background-image:url('${resource(dir: "images", file: "itam-header3.jpg")}'); width: 100%; background-repeat: repeat;background-position: center;" }%
      <nav class="navbar navbar-expand-lg navbar-dark navbar-static-top" role="navigation">
          <a class="navbar-brand" href="/#"><asset:image src="itam-logo.png" height="50px" width="50px" alt="ITAM Logo"/></a>
          <div class="navbar-brand"><h1> Asset Management </h1></div>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse" aria-expanded="false" style="height: 0.8px;" id="navbarContent">
              <ul class="nav navbar-nav ml-auto">
                  <g:pageProperty name="page.nav"/>
                  <sec:ifLoggedIn>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                          Logged in as <sec:loggedInUserInfo field='username'/>
                        </a>
                        <div class="dropdown-menu navbar-dark">
                            <g:form controller="dashboard">
                              <g:submitButton class="dropdown-item navbar-dark color-light" name="Submit" value="Dashboard" style="color:gray" />
                            </g:form>
                          <g:form controller="asset">
                            <g:submitButton class="dropdown-item navbar-dark color-light" name="Submit" value="Assets" style="color:gray" />
                          </g:form>
                          <g:form controller="location">
                            <g:submitButton class="dropdown-item navbar-dark color-light" name="Submit" value="Locations" style="color:gray" />
                          </g:form>
                          <g:form controller="workOrder">
                            <g:submitButton class="dropdown-item navbar-dark color-light" name="Submit" value="Work Order Tracking" style="color:gray" />
                          </g:form>
                          <g:form controller="user">
                            <g:submitButton class="dropdown-item navbar-dark color-light" name="Submit" value="Users" style="color:gray" />
                          </g:form>
                          <g:form controller="role">
                            <g:submitButton class="dropdown-item navbar-dark color-light" name="Submit" value="Roles" style="color:gray" />
                          </g:form>
                          <g:form controller="person">
                            <g:submitButton class="dropdown-item navbar-dark color-light" name="Submit" value="People" style="color:gray" />
                          </g:form>
                          <g:form controller="logout">
                            <g:submitButton class="dropdown-item navbar-dark color-light" name="Submit" value="Logout" style="color:gray" />
                          </g:form>
                        </div>
                    </li>
                  </sec:ifLoggedIn>
              </ul>
          </div>

      </nav>
    </header>


<div class="container">
    <g:layoutBody/>
</div>

<div id="spinner" class="spinner" style="display:none;">
    <g:message code="spinner.alt" default="Loading&hellip;"/>
</div>

<asset:javascript src="application.js"/>

</body>
</html>
