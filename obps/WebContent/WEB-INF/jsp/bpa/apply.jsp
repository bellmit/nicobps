<html>
	<head>
		<title>OBPS</title>
		<%@include file="../common/headerfiles.jsp"%>
		<style type="text/css">
			.custom-modal-dialog{
                width: 80%;
                margin: auto;
                max-width: 80%;
                
            }
            .custom-modal-dim{
                top: 20px;
                padding-bottom: 20px;
                margin: auto;
                /*height: 90%;*/
                /*overflow-y: scroll;*/
                width: 100%;
            }
		</style>
		<script type="text/javascript">
        </script>
	</head>
	<body ng-app="CommonApp" ng-controller="CommonCtrl">
<!-- 	<body> -->
		<div class="d-flex" id="wrapper">
			<%@include file="../common/menuside.jsp"%>
			<div id="page-content-wrapper">
				<%@include file="../common/menutop.jsp"%>
				<div class="container-fluid">
					<h3 class="mt-4" style="font-size: 32px; border-bottom: 3px solid #005776">
						Apply for building permit
					</h3>
					<form id="msform" name="bpaform">
						<div ng-include="'bpaform.htm'"></div>
                   	</form>
				</div>
			</div>
		</div>
	</body>
	<script src="resources/js/util/ngdirectives.js" type="text/javascript"></script>
	<script src="resources/js/application/models/bpa.js" type="text/javascript"></script>
	<script src="resources/js/commons/bpaService.js" type="text/javascript"></script>
	<script src="resources/js/application/bpa/apply.js" type="text/javascript"></script>
	<script type="text/javascript">
		const EDCRNUMBER = '${edcrnumber}';
		console.log("EDCRNUMBER: ",EDCRNUMBER);
	</script>
</html>