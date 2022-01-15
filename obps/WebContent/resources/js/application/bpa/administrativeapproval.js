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
		$scope.documents = new Array();
		//added
		$scope.Enclosures = [];
		$scope.SiteEnclosures = new Array({ code: null, name: '', file: null, error: false, errormsg: null });


		/* ACTION */
		$scope.addRemoveMoreFile = (opt) => {
			switch (opt) {
				case 1:
					$scope.SiteEnclosures.push({ code: null, name: '', file: null, error: false, errormsg: null });
					break;
				default:
					$scope.SiteEnclosures.pop();
			}

		};

		$scope.filterEnclosure = (opt, code) => {
			if (opt == 1) {
				$scope.SiteEnclosures.find(l => l.code == code).error = false;
				if ($scope.SiteEnclosures.find(l => l.code == code).name == null || $scope.SiteEnclosures.find(l => l.code == code).name == "") {
					$scope.SiteEnclosures.find(l => l.code == code).error = true;
					$scope.SiteEnclosures.find(l => l.code == code).errormsg = `Please select enclosure first`;
					return;
				}
				if ($scope.SiteEnclosures.find(l => l.code == code).file == null || $scope.SiteEnclosures.find(l => l.code == code).file == "") {
					$scope.SiteEnclosures.find(l => l.code == code).error = true;
					$scope.SiteEnclosures.find(l => l.code == code).errormsg = `Please upload ${$scope.SiteEnclosures.find(l => l.code == code).name} first`;
					return;
				}
				$scope.addRemoveMoreFile(opt);
				$scope.Enclosures.find(e => e.enclosurecode == code).selected = true;
				$scope.SiteEnclosures.find(l => l.code == code).flag = true;

			} else {
				$scope.addRemoveMoreFile(opt);
				$scope.SiteEnclosures[$scope.SiteEnclosures.length - 1].flag = false;
				$scope.Enclosures.find(e => e.enclosurecode == $scope.SiteEnclosures[$scope.SiteEnclosures.length - 1].code).selected = false;

			}
		};

		$scope.setLabel = (index, code) => {
			if (index != null && code != null) {
				$scope.SiteEnclosures[index].name = $scope.Enclosures.find(e => e.enclosurecode == code).enclosurename;
				$scope.SiteEnclosures[index].code = code;
				$scope.SiteEnclosures[index].error = false;
			} else {
				if ($scope.SiteEnclosures[index].file != null && $scope.SiteEnclosures[index].file != '')
					$scope.SiteEnclosures[index].error = false;
			}
		}


		$scope.validateForm = (opt = 0) => {
			let status = true;
			let form = $scope.bpa;
			form = $scope.SiteEnclosures;

			if ($scope.Enclosures.length != 0) {
				try {
					//				status = $scope.validateQuestionnairesResponse(); 
					//				if(!status) return status;

					$scope.SiteEnclosures.find(e => e.file == null || e.file == '').error = true;
					if ($scope.SiteEnclosures.find(e => e.file == null || e.file == '').name != null
						&& $scope.SiteEnclosures.find(e => e.file == null || e.file == '').name != '')
						$scope.SiteEnclosures.find(e => e.file == null || e.file == '').errormsg
							= `Please upload ${$scope.SiteEnclosures.find(e => e.file == null || e.file == '').name} first`;
					else
						$scope.SiteEnclosures.find(e => e.file == null || e.file == '').errormsg = `Please select enclosure first`;
					if ($scope.SiteEnclosures.find(e => e.file == null || e.file == '').error)
						status = false;

				} catch (e) { }

				if (status) {
					$scope.bpa.documents = $scope.SiteEnclosures.filter(e => e.code != null && e.code != '' && e.file != null && e.file != '');
				}

				return status;
			}


			form = $scope.bpa;
			if (form.reports != null && form.reports.length > 0) {
				form.error = new Array();
				for (let x = 0; x < form.reports.length; x++) {
					if (form.reports[x] == undefined || form.reports[x] == null || form.reports[x] == '') {
						status = false;
						form.error[x] = true;
						$timeout(() => form.error[x] = false, 3000);
					}
				}
			}
			return status;
		};


		//end 


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
		$scope.init = (applicationcode) => {

			APPCODE = applicationcode;
			BS.listBPAEnclosures((response) => {
				$scope.Enclosures = response;

				$scope.Enclosures.forEach((o, x) => {
					o.selected = false;
				});
			}, APPCODE);
			$scope.bpa.applicationcode = APPCODE;

			BS.listBPAConditions((response) => {
				$scope.Conditions = response;
				$scope.Conditions.forEach(c => {
					if (c.checked === 'Y')
						c.checked = true;
					else
						c.checked = false;
				});
			}, APPCODE);

		};
		$scope.forward = () => {


			let data = {}, valid = false;
			valid = $scope.validateForm();

			if (!valid) {
				$('#commonModal').modal('hide');
				$timeout(() => {
					alert("Please fill all mandatory fields");
				}, 5);
				return;
			}

			let conditions = [];
			$scope.Conditions.forEach((c, x) => {
				if (c.checked) {
					conditions.push({ key: c.conditioncode, value: c.conditiondescription });
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
			//		console.log("doc"+$scope.bpa.documents)

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