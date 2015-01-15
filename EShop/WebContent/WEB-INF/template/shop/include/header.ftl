<link href="${base}/resources/shop/css/searchbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready(function() {

	var $headerLogin = $("#headerLogin");
	var $headerRegister = $("#headerRegister");
	var $headerUsername = $("#headerUsername");
	var $headerLogout = $("#headerLogout");
	var $productSearchForm = $("#productSearchForm");
	var $keyword = $("#productSearchForm input");
	var defaultKeyword = "${message("shop.header.keyword")}";
	
	var username = getCookie("username");
	if (username != null) {
		$headerUsername.text("${message("shop.header.welcome")}, " + username).show();
		$headerLogout.show();
	} else {
		$headerLogin.show();
		$headerRegister.show();
	}
	
	$keyword.focus(function() {
		if ($keyword.val() == defaultKeyword) {
			$keyword.val("");
		}
	});
	
	$keyword.blur(function() {
		if ($keyword.val() == "") {
			$keyword.val(defaultKeyword);
		}
	});
	
	$productSearchForm.submit(function() {
		if ($.trim($keyword.val()) == "" || $keyword.val() == defaultKeyword) {
			return false;
		}
	});

});
</script>
<div class="container header">
	<div class="span10">
		<div class=" tagWrap">
			<div class="topNav floatleft">
				<li id="headerLogin" class="headerLogin">
					<a href="${base}/login.jhtml">${message("shop.header.login")}</a>
				</li>
				<li id="headerRegister" class="headerRegister">
					<a href="${base}/register.jhtml">${message("shop.header.register")}</a>
				</li>
				<li id="headerUsername" class="headerUsername"></li>
				<li id="headerLogout" class="headerLogout">
					<a href="${base}/logout.jhtml">[${message("shop.header.logout")}]</a>
				</li>					
			</div>
			<div class="topNav clearfix">
				<ul>
					<li>
		          		<a class="cart" href="${base}/cart/list.jhtml">${message("shop.header.cart")}</a>
		  			</li>
					[@navigation_list position = "top"]
						[#list navigations as navigation]
							<li>
								<a href="${base}/${navigation.url}"[#if navigation.isBlankTarget] target="_blank"[/#if]>${navigation.name}</a>
								[#if navigation_has_next]|[/#if]
							</li>
						[/#list]
					[/@navigation_list]
					
					[#if setting.phone??]
						<li class="phone">
							${message("shop.header.phone")}:
							<strong>${setting.phone}</strong>
						</li>
					[/#if]
				</ul>
			</div>
		</div>
	</div>
	<div class="span24">
		<div class="logo">
			<a href="${base}/">
				<img src="${base}/${setting.logo}" alt="${setting.siteName}" />
			</a>
		</div>
		<div class="search">
	        <form id="productSearchForm" method="get" action="/EShop/product/search.jhtml">
	            <input class="keyword inputs" maxlength="30" value="商品搜索" name="keyword">
	            <button class="submit icon-btn-search" type="submit">搜 索</button>
	        </form>
	    </div>
	    <div class="search">
			<div class="hotSearch">
				[#if setting.hotSearches?has_content]
					${message("shop.header.hotSearch")}:
					[#list setting.hotSearches as hotSearch]
						<a href="${base}/product/search.jhtml?keyword=${hotSearch?url}">${hotSearch}</a>
					[/#list]
				[/#if]
			</div>
		</div>
	</div>
	[#--
	<div class="span24">
		<ul class="mainNav">
			[@navigation_list position = "middle"]
				[#list navigations as navigation]
					<li[#if navigation.url = url] class="current"[/#if]>
						<a href="${base}/${navigation.url}"[#if navigation.isBlankTarget] target="_blank"[/#if]>${navigation.name}</a>
						[#if navigation_has_next]|[/#if]
					</li>
				[/#list]
			[/@navigation_list]
		</ul>
	</div>
	--]
	<div class="span24">
		<div class="mainNav">
			<ul class="tag">
				[@tag_list type="product" count = 9]
					[#list tags as tag]
						<li[#if tag.icon??] class="icon" style="background: url(${tag.icon}) right no-repeat;"[/#if]>
							<a href="${base}/product/list.jhtml?tagIds=${tag.id}">${tag.name}</a>
						</li>
					[/#list]
				[/@tag_list]
				
				
				[@navigation_list position = "middle"]
					[#list navigations as navigation]
						<li class="icon">
							<a href="${base}/${navigation.url}"[#if navigation.isBlankTarget] target="_blank"[/#if]>${navigation.name}</a>
							
						</li>
					[/#list]
				[/@navigation_list]
			</ul>
		</div>
	</div>
</div>