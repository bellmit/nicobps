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
		console.log("BPA Common");

		$scope.colindex = new Set();

		var table_columns = [{
			"title": "Slno",
			"data": "edrnumber",
			"width": "1%",
			render: function(data, type, row, meta) {
				return meta.row + 1;
			}
		}, {
			"title": "Application code",
			"data": "applicationcode",
			"width": "15%",
			render: (data, type, row, meta) => {
				let content = "";
				if (data != null) {
					content = '<b>' + data + '</b>';
					//							content += '<br><small>eDCRNumber:</small> ' + row.edcrnumber;
					if (row.permitnumber != null)
						content += '<br><small>Permit Number:</small> ' + row.permitnumber;
					if (row.ownername != null)
						content += '<br><small>Owner(s) Name:</small> ' + row.ownername;
					else if (row.ownersname != null)
						content += '<br><small>Owner(s) Name:</small> ' + row.ownersname;


					return content;
				} else {
					$scope.colindex.add(1);
					return content;
				}


			}
		}, {
			"title": "EDCR Number",
			"data": "edcrnumber",
			"width": "5%",
			render: (data, type, row, meta) => {
				if (data == null) {
					$scope.colindex.add(2);
					return "";
				} else
					return data;
			}

		}, {
			"title": "filename(.dxf) ",
			"data": "originalfilename",
			"width": "5%",
			render: (data, type, row, meta) => {
				if (data == null) {
					$scope.colindex.add(3);
					return "";
				} else
					return data;
			}

		}, {
			"title": "scrutiny date ",
			"data": "edcrdate",
			"width": "5%",
			render: (data, type, row, meta) => {
				if (data == null) {
					$scope.colindex.add(4);
					return "";
				} else
					return data;
			}
		}, {
			"title": "Site Address ",
			"data": "address",
			"width": "14%",
			render: (data, type, row, meta) => {
				if (data == null) {
					$scope.colindex.add(5);
					return "";
				} else
					return data;
			}
		}, //45
		{
			"title": "Current Process ",
			"data": "currentprocessname",
			"width": "10%",
			render: (data, type, row, meta) => {
				if (data == null) {
					$scope.colindex.add(6);
					return "";
				} else
					return data;
			}
		}, {
			"title": "Current User",
			"data": "fromusername",
			"width": "10%",
			render: (data, type, row, meta) => {
				if (data == null) {
					$scope.colindex.add(7);
					return "";
				} else
					return data;
			}
		}, {
			"title": "Remarks",
			"data": "remarks",
			"width": "10%",
			render: (data, type, row, meta) => {
				let status = data;
				if (row.rejectdate != null && row.rejectdate != '') {
					status = row.rejectremarks;
				} else {
					if (data == null) {
						status = "";
						$scope.colindex.add(8);
					}
				}

				return status;
			}
		}, {
			"title": "Next Process",
			"data": "nextprocessname",
			"width": "10%",
			render: (data, type, row, meta) => {

				if (data == null || data == '')
					return "Apply for BPA";
				else
					return data;

			}
		}, {
			"title": "Next User ",
			"data": "tousername",
			"width": "10%",
			render: (data, type, row, meta) => {

				if (data == null) {

					$scope.colindex.add(10);
					return "";
				} else
					return data;
			}
		}, //40  = 85 
		{
			"title": "Is Tatkal ",
			"data": "istatkal",
			"width": "1%",
			render: (data, type, row, meta) => {
				if (data == null) {
					$scope.colindex.add(11);
					return "";
				} else
					return data == 'Y' ? 'YES' : 'NO';
			}
		}, {
			"title": "Date",
			"data": "appdate",
			"width": "4%",
			render: (data, type, row, meta) => {
				if (data == null) {
					$scope.colindex.add(12);
					return "";
				} else
					return data;
			}
		}
			// 15 = 100
		];

		$scope.displaycols = [];

		$scope.BPA = [];


		$scope.init = (page, processcode, applicationcode) => {


			$scope.page = page.toString().trim();
			$scope.processcode = processcode;
			$scope.search = (page == "bpasearch" ? true : false);

			console.log("search  : " + $scope.search);
			if ($scope.page == "bpastatus") {
				APPCODE = applicationcode;

			};
		}

		/*GET_DATA*/
		$timeout(function() {


			if ($scope.page == "bpainbox") {
				$scope.displaycols = table_columns;
				jQuery("#ajaxLoading").fadeIn();
				$scope.listBPApplications = () => {
					BS.listBPApplications((response) => {
						$scope.BPA = response;
						$scope.setBPAsTable($scope.BPA, $scope.displaycols);
						jQuery("#ajaxLoading").fadeOut();
					}, (data = $scope.processcode));
				};
				$scope.listBPApplications();

			} else if ($scope.page == "bpastatus") {

				$scope.displaycols = table_columns;
				jQuery("#ajaxLoading").fadeIn();
				$scope.listApplictionsCurrentProcessStatus = () => {
					BS.listApplictionsCurrentProcessStatus((response) => {
						$scope.BPA = response;
						$scope.setBPAsTable($scope.BPA, $scope.displaycols);
						jQuery("#ajaxLoading").fadeOut();
					}, (data = ""));
				};
				$scope.listApplictionsCurrentProcessStatus();
			} else if ($scope.page == "buildingpermit") {
				$scope.displaycols = table_columns;
				jQuery("#ajaxLoading").fadeIn();
				$scope.listAppScrutinyDetailsForBPA = () => {
					BS.listAppScrutinyDetailsForBPAV2((response) => {
						$scope.BPA = response;
						$scope.setBPAsTable($scope.BPA, $scope.displaycols);
						jQuery("#ajaxLoading").fadeOut();
					}, (data = ""));
				};
				$scope.listAppScrutinyDetailsForBPA();
			} else if ($scope.page == "bpasearch") {
				$scope.displaycols = table_columns;
			}

		}, 100);



		$scope.searchApplication = () => {

			$scope.displaycols = table_columns;
			$scope.errorMsg = "";
			$("#displayRecords").html("");
			if ($scope.searchParam == null || $scope.searchParam == '') {
				$scope.errorMsg = "Cannot search empty";
				$('#searchParam').focus();
				return;
			}
			jQuery("#ajaxLoading").fadeIn();
			BS.listBPApplicationsStatus((response) => {
				if (response != null && response.length > 0)
					//							$scope.setBPAsTable(response);
					$scope.setBPAsTable(response, $scope.displaycols);
				else
					$scope.errorMsg = "Application(s) not found";

				jQuery("#ajaxLoading").fadeOut();
			}, ($scope.searchParam));
		};



		$scope.setBPAsTable = (tData, displaycols) => {
			$("#displayRecords").html("");
			$("#displayRecords").html("<table id='displayRecordsTable' style='width: 100%; margin: auto;font-size:85%;' border='1' class='table  table-bordered  table-hover'></table>");
			var table = jQuery('#displayRecordsTable').DataTable({
				"data": tData,
				"columns": displaycols,
				//				"columnDefs": [{"targets":[1],"visible":false,"searchable":false}],
				//				"stateSave": true,
				"createdRow": function(row, data, dataIndex) {
					$compile(angular.element(row).contents())($scope);
				},
				"rowCallback": function(row, data, index) {
					//					if ($scope.page == "bpainbox") {
					if (data.istatkal == "Y") {
						$('td', row).addClass('table-danger');
						// $('td', row).css('color', 'white');
					}

					//					}
				}


			});



			var emptyflag = 1;

			for (let col of $scope.colindex) {


				jQuery.each(table.column(col).data(), function(key, val) {

					if (val != null && val != "") {
						console.log("not empty");
						emptyflag = 0;
					}

				});

				if (emptyflag == 1) {

					table.column(col).visible(false, false);
				}

				emptyflag = 1;
			}


			table.columns.adjust().draw();


			table.on('click', 'tr', function() {
				if ($scope.page != "bpasearch" && $scope.page != "bpastatus") {
					var data = table.row(this).data();
					if (data.applicationcode == null || data.applicationcode == '')
						$window.location.href = "applybuildingpermit.htm?edcrnumber=" + data.edcrnumber;
					else
						$window.location.href = data.pageurl + "?applicationcode=" + data.applicationcode;
				}


			});


			if ($scope.page == "bpastatus") {

				try {

					if (APPCODE != null && APPCODE != '' && APPCODE.length > 10) {
						table.search(APPCODE).draw();
					}
				} catch (e) { console.log(e); }
			}



		}
	},
]);
