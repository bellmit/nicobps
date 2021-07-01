
app.controller("CommonCtrl", [
	"$scope", "$http", "$timeout", "commonInitService",
	function ($scope, $http, $timeout, commonInitService) {
		$scope.registeringofficecode = 0;
		$scope.fee = -1;
		
		$scope.registerStakeholder = () => {
			
					jQuery("#form").submit();
//			let data = "officecode=" + $scope.registeringofficecode;
//			commonInitService.http("POST", "ulbregistration.htm", data, function (response) {
//				if(response===ALREADY_REPORTED){
//					MsgBox("Office is already registered and valid.");
//				}else if (response=='false') {
//					MsgBox("Error! Please try again.");
//				}else {
////					jQuery("#form").find("input[name='applicationcode']").val(response);
////					jQuery("#form").submit();
//				}
//			}, function () {
//				alert("Sorry, there was an error while trying to process the request.");
//			});
		},
		$scope.getOffices=(officecode)=>{
			$http.post("./getApplicationfee.htm?officecode="+officecode).success(
				function (response, status, headers, config) {
					$scope.fee = response
				});
			$http.post("./listOffices/registeringoffice.htm?registeringofficecode="+officecode).success(
				function (response, status, headers, config) {
					$scope.Offices = response
				});
		}

	},
]);