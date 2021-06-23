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
		console.log("RejectedApplication");
		$scope.RejectedApplications = [];
		/*GET_DATA*/
		$scope.listRejectedApplications = () => {
			BS.listRejectedApplications((response) => {
				$scope.RejectedApplications = response;
				$scope.setRejectedApplicationsTable($scope.RejectedApplications);
			}, (data = ""));
		};
		$scope.listRejectedApplications();

		/* CREATE */
		$scope.unreject = (data) => {

		};

		/* ACTION */
		$scope.setRejectedApplicationsTable = (tData) => {
			$("#displayRecords").html("");
			$("#displayRecords").html("<table id='displayRecordsTable' style='width: 100%; margin: auto;' border='1' class='table table table-bordered  table-hover'></table>");
			var table = jQuery('#displayRecordsTable').DataTable({
				"data": tData,
				"columns": [
					{
						"title": "Slno",
						"data": "slno",
						"width": "2%",
						render: function (data, type, row, meta) {
							return meta.row + 1;
						}
					}, {
						"title": "Application code",
						"data": "applicationcode"
					}, {
						"title": "Reject by",
						"data": "username"
					}, {
						"title": "Remarks",
						"data": "remarks",
						render: (data, type, row, meta) => {
							if (data == null || data == '') return "Apply for BPA";
							else return data;
						}
					}, {
						"title": "Date",
						"data": "rejectdate"
					}, {
						"title": "Action",
						"data": "slno",
						render: (data, type, row, meta) => {
							let btn = "";
							if (row.usercode != null && row.usercode != '') {
								btn = '<button class="btn btn-sm btn-outline-primary" ng-click="unreject(\'' + data + '\')">Un-Reject</button>';
							}
							return btn;
						}
					},
				],
				"stateSave": true,
				"createdRow": function (row, data, dataIndex) {
					$compile(angular.element(row).contents())($scope);
				}
			});

		}
	},
]);
