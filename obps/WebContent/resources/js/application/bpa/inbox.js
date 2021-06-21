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
	function ($scope, $http, $timeout, $compile, $window, CIS, BS) {
		console.log("BuildingPermitApplication");

		$scope.BPA = [];
		/*GET_DATA*/

		$scope.listBPApplications = () => {
			BS.listBPApplications((response) => {
				$scope.BPA = response;
				$scope.setBPAsTable($scope.BPA);
			}, (data = ""));
		};
		$scope.listBPApplications();

		$scope.setBPAsTable = (tData) => {
			$("#displayRecords").html("");
			$("#displayRecords").html("<table id='displayRecordsTable' style='width: 100%; margin: auto;' border='1' class='table table table-bordered  table-hover'></table>");
			var table = jQuery('#displayRecordsTable').DataTable({
				"data": tData,
				"columns": [
					{
						"title": "Slno",
						"data": "edrnumber",
						"width": "2%",
						render: function (data, type, row, meta) {
							return meta.row + 1;
						}
					}, {
						"title": "Applicationcode",
						"data": "applicationcode"
					}, {
						"title": "EDCR Number",
						"data": "edcrnumber"
					}, {
						"title": "From User",
						"data": "fromusername"
					}, {
						"title": "Next Process",
						"data": "processname",
					}
				],
				"stateSave": true,
				"createdRow": function (row, data, dataIndex) {
					$compile(angular.element(row).contents())($scope);
				}
			});

			table.on('click', 'tr', function () {
				var data = table.row(this).data();
				$window.location.href = data.pageurl + "?applicationcode=" + data.applicationcode;
			});
		}
	},
]);
