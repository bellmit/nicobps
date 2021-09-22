var app = angular.module("CommonApp", []);

app.controller('suspendstakeholderCtrl', function($scope, $compile, $timeout) {


	$scope.stakeholdersList = [];


	$scope.listStakeholders = function() {



		var param = {
			"officecode": $scope.officecode,

		};

		jQuery.ajax({
			url: "./listSuspendStakeholders.htm",
			dataType: 'json',
			data: param,
			type: "POST",
			success: function(data) {

				$("#searchresults").removeClass("d-none");
				$scope.setDataTable(data);


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
					"title": "User",
					"data": "fullname"
				},
				{
					"title": "Remarks",
					"data": "remarks"
				},



				{
					"title": "Action",
					"sortable": false,
					"data": "usercode",
					"render": function(data, type, row, meta) {
						let status = row.enabled == 'Y' ? 'Disable' : 'Enable';
						console.log("row.enabled" + row.enabled);
						console.log("status1" + status);
						let div = '';
						if (status == "Disable") {
							console.log("statusD" + status);
							div += '<div style="text-align:center"><button style="padding:.1em; margin-right: .5em" value="Disable" ng-click="showRemarks(' + data + ')" id="enable_disable' + data + '" class="btn btn-warning" >Disable</button>';
						} else {
							console.log("statusE" + status);
							div += '<div style="text-align:center"><button style="padding:.1em; margin-right: .5em" value="Enable" ng-click="showRemarks(' + data + ')" id="enable_disable' + data + '" class="btn btn-success" >Enable</button>';
						}


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

$scope.showRemarks = (p) => {
				$scope.usercode = p;
				$scope.remarks = "";
				jQuery.fancybox.close();
				jQuery.fancybox({
					href: '#remarksTable',
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

		$scope.enabledisable = function() {
			 console.log($scope.usercode);
			   console.log(jQuery('#enable_disable'+$scope.usercode).attr('id')); 
			   console.log($('#enable_disable'+$scope.usercode).val()); 
			   console.log($('#enable_disable'+$scope.usercode).text()); 
//				$('#enable_disable'+usercode).toggleClass('highlight')
//			  $('#enable_disable'+usercode).text( $('#enable_disable'+usercode).text() == 'Disable' ? 'Enable' : 'Disable' );
//			$('#enable_disable' + usercode).val($('#enable_disable' + usercode).val() == 'Disable' ? 'N' : 'Y');

			$scope.enable_disable = $('#enable_disable' + $scope.usercode).val() == 'Disable' ? 'N' : 'Y';
			console.log("scope value::" + $scope.enable_disable);
			var param = {
				"usercode": $scope.usercode,
				"remarks":$scope.remarks,
				"enabledisable": $scope.enable_disable,
			};
			jQuery.ajax({
				type: 'POST',
				url: "./enabledisablestakeholder.htm",
				dataType: "json",
				data: param,
				success: function(data) {

					if (data == "1") {
						MsgBox("Successfully Enabled/Disabled.");
						$scope.listStakeholders();

					} else {
						alert("Could not process data!Something is wrong!!");

					}


				},
				error: function(request, status, error) {
					alert("* Error : " + request.responseText);
				}
			});
		};



});//end


