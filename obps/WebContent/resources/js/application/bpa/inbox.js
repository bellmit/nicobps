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

		$scope.BPA = [];
		/*GET_DATA*/
		var processcode = "";
		processcode = document.getElementById("pcode").innerText;
		$scope.listBPApplications = () => {
			BS.listBPApplications((response) => {
				$scope.BPA = response;
				$scope.setBPAsTable($scope.BPA);
			}, (data = processcode));
		};
		$scope.listBPApplications();

		$scope.setBPAsTable = (tData) => {
			$("#displayRecords").html("");
			$("#displayRecords").html("<table id='displayRecordsTable' style='width: 100%; margin: auto;font-size:85%;' border='1' class='table  table-bordered  table-hover'></table>");
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
						"title": "Applicationcode",
						"data": "applicationcode"
					}, {
						"title": "Owner Name ",
						"data": "ownername"
					}, {
						"title": "Site Address ",
						"data": "address"
					}, {
						"title": "Current Process ",
						"data": "currentprocessname"
					},  {
						"title": "Next Process",
						"data": "nextprocessname"
					}
				],
				"stateSave": true,
				"createdRow": function(row, data, dataIndex) {
					$compile(angular.element(row).contents())($scope);
				}
			});

			table.on('click', 'tr', function() {
				var data = table.row(this).data();
				$window.location.href = data.pageurl + "?applicationcode=" + data.applicationcode;
			});
		}
	},
]);
