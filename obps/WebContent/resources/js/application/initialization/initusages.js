app.controller('initusagesCtrl', ['$scope', '$sce', '$compile', '$timeout', 'commonInitFactory', 'commonInitService',
	function($scope, $sce, $compile, $timeout, commonInitFactory, commonInitService) {
		var scope = angular.element($("#initusagesCtrl")).scope();
		commonInitService.success();
		/* Common Ajax Params */
		var successMsg = "Success: Usage created or updated successfully";
		var errorMsg = "Error: Unable to perform action or Already existing data";
		$scope.errorCallback = "";
		$scope.method = "POST";
		$scope.successCallback = "";
		$scope.urlEndpoint = "";

		/*------------------------*/

		$scope.usage = new Usage();
		$scope.usages = [];
		$scope.actionButton = 1;



		$scope.trustHTML = function(post) {
			return $sce.trustAsHtml(post);
		};
		//    $scope.officeDesc = function (offc) {
		//    	return offc.officename1+((offc.officename2)?(', '+offc.officename2):'')+((offc.officename3)?(', '+offc.officename3):'');
		//    };

		$scope.edit = function(usagecode) {
			$scope.IsDisabled = true;
//		alert(usagecode)
			$scope.usage.suboccupancies.suboccupancycode = 0;


			$scope.actionButton = 2;


			$scope.usages.forEach((o, x) => {

				if (o.usagecode == usagecode) {
//							alert("o.usagecode"+o.usagecode)
//						alert("o.suboccupancycode"+o.suboccupancycode)
//						alert("o.usagename"+o.usagename)
//						alert("o.description"+o.description)




					$scope.usage.suboccupancies.suboccupancycode = o.suboccupancycode;
					$scope.usage.usagecode = o.usagecode;
					$scope.usage.usagename = o.usagename;
					$scope.usage.description = o.description;

					$scope.usages.push({
						//	userpagecode: 0,
						//            feeamount: $scope.user.usercode,
						usage: o,
					});

					//alert("officecode"+$scope.usage.officecode)
				}
			});
			//        $scope.listOfficeCells($scope.user.officecode);
			jQuery('html, body').animate({
				scrollTop: 0
			}, 2000);
		};

		$scope.reset = function() {
			$scope.listUsages();
				$scope.IsDisabled = false;
			$scope.usage = new Usage();
			$scope.actionButton = 1;
		};

		$scope.save = function() {

			$scope.validateusageForm();


			$scope.usage.suboccupancycode = $scope.usage.suboccupancies.suboccupancycode;

//			alert("usage .suboccupancycode"+$scope.usage.suboccupancycode)

			//			if ($scope.usageForm.$invalid)
			//				return false;

			$scope.method = "POST";
			$scope.urlEndpoint = "./initusages.htm";

			commonInitService.save($scope.method, $scope.urlEndpoint, $scope.usage, function (response) {
				if (response.data=="Success") {
					MsgBox("Usages Inserted successfully.");
					$scope.reset();
					$scope.listUsages()
				} else if (response.data=="Exist") {
					MsgBox("Usages already exist");
					$scope.listUsages()
				}
				
				else if (response.data=="suboccupancycodesizeerror") {
					MsgBox("Sub Occupancy Code Cannot be more than 10 characters");
					$scope.listUsages()
				
				}else if (response.data=="suboccupancycodenull") {
					MsgBox("Sub Occupancy Code Cannot Be Null");
					$scope.listUsages()
				}else if (response.data=="usagecodecharactererror") {
					MsgBox("No Special Characters or Numbers allowed in Usage Code");
					$scope.listUsages()
				
				}else if (response.data=="usagecodesizeerror") {
					MsgBox("Usage Code Cannot be more than 20 characters");
					$scope.listUsages()
				}else if (response.data=="usagecodenull") {
					MsgBox("Usage Code Cannot be Null");
					$scope.listUsages()
				}else if (response.data=="usagenamecharactererror") {
					MsgBox("No Special Characters or Numbers allowed in Usage Name");
					$scope.listUsages()
				
				}else if (response.data=="usagenamesizeerror") {
					MsgBox("Usage Name Cannot be more than 255 characters");
					$scope.listUsages()
				}else if (response.data=="usagenamenull") {
					MsgBox("Usage Name Cannot be Null");
					$scope.listUsages()
				}else if (response.data=="descriptionsizeerror") {
					MsgBox("Description Cannot be more than 250 characters");
					$scope.listSubOccupancy()
				}else if (response.data=="descriptionnull") {
					MsgBox("Description Cannot be Null");
					$scope.listSubOccupancy()
				}
				else if(response.data=="Error"){
				alert("Error");
				
				}
				
				
			}, function () {
				
				alert("Error");
				$scope.reset();
				$scope.listUsages()
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
			$scope.validateusageForm();

			$scope.usage.suboccupancycode = $scope.usage.suboccupancies.suboccupancycode;

			//$scope.usage.usagecode=usagecode;
//			alert("usagecode"+$scope.usage.usagecode)
//			alert("usage update suboccupancycode"+$scope.usage.suboccupancycode+"usagename"+$scope.usage.usagename+"description"+$scope.usage.description)
//			if ($scope.usageForm.$invalid)
//				return false;
			$scope.method = "POST";
			$scope.urlEndpoint = "./updateusages.htm";
			commonInitService.save($scope.method, $scope.urlEndpoint, $scope.usage, function (response) {
				if (response.data=="Success") {
					MsgBox("Usages Inserted successfully.");
					$scope.reset();
					$scope.listUsages()
				} else if (response.data=="Exist") {
					MsgBox("Usages already exist");
					$scope.listUsages()
				}
				
				else if (response.data=="suboccupancycodesizeerror") {
					MsgBox("Sub Occupancy Code Cannot be more than 10 characters");
					$scope.listUsages()
				
				}else if (response.data=="suboccupancycodenull") {
					MsgBox("Sub Occupancy Code Cannot Be Null");
					$scope.listUsages()
				}else if (response.data=="usagecodesizeerror") {
					MsgBox("Usage Code Cannot be more than 20 characters");
					$scope.listUsages()
				}else if (response.data=="usagecodenull") {
					MsgBox("Usage Code Cannot be Null");
					$scope.listUsages()
				}else if (response.data=="usagenamesizeerror") {
					MsgBox("Usage Name Cannot be more than 255 characters");
					$scope.listUsages()
				}else if (response.data=="usagenamenull") {
					MsgBox("Usage Name Cannot be Null");
					$scope.listUsages()
				}else if (response.data=="descriptionsizeerror") {
					MsgBox("Description Cannot be more than 250 characters");
					$scope.listSubOccupancy()
				}else if (response.data=="descriptionnull") {
					MsgBox("Description Cannot be Null");
					$scope.listSubOccupancy()
				}
				else if(response.data=="Error"){
				alert("Error");
				
				}
				
				
			}, function () {
				
				alert("Error");
				$scope.reset();
				$scope.listUsages()
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
						"title": "Suboccupancy Code",
						"data": "suboccupancycode"
					},

					{
						"title": "Usage Code",
						"data": "usagecode"
					},

					{
						"title": "Usage Name",
						"data": "usagename"
					},
					{
						"title": "Description",
						"data": "description"
					},


					{
						"title": "Action",
						"sortable": false,
						"data": "usagecode",
						"render": function(data, type, row, meta) {
							let status = row.enabled == 'Y' ? 'Disable' : 'Enable';
							let div = '<div style="text-align:center"><button style="padding:.1em; margin-right: .5em" value="Edit" ng-click="edit(\''+data+'\')" class="button-primary">Edit</button>';
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

		$scope.validateusageForm = function() {
			//alert("validate"+$scope.usage.offices.officecode)




			if ($scope.usage.suboccupancies.suboccupancycode === "" || $scope.usage.suboccupancies.suboccupancycode === 0 || $scope.usage.suboccupancies.suboccupancycode === null) {
				jQuery("#suboccupancycode").focus();
				alert("suboccupancy cannot be empty");
				return false;
			}

			if ($scope.usage.usagecode === "" || $scope.usage.usagecode === 0 || $scope.usage.usagecode === null) {
				jQuery("#usagecode").focus();
				alert("Usage cannot be empty");
				return false;
			}

			if ($scope.usage.usagename === "" || $scope.usage.usagename === 0 || $scope.usage.usagename === null) {
				jQuery("#usagename").focus();
				alert(" Usage name cannot be empty");
				return false;
			}

			if ($scope.usage.description === "" || $scope.usage.description === 0 || $scope.usage.description === null) {
				jQuery("#description").focus();
				alert("description cannot be empty");
				return false;
			}

			return true;
		};

		/* READ DATA */
		$scope.listUsages = (usagecode = 0) => {

			if (usagecode == 0) {
				commonInitFactory.listUsages((response) => {
					$scope.usages = response;
					$scope.setDataTable($scope.usages);
				});
			} else {
				commonInitFactory.listUsages((response) => {
					$scope.usages.forEach((o, x) => {
						if (o.usagecode == usagecode) {
							$scope.usages[x] = response;
						}
					});
					$scope.setDataTable($scope.usages);
				}, usagecode);
			}

		};
		$scope.listUsages();



	}]);



