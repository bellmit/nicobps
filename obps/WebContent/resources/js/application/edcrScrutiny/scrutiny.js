var app = angular.module("CommonApp", []);
app.controller('edcrscrutinyController', function($scope, $compile, $timeout, $window) {
	$scope.listLicensees = function() {
		jQuery.ajax({
			type: "GET",
			url: "./fetch_edcr_usercd.htm",
			cache: false,
			dataType: 'json',
			success: function(response) {
				$timeout(function() {
					$scope.edcrscrutinylist = response;
					$scope.setEdcrListTable($scope.edcrscrutinylist);
				}, 0);
			}
		});
	};
	$scope.setEdcrListTable = (tData) => {
	  	$("#displayRecords").html("");
		$("#displayRecords").html("<table id='displayRecordsTable' style='width: 100%; margin: auto;' border='1' class='table table table-bordered  table-hover'></table>");
		var table = jQuery('#displayRecordsTable').DataTable({
			"data": tData,
			"columns": [
				{
					"title": "Slno",
					"data": "slno",
					"width": "2%",
					render: function(data, type, row, meta) {
						return meta.row + 1;
					}
				}, {
					"title": "Office / ULBs",
					"data": "officename"
				}, {
					"title": "eDCR Number",
					"data": "edcrnumber",
					render: (data, type, row, meta) => {
						let edcrnumber = '<font style="color:grey;font-weight:bold">' + data + '</font>';
						return edcrnumber;
					}
				}, {
					"title": "Uploaded On",
					"data": "entrydate",
					render: (data, type, row, meta) => {
						let entrydate = data;
						return entrydate.substring(0, 19);
						}

				}, {
					"title": "Status",
					"data": "status",
					render: (data, type, row, meta) => {
						let status = data;
						if (row.status != 'Accepted') {
							status = '<font style="color:red">' + status + '</font>';
						} else {
							status = '<font style="color:green">' + status + '</font>';
						}
						return status;
					}
				}, {
					"title": "Plan Report",
					"data": "scrutinyreport",
					render: (data, type, row, meta) => {
						let planreport = '<a href="' + data + '" target="_blank">View/Download<a>';
						return planreport;
					}
				},
			],
			'columnDefs': [
				{
					"targets": 0, // your case first column
					"className": "text-center",
					"width": "4%"
				},
				{
					"targets": 1, // your case first column
					"className": "dt-head-center",

				},
				{
					"targets": 2,
					"className": "text-center",
				},
				{
					"targets": 3, // your case first column
					"className": "text-center",

				},
				{
					"targets": 4,
					"className": "text-center",
				},
				{
					"targets": 5,
					"className": "text-center",
				}],
			"stateSave": true,
			"createdRow": function(row, data, dataIndex) {
				$compile(angular.element(row).contents())($scope);
			}
		});
	}
	$scope.listLicensees();
	$scope.listOffices = function() {
		jQuery.ajax({
			type: "POST",
			url: "./listOffices/ValidUserOffices.htm",
			cache: false,
			dataType: 'json',
			success: function(response) {
				console.log(JSON.stringify(response));
				$timeout(function() {
					$scope.userofficelist = response;
				}, 0);
			}
		});
	};
	$scope.listOffices();
	$scope.generateReport = (planreport) => {
		$window.open(planreport, '_blank');
	}
	$scope.submitDetails = () => {
		if (validateDetails()) {
			var flag = confirm(" Click OK to Upload. Click Cancel if you want to review your entries");
			if (!flag) { return false; }
			var data = new FormData();
			jQuery.each(jQuery('#dxffile')[0].files, function(i, file) {
				data.append('planFile', file);
			});
			 
			data.append('OfficeCode', (JSON.parse($scope.validoffice)).officecode);
			data.append('stateid', (JSON.parse($scope.validoffice)).stateid);
			data.append('tenantid', (JSON.parse($scope.validoffice)).tenantid);
			jQuery.ajax({
				type: "POST",
				url: "./scrutinize_edcr.htm",
				cache: false,
				contentType: false,
				dataType: 'json',
				processData: false,
				data: data,
				success: function(response) {
					$timeout(function() {
						$scope.edcrscrutiny = response;

					}, 0);
				 
					if (response.status != '') {
						if (response.status == 'error') {
							jQuery('.edcrResp').css("visibility", "hidden");
							jQuery('#genReport').css("visibility", "hidden");
							jQuery('#errmsg').html('<div class="card text-white bg-danger mb-3" style="width: 600px">' +
								'<div class="card-body"><p class="card-title">eDCR Scrutiny Failed: Error-Please contact System Administrator.</p></div>' +
								'</div>');
							$timeout(function() {
								jQuery('#errmsg').html("");
							}, 4000);
						} else {
							jQuery('#entrytable').css("visibility", "hidden");
							jQuery('.edcrResp').css("visibility", "visible");
							jQuery('#genReport').css("visibility", "visible");
							if (response.status == "Accepted") {
								jQuery('#edcrstatus').css("color", "green")
							} else {
								jQuery('#edcrstatus').css("color", "red")
							}
							jQuery('#edcrnumber').css("color", "blue")
							$scope.listLicensees();
						}
					}
				},
				error: function(xhr) {
					alert(xhr.status + " = " + xhr);
					alert(
						"Sorry, there was an error while trying to process the request."
					);
				},
			});
		}
	};
	function validateDetails() {
		if ($scope.validoffice == "" || $scope.validoffice == undefined) {
			$scope.errorSelectmsg = "*this field is required";
			$timeout(function() {
				$scope.errorSelectmsg = "";
			}, 3000);
			return false;
		} else {
			$scope.errorSelectmsg = "";
		}
		if ($('#dxffile').get(0).files.length == 0) {
			$scope.errorDxfFile = "*File is required";
			$timeout(function() {
				$scope.errorDxfFile = "";
			}, 3000);
			return false;
		} else {
			$scope.errorDxfFile = "";
		}
		//---------check file extension-----------
		var fileExtension = $('#dxffile').val().replace(/^.*\./, '');
		if (fileExtension != "dxf") {
			$scope.errorDxfFile = "*Invalid File Type";
			$timeout(function() {
				$scope.errorDxfFile = "";
			}, 3000);
			return false;
		} else {
			$scope.errorDxfFile = "";
		}
		return true;
	}


});
//app.controller("edcrscrutinyController", [
//	"$scope", "$http", "$timeout", "commonInitService",
//	function($scope, $http, $timeout, commonInitService) {
//		$scope.submitDetails = () => {
//			var flag = confirm(" Click OK to Upload. Click Cancel if you want to review your entries");
//			if (!flag) { return false; }
//			var data = new FormData();
//			jQuery.each(jQuery('#dxffile')[0].files, function(i, file) {
//				data.append('planFile', file);
//			});
//			jQuery.ajax({
//				type: "POST",
//				url: "./scrutinize_edcr.htm",
//				cache: false,
//				contentType: false,
//				dataType: 'json',
//				processData: false,
//				data: data,
//				success: function(response) {
//					$timeout(function() {
//						$scope.edcrscrutiny = response;
//					}, 0);
//					dataresponse = response;
//					if (response.status != '') {
//						jQuery('#entrytable').css("visibility", "hidden");
//						jQuery('#edcrResp').css("visibility", "visible");
//						if (response.status = "Accepted") {
//							jQuery('#edcrstatus').css("color", "green")
//						} else {
//							jQuery('#edcrstatus').css("color", "red")
//						}
//						jQuery('#edcrnumber').css("color", "blue")
//						jQuery('#edcrstatus').text(response.status);
//						jQuery('#edcrnumber').text(response.edcrnumber);
//					}
//				},
//				error: function(xhr) {
//					alert(xhr.status + " = " + xhr);
//					alert(
//						"Sorry, there was an error while trying to process the request."
//					);
//				},
//			}) 
//
//		};
//
//	},
//]);


