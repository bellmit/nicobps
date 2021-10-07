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
			$("#displayRecords").html("<table id='displayRecordsTable' style='width: 100%; margin: auto;' border='1' class='table table table-bordered  table-hover'></table>");
			var table = jQuery('#displayRecordsTable').DataTable({
				"data": tData,
				"columns": [
					{
						"title": "Slno",
						"data": "edrnumber",
						"width": "2%",
						render: function(data, type, row, meta) {
							return meta.row + 1;
						}
					}, {
						"title": "EDCR Number",
						"data": "edcrnumber"
					}, {
						"title": "filename(.dxf) ",
						"data": "originalfilename"
					}, {
						"title": "scrutiny date ",
						"data": "edcrdate"
					}, {
						"title": "Application code",
						"data": "applicationcode"
					}, {
						"title": "Owner Name ",
						"data": "ownername"
					}, {
						"title": "Site Address ",
						"data": "address"
					},{
						"title": "Current Process ",
						"data": "fromprocessname"
					}, {
						"title": "Next Process",
						"data": "toprocessname",
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
