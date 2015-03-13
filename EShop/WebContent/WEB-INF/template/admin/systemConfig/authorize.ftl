<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.admin.edit")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<style type="text/css">
.roles label {
	width: 150px;
	display: block;
	float: left;
	padding-right: 5px;
}
</style>
<script type="text/javascript">
$().ready(function() {

});
</script>
</head>
<body>
	<form id="inputForm" action="${base}/admin/systemConfig/authorize.jhtml" method="post">
		<ul id="tab" class="tab">
			<li>
				${message("admin.unauthorizedUsageOfSoftware")}
			</li>
		</ul>
		
		<table class="input tabContent">
			<tr>
				<th>
					${message("admin.authorize.id")}:
				</th>
				<td>
					${authorizationID}
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.authorize.id")}:
				</th>
				<td>
					<input type="text" name="encryption" style="width: 500px" class="text" value="" maxlength="400" />
					
					[#if authorizeFail]
						<label for="rePassword" class="fieldError">${message("admin.authorize.wrongcode")}</label>
					[/#if]
					
				</td>
			</tr>
		</table>
		<table class="input">
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>