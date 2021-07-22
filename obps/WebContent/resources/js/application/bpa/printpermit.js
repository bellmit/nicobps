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
							alert("Error :" + data[0].error);
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
		jQuery("#displayRecords").html("<table id='displayRecordsTable' class='table table-striped' style='width:100%;' > </table>");
		jQuery('#displayRecordsTable').DataTable({
			data: obj,
			columns: [
				{
					"title": "Slno",
					"render": function(data, type, full, meta) {
						return i++;
					}
				},
				{
					"title": "Application Code",
					"data": "applicationcode"
				}, {
					"title": "Permit Number",
					"data": "permitnumber"
				},
				{
					"title": "EDCR Number",
					"data": "edcrnumber"
				},
				{
					"title": "Owner Name",
					"data": "ownername"
				},

				{
					"title": "Ownership SubType",
					"data": "ownershipsubtype"
				},
				{
					"title": "Entry Date",
					"data": "entrydate"
				},

				{
					"title": "Action",
					"sortable": false,
					"data": "permitnumber",
					"render": function(data, type, row, meta) {

						let div = '<div style="text-align:center"><button style="padding:.1em; margin-right: .5em" value="Print" ng-click="printpermit(\'' + data + '\')" class=" btn btn-primary">Print</button>';
						return div;
					}
				}
			],
			"sortable": false,
			"columnDefs": [
				{
					"width": "2%", "targets": 0, "searchable": false,
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



