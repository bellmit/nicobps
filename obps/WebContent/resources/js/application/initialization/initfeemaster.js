app.controller('createfeemasterCtrl', ['$scope', '$sce', '$compile', '$timeout', 'commonInitFactory', 'commonInitService',
	function($scope, $sce, $compile, $timeout, commonInitFactory, commonInitService) {
		var scope = angular.element($("#createfeemasterCtrl")).scope();
		commonInitService.success();
		/* Common Ajax Params */
		var successMsg = "Success: Fee Master created or updated successfully";
		var errorMsg = "Error: Unable to perform action or Already Existing Data";
		$scope.errorCallback = "";
		$scope.method = "POST";
		$scope.successCallback = "";
		$scope.urlEndpoint = "";

		/*------------------------*/

		$scope.feemaster = new FeeMaster();
		$scope.feemasters = [];
		$scope.actionButton = 1;



		$scope.trustHTML = function(post) {
			return $sce.trustAsHtml(post);
		};
		//    $scope.officeDesc = function (offc) {
		//    	return offc.officename1+((offc.officename2)?(', '+offc.officename2):'')+((offc.officename3)?(', '+offc.officename3):'');
		//    };

		$scope.edit = function(feecode) {

			$scope.feemaster.licenseetypes.licenseetypecode = 0;

			$scope.feemaster.offices.officecode = 0;
			$scope.feemaster.feetypes.feetypecode = 0;

			$scope.actionButton = 2;
			$scope.feemaster = new FeeMaster();

			$scope.feemasters.forEach((o, x) => {

				if (o.feecode == feecode) {
					//		alert("o.feecode"+o.feecode)
					//	alert("o.officecode"+o.officecode)
					//	alert("o.feetypecode"+o.feetypecode)
					//	alert("o.feeamount"+o.feeamount)
					//	alert("o.licenseetypecode"+o.licenseetypecode)




					$scope.feemaster.licenseetypes.licenseetypecode = o.licenseetypecode;
					$scope.feemaster.feetypes.feetypecode = o.feetypecode;
					$scope.feemaster.offices.officecode = o.officecode;
					$scope.feemaster.feeamount = o.feeamount;
					$scope.feemaster.feecode = o.feecode;

					$scope.feemasters.push({
						//	userpagecode: 0,
						//            feeamount: $scope.user.usercode,
						feemaster: o,
					});

					//alert("officecode"+$scope.feemaster.officecode)
				}
			});
			//        $scope.listOfficeCells($scope.user.officecode);
			jQuery('html, body').animate({
				scrollTop: 0
			}, 2000);
		};

		$scope.reset = function() {
		$scope.listFeeMaster();
			$scope.feemaster = new FeeMaster();
			$scope.actionButton = 1;
		};

		$scope.save = function() {

			$scope.validateFeeMasterForm();


			$scope.feemaster.licenseetypecode = $scope.feemaster.licenseetypes.licenseetypecode;
			$scope.feemaster.feetypecode = $scope.feemaster.feetypes.feetypecode;
			$scope.feemaster.officecode = $scope.feemaster.offices.officecode;
			//alert("feemaster licenseecode"+$scope.feemaster.licenseetypecode)

			//			if ($scope.feemasterForm.$invalid)
			//				return false;

			$scope.method = "POST";
			$scope.urlEndpoint = "./initfeemaster.htm";

			commonInitService.save($scope.method, $scope.urlEndpoint, $scope.feemaster, (response) => { 
			
			if(response.data=="licensetypecodecharactererror")
				MsgBox("Only Numeric Characters Allowed in License Type Code");
			else if(response.data=="licensetypecodesizeerror")
				MsgBox("License Type Code Size Exceeds limit in License Type Code. Enter less than 5 digits");
			else if(response.data=="officecodecharactererror")
				MsgBox("Only Numeric Characters Allowed in Office Code");
			else if(response.data=="officecodesizeerror")
				MsgBox("Office Code Size Exceeds limit. Enter less than 5 digits ");
			else if(response.data=="feecodecharactererror")
				MsgBox("Only Numeric Characters Allowed in Fee Code");
			else if(response.data=="feecodesizeerror")
				MsgBox("Fee Code Size Exceeds limit. Enter less than 5 digits");
			else if(response.data=="feecodenullerror")
				MsgBox("Fee Code Cannot be Null");
			else if(response.data=="feetypecodecharactererror")
				MsgBox("Only Numeric Characters Allowed in Fee Type Code");
			else if(response.data=="feetypecodesizeerror")
				MsgBox("Fee Type Code Size Exceeds limit. Enter less than 5 digits");
			else if(response.data=="feetypecodenullerror")
				MsgBox("Fee Type Code Cannot be Null");
			else if(response.data=="feeamountnumbererror")
				MsgBox("Only Numeric Characters Allowed in Fee Amount");
			else if(response.data=="feeamountsizeerror")
				MsgBox("Fee Amount Size Exceeds limit. Enter less than 5 digits");
			else if(response.data=="feeamountnullerror")
				MsgBox("Fee Amount Cannot be Null");
			else if(response.data=="Error")
				MsgBox("Error Inserting Data");
			else if(response.data=="Success"){
				MsgBox(successMsg);
				$scope.reset(); 
			}
				
			else{
				MsgBox("Error");			
				
			}
			$scope.listFeeMaster();  
			
			}, () => {
			 alert(errorMsg) 
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
			$scope.validateFeeMasterForm();

			$scope.feemaster.licenseetypecode = $scope.feemaster.licenseetypes.licenseetypecode;
			$scope.feemaster.feetypecode = $scope.feemaster.feetypes.feetypecode;
			$scope.feemaster.officecode = $scope.feemaster.offices.officecode;
			//$scope.feemaster.feecode=feecode;
			//alert("feecode"+$scope.feemaster.feecode)
			//alert("feemaster update licenseecode"+$scope.feemaster.licenseetypecode+"feetypecode"+$scope.feemaster.feetypecode+"officecode"+$scope.feemaster.officecode+"feeamount:"+$scope.feemaster.feeamount)
			if ($scope.feemasterForm.$invalid)
				return false;
			$scope.method = "POST";
			$scope.urlEndpoint = "./updatefeemaster.htm";
			commonInitService.save($scope.method, $scope.urlEndpoint, $scope.feemaster, (response) => { 
			
			if(response.data=="licensetypecodecharactererror") 
				MsgBox("Only Numeric Characters Allowed in License Type Code");
			else if(response.data=="licensetypecodesizeerror")
				MsgBox("License Type Code Size Exceeds limit in License Type Code. Enter less than 5 digits");
			else if(response.data=="officecodecharactererror")
				MsgBox("Only Numeric Characters Allowed in Office Code");
			else if(response.data=="officecodesizeerror")
				MsgBox("Office Code Size Exceeds limit. Enter less than 5 digits ");
			else if(response.data=="feecodecharactererror")
				MsgBox("Only Numeric Characters Allowed in Fee Code");
			else if(response.data=="feecodesizeerror")
				MsgBox("Fee Code Size Exceeds limit. Enter less than 5 digits");
			else if(response.data=="feecodenullerror")
				MsgBox("Fee Code Cannot be Null");
			else if(response.data=="feetypecodecharactererror")
				MsgBox("Only Numeric Characters Allowed in Fee Type Code");
			else if(response.data=="feetypecodesizeerror")
				MsgBox("Fee Type Code Size Exceeds limit. Enter less than 5 digits");
			else if(response.data=="feetypecodenullerror")
				MsgBox("Fee Type Code Cannot be Null");
			else if(response.data=="feeamountnumbererror")
				MsgBox("Only Numeric Characters Allowed in Fee Amount");
			else if(response.data=="feeamountsizeerror")
				MsgBox("Fee Amount Size Exceeds limit. Enter less than 5 digits");
			else if(response.data=="feeamountnullerror")
				MsgBox("Fee Amount Cannot be Null");
			else if(response.data=="Error")
				MsgBox("Error Inserting Data");
			else if(response.data=="Success"){
				MsgBox(successMsg);
				$scope.reset(); 
			}
				
			else{
				MsgBox("Error");			
				
			}
			$scope.listFeeMaster();  
			
			}, () => {
			 alert(errorMsg) 
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
						"title": "Slno",
						"data": "feecode"
					},
					{
						"title": "Office",
						"data": "officename1"
					},
					{
						"title": "Licensee Type",
						"data": "licenseetypename"
					},
					{
						"title": "Fee Type Description",
						"data": "feetypedescription"
					},
					{
						"title": "Fee Amount",
						"data": "feeamount"
					},

					{
						"title": "Action",
						"sortable": false,
						"data": "feecode",
						"render": function(data, type, row, meta) {
							let status = row.enabled == 'Y' ? 'Disable' : 'Enable';
							let div = '<div style="text-align:center"><button style="padding:.1em; margin-right: .5em" value="Edit" ng-click="edit(' + data + ')" class="button-primary">Edit</button>';
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

		$scope.validateFeeMasterForm = function() {
			//alert("validate"+$scope.feemaster.offices.officecode)



			//
			//			if ($scope.feemaster.offices.officecode === "" || $scope.feemaster.offices.officecode === 0 || $scope.feemaster.offices.officecode === null) {
			//				jQuery("#officecode").focus();
			//				alert("office cannot be empty");
			//				return false;
			//			}

			//			if ($scope.feemaster.licenseetypes.licenseetypecode === "" || $scope.feemaster.licenseetypes.licenseetypecode === 0 || $scope.feemaster.licenseetypes.licenseetypecode === null) {
			//				jQuery("#licenseetypecode").focus();
			//				alert("licensee type cannot be empty");
			//				return false;
			//			}

			if ($scope.feemaster.feetypes.feetypecode === "" || $scope.feemaster.feetypes.feetypecode === 0 || $scope.feemaster.feetypes.feetypecode === null) {
				jQuery("#feetypecode").focus();
				alert("fee type cannot be empty");
				return false;
			}

			if ($scope.feemaster.feeamount === "" || $scope.feemaster.feeamount < 0 || $scope.feemaster.feeamount === null) {
				jQuery("#feeamount").focus();
				alert("fee amount cannot be empty");
				return false;
			}

			return true;
		};

		/* READ DATA */
		$scope.listFeeMaster = (feecode = 0) => {

			if (feecode == 0) {
				commonInitFactory.listFeeMaster((response) => {
					$scope.feemasters = response;
					$scope.setDataTable($scope.feemasters);
				});
			} else {
				commonInitFactory.listFeeMaster((response) => {
					$scope.feemasters.forEach((o, x) => {
						if (o.feecode == feecode) {
							$scope.feemasters[x] = response;
						}
					});
					$scope.setDataTable($scope.feemasters);
				}, feecode);
			}

		};
		$scope.listFeeMaster();



	}]);



