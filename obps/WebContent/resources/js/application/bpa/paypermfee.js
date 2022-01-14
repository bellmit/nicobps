/**
 * @author Decent Khongstia
 */
var APPCODE;
app.controller("CommonCtrl", [
	"$scope",
	"$http",
	"$timeout",
	"$window",
	"commonInitService",
	"bpaService",
	function($scope, $http, $timeout, $window, CIS, BS) {
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

		$scope.FeeType = FeeType;
		$scope.init = (applicationcode) => {

			APPCODE = applicationcode;

		};

		/* GET */
		BS.getBpaPermitFee((response) => {
			$scope.Fees = response;
			if ($scope.Fees != null) {
				$scope.bpa.feetypecode = $scope.Fees.feetypecode;
				let len = Object.keys($scope.Fees).length;
				if (len > 0)
					$scope.bpa.amount = $scope.Fees.feeamount;
			}
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

			//			CIS.save("POST", "./bpapaypermfees.htm", data, (success) => {
			CIS.save("POST", ProcessingUrl.bpaMakePayment, data, (success) => {
				$scope.serverMsg = success.msg;
				if (success.code == '201') {
					$scope.serverMsg = "You will be redirect to a different page to complete payment.";
					$scope.serverResponseInfo = true;
					if (success.nextProcess != null && success.nextProcess.key != null && success.nextProcess.key != '') {
						try {
							$timeout(() => {
								let url = success.nextProcess.key;
								$window.location.href = url;
							}, Timeout.TwoSecond);
						} catch (e) { }
					} else {
						$time(() => $window.location.reload(), Timeout.TwoSecond);
					}

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