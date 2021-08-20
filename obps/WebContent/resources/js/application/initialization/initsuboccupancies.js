app.controller('initsuboccupanciesCtrl', ['$scope', '$sce', '$compile', '$timeout', 'commonInitFactory', 'commonInitService',
	function($scope, $sce, $compile, $timeout, commonInitFactory, commonInitService) {
		var scope = angular.element($("#initsuboccupanciesCtrl")).scope();
		commonInitService.success();
		/* Common Ajax Params */
		var successMsg = "Success: Sub Occupancy created or updated successfully";
		var errorMsg = "Error: Unable to perform action or Already existing data";
		$scope.errorCallback = "";
		$scope.method = "POST";
		$scope.successCallback = "";
		$scope.urlEndpoint = "";

		/*------------------------*/
 
		$scope.suboccupancy = new SubOccupancy();
		$scope.suboccupancies = [];
		$scope.actionButton = 1;



		$scope.trustHTML = function(post) {
			return $sce.trustAsHtml(post);
		};
		//    $scope.officeDesc = function (offc) {
		//    	return offc.officename1+((offc.officename2)?(', '+offc.officename2):'')+((offc.officename3)?(', '+offc.officename3):'');
		//    };

		$scope.edit = function(suboccupancycode) {
			$scope.IsDisabled = true;
//		alert(suboccupancycode)
			$scope.suboccupancy.occupancies.occupancycode = 0;


			$scope.actionButton = 2;


			$scope.suboccupancies.forEach((o, x) => {

				if (o.suboccupancycode == suboccupancycode) {
//							alert("o.suboccupancycode"+o.suboccupancycode)
//						alert("o.occupancycode"+o.occupancycode)
//						alert("o.suboccupancyname"+o.suboccupancyname)
//						alert("o.description"+o.description)




					$scope.suboccupancy.occupancies.occupancycode = o.occupancycode;
					$scope.suboccupancy.suboccupancycode = o.suboccupancycode;
					$scope.suboccupancy.suboccupancyname = o.suboccupancyname;
					$scope.suboccupancy.description = o.description;

					$scope.suboccupancies.push({
						//	userpagecode: 0,
						//            feeamount: $scope.user.usercode,
						suboccupancy: o,
					});

					//alert("officecode"+$scope.suboccupancy.officecode)
				}
			});
			//        $scope.listOfficeCells($scope.user.officecode);
			jQuery('html, body').animate({
				scrollTop: 0
			}, 2000);
		};

		$scope.reset = function() {
				$scope.listSubOccupancy();
			$scope.IsDisabled = false;
			$scope.suboccupancy = new SubOccupancy();
			$scope.actionButton = 1;
		};

		$scope.save = function() {

			$scope.validateSubOccupancyForm();


			$scope.suboccupancy.occupancycode = $scope.suboccupancy.occupancies.occupancycode;

			//alert("suboccupancy licenseecode"+$scope.suboccupancy.licenseetypecode)

			//			if ($scope.suboccupancyForm.$invalid)
			//				return false;

			$scope.method = "POST";
			$scope.urlEndpoint = "./initsuboccupancies.htm";

			
			commonInitService.save($scope.method, $scope.urlEndpoint, $scope.suboccupancy, function (response) {
				if (response.data=="Success") {
					MsgBox("Sub Occupancies Inserted successfully.");
					$scope.reset();
					$scope.listSubOccupancy()
				} else if (response.data=="Exist") {
					MsgBox("Occupancies already exist");
					$scope.listSubOccupancy()
				}
				else if(response.data=="Error"){
				MsgBox("Error");
				
				}
				else{
				MsgBox(response.data)
				$scope.listSubOccupancy()
				}
				
				
			}, function () {
				
				alert("Error");
				$scope.reset();
				$scope.listSubOccupancy()
			});
		};

		//    $scope.toggleUserStatus= function (usercode) {
		//    	$scope.users.forEach((o, x) => {
		//        	if (o.usercode == usercode){
		//        		$scope.user = o;
		//        	}
		//        });
		//    	$scope.method = "POST";
		//    	$scope.urlEndpoint = "./updateuser/status";
		//    	commonInitService.save($scope.method, "./ableuser.htm", $scope.user, () => {$scope.reset();$scope.listLicensees(licenseeregistrationcode);}, () =>{alert("failed")});
		//    	
		//    };
		//    
		$scope.update = () => {
			$scope.validateSubOccupancyForm();

			$scope.suboccupancy.occupancycode = $scope.suboccupancy.occupancies.occupancycode;

			//$scope.suboccupancy.suboccupancycode=suboccupancycode;
			//alert("suboccupancycode"+$scope.suboccupancy.suboccupancycode)
			//alert("suboccupancy update licenseecode"+$scope.suboccupancy.licenseetypecode+"feetypecode"+$scope.suboccupancy.feetypecode+"officecode"+$scope.suboccupancy.officecode+"feeamount:"+$scope.suboccupancy.feeamount)
			if ($scope.suboccupancyForm.$invalid)
				return false;
			$scope.method = "POST";
			$scope.urlEndpoint = "./updatesuboccupancy.htm";
			commonInitService.save($scope.method, $scope.urlEndpoint, $scope.suboccupancy, function (response) {
				if (response.data=="Success") {
					MsgBox("Sub Occupancies Updated successfully.");
					$scope.reset();
					$scope.listSubOccupancy()
				} else if (response.data=="Exist") {
					MsgBox("Occupancies already exist");
					$scope.listSubOccupancy()
				}
				else if(response.data=="Error"){
				MsgBox("Error");
				
				}
				else{
				MsgBox(response.data)
				$scope.listSubOccupancy()
				}
				
				
			}, function () {
				
				alert("Error");
				$scope.reset();
				$scope.listSubOccupancy()
			});
		}



		$scope.setDataTable = function(obj) {

			jQuery("#displayRecords").html("");
			jQuery("#displayRecords").html("<table id='displayRecordsTable' style='width:100%' border='1'>\n\
                                                </table>");
			jQuery('#displayRecordsTable').DataTable({
				data: obj,
				columns: [
					{
						"title": "Occupancy Code",
						"data": "occupancycode"
					},

					{
						"title": "Sub Occupancy Code",
						"data": "suboccupancycode"
					},

					{
						"title": "Sub Occupancy Name",
						"data": "suboccupancyname"
					},
					{
						"title": "Description",
						"data": "description"
					},


					{
						"title": "Action",
						"sortable": false,
						"data": "suboccupancycode",
						"render": function(data, type, row, meta) {
							let status = row.enabled == 'Y' ? 'Disable' : 'Enable';
							let div = '<div style="text-align:center"><button style="padding:.1em; margin-right: .5em" value="Edit" ng-click="edit(\''+data+'\')" class="btn btn-primary b-btn" >Edit</button>';
							//                    		div += '<button style="padding:.1em; margin-right: .5em" value="Edit" ng-click="toggleUserStatus(' + data + ')" class="button-primary">'+status+'</button></div>';
							return div;
						}
					}
				],
				"sortable": false,
				"columnDefs": [
					{ "width": "2%", "targets": 0 }
				],
				"bLengthChange": false,
				createdRow: function(row, data, dataIndex) {
					$compile(angular.element(row).contents())($scope);
				}
			});
		};

		$scope.validateSubOccupancyForm = function() {
			//alert("validate"+$scope.suboccupancy.offices.officecode)




			if ($scope.suboccupancy.occupancies.occupancycode === "" || $scope.suboccupancy.occupancies.occupancycode === 0 || $scope.suboccupancy.occupancies.occupancycode === null) {
				jQuery("#occupancycode").focus();
				alert("occupancy cannot be empty");
				return false;
			}

			if ($scope.suboccupancy.suboccupancycode === "" || $scope.suboccupancy.suboccupancycode === 0 || $scope.suboccupancy.suboccupancycode === null) {
				jQuery("#suboccupancycode").focus();
				alert("sub occupancy   cannot be empty");
				return false;
			}

			if ($scope.suboccupancy.suboccupancyname === "" || $scope.suboccupancy.suboccupancyname === 0 || $scope.suboccupancy.suboccupancyname === null) {
				jQuery("#suboccupancyname").focus();
				alert("sub occupancy name cannot be empty");
				return false;
			}

			if ($scope.suboccupancy.description === "" || $scope.suboccupancy.description === 0 || $scope.suboccupancy.description === null) {
				jQuery("#description").focus();
				alert("description cannot be empty");
				return false;
			}

			return true;
		};

		/* READ DATA */
		$scope.listSubOccupancy = (suboccupancycode = 0) => {

			if (suboccupancycode == 0) {
				commonInitFactory.listSubOccupancy((response) => {
					$scope.suboccupancies = response;
					$scope.setDataTable($scope.suboccupancies);
				});
			} else {
				commonInitFactory.listSubOccupancy((response) => {
					$scope.suboccupancies.forEach((o, x) => {
						if (o.suboccupancycode == suboccupancycode) {
							$scope.suboccupancies[x] = response;
						}
					});
					$scope.setDataTable($scope.suboccupancies);
				}, suboccupancycode);
			}

		};
		$scope.listSubOccupancy();



	}]);



