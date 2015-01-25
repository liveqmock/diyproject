<style type="text/css">
*{margin:0;padding:0;list-style-type:none;}
a,img{border:0;}
a{color:#5e5e5e;text-decoration:none;}
a:hover{color:#990000;}
/*body{font:12px/180% Arial, Helvetica, sans-serif, "新宋体";background:url('images/bg_html.jpg') top center repeat #140a04;}*/
/* yourweb_container */
/* .yourweb_container{width:1230px;margin:0 auto;} */
.sidebar{width:950px;float:right;height:auto;background:repeat-y url('${base}/resources/shop/images/bg_line_t.jpg') #ffffff;}
.sidebar h1{font-size:16px;font-family:'微软雅黑';padding:10px 20px;}
.sidebar p{padding:10px 20px;font-size:14px;}
</style>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/jquery.tmailsider.css" />
	
	<!-- <div class="yourweb_container">
		<div style="height:60px;overflow:hidden;"></div>
		<div id="Z_RightSide" class="sidebar">
			<h1>仿天猫导航</h1>
			<p>仿淘宝天猫左侧分类导航 兼容IE6(没有折叠效果), IE7 IE8及以上 Firefox,Chrome等主流浏览器<br/>Design by 夏の寒风 QQ:490720430</p>
		</div>
	</div> --><!--yourweb_container end-->
	
	<div id="Z_TypeList" class="Z_TypeList">
		<h1 class="title">所有商品分类</h1>
		<div class="Z_MenuList">
			
			<ul>
			
			[@product_category_root_list]
				[#list productCategories as category]				
					<li class="list-item0 [#if (category_index % 2) == 0] alt[/#if]">
						<a href="${base}${category.path}"><h3>${category.name}</h3></a>
					</li>
				[/#list]
			[/@product_category_root_list]
			
			</ul>
		</div>
		<div class="Z_SubList">
			[@product_category_root_list]
				[#list productCategories as category]
				<div class="subView">
					<ul>
						<li class="subItem">
							<h3 class="subItem-hd"><a target="_blank" href="${base}${category.path}">${category.name}</a></h3>
							<p class="subItem-cat">							
								[@product_category_children_list productCategoryId = category.id count = 20]
									[#list productCategories as productCategory]
										<a  href="${base}${productCategory.path}">${productCategory.name}</a>
									[/#list]
								[/@product_category_children_list]
								</p>
						</li>
					</ul>
				</div>
				[/#list]
			[/@product_category_root_list]					
			
		</div>		
	</div>
	
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.tmailsider.js"></script>
	<script type="text/javascript">
		$('#Z_TypeList').Z_TMAIL_SIDER(
            {
            target: $('#Z_RightSide'),
            fTop: 100, // 距离顶部距离
            cTop: 100, // 滚动条滚动多少像素后开始折叠的高度
            unitHeight: 80, // 每滚动多少距离折叠一个
            autoExpan: false
            }
        );
	</script>