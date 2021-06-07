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
		console.log("BPA: Site Inspection");

		let data = "";
		$scope.serverResponseError = false;
		$scope.serverResponseFail = false;
		$scope.serverResponseInfo = false;
		$scope.serverResponseSuccess = false;

		$scope.bpa = new SiteInspection();
		$scope.bpa.applicationcode = APPCODE;

		/*GET*/
		$http.get('./listNextProcess.htm?applicationcode=' + $scope.bpa.applicationcode).then((response) => {
			console.log(response);
		}, (errResponse) => {
			console.error(response);
		});

		/*ACTION*/
		$scope.clearAfterCreateProcess = () => {
			$timeout(() => {
				$scope.serverResponseError = false;
				$scope.serverResponseFail = false;
				$scope.serverResponseInfo = false;
				$scope.serverResponseSuccess = false;
				$scope.serverMsg = "";
			}, 5000);
		};

		$scope.validateForm = () => {
			let status = true;
			let form = $scope.bpa;
			console.log("form: ", form);
			form.report.$touched = true;
			if (form.report == null || form.report == undefined || form.report == "") {
				status = false;
				form.error.report = "Required";
				$timeout(() => form.error.report = '', 3000);
			}

			return status;
		};

		/*CREATE*/
		$scope.reject = () => {
			let data = {}, valid = false;
			valid = $scope.validateForm();
			data = $scope.bpa.init($scope.bpa);
			if (!valid) return;

			valid = $window.confirm("Are you sure you want to submit?");
			if (!valid) return;

			CIS.save("POST", "./rejectbpa.htm", data, (success) => {
				$scope.serverMsg = success.msg;
				if (success.code == '201') {
					$scope.serverResponseSuccess = true;
					try {
						$scope.serverMsg += "\nNext Process: " + success.nextProcess.value;
						$timeout(() => {
							let url = success.nextProcess.key + "?applicationcode=" + success.nextProcess.value1;
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

		$scope.save = () => {
			let data = {}, valid = false;
			valid = $scope.validateForm();
			data = $scope.bpa.init($scope.bpa);
			if (!valid) return;

			valid = $window.confirm("Are you sure you want to submit?");
			if (!valid) return;

			CIS.save("POST", "./savebpasiteinspection.htm", data, (success) => {
				$scope.serverMsg = success.msg;
				if (success.code == '201') {
					$scope.serverResponseSuccess = true;
					try {
						$scope.serverMsg += "\nNext Process: " + success.nextProcess.value;
						$timeout(() => {
							let url = success.nextProcess.key + "?applicationcode=" + success.nextProcess.value1;
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