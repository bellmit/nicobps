/**
 * @author Decent Khongstia
 */
var APPCODE;
app.controller("CommonCtrl", [
	"$sce",
	"$scope",
	"$http",
	"$timeout",
	"$window",
	"commonInitService",
	"bpaService",
	function($sce, $scope, $http, $timeout, $window, CIS, BS) {
		console.log("BPA: Citizen Task");

		let data = "";
		$scope.serverResponseError = false;
		$scope.serverResponseFail = false;
		$scope.serverResponseInfo = false;
		$scope.serverResponseSuccess = false;

		$scope.bpa = new ProcessFlow();
		$scope.taskStatus = new TaskStatus();


		/*GET*/
		$scope.taskStatus = new TaskStatus();

		

		/*ACTION*/
		$scope.clearAfterCreateProcess = () => {
			$timeout(() => {
				$scope.serverResponseError = false;
				$scope.serverResponseFail = false;
				$scope.serverResponseInfo = false;
				$scope.serverResponseSuccess = false;
				$scope.serverMsg = "";
			}, Timeout.ThreeSecond);
		};

		$scope.setModalTitle = (opt) => {
			$scope.modal = new Modal();
			switch (opt) {
				case 4:
					$scope.modal.action = 4;
					$scope.modal.actionname = "Send";
					$scope.modal.title = 'Return From Citizen';
					break;
			}
		}

		/*CREATE*/
		$scope.returnFromCitizen = () => {
			let data = {}, valid = false;

			if ($scope.modal.remarks == null || $scope.modal.remarks == "") {
				alert("Please enter remarks");
				return false;
			}
			data.applicationcode = $scope.bpa.applicationcode;
			data.remarks = $scope.modal.remarks;
			valid = $window.confirm("Are you sure you want to Send to Citizen?");
			if (!valid) return;

			$('#commonModal').modal('hide');
			CIS.save("POST", ProcessingUrl.bpaReturnFromCitizen, data, (success) => {
				$scope.serverMsg = success.msg;
				if (success.code == '201') {
					$scope.serverResponseSuccess = true;
					$timeout(() => { $window.location.reload(); }, Timeout.Reload);
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
		};// End returnFromCitizen
		$scope.init = (applicationcode) => {

			APPCODE = applicationcode;
			$scope.bpa.applicationcode = APPCODE;
			BS.getCurrentProcessTaskStatus((response) => {
				$scope.taskStatus = response;
			}, APPCODE);

		};
	}]);