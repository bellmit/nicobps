/**
 * @author Decent Khongstia
 */

app.controller("CommonCtrl", [
	"$scope",
	"$http",
	"$timeout",
	"$compile",
	"$window",
	"commonInitService",
	"bpaService",
	function($scope, $http, $timeout, $compile, $window, CIS, BS) {
		console.log("BuildingPermitApplication");

		$scope.ScrutinzedApp = [];
		/*GET_DATA*/

		$scope.listAppScrutinyDetailsForBPA = () => {
			BS.listAppScrutinyDetailsForBPAV2((response) => {
				$scope.ScrutinzedApp = response;
				$scope.setAppEdcrDetailsTable($scope.ScrutinzedApp);
			}, (data = ""));
		};
		$scope.listAppScrutinyDetailsForBPA();

		$scope.setAppEdcrDetailsTable = (tData) => {
			$("#displayRecords").html("");
			$("#displayRecords").html("<table id='displayRecordsTable' style='width: 100%; margin: auto;font-size:85%;' border='1' class='table  table-bordered  table-hover'></table>");
			var table = jQuery('#displayRecordsTable').DataTable({
				"data": tData,
				"columns": [
					{
						"title": "Slno",
						"data": "edrnumber",
						"width": "1%",
						render: function(data, type, row, meta) {
							return meta.row + 1;
						}
					}, {
						"title": "EDCR Number",
						"data": "edcrnumber",
						"width": "5%"
					}, {
						"title": "filename(.dxf) ",
						"data": "originalfilename",
						"width": "8%"
					}, {
						"title": "scrutiny date ",
						"data": "edcrdate",
						"width": "5%"
					}, {
						"title": "Application code",
						"data": "applicationcode",
						"width": "8%"
					}, {
						"title": "Owner Name ",
						"data": "ownername",
						"width": "13%"
					}, {
						"title": "Site Address ",
						"data": "address",
						"width": "20%"
					},{
						"title": "Current Process ",
						"data": "fromprocessname",
						"width": "20%"
					}, {
						"title": "Next Process",
						"data": "toprocessname",
						"width": "20%",
						render: (data, type, row, meta) => {
							if (data == null || data == '') return "Apply for BPA";
							else return data;
						}
					}
				],
				"stateSave": true,
				"createdRow": function(row, data, dataIndex) {
					$compile(angular.element(row).contents())($scope);
				}
			});

			table.on('click', 'tr', function() {
				var data = table.row(this).data();
				if (data.applicationcode == null || data.applicationcode == '')
					$window.location.href = "applybuildingpermit.htm?edcrnumber=" + data.edcrnumber;
				else
					$window.location.href = data.pageurl + "?applicationcode=" + data.applicationcode;
			});
		}
	},
]);
