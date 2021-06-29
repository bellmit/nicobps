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
		console.log("TrackApplicationStatus");

		$scope.AppStatus = [];
		/*GET_DATA*/

		$scope.listApplictionsCurrentProcessStatus = () => {
			BS.listApplictionsCurrentProcessStatus((response) => {
				$scope.AppStatus = response;
				$scope.setAppStatusTable($scope.AppStatus);
			}, (data = ""));
		};
		$scope.listApplictionsCurrentProcessStatus();

		$scope.setAppStatusTable = (tData) => {
			$("#displayRecords").html("");
			$("#displayRecords").html("<table id='displayRecordsTable' style='width: 100%; margin: auto;' border='1' class='table table table-bordered  table-hover'></table>");
			var table = jQuery('#displayRecordsTable').DataTable({
				"data": tData,
				"columns": [
					{
						"title": "Slno",
						"data": "applicationcode",
						"width": "2%",
						render: function (data, type, row, meta) {
							return meta.row + 1;
						}
					}, {
						"title": "Application code",
						"data": "applicationcode",
						render: (data, type, row, meta) => {
							let content = '<b>'+data+'</b>';
							content += '<br><small>eDCRNumber:</small> '+row.edcrnumber;
							return content;
						}
					}, {
						"title": "Updated By",
						"data": "updatedby",
						/* }, {
							"title": "Assigned To",
							"data": "assignee",
							render: (data, type, row, meta) => {
								if (data != null && data != '')
									return data;
								else
									return '';
							} */
					}, {
						"title": "Next Process",
						"data": "nextprocessname",
						render: (data, type, row, meta) => {
							let status = data;
							if (row.rejectdate != null && row.rejectdate != '')
								status = 'NA';
							return status;
						}
					}, {
						"title": "Status",
						"data": "status",
						render: (data, type, row, meta) => {
							let status = data;
							if (row.rejectdate != null && row.rejectdate != '')
								status = "Rejected";
							return status;
						}
					}, {
						"title": "Remarks",
						"data": "remarks",
						render: (data, type, row, meta) => {
							let status = data;
							if (row.rejectdate != null && row.rejectdate != '')
								status = row.rejectremarks;
							return status;
						}
					}, {
						"title": "Date",
						"data": "appdate",
					}
				],
				"stateSave": true,
				"createdRow": function (row, data, dataIndex) {
					$compile(angular.element(row).contents())($scope);
				}
			});
		}
	},
]);
