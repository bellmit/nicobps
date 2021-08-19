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
		console.log("Search Application");

		$scope.AppStatus = [];
		$scope.searchParam = "";

		/*GET_DATA*/
		$scope.searchApplication= () => {
			$scope.errorMsg = "";
			$("#displayRecords").html("");
			if($scope.searchParam == null || $scope.searchParam == ''){
				$scope.errorMsg = "Cannot search empty";
				$('#searchParam').focus();
				return;
			}
			BS.listBPApplicationsStatus((response) => {
				if(response != null && response.length > 0)
					$scope.setAppStatusTable(response);
				else
					$scope.errorMsg = "Application(s) not found";
			}, ($scope.searchParam));
		};

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
							if(row.permitnumber != null)
								content += '<br><small>Permit Number:</small> '+row.permitnumber;
							if(row.ownersname != null)
								content += '<br><small>Owner(s) Name:</small> '+row.ownersname;
							return content;
						}
					}, {
						"title": "Updated By",
						"data": "updatedby",
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
				"bSearch": false,
				"createdRow": function (row, data, dataIndex) {
					$compile(angular.element(row).contents())($scope);
				}
			});
		}
	},
]);
