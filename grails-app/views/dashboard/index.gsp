<!DOCTYPE html>
<html lang="en">
<head>
<meta name="layout" content="main"/>
    <asset:stylesheet src="dashboard.css"/>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="custom.css">
    <link href="https://fonts.googleapis.com/css?family=Raleway:100,200,400,500,600" rel="stylesheet" type="text/css">
</head>

<body>
	<div class="main-section">
		<div class="dashbord">
			<div class="icon-section">
				<i class="fa fa-users" aria-hidden="true"></i><br>
				<small>People</small>
				<p>${com.hcl.Person.count() ?: 0}</p>
			</div>
			<div class="detail-section">
				<g:link controller="person" action="index" >Manage People</g:link>
			</div>
		</div>
		<div class="dashbord dashbord-green">
			<div class="icon-section">
				<i class="fa fa-user" aria-hidden="true"></i><br>
				<small>Users</small>
				<p>${com.hcl.User.count() ?: 0}</p>
			</div>
			<div class="detail-section">
				<g:link controller="user" action="index" >Manage Users</g:link>
			</div>
		</div>
		<div class="dashbord dashbord-orange">
			<div class="icon-section">
			   <i class="fa fa-unlock"></i><br>
				<small>Roles</small>
				<p>${com.hcl.Role.count() ?: 0}</p>
			</div>
			<div class="detail-section">
				<g:link controller="role" action="index" >Manage Roles</g:link>
			</div>
		</div>
		<div class="dashbord dashbord-skyblue">
            <div class="icon-section">
                <i class="fa fa-laptop" aria-hidden="true"></i><br>
                <small>Assets</small>
                <p>${com.hcl.Asset.count() ?: 0}</p>
            </div>
            <div class="detail-section">
                <g:link controller="asset" action="index" >Manage Assets</g:link>
            </div>
        </div>
		<div class="dashbord dashbord-red">
			<div class="icon-section">
				<i class="fa fa-map-marker" aria-hidden="true"></i><br>
				<small>Locations</small>
				<p>${com.hcl.Location.count() ?: 0}</p>
			</div>
			<div class="detail-section">
				<g:link controller="location" action="index" >Manage Locations</g:link>
			</div>
		</div>

		<div class="dashbord dashbord-blue">
            <div class="icon-section">
                <i class="fa fa-tasks" aria-hidden="true"></i><br>
                <small>Work Orders</small>
                <p>${com.hcl.WorkOrder.count() ?: 0}</p>
            </div>
            <div class="detail-section">
                <g:link controller="workOrder" action="index" >Work Order Tracking</g:link>
            </div>
        </div>
	</div>
</body>
</html>