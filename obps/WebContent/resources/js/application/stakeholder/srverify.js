var signatures = {
	"JVBER": "application/pdf",
	"/9J/4": "image/jpeg",
	"IVBOR": "image/png"
};

function detectMimeType(b64) {
	return signatures[b64.toUpperCase().substr(0, 5)];
}
app.controller("CommonCtrl", [
	"$scope", "$http", "$timeout", "commonInitService",
	function ($scope, $http, $timeout, commonInitService) {
		$scope.applicant = {};
		$scope.Licensees = [];
		$scope.process = {};
		$scope.viewDetails = (applicant) => {
				jQuery('#enclosureWindow').html();
				$scope.applicant = applicant;
				jQuery.fancybox.close();
				jQuery.fancybox({
					href: '#detailView',
					'autoSize': false,
					'transitionIn': 'elastic',
					'transitionOut': 'elastic',
					'speedIn': 600,
					'speedOut': 200,
					'overlayShow': false,
					'modal': false,
					'width': '100%',
				});
				$scope.listNextProcess($scope.applicant.processcode);
			},
			$scope.showRemarks = (p) => {
				$scope.process = p;
				$scope.process.remarks = "";
				jQuery.fancybox.close();
				jQuery.fancybox({
					href: '#remarksTable',
					'autoSize': false,
					'transitionIn': 'elastic',
					'transitionOut': 'elastic',
					'speedIn': 600,
					'speedOut': 200,
					'overlayShow': false,
					'modal': false,
					'height': 'auto',
					'width': '350px'
				});
			};
		$scope.updateStakeholder = (applicant, process) => {
				let data = "officecode="+applicant.officecode+"&applicationcode=" + applicant.applicationcode + "&usercode=" + applicant.usercode + "&toprocesscode=" + process.toprocesscode + "&remarks=" + process.remarks;
				commonInitService.http("POST", "updateStakeholder.htm", data, function (response) {
					if (response) {
						MsgBox("Stakeholder application updated.");
						$scope.listLicensees();
					} else {
						MsgBox("Error! Please try again.");
					}
				}, function () {
					alert("Sorry, there was an error while trying to process the request.");
				});
			},

			$scope.getEnclosure = (usercode, enclosurecode) => {
				jQuery.ajax({
					type: "POST",
					url: "./getEnclosure.htm",
					data: "enclosurecode=" + enclosurecode + "&usercode=" + usercode,
					success: function (response) {
						if (response.length > 0) {
							jQuery('#enclosureWindow').html('<iframe src="data:' + detectMimeType(response) + ';base64,' + response + '"' +
								'style="width:100%;height:calc(80vh);" frameborder="0"></iframe>');
						} else {
							jQuery('#enclosureWindow').html('<div class="card text-white bg-warning mb-3" style="width: 100%;">' +
								'<div class="card-body"><h5 class="card-title">Enclosure not submitted.</h5></div>' +
								'</div>');
						}
					},
					error: function (xhr) {
						alert(xhr.status + " = " + xhr);
						alert(
							"Sorry, there was an error while trying to process the request."
						);
					},
				});
			},
			$scope.listNextProcess = (processcode) => {
				$http.post("./listNextProcess.htm?", {
					"currentprocesscode": processcode
				}).success(
					function (response, status, headers, config) {
						$scope.nextProcesses = response
					});
			};
		$scope.listLicensees = function () {
				$http.post("./listLicensees.htm").success(
					function (response, status, headers, config) {
						$scope.Licensees = response
					});
			},
			$http.post("./listEnclosures.htm").success(
				function (response, status, headers, config) {
					$scope.Enclosures = response
				});
		$scope.listLicensees();
	},
]);