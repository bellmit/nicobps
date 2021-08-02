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
				$scope.applicant = angular.copy(applicant);
				$scope.applicant.enclosures=JSON.parse($scope.applicant.enclosures.value);
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
					'width': '100%'
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
				'width': '500px'
			});
		};  
		$scope.updateStakeholder = (applicant, process) => {
			let data = "officecode="+applicant.officecode+"&applicationcode=" + applicant.applicationcode + "&usercode=" + applicant.usercode + "&toprocesscode=" + process.toprocesscode + "&remarks=" + process.remarks;
			commonInitService.http("POST", "updateStakeholder.htm", data, function (response) {
				if (response=="success") {
					MsgBox("Stakeholder application updated.");
					$scope.listLicensees();
				} else if(response == "false"){
					MsgBox("Error! Please try again.");
				}
				else{
				MsgBox(response.data)
				$scope.listLicensees();
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
						jQuery('#enclosures').closest('div').removeClass('h-50');
						jQuery('#enclosures').closest('div').removeClass('h-100');
						jQuery('#enclosures').closest('div').addClass('h-100');
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

						jQuery("#displayRecords").html("<table id='displayRecordsTable' style='width:100%' border='1'>\n\
                                                </table>");
						var table= jQuery('#displayRecordsTable').DataTable({
							data: $scope.Licensees,
							columns: [
								{
									"title": "Application No.",
									"data": "applicationcode"
								},{
									"title": "Application Date",
									"data": "applicationdate",
									"render":(data,type,row,meta)=>{
										return (new Date(data)).getDay()+"/"+(new Date(data)).getMonth()+'/'+(new Date(data)).getFullYear()
									}
								},{
									"title": "Licence",
									"data": "licenseetypename"
								},{
									"title": "Firm/Individual",
									"data": "firmindividual",
									"render":(data,type,row,meta)=>{
										return data=='I'?'Individual':'Firm'
									}
								},{
									"title": "Applicant/Firm Name",
									"data": "applicantsname"
								},{
									"title": "Office",
									"data": "officename1"
								},{
									"title": "District",
									"data": "districtname"
								},{
									"title": "Action",
									"data": "nextprocessname"
								}
							]
						});
						table.on('click', 'tr', function () {
							var data = table.row(this).data();
							$scope.viewDetails(data)
						});
					}
					);
			},
			$http.post("./listEnclosures.htm").success(
				function (response, status, headers, config) {
					$scope.Enclosures = response
				});
		$scope.listLicensees();
		$scope.print = function(transactioncode) {
			console.log(" transactioncode " + transactioncode);
			window.open("./Report?status=1&transactioncode=" + transactioncode);
		};
	},
]);