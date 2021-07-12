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
		console.log("BPA: Administrative Approval");

		let data = "";
		$scope.serverResponseError = false;
		$scope.serverResponseFail = false;
		$scope.serverResponseInfo = false;
		$scope.serverResponseSuccess = false;

		$scope.bpa = {};
		$scope.basicDetail = {};
		$scope.Blocks = {};
		$scope.EDCR = {};
		$scope.fileModal = new ModalFile();
		$scope.modal = new Modal();
		$scope.taskStatus = new TaskStatus();

		$scope.Conditions = [];
		$scope.DocumentDetails = [];
		$scope.OwnerDetails = [];
		$scope.SiteReportDetails = [];

		$scope.bpa.applicationcode = APPCODE;
		
		BS.listBPAConditions((response) => {
			$scope.Conditions = response;
			$scope.Conditions.forEach( c => {
				if(c.checked === 'Y')
					c.checked = true;
				else
					c.checked = false;
			});
		}, APPCODE);
		
		/*ACTION*/
		$scope.addRemoveMoreConditions = (key) => {
			switch (key) {
			case 1:
				let condition = {
					conditioncode: $scope.Conditions.length + 1000,
					conditiondescription: null,
					checked: true,
				};
				$scope.Conditions.push(condition);
				break;
			default:
				$scope.Conditions.pop();
				break;
			}
		};
		
		$scope.clearAfterCreateProcess = () => {
			$timeout(() => {
				$scope.serverResponseError = false;
				$scope.serverResponseFail = false;
				$scope.serverResponseInfo = false;
				$scope.serverResponseSuccess = false;
				$scope.serverMsg = "";
			}, Timeout.Reload);
		};
		
		$scope.forward  = () => {
			let data = {}, valid = false;
			
			let conditions = [];
			$scope.Conditions.forEach((c,x) => {
				if(c.checked){
					conditions.push({key: c.conditioncode, value: c.conditiondescription});
				}
			});
			if ($scope.modal.remarks == null || $scope.modal.remarks == "") {
				alert("Please enter remarks");
				return false;
			}
			
			let processflow = new ProcessFlow();
			processflow.tousercode = $scope.modal.usercode;
			processflow.remarks = $scope.modal.remarks;
			processflow.applicationcode = $scope.bpa.applicationcode;
			$scope.bpa.processflow = processflow;
			$scope.bpa.conditions = conditions;
			data = $scope.bpa;

			valid = $window.confirm("Are you sure you want to forward?");
			if (!valid) return;
			$('#commonModal').modal('hide');
			CIS.save("POST", ProcessingUrl.bpaApprove, data, (success) => {
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