var app = angular.module("PermitApp", []);

app.controller('permitCtrl', function($scope, $compile, $timeout) {


	$scope.getpermitlist = function(criteria) {

		var param = {
			applicationcode: $scope.applicationcode,
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

				$("#searchresults").removeClass("d-none");
				$scope.setDataTable(data);

			},
			error: function(e) {

			}
		});



	};




	$scope.printpermit = function(edcrno) {

		console.log("edcr " + edcrno);

		$scope.param_edcrnumber = edcrno;

		console.log("value : " + $scope.param_edcrnumber);
		$timeout(function() {
			$("#printPermit").submit();
		}, 1);


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
					"title": "Entry Date",
					"data": "entrydate"
				},
				{
					"title": "Ownership Type",
					"data": "ownershipsubtype"
				},
				{
					"title": "Land Registration",
					"data": "landregistrationdetails"
				},
				{
					"title": "Action",
					"sortable": false,
					"data": "edcrnumber",
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

});

$(document).ready(function() {

	$(".collapse").on('shown.bs.collapse hidden.bs.collapse', function() {
		$('#form1')[0].reset();
		$("#displayRecords").html("");
		$("#searchresults").addClass("d-none");
	});


	var dateFormat = "dd-mm-yy", from = $("#fromentrydate").datepicker(
		{
			defaultDate: "0",
			changeMonth: true,
			dateFormat: dateFormat,
			maxDate: "0"

		}).on("change", function() {
			to.datepicker("option", "minDate", getDate(this));
		}), to = $("#toentrydate").datepicker({
			defaultDate: "0",
			changeMonth: true,
			dateFormat: dateFormat,
			maxDate: "0"

		}).on("change", function() {
			from.datepicker("option", "maxDate", getDate(this));
		});

	function getDate(element) {
		var date;
		try {
			date = $.datepicker.parseDate(dateFormat, element.value);
		} catch (error) {
			date = null;
		}

		return date;
	}
});



