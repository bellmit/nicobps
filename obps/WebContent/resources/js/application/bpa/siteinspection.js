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
		$scope.bpa.applicationcode = APPCODE;
		// $scope.bpaEdcr = new BPA();
		$scope.basicDetail = {};

		$scope.modal = new Modal();
		$scope.taskStatus = new TaskStatus();

		/*GET*/
		BS.getBpaApplicationDetails((response) => {
			$scope.basicDetail.address = response.plotaddressline1 + " " + response.plotaddressline2;
			$scope.basicDetail.citytown = response.plotvillagetown;
			$scope.basicDetail.pincode = response.plotpincode;
			$scope.basicDetail.officelocationname = response.locationname;
			$scope.basicDetail.plotidentifier1 = response.plotidentifier1;
			$scope.basicDetail.holdingno = response.holdingno;
			$scope.basicDetail.landregistrationno = response.landregistrationno;
			$scope.basicDetail.landregistrationdetails = response.landregistrationdetails;
			$scope.basicDetail.OwnerDetails = response.ownerdetails;
			$scope.basicDetail.DocumentDetails = response.documentdetails;
		}, APPCODE);

		BS.getCurrentProcessTaskStatus((response) => {
			$scope.taskStatus.taskdate = response.taskdate;
			$scope.taskStatus.status = response.flowname;
			$scope.taskStatus.remarks = response.remarks;
			$scope.taskStatus.fullname = response.fullname;
		}, APPCODE);

		BS.getEdcrDetailsV2((response) => {
			let edcr = new EdcrDetail();
			$scope.EDCR = edcr.init(response);
			$scope.planInfo = edcr.extractPlanInfo($scope.EDCR);
			$scope.gmapAddress = ($scope.planInfo.district).toLocaleLowerCase();
			$scope.occupancy = edcr.extractPlanOccupancy($scope.EDCR);
			$scope.Blocks = edcr.extractPlanBlocks($scope.EDCR);
			$scope.basicDetail.applicationcode = APPCODE;
			$scope.basicDetail.edcrnumber = $scope.EDCR.edcrnumber;
			$scope.basicDetail.occupancy = ($scope.occupancy != null && $scope.occupancy.type != null) ? ($scope.occupancy.type).join(",") : "NA";
			$scope.basicDetail.plotarea = $scope.planInfo.plotArea;
		}, APPCODE);

		/* $http.get('./listNextProcess.htm?applicationcode=' + $scope.bpa.applicationcode).then((response) => {
		}, (errResponse) => {
		}); */

		/*ACTION*/
		$scope.getFloorArea = (occupanies) => {
			return BS.getFloorTotalArea(occupanies);
		};

		$scope.getFloorBuiltUpArea = (occupanies) => {
			return BS.getFloorTotalBuiltUpArea(occupanies);
		};

		$scope.getFloorName = (floorNumber) => {
			return BS.getFloorName(floorNumber);
		};

		$scope.getFloorSubOccupancy = (data) => {
			return BS.getFloorSubOccupancy(data);
		};

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
			data = $scope.rejectData;
			valid = $window.confirm("Are you sure you want to reject?");
			if (!valid) return;

			CIS.save("POST", "./rejectbpaTest.htm", data, (success) => {
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

			valid = $window.confirm("Are you sure you want to forward?");
			if (!valid) return;

			$('#commonModal').modal('hide');
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

		/* MODAL */
		$scope.setModalTitle = (opt) => {
			$scope.modal = new Modal();
			switch (opt) {
				case 1:
					$scope.modal.action = 1;
					$scope.modal.actionname = "Forward";
					$scope.modal.title = 'Forward Application';
					break;
				default:
					$scope.modal.action = 2;
					$scope.modal.actionname = "Reject";
					$scope.modal.title = 'Reject Application';
			}
		};

		$scope.rejectData = {};
		$scope.modalAction = (modalRemarks) => {
			switch ($scope.modal.action) {
				case 1:
					$scope.save();
					break;
				default:
					$scope.reject();
			}
		};
	}
]);

var TaskStatus = function () {
	var TaskStatus = {
		taskdate: null,
		username: '',
		status: '',
		remarks: '',
	}

	return TaskStatus;
}