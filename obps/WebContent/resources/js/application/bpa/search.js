/**
 * @author Decent Khongstia
 */
var APPCODE;
app.controller("CommonCtrl", [
	"$scope",
	"$http",
	"$timeout",
	"$compile",
	"$window",
	"commonInitService",
	"bpaService",
	function($scope, $http, $timeout, $compile, $window, CIS, BS) {
		console.log("Search Application");

		$scope.AppStatus = [];
		$scope.searchParam = "";
		$scope.init = (applicationcode) => {

			APPCODE = applicationcode;

		};
		/*GET_DATA*/
		$scope.searchApplication = () => {
			$scope.errorMsg = "";
			$("#displayRecords").html("");
			if ($scope.searchParam == null || $scope.searchParam == '') {
				$scope.errorMsg = "Cannot search empty";
				$('#searchParam').focus();
				return;
			}
			BS.listBPApplicationsStatus((response) => {
				if (response != null && response.length > 0)
					$scope.setAppStatusTable(response);
				else
					$scope.errorMsg = "Application(s) not found";
			}, ($scope.searchParam));
		};

		$scope.setAppStatusTable = (tData) => {
			$("#displayRecords").html("");
			$("#displayRecords").html("<table id='displayRecordsTable' style='width: 100%; margin: auto;font-size:85%;' border='1' class='table  table-bordered  table-hover'></table>");
			var table = jQuery('#displayRecordsTable').DataTable({
				"data": tData,
				"columns": [
					{
						"title": "Slno",
						"data": "applicationcode",
						"width": "1%",
						render: function(data, type, row, meta) {
							return meta.row + 1;
						}
					}, {
						"title": "Application code",
						"data": "applicationcode",
						"width": "10%",
						render: (data, type, row, meta) => {
							let content = '<b>' + data + '</b>';
							content += '<br><small>eDCRNumber:</small> ' + row.edcrnumber;
							if (row.permitnumber != null)
								content += '<br><small>Permit Number:</small> ' + row.permitnumber;
							if (row.ownersname != null)
								content += '<br><small>Owner(s) Name:</small> ' + row.ownersname;
							return content;
						}
					}, {
						"title": "filename(.dxf) ",
						"data": "originalfilename",
						"width": "10%"
					}, {
						"title": "scrutiny date ",
						"data": "edcrdate",
						"width": "5%"
					}, {
						"title": "Site Address ",
						"data": "address",
						"width": "25%"
					}, {
						"title": "Updated By",
						"data": "updatedby",
						"width": "5%",
					}, {
						"title": "Current Process ",
						"data": "currentprocessname",
						"width": "15%"
					}, {
						"title": "Next Process",
						"data": "nextprocessname",
						"width": "15%",
						render: (data, type, row, meta) => {
							let status = data;
							if (row.rejectdate != null && row.rejectdate != '')
								status = 'NA';
							return status;
						}
					}, {
						"title": "Status",
						"data": "status",
						"width": "5%",
						render: (data, type, row, meta) => {
							let status = data;
							if (row.rejectdate != null && row.rejectdate != '')
								status = "Rejected";
							return status;
						}
					}, {
						"title": "Remarks",
						"data": "remarks",
						"width": "5%",
						render: (data, type, row, meta) => {
							let status = data;
							if (row.rejectdate != null && row.rejectdate != '')
								status = row.rejectremarks;
							return status;
						}
					}, {
						"title": "Date",
						"data": "appdate",
						"width": "4%",
					}
				],
				"searching": false,
				"createdRow": function(row, data, dataIndex) {
					$compile(angular.element(row).contents())($scope);
				}
			});
		}
	},
]);
