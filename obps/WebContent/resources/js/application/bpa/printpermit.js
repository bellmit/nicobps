var app = angular.module("PermitApp", []);

app.controller('permitCtrl', function($scope, $compile, $timeout) {

	$scope.init = function(applicationcode) {


		if (applicationcode != "" && applicationcode != undefined) {
			$scope.applicationcode = applicationcode;
			$scope.paramexist = true;

			jQuery("#byappcode").collapse("show");

			$timeout(function() {

				$scope.getpermitlist('byappcode');

			}, 0);

		}


	}

	$scope.getpermitlist = function(criteria) {

		var param = {
			applicationcode: $scope.applicationcode,
			permitnumber: $scope.permitnumber,
			edcrnumber: $scope.edcrnumber,
			ownername: $scope.ownername,
			fromentrydate: $scope.fromentrydate,
			toentrydate: $scope.toentrydate,
			criteria: criteria


		};

		jQuery.ajax({
			type: 'POST',
			url: "./getPermitList.htm",
			dataType: "json",
			data: param,
			success: function(data) {
				console.log("data" + data)
				if ($scope.paramexist == true) {
					$("#searchresults").removeClass("d-none");
					$scope.setDataTable(data);
				} else {
					if (data != "" && data != null) {
						if (data[0].error == undefined || data[0].error == null) {
							$("#searchresults").removeClass("d-none");
							$scope.setDataTable(data);
						} else {
							alert("" + data[0].error);
						}
					} else {
						$("#searchresults").removeClass("d-none");
						$scope.setDataTable(data);
					}

				}


			},
			error: function(e) {

			}
		});



	};




	$scope.printpermit = function(permitno) {

		console.log("permitno " + permitno);
		window.open("./Report?status=2&permitnumber=" + permitno);

	};


	/*-----------------------------------------------------------------------------------------------------------------------------------*/
	$scope.setDataTable = function(obj) {

		var i = 1;
		jQuery("#displayRecords").html("");
		$("#displayRecords").html("<table id='displayRecordsTable' style='width: 100%; margin: auto;font-size:90%;' border='1' class='table  table-bordered  table-hover'></table>");
		jQuery('#displayRecordsTable').DataTable({
			dom: '<"top"i>rt<"bottom"lp>',
			data: obj,
			columns: [
				{
					"title": "Slno",
					"width": "1%",
					"render": function(data, type, full, meta) {
						return i++;
					}
				},
				{
					"title": "EDCR Number",
					"data": "edcrnumber",
					"width": "5%"
				},
				{
					"title": "filename(.dxf) ",
					"data": "originalfilename",
					"width": "7%"
				},
				{
					"title": "Application Code",
					"data": "applicationcode",
					"width": "5%"
				}, {
					"title": "Permit Number",
					"data": "permitnumber",
					"width": "5%"
				},

				{
					"title": "Owner Name",
					"data": "ownername",
					"width": "8%"
				},

				{
					"title": "Ownership SubType",
					"data": "ownershipsubtype",
					"width": "4%"
				},
				{
					"title": "Site Address ",
					"data": "address",
					"width": "20%"
				},
				{
					"title": "Current Process",
					"data": "fromprocessname",
					"width": "20%"
				}, {
					"title": "Next Process",
					"data": "toprocessname",
					"width": "20%"
				},


				{
					"title": "Action",
					"sortable": false,
					"data": "permitnumber",
					"width": "5%",
					"render": function(data, type, row, meta) {
						console.log("processcode :: "+ row.toprocesscode);
						let div ="<div></div>"
						if (row.toprocesscode==14){
							div = '<div style="text-align:center"><button style="padding:.1em; margin-right: .5em" value="Print" ng-click="printpermit(\'' + data + '\')" class=" btn btn-primary">Print</button>';
						}
						return div;
					}
				}
			],
			"sortable": false,
			"columnDefs": [
				{
					"width": "1%", "targets": 0, "searchable": false,
					"orderable": false,
				},

			],
			"bLengthChange": false,
			createdRow: function(row, data, dataIndex) {
				$compile(angular.element(row).contents())($scope);
			}
		});
	};

	jQuery(".collapse").on('shown.bs.collapse hidden.bs.collapse', function() {
		console.log("collapse");
		if ($scope.paramexist == true) {
			$scope.paramexist = false;
		} else {
			jQuery('#form1')[0].reset();
			jQuery("#displayRecords").html("");
			jQuery("#searchresults").addClass("d-none");
			$scope.from.datepicker("option", "maxDate", "0");
			$scope.to.datepicker("option", "maxDate", "0");
		}



	});


	$scope.dateFormat = "dd-mm-yy";
	$scope.from = jQuery("#fromentrydate").datepicker(
		{
			defaultDate: "0",
			changeMonth: true,
			dateFormat: $scope.dateFormat,
			maxDate: "0"

		}).on("change", function() {
			$scope.to.datepicker("option", "minDate", $scope.getDate(this));
		});

	$scope.to = jQuery("#toentrydate").datepicker({
		defaultDate: "0",
		changeMonth: true,
		dateFormat: $scope.dateFormat,
		maxDate: "0"

	}).on("change", function() {
		$scope.from.datepicker("option", "maxDate", $scope.getDate(this));
	});

	$scope.getDate = function(element) {
		try {
			$scope.date = $.datepicker.parseDate($scope.dateFormat, element.value);
		} catch (error) {
			$scope.date = null;
		}
		return $scope.date;
	}

});



