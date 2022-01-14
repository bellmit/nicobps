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
		console.log("BPA: Common Process");

		let data = "";
		$scope.serverResponseError = false;
		$scope.serverResponseFail = false;
		$scope.serverResponseInfo = false;
		$scope.serverResponseSuccess = false;

		$scope.bpa = new ProcessFlow();
		$scope.basicDetail = {};
		$scope.Blocks = {};
		$scope.EDCR = {};
		$scope.fileModal = new ModalFile();
		$scope.modal = new Modal();
		$scope.taskStatus = new TaskStatus();

		$scope.DocumentDetails = [];
		$scope.Enclosures = [];
		$scope.BPAEnclosures = new Array({ code: null, name: '', file: null, error: false, errormsg: null });
		$scope.OwnerDetails = [];
		$scope.Questionnaires = [];

		$scope.bpa.applicationcode = APPCODE;
		$scope.init = (applicationcode) => {

			APPCODE = applicationcode;

		};
		/* GET */
		BS.listBPAEnclosures((response) => {
			$scope.Enclosures = response;
			$scope.Enclosures.forEach((o, x) => {
				o.selected = false;
			});
		}, APPCODE);

		/*ACTION*/
		$scope.validateForm = () => {
			let status = true;
			try {
				if ($scope.Enclosures.length > 0) {
					if ($scope.BPAEnclosures.find(e => e.file == null || e.file == ''))
						$scope.BPAEnclosures.find(e => e.file == null || e.file == '').error = true;

					if ($scope.BPAEnclosures.find(e => e.file == null || e.file == '')
						&& $scope.BPAEnclosures.find(e => e.file == null || e.file == '').name != null
						&& $scope.BPAEnclosures.find(e => e.file == null || e.file == '').name != '')
						$scope.BPAEnclosures.find(e => e.file == null || e.file == '').errormsg
							= `Please upload ${$scope.BPAEnclosures.find(e => e.file == null || e.file == '').name} first`;
					else if ($scope.BPAEnclosures.find(e => e.file == null || e.file == ''))
						$scope.BPAEnclosures.find(e => e.file == null || e.file == '').errormsg = `Please select enclosure first`;

					if ($scope.BPAEnclosures.find(e => e.file == null || e.file == '')
						&& $scope.BPAEnclosures.find(e => e.file == null || e.file == '').error)
						status = false;

					if (status) {
						$scope.bpa.enclosures = $scope.BPAEnclosures;
					}
				}
			} catch (e) { console.log(e) }
			return status;
		};

		$scope.forward = () => {
			let data = {}, valid = false;
			valid = $scope.validateForm();

			if (!valid) {
				$('#commonModal').modal('hide');
				return false;
			}

			if ($scope.modal.usercode == null || $scope.modal.usercode == "") {
				alert("Please select user");
				return false;
			}
			if ($scope.modal.remarks == null || $scope.modal.remarks == "") {
				alert("Please enter remarks");
				return false;
			}
			$scope.bpa.tousercode = $scope.modal.usercode;
			$scope.bpa.remarks = $scope.modal.remarks;
			console.log("$scope.bpa: ", $scope.bpa);
			data = $scope.bpa.init($scope.bpa);

			console.log("data: ", data);
			valid = $window.confirm("Are you sure you want to forward?");
			if (!valid) return;

			$('#commonModal').modal('hide');
			CIS.save("POST", ProcessingUrl.bpaProcess, data, (success) => {
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