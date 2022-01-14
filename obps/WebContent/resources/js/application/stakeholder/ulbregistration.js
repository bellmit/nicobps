
app.controller("CommonCtrl", [
	"$scope", "$http", "$timeout", "commonInitService",
	function($scope, $http, $timeout, commonInitService) {
		$scope.registeringofficecode = 0;
		$scope.fee = -1;
		$scope.ifError = false;
		$scope.errorMsg = [];

		$scope.registerStakeholder = () => {
			jQuery("#form").submit();
		},

			$scope.getOffices = (officecode) => {
				commonInitService.http("POST", "./getLicenseValidity.htm", "officecode=" + officecode, (response) => {
					$scope.errorMapList = response;
					$scope.errorMsg = [];
					jQuery('#registerbtn').prop('disabled', false);
					jQuery('#registerbtn').html('Register');
					if (response["EXPIRED_LICENCE"]) {
						$scope.ifError = true;
						$scope.errorMsg.push(response["EXPIRED_LICENCE"]);
						$timeout(() => {jQuery('#registerbtn').html('Renew');},0);
					}
					for (var key in response) {
						$scope.ifError = true;
						$scope.errorMsg.push(response[key]);
						$timeout(() => {jQuery('#registerbtn').prop('disabled', true);},0);
					}
				});
				$http.post("./getApplicationfee.htm?officecode=" + officecode).then(
					function(response, status, headers, config) {
						$scope.fee = response.data;
					});
				$http.post("./listOffices/registeringoffice.htm?registeringofficecode=" + officecode).then(
					function(response, status, headers, config) {
						$scope.Offices = response.data;
					});
			}
	}
]);