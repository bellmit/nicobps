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
		$scope.Questionnaires = [];
		$scope.Users = [];

		$scope.bpa.applicationcode = APPCODE;

		/*GET*/
		BS.listSiteInspectionQuestionnaires((response) => {
			$scope.Questionnaires = response;
		}, APPCODE);
		
		/*ACTION*/
		$scope.addRemoveMoreFile = (opt) => {
			switch(opt){
				case 1:
					$scope.bpa.reports.push(null);
					break;
				default:
					$scope.bpa.reports.pop();
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

		$scope.validateForm = () => {
			let status = true;
			let form = $scope.bpa;
			if(form.reports != null && form.reports.length > 0){
				form.error = new Array();
				for(let x = 0; x < form.reports.length; x ++){
					if(form.reports[x] == undefined || form.reports[x] == null || form.reports[x] == ''){
						status = false;
						form.error[x]= true;
						$timeout(() => form.error[x]= false, 3000);
					}
				}
			}
			return status;
		};

		/*CREATE*/
		$scope.forward = () => {
			let data = {}, valid = false;
			valid = $scope.validateForm();
			$scope.bpa.tousercode = $scope.modal.usercode;
			$scope.bpa.remarks = $scope.modal.remarks;
			$scope.bpa.questionnaires = $scope.bpa.initQuestionnaires($scope.Questionnaires);
			data = $scope.bpa.init($scope.bpa);
			console.log("f-data: ",data)
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
