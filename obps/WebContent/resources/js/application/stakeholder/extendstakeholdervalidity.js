var app = angular.module("CommonApp", []);

app.controller('extendvalidityCtrl', function($scope, $compile, $timeout) {


 $scope.stakeholdersList = [];

	$scope.getValidity = function(criteria) {
		$("#extendedto").datepicker("destroy");
		var param = {
			usercode: $scope.usercode,

		};

		jQuery.ajax({
			type: 'POST',
			url: "./getValidity.htm",
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

	$scope.listStakeholders = function() {


		$("#extendedto").datepicker("destroy");
		 
		var param = {
			"officecode": $scope.officecode,

		};
		
		jQuery.ajax({
			url: "./listStakeholders.htm",
			dataType: 'json',
			data: param,
			type: "POST",
			success: function(data) {
	
				$timeout(() =>{
						$scope.stakeholdersList = data.listStakeholders;
				
				},0);
					 

			},
			error: function(request, status, error) {
				//alert(request.responseText);
			}
		});
	}
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
					"title": "Office Name",
					"data": "officename1"
				}, {
					"title": "Valid From",
					"data": "validfrom"
				},
				{
					"title": "Valid To",
					"data": "validto"
				},



				{
					"title": "Action",
					"sortable": false,
					"data": "officecode",
					"render": function(data, type, row, meta) {

						let div = '<div style="text-align:center"><button style="padding:.1em; margin-right: .5em" value="Print" ng-click="showextendvalidity(\'' + data + '\',\'' + row.validto + '\',\'' + row.usercode + '\')" class=" btn btn-primary">Extend Validity</button>';
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

	$scope.updateStakeholderValidity = (extendedto, usercode) => {

		$scope.extendedto = extendedto;
		$scope.usercode = usercode;
		
		var param = {
			"usercode": $scope.usercode,
			"extendedto": $scope.extendedto,
			"officecode": $scope.officecode2,
		};

		jQuery.ajax({
			type: 'POST',
			url: "./extendValidity.htm",
			dataType: "json",
			data: param,
			success: function(data) {

				if (data == true) {
					alert("Validity has been extended!");
					jQuery.fancybox.close();
					$("#extendedto").datepicker("destroy");
					$scope.getValidity();
				} else {
					alert("Something is wrong!!Validity could not be extended!");

				}


			},
			error: function(e) {

			}
		});
	};

	$scope.showextendvalidity = (officecode, validto, usercode) => {



		$scope.officecode2 = officecode;
		$scope.usercode = usercode;
		$scope.validto = validto;



		$scope.b = validto.split(/\D/);

		$scope.reverserdate = $scope.b.reverse().join('-');
		$scope.newvaliddate = new Date($scope.reverserdate); // Now
		$scope.newvaliddate.setDate($scope.newvaliddate.getDate() + 30); // Set now + 30 days as the new date
		$scope.dateFormat = "dd-mm-yy";

		$scope.extendedto = jQuery("#extendedto").datepicker(
			{

				defaultDate: $scope.validto,
				changeMonth: true,
				changeYear: true,
				dateFormat: $scope.dateFormat,
				minDate: $scope.validto,
				maxDate: $scope.newvaliddate

			});

		$scope.extendedto = "";
		jQuery.fancybox.close();
		jQuery.fancybox({
			href: '#dateTable',
			'autoSize': false,
			'transitionIn': 'elastic',
			'transitionOut': 'elastic',
			'speedIn': 600,
			'speedOut': 200,
			'overlayShow': false,
			'modal': false,
			'height': 'auto',
			'width': '500px'
		});

	};










});//end


