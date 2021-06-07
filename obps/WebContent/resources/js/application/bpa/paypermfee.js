/**
 * @author Decent Khongstia
 */

app.controller("CommonCtrl", [
	"$scope",
	"$http",
	"$timeout",
	"$window",
	"commonInitService",
	"bpaService",
	function ($scope, $http, $timeout, $window, CIS, BS) {
		console.log("BPA: Pay Application Fee");

		let data = "";
		$scope.serverResponseError = false;
		$scope.serverResponseFail = false;
		$scope.serverResponseInfo = false;
		$scope.serverResponseSuccess = false;

		$scope.bpa = {};
		$scope.bpa.applicationcode = APPCODE;

		$scope.Fees = [];
		$scope.PayModes = [];


		/* GET */
		BS.getBpaPermitFee((response) => {
			$scope.Fees = response;
			let len = Object.keys($scope.Fees).length;
			if (len > 0)
				$scope.bpa.amount = $scope.Fees.feeamount;
			console.log("$scope.bpa: ", $scope.bpa);
		}, $scope.bpa.applicationcode);

		BS.getOfficePaymentMode((response) => {
			$scope.PayModes = response;
		}, $scope.bpa.applicationcode);

		/* ACTION */
		$scope.clearAfterCreateProcess = () => {
			$timeout(() => {
				$scope.serverResponseError = false;
				$scope.serverResponseFail = false;
				$scope.serverResponseInfo = false;
				$scope.serverResponseSuccess = false;
				$scope.serverMsg = "";
			}, 5000);
		};

		/* CREATE */
		$scope.pay = () => {
			let data = {}, valid = false;
			data = $scope.bpa;

			valid = $window.confirm("Are you sure you want to submit?");
			if (!valid) return;

			CIS.save("POST", "./bpapaypermfee.htm", data, (success) => {
				$scope.serverMsg = success.msg;
				if (success.code == '201') {
					$scope.serverResponseSuccess = true;
					try {
						$timeout(() => {
							let url = success.nextProcess.key;
							$window.location.href = url;
						}, 4500);
					} catch (e) { }

				} else {
					$scope.serverResponseFail = true;
				}
			}, (error) => {
				try {
					$scope.serverMsg = error.msg;
				} catch (e) {
					$scope.serverMsg = "Internal server error";
				}
				$scope.serverResponseError = true;
			});

			$scope.clearAfterCreateProcess();
		};
	}
]);