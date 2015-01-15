[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.main.title")}</title>

<link rel="stylesheet" type="text/css" href="${base}/resources/admin2.0/js/jquery-easyui-1.3.2/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${base}/resources/admin2.0/js/jquery-easyui-1.3.2/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${base}/resources/admin2.0/css/base.css">
<!-- ** Javascript ** -->
<script type="text/javascript" src="${base}/resources/admin2.0/js/commons/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resources/admin2.0/js/commons/jquery.form.js"></script>
<script type="text/javascript" src="${base}/resources/admin2.0/js/commons/package.js"></script>
<script type="text/javascript" src="${base}/resources/admin2.0/js/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${base}/resources/admin2.0/js/commons/urls.js?v=11"></script>
<script type="text/javascript" src="${base}/resources/admin2.0/js/commons/base.js?v=11"></script>
<script type="text/javascript" src="${base}/resources/admin2.0/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${base}/resources/admin2.0/js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>

<link href="${base}/resources/admin2.0/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin2.0/js/main.js"></script>
<style type="text/css">
*{
	font: 12px tahoma, Arial, Verdana, sans-serif;
}
html, body {
	width: 100%;
	height: 100%;
	overflow: hidden;
}
.ui-login-info a{
	padding-left: 10px;
}
</style>
<script type="text/javascript">
$().ready(function() {
	$('#editprofile').bind('click',jeecg.main.treeSelect);
});
</script>
</head>
<body class="easyui-layout">
 	<div class="ui-header" data-options="region:'north',split:true,border:false" style="height:40px;overflow: hidden;">
 	<h1>${systemName} 后台管理系统</h1>
 	<div  class="ui-login">
 		[#--<div class="ui-login-info">
	 		欢迎 <span class="orange">${user.nickName}</span> 第[<span class="orange">${user.loginCount}</span>]次登录 
	 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 		<a class="modify-pwd-btn"  href="javascript:void(0);">修改密码</a> |
 			<a class="logout-btn" href="<%=basePath%>/logout.shtml">退出</a>
 		</div>--]
 		
 		<div class="ui-login-info">
			<strong>[@shiro.principal /]</strong>
			${message("admin.main.hello")}!
			<a href="../profile/edit.jhtml" id="editprofile">${message("admin.main.profile")}</a>
			<a href="../logout.jsp" target="_top">${message("admin.main.logout")}</a>
		</div>
 	</div>
 	</div>
	<!-- 树形菜单 -->
	<div data-options="region:'west',split:true,title:'功能导航'" style="width:200px;">
	
		
		<div id="tree-box" class="easyui-accordion" data-options="fit:true,border:false">
			
			<!-- 商品管理 -->
			[#list ["admin:product", "admin:productCategory", "admin:parameterGroup", "admin:attribute", "admin:specification", "admin:brand", "admin:productNotify"] as permission]
				[@shiro.hasPermission name = permission]
					<div title="${message("admin.main.productNav")}">				
						
						[@shiro.hasPermission name="admin:product"]
						
						<a href="../product/list.jhtml" class="menu-item" >${message("admin.main.product")}</a>
					
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:productCategory"]
							
								<a href="../product_category/list.jhtml" class="menu-item" >${message("admin.main.productCategory")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:parameterGroup"]
							
								<a href="../parameter_group/list.jhtml" class="menu-item" >${message("admin.main.parameterGroup")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:attribute"]
							
								<a href="../attribute/list.jhtml" class="menu-item" >${message("admin.main.attribute")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:specification"]
							
								<a href="../specification/list.jhtml" class="menu-item" >${message("admin.main.specification")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:brand"]
							
								<a href="../brand/list.jhtml" class="menu-item" >${message("admin.main.brand")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:productNotify"]
							
								<a href="../product_notify/list.jhtml" class="menu-item" >${message("admin.main.productNotify")}</a>
							
						[/@shiro.hasPermission]
					</div>
					[#break /]
				[/@shiro.hasPermission]
			[/#list]
			
			
			<!-- 订单管理 -->
			[#list ["admin:order", "admin:payment", "admin:refunds", "admin:shipping", "admin:returns", "admin:deliveryCenter", "admin:deliveryTemplate"] as permission]
				[@shiro.hasPermission name = permission]
					<div title="${message("admin.main.orderNav")}">				
						[@shiro.hasPermission name="admin:order"]
						
							<a href="../order/list.jhtml" class="menu-item" >${message("admin.main.order")}</a>
						
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:payment"]
							
								<a href="../payment/list.jhtml" class="menu-item" >${message("admin.main.payment")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:refunds"]
							
								<a href="../refunds/list.jhtml" class="menu-item" >${message("admin.main.refunds")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:shipping"]
							
								<a href="../shipping/list.jhtml" class="menu-item" >${message("admin.main.shipping")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:returns"]
							
								<a href="../returns/list.jhtml" class="menu-item" >${message("admin.main.returns")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:deliveryCenter"]
							
								<a href="../delivery_center/list.jhtml" class="menu-item" >${message("admin.main.deliveryCenter")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:deliveryTemplate"]
							
								<a href="../delivery_template/list.jhtml" class="menu-item" >${message("admin.main.deliveryTemplate")}</a>
							
						[/@shiro.hasPermission]
					</div>
					[#break /]
				[/@shiro.hasPermission]
			[/#list]
			
			<!-- 会员管理 -->
			[#list ["admin:member", "admin:memberRank", "admin:memberAttribute", "admin:review", "admin:consultation"] as permission]
				[@shiro.hasPermission name = permission]
					<div title='${message("admin.main.memberNav")}'>
						[@shiro.hasPermission name="admin:member"]
						
							<a href="../member/list.jhtml" class="menu-item" >${message("admin.main.member")}</a>
						
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:memberRank"]
							
								<a href="../member_rank/list.jhtml" class="menu-item" >${message("admin.main.memberRank")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:memberAttribute"]
							
								<a href="../member_attribute/list.jhtml" class="menu-item" >${message("admin.main.memberAttribute")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:review"]
							
								<a href="../review/list.jhtml" class="menu-item" >${message("admin.main.review")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:consultation"]
							
								<a href="../consultation/list.jhtml" class="menu-item" >${message("admin.main.consultation")}</a>
							
						[/@shiro.hasPermission]
					</div>
					[#break /]
				[/@shiro.hasPermission]
			[/#list]
			
			<!-- 内容管理  -->
			[#list ["admin:navigation", "admin:article", "admin:articleCategory", "admin:tag", "admin:friendLink", "admin:adPosition", "admin:ad", "admin:template", "admin:cache", "admin:static", "admin:index"] as permission]
				[@shiro.hasPermission name = permission]
					<div title="${message("admin.main.contentNav")}">
						[@shiro.hasPermission name="admin:navigation"]
						
							<a href="../navigation/list.jhtml" class="menu-item" >${message("admin.main.navigation")}</a>
						
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:article"]
							
								<a href="../article/list.jhtml" class="menu-item" >${message("admin.main.article")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:articleCategory"]
							
								<a href="../article_category/list.jhtml" class="menu-item" >${message("admin.main.articleCategory")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:tag"]
							
								<a href="../tag/list.jhtml" class="menu-item" >${message("admin.main.tag")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:friendLink"]
							
								<a href="../friend_link/list.jhtml" class="menu-item" >${message("admin.main.friendLink")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:adPosition"]
							
								<a href="../ad_position/list.jhtml" class="menu-item" >${message("admin.main.adPosition")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:ad"]
							
								<a href="../ad/list.jhtml" class="menu-item" >${message("admin.main.ad")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:template"]
							
								<a href="../template/list.jhtml" class="menu-item" >${message("admin.main.template")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:cache"]
							
								<a href="../cache/clear.jhtml" class="menu-item" >${message("admin.main.cache")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:static"]
							
								<a href="../static/build.jhtml" class="menu-item" >${message("admin.main.static")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:index"]
							
								<a href="../index/build.jhtml" class="menu-item" >${message("admin.main.index")}</a>
							
						[/@shiro.hasPermission]
					</div>
					[#break /]
				[/@shiro.hasPermission]
			[/#list]
			
			<!-- 市场营销 -->
			[#list ["admin:promotion", "admin:coupon", "admin:seo", "admin:sitemap"] as permission]
				[@shiro.hasPermission name = permission]
					<div title="${message("admin.main.marketingNav")}">
						[@shiro.hasPermission name="admin:promotion"]
						
							<a href="../promotion/list.jhtml" class="menu-item" >${message("admin.main.promotion")}</a>
						
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:coupon"]
							
								<a href="../coupon/list.jhtml" class="menu-item" >${message("admin.main.coupon")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:seo"]
							
								<a href="../seo/list.jhtml" class="menu-item" >${message("admin.main.seo")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:sitemap"]
							
								<a href="../sitemap/build.jhtml" class="menu-item" >${message("admin.main.sitemap")}</a>
							
						[/@shiro.hasPermission]
					</div>
					[#break /]
				[/@shiro.hasPermission]
			[/#list]
			
			<!-- 统计管理 -->
			[#list ["admin:statistics", "admin:sales", "admin:salesRanking", "admin:purchaseRanking", "admin:deposit"] as permission]
				[@shiro.hasPermission name = permission]
					<div title="${message("admin.main.statisticsNav")}">
						[@shiro.hasPermission name="admin:statistics"]
						
							<a href="../statistics/view.jhtml" class="menu-item" >${message("admin.main.statistics")}</a>
						
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:statistics"]
							
								<a href="../statistics/setting.jhtml" class="menu-item" >${message("admin.main.statisticsSetting")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:sales"]
							
								<a href="../sales/view.jhtml" class="menu-item" >${message("admin.main.sales")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:salesRanking"]
							
								<a href="../sales_ranking/list.jhtml" class="menu-item" >${message("admin.main.salesRanking")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:purchaseRanking"]
							
								<a href="../purchase_ranking/list.jhtml" class="menu-item" >${message("admin.main.purchaseRanking")}</a>
							
						[/@shiro.hasPermission]
							[@shiro.hasPermission name="admin:deposit"]
							
								<a href="../deposit/list.jhtml" class="menu-item" >${message("admin.main.deposit")}</a>
							
						[/@shiro.hasPermission]
					</div>
					[#break /]
				[/@shiro.hasPermission]
			[/#list]
			[#list ["admin:setting", "admin:area", "admin:paymentMethod", "admin:shippingMethod", "admin:deliveryCorp", "admin:paymentPlugin", "admin:storagePlugin", "admin:admin", "admin:role", "admin:message", "admin:log"] as permission]
				[@shiro.hasPermission name = permission]
					<div title="${message("admin.main.systemNav")}">
						[@shiro.hasPermission name="admin:setting"]
						
							<a href="../setting/edit.jhtml" class="menu-item" >${message("admin.main.setting")}</a>
						
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:area"]
							
								<a href="../area/list.jhtml" class="menu-item" >${message("admin.main.area")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:paymentMethod"]
							
								<a href="../payment_method/list.jhtml" class="menu-item" >${message("admin.main.paymentMethod")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:shippingMethod"]
							
								<a href="../shipping_method/list.jhtml" class="menu-item" >${message("admin.main.shippingMethod")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:deliveryCorp"]
							
								<a href="../delivery_corp/list.jhtml" class="menu-item" >${message("admin.main.deliveryCorp")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:paymentPlugin"]
							
								<a href="../payment_plugin/list.jhtml" class="menu-item" >${message("admin.main.paymentPlugin")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:storagePlugin"]
							
								<a href="../storage_plugin/list.jhtml" class="menu-item" >${message("admin.main.storagePlugin")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:admin"]
							
								<a href="../admin/list.jhtml" class="menu-item" >${message("admin.main.admin")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:role"]
							
								<a href="../role/list.jhtml" class="menu-item" >${message("admin.main.role")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:message"]
							
								<a href="../message/send.jhtml" class="menu-item" >${message("admin.main.send")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:message"]
							
								<a href="../message/list.jhtml" class="menu-item" >${message("admin.main.message")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:message"]
							
								<a href="../message/draft.jhtml" class="menu-item" >${message("admin.main.draft")}</a>
							
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:log"]
							
								<a href="../log/list.jhtml" class="menu-item" >${message("admin.main.log")}</a>
							
						[/@shiro.hasPermission]
					</div>
					[#break /]
				[/@shiro.hasPermission]
			[/#list]			
		</div>
	
	
	
	</div>
	<div data-options="region:'south',split:true,border:false" style="height: 30px;overflow:hidden;">
		<div class="panel-header" style="border: none;text-align: center;" >CopyRight &copy; 2013 EShop 版权所有. &nbsp;&nbsp;官方网址: www.eshop.org</div>
	</div>
	<!-- 中间内容页面 -->
	<div data-options="region:'center'" >
		<div class="easyui-tabs" id="tab-box" 
			data-options="fit:true,border:false">
				<div title="欢迎" href="${base}/upload/document/manual.htm"></div>
		</div>	
	</div>
  </body>
</html>