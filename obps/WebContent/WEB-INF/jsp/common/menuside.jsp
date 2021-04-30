<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
body {
	background-color: #fff;
	/*background-image: url('https://cdn.crunchify.com/bg.png');*/
}

.b-leftmenu ul {
	list-style: none;
	margin: 0;
	padding: 0;
}

.b-leftmenu ul li {
	/* Sub Menu */
	
}

.b-leftmenu ul li a {
	display: block;
	background: #ebebeb;
	padding: 10px 15px;
	color: #333;
	text-decoration: none;
	-webkit-transition: 0.2s linear;
	-moz-transition: 0.2s linear;
	-ms-transition: 0.2s linear;
	-o-transition: 0.2s linear;
	transition: 0.2s linear;
}

.b-leftmenu ul li a:hover {
	background: #f8f8f8;
	color: #515151;
}

.b-leftmenu ul li a .fa {
	width: 16px;
	text-align: center;
	margin-right: 5px;
	float: right;
}

.b-leftmenu ul ul {
	background-color: #ebebeb;
}

.b-leftmenu .sub-menu ul li a {
	background: #f8f8f8;
	border-left: 4px solid transparent;
	padding: 10px 25px;
}

.b-leftmenu .sub-sub-menu ul li a {
	padding: 10px 20px 10px 40px;
}

.b-leftmenu a.b-newpage:hover {
	background: #ebebeb;
	border-left: 4px solid #3498db;
}
</style>

<!-- 
<nav class="navbar navbar-expand-sm bg-dark navbar-dark sticky-top">
	<a class="navbar-brand" href="#">Logo</a>
	<ul class="navbar-nav">
		<li class="nav-item">
			<a class="nav-link" href="#">Link</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="#">Link</a>
		</li>
	</ul>
</nav> 
-->

<!-- Sidebar -->
<div class="dashboard-bgcolor border-right" id="sidebar-wrapper">
	<div class="sidebar-heading text-left text-white" style="font-size: 24px">
		<!-- <span class="fas fa-tachometer-alt"></span> &nbsp; -->
		<span class="text-uppercase">OBPS</span>
	</div>
	<div class="list-group list-group-flush b-leftmenu">

		<ul>
			<li><a href='home.htm' class="dashboard-bgcolor text-white border-bottom b-newpage">Home</a></li>
			<core:forEach  items="${menu}" var="record">
				<li class='sub-menu'>
	                <a class="gn-icon ${record.icon} dashboard-bgcolor border-bottom text-white b-newpage" 
	                	href="${(record.pageurl == null || record.pageurl=='')?'#':record.pageurl}">${record.name}
	                	<core:if test = "${record.pageurl == null || record.pageurl == ''}">
	                		<div class='fa fa-caret-down right'></div>
	                	</core:if>
	              	</a>                
	              	<core:if test = "${record.pageurl == null || record.pageurl == ''}">
		                <ul>
							<core:forEach  items="${record.suburls}" var="subrecord">
		                        <li class="sub-sub-menu">
			                        <a class="b-newpage gn-icon ${subrecord.icon}" 
			                        	href="${(subrecord.pageurl==null || subrecord.pageurl=='')?'#':subrecord.pageurl}">
			                        	${subrecord.name}
			                        	<core:if test = "${subrecord.pageurl == null || subrecord.pageurl == ''}">
					                		<div class='fa fa-caret-down right'></div>
					                	</core:if>
			                        </a>
			                        <core:if test = "${subrecord.pageurl == null || subrecord.pageurl == ''}">
				                        <ul>
											<core:forEach  items="${subrecord.suburls}" var="subsubrecord">
					                            <li>
					                                <a class="b-newpage gn-icon ${subsubrecord.icon}" 
					                                href="${(subsubrecord.pageurl=='')?'#':subsubrecord.pageurl}">${subsubrecord.name}</a>
					                            </li>
		                    				</core:forEach>
				                        </ul>
				                    </core:if>
			                    </li>
		                    </core:forEach>
		                </ul>
	                </core:if>
	            </li>
            </core:forEach>
		</ul>
		
	</div>
</div>

<!-- /#sidebar-wrapper -->
<script>
  	$('.sub-menu ul').hide();
  	$('.sub-sub-menu ul').hide();
	$(".sub-menu a").click(function () {
		$(this).parent(".sub-menu").children("ul").slideToggle("100");
		$(this).find(".right").toggleClass("fa-caret-up fa-caret-down");
	});
	$(".sub-sub-menu a").click(function () {
		$(this).parent(".sub-sub-menu").children("ul").slideToggle("100");
		$(this).find(".right").toggleClass("fa-caret-up fa-caret-down");
	});
  </script>