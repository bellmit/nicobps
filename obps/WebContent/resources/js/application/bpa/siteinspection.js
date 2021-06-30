/**
 * @author Decent Khongstia
 */

app.controller("CommonCtrl", [
	"$sce",
	"$scope",
	"$http",
	"$timeout",
	"$window",
	"commonInitService",
	"bpaService",
	function ($sce, $scope, $http, $timeout, $window, CIS, BS) {
		console.log("BPA: Site Inspection");

		let data = "";
		$scope.serverResponseError = false;
		$scope.serverResponseFail = false;
		$scope.serverResponseInfo = false;
		$scope.serverResponseSuccess = false;

		$scope.bpa = new SiteInspection();
		$scope.basicDetail = {};
		$scope.Blocks = {};
		$scope.EDCR = {};
		$scope.fileModal = new ModalFile();
		$scope.modal = new Modal();
		$scope.taskStatus = new TaskStatus();

		$scope.DocumentDetails = [];
		$scope.OwnerDetails = [];
		$scope.Users = [];

		$scope.bpa.applicationcode = APPCODE;

		/*ACTION*/
		$scope.clearAfterCreateProcess = () => {
			$timeout(() => {
				$scope.serverResponseError = false;
				$scope.serverResponseFail = false;
				$scope.serverResponseInfo = false;
				$scope.serverResponseSuccess = false;
				$scope.serverMsg = "";
			}, Timeout.Reload);
		};

		$scope.validateForm = () => {
			let status = true;
			let form = $scope.bpa;
			form.report.$touched = true;
			if (form.report == null || form.report == undefined || form.report == "") {
				status = false;
				form.error.report = "Required";
				$timeout(() => form.error.report = '', 3000);
			}

			return status;
		};

		/*CREATE*/
		$scope.forward = () => {
			let data = {}, valid = false;
			valid = $scope.validateForm();
			$scope.bpa.tousercode = $scope.modal.usercode;
			$scope.bpa.remarks = $scope.modal.remarks;
			data = $scope.bpa.init($scope.bpa);

			if (!valid) {
				$('#commonModal').modal('hide');
				$timeout(() => {
					alert("Please fill all mandatory fields");
				}, 5);
				return;
			}
			if ($scope.modal.usercode == null || $scope.modal.usercode == "") {
				alert("Please select user");
				return false;
			}
			if ($scope.modal.remarks == null || $scope.modal.remarks == "") {
				alert("Please enter remarks");
				return false;
			}
			valid = $window.confirm("Are you sure you want to forward?");
			if (!valid) return;

			$('#commonModal').modal('hide');
			CIS.save("POST", ProcessingUrl.bpaSiteInspection, data, (success) => {
				$scope.serverMsg = success.msg;
				if (success.code == '201') {
					$scope.serverResponseSuccess = true;
					try {
						if (success.nextProcess.key != null) {
							$scope.serverMsg += "\nNext Process: " + success.nextProcess.value;
							$timeout(() => {
								let url = success.nextProcess.key + "?applicationcode=" + success.nextProcess.value1;
								$window.location.href = url;
							}, Timeout.Reload);
						} else
							$timeout(() => { $window.location.reload(); }, Timeout.Reload);
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
