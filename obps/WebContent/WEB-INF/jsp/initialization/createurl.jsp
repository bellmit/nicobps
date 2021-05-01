<html>
	<head>
		<title>OBPS | Create User</title>
		<%@include file="../common/headerfiles.jsp" %>     		
		<script src="resources/js/util/sha256.min.js"></script>
		<style>
			.iconSelection{
				border: 1px solid black; 
				width: 100%; 
				height: 200px; 
				overflow: hidden;overflow-y:scroll; 
				background-color: whitesmoke;
				position: absolute;
				left:0;
				z-index:10;
			}
			.hide{
				display:none;
			}
			.table-condensed td{
				padding-bottom:10px;
			}
		</style> 
	</head>
	<body>
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp" %>  		   
	    <div id="page-content-wrapper">	
		  <%@include file="../common/menutop.jsp" %>     
	      <div class="container-fluid">
	        <h3 class="mt-4" style="font-size:32px;">Create URL</h3>
	        
	        <div class="row" ng-app="CommonApp">	         	
	        	<div class="col-md-12 py-12 px-12">
	        		<div class='containerBody' id="pageurlCtrl" ng-controller="pageurlCtrl">		
						<div style='width: 80%; margin: 50px auto 50px auto;'>
							<form>
								<table class="table-condensed" width="70%" style="margin: 0 auto">
									<tr>
										<td class="title">Menu Header<span class="mandatory ">*</span></td>
										<td style="width:500px">
											<select name="parent" ng-model="url.parent"
											ng-change="getSubmenu();" style="background-color:beige;width:44%">
												<option value="">--Select--</option>
												<option ng-selected="head.key == url.parent"
													ng-repeat='head in header track by $index' ng-value="head.key">
													{{head.key}}</option>
											</select> 
											<input type="text" name="parentNew" ng-model="url.parent" placeholder="New Menu Header" style="width:45%"/>   
											<div style="display:inline-block;position: relative;width:100%">
												<div style="display:flex;flex-direction: row;">
													<div style='border:1px solid black;border-radius:10px;text-align:center;width:20%;padding: 5px;' ng-click="parenticonPressed=true">
														<span style="color:grey" ng-if="!url.parenticon">Icon</span>
														<input style="width:auto;"	type="hidden" readonly="readonly" name="parenticon" ng-model="url.parenticon" placeholder='Icon'/><a class="fa {{url.parenticon}}"></a>
													</div>
													<input  style='width:auto;width:70%'   
														type="number" placeholder="Order" ng-model="url.parentorder"/>
												</div>
												<div class="iconSelection" ng-class="{'hide':parenticonPressed!=true}">
														<button ng-click="parenticonPressed=false" style="position:absolute;right:0">Close</button>
													<div style="padding:20px;width: 100%; display: grid; grid-gap: 1rem; grid-template-columns: repeat(auto-fit, minmax(20px, 40px));">
														<div ng-repeat="i in icons">
															<a class="fa {{i}}" ng-click="url.parenticon=i"></a>
														</div>
													</div>
												</div>														
											</div>
										</td>
									</tr>
									<tr>
										<td class="title">Sub Menu</td>
										<td>
												<select name="submenu" ng-model="url.submenu" style="background-color:beige;width:44%">
														<option value="">--Select--</option>
														<option ng-selected="head.submenu == url.submenu"
															ng-repeat='head in submenu track by $index'
															ng-value="head.submenu">{{head.submenu}}</option>
												</select> 
												<input type="text" ng-model="url.submenu" name="submenuNew" placeholder="New Submenu" style="width:45%"/> 
												<div style="display:inline-block;position: relative;width:100%">
													<div style="display:flex;flex-direction: row;">
														<div style='border:1px solid black;border-radius:10px;text-align:center;width:20%;padding: 5px;' ng-click="submenuiconPressed=true">
															<span style="color:grey" ng-if="!url.submenuicon">Icon</span>
															<input style="width:auto;" type="hidden" placeholder='Icon' readonly="readonly" name="submenuicon" ng-model="url.submenuicon" ng-click="submenuiconPressed=true"/><a class="fa {{url.submenuicon}}"></a>
														</div>
														<input  style='width:auto;width:70%' 
															type="number" placeholder="Order" ng-model="url.submenuorder"/>
													</div>
													<div class="iconSelection" ng-class="{'hide':submenuiconPressed!=true}">
															<button ng-click="submenuiconPressed=false" style="position:absolute;right:0">Close</button>
														<div style="padding:20px;width: 100%; display: grid; grid-gap: 1rem; grid-template-columns: repeat(auto-fit, minmax(20px, 40px));">
															<div ng-repeat="i in icons">
																<a class="fa {{i}}" ng-click="url.submenuicon=i"></a>
															</div>
														</div>
													</div>														
												</div>
										</td>
									</tr>
									<tr>
										<td class="title">Sub sub Menu</td>
										<td>
											<input type="text" ng-model="url.subsubmenu" name="subsubmenuNew" placeholder="New Submenu" style="width:90%"/> 
											<div style="display:inline-block;position: relative;width:100%">
												<div style="display:flex;flex-direction: row;">
													<div style='width:20%;border:1px solid black;border-radius:10px;text-align:center;padding: 5px;' ng-click="subsubmenuiconPressed=true">
														<span style="color:grey" ng-if="!url.subsubmenuicon">Icon</span>
														<input style="width:auto;" type="hidden" placeholder='Icon' readonly="readonly" name="subsubmenuicon" ng-model="url.subsubmenuicon" ng-click="subsubmenuiconPressed=true"/><a class="fa {{url.subsubmenuicon}}"></a>
													</div>
													<input  style='width:auto;width:70%' 
														type="number" placeholder="Order" ng-model="url.subsubmenuorder"/>
												</div>
												<div class="iconSelection" ng-class="{'hide':subsubmenuiconPressed!=true}">
														<button ng-click="subsubmenuiconPressed=false" style="position:absolute;right:0">Close</button>
													<div style="padding:20px;width: 100%; display: grid; grid-gap: 1rem; grid-template-columns: repeat(auto-fit, minmax(20px, 40px));">
														<div ng-repeat="i in icons">
															<a class="fa {{i}}" ng-click="url.subsubmenuicon=i"></a>
														</div>
													</div>
												</div>														
											</div>
										</td>
									</tr>
				
									<tr>
										<td class="title">Page URL<span class="mandatory ">*</span></td>
										<td><input type="text" name="pageurl" ng-model="url.pageurl" style="width:90%"/> 
										</td>
										<td>&nbsp;<span id="errmsgpageurl" class="mandatory "></span></td>
									</tr>
				
									<tr>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td colspan="3" align="center">(<span class="mandatory ">*</span>)
											specify mandatory fields <input name="add" type="button"
											value="Save" id="add" class="btn btn-primary" ng-click="save()" />
				
											<input name="reset" type="button" value="RESET" id="reset"
											class="btn btn-default" ng-click="reset()" />
										</td>
				
									</tr>
								</table>
							</form>
						</div>
						<div
							style='width: 80%; max-height: 400px; overflow-y: scroll; margin: 100px auto 50px auto;'>
							<table id="displayRecordsTable" width="100%" cellspacing="0"
								border="1">
								<thead>
									<tr>
										<th>Url Code</th>
										<th>Menu Header</th>
										<th>Sub Menu</th>
										<th>Sub sub Menu</th>
										<th>Page URL</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat='url in URLs track by $index'>
										<td style='width: 10%'>{{url.urlcode}}</td>
										<td style='width: 10%'>{{url.parent}}</td>
										<td style='width: 10%'>{{url.submenu}}</td>
										<td style='width: 10%'>{{url.subsubmenu}}</td>
										<td style='width: 10%'>{{url.pageurl}}</td>
										<td style='width: 10%'><button style="padding: 5px"
												value="Edit" ng-click="edit($index)">Edit</button></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
	        	</div>			        	       	               	
	        </div>           
	             
	      </div>	      
	    </div> 	    
	  </div>
	</body>	
  <script src="resources/js/application/models/initializations.js"></script>	 
  <script src="resources/js/application/initialization/pageurls.js"></script>
</html>