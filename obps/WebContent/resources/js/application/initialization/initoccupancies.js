app.controller('createoccupanciesCtrl', ['$scope', '$sce', '$compile', '$timeout', 'commonInitFactory', 'commonInitService',
	function($scope, $sce, $compile, $timeout, commonInitFactory, commonInitService) {
		var scope = angular.element($("#createoccupanciesCtrl")).scope();
		commonInitService.success();
		/* Common Ajax Params */
		var successMsg = "Success: Occupancy created or updated successfully";
		var errorMsg = "Error: Unable to perform action or Already existing data";
		$scope.errorCallback = "";
		$scope.method = "POST";
		$scope.successCallback = "";
		$scope.urlEndpoint = "";
 
		/*------------------------*/ 

		$scope.actionButton = 1;
		//	$scope.cellcode = null;
		//	$scope.cells = [];
		//	$scope.officecode = null;
		//	$scope.offices = [];


		$scope.occupancy = new Occupancy();
		$scope.occupancies = [];


		//    $scope.user = new Userlogins();
		//    $scope.users = [];
		//    $scope.repassword = "";

		$scope.trustHTML = function(post) {
			return $sce.trustAsHtml(post);
		};
		//    $scope.officeDesc = function (offc) {
		//    	return offc.officename1+((offc.officename2)?(', '+offc.officename2):'')+((offc.officename3)?(', '+offc.officename3):'');
		//    };

		$scope.edit = function(occupancycode) {

			$scope.IsDisabled = true;
			$scope.actionButton = 2;
			$scope.occupancy = new Occupancy();

			$scope.occupancies.forEach((o, x) => {

				if (o.occupancycode == occupancycode) {

					$scope.occupancy = o;
				}
			});
			//        $scope.listOfficeCells($scope.user.officecode);
			jQuery('html, body').animate({
				scrollTop: 0
			}, 2000);
		};

		$scope.reset = function() {
			$scope.listOccupancies();
		$scope.IsDisabled = false;
			$scope.occupancy = new Occupancy();
			$scope.actionButton = 1;
		};

		$scope.save = function() {

			if ($scope.occupancyForm.$invalid)
				return false;

			$scope.method = "POST";
			$scope.urlEndpoint = "./initoccupancies.htm";

			
			commonInitService.save($scope.method, $scope.urlEndpoint, $scope.occupancy, function (response) {
				if (response.data=="Success") {
					MsgBox("Occupancies Inserted successfully.");
					$scope.reset();
					$scope.listOccupancies()
				} else if (response.data=="Exist") {
					MsgBox("Occupancies already exist");
					$scope.listOccupancies()
				}else if (response.data=="occupancycodecharactererror") {
					MsgBox("Special Characters allowed in Occupancy Code are  - _");
					$scope.listOccupancies()
				
				
				
				}else if (response.data=="occupancycodesizeerror") {
					MsgBox("Occupancy Code Cannot be more than 10 characters");
					$scope.listOccupancies()
				
				}else if (response.data=="occupancycodenull") {
					MsgBox("Occupancy Code Cannot Be Null");
					$scope.listOccupancies()
				}else if (response.data=="occupancynamecharactererror") {
					MsgBox("Special Characters allowed in Occupancy Name are  , . / ( ) - _");
					$scope.listOccupancies()
				
				}else if (response.data=="occupancynamesizeerror") {
					MsgBox("Occupancy Name Cannot be more than 50 characters");
					$scope.listOccupancies()
				}else if (response.data=="occupancynamenull") {
					MsgBox("Occupancy Name Cannot be Null");
					$scope.listOccupancies()
				}else if (response.data=="occupancyaliascharactererror") {
					MsgBox("Special Characters allowed in Occupancy Alias are  , . / ( ) - _");
					$scope.listOccupancies()
				
				}else if (response.data=="occupancyaliassizeerror") {
					MsgBox("Occupancy Alias Cannot be more than 50 characters");
					$scope.listOccupancies()
				}else if (response.data=="occupancyaliasnull") {
					MsgBox("Occupancy Alias Cannot be Null");
					$scope.listOccupancies()
				}
				else if(response.data=="Error"){
				alert("Error");
				
				}
				
				
			}, function () {
				
				alert("Error");
				$scope.reset();
				$scope.listLicensees()
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
		//    	commonInitService.save($scope.method, "./ableuser.htm", $scope.user, () => {$scope.reset();$scope.listOccupancies(licenseeregistrationcode);}, () =>{alert("failed")});
		//    	
		//    };
		//    
		$scope.update = () => {
			if ($scope.occupancyForm.$invalid)
				return false;
			$scope.method = "POST";
			$scope.urlEndpoint = "./updateoccupancy.htm";
			commonInitService.save($scope.method, $scope.urlEndpoint, $scope.occupancy, function (response) {
				if (response.data=="Success") {
					MsgBox("Occupancies Updated successfully.");
					$scope.reset();
					$scope.listOccupancies()
				} else if (response.data=="Exist") {
					MsgBox("Occupancies already exist");
					$scope.listOccupancies()
				}else if (response.data=="occupancycodecharactererror") {
					MsgBox("Special Characters allowed in Occupancy Code are  - _");
					$scope.listOccupancies()
				
				
				
				}else if (response.data=="occupancycodesizeerror") {
					MsgBox("Occupancy Code Cannot be more than 10 characters");
					$scope.listOccupancies()
				
				}else if (response.data=="occupancycodenull") {
					MsgBox("Occupancy Code Cannot Be Null");
					$scope.listOccupancies()
				}else if (response.data=="occupancynamecharactererror") {
					MsgBox("Special Characters allowed in Occupancy Name are  , . / ( ) - _");
					$scope.listOccupancies()
				
				}else if (response.data=="occupancynamesizeerror") {
					MsgBox("Occupancy Name Cannot be more than 50 characters");
					$scope.listOccupancies()
				}else if (response.data=="occupancynamenull") {
					MsgBox("Occupancy Name Cannot be Null");
					$scope.listOccupancies()
				}else if (response.data=="occupancyaliascharactererror") {
					MsgBox("Special Characters allowed in Occupancy Alias are  , . / ( ) - _");
					$scope.listOccupancies()
				
				}else if (response.data=="occupancyaliassizeerror") {
					MsgBox("Occupancy Alias Cannot be more than 50 characters");
					$scope.listOccupancies()
				}else if (response.data=="occupancyaliasnull") {
					MsgBox("Occupancy Alias Cannot be Null");
					$scope.listOccupancies()
				}
				else if(response.data=="Error"){
				alert("Error");
				
				}
				
				
			}, function () {
				
				alert("Error");
				$scope.reset();
				$scope.listLicensees()
			});
		}

		/*-----------------------------------------------------------------------------------------------------------------------------------*/

		$scope.setDataTable = function(obj) {
			jQuery("#displayRecords").html("");
			jQuery("#displayRecords").html("<table id='displayRecordsTable' style='width:100%' border='1'>\n\
                                                </table>");
			jQuery('#displayRecordsTable').DataTable({
				data: obj,
				columns: [
					{
						"title": "Occupancy code",
						"data": "occupancycode"
					},
					{
						"title": "Occupancy Name",
						"data": "occupancyname"
					},
					{
						"title": "Occupancy Alias",
						"data": "occupancyalias"
					},
					{
						"title": "Action",
						"sortable": false,
						"data": "occupancycode",
						"render": function(data, type, row, meta) {
							let status = row.enabled == 'Y' ? 'Disable' : 'Enable';
							let div = '<div style="text-align:center"><button style="padding:.1em; margin-right: .5em" value="Edit" ng-click="edit(\'' + data + '\')" class="button-primary">Edit</button>';
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


		$scope.validateoccupancyForm = function() {


			if ($scope.occupancy.occupancycode === "" || $scope.occupancy.occupancycode === null) {
				jQuery("#occupancycode").focus();
				alert("occupancycode cannot be empty");
				return false;
			}

			if ($scope.occupancy.occupancyname === "" || $scope.occupancy.occupancyname === null) {
				jQuery("#occupancyname").focus();
				alert("occupancyname cannot be empty");
				return false;
			}

			if ($scope.occupancy.occupancyalias === "" || $scope.occupancy.occupancyalias === null) {
				jQuery("#occupancyalias").focus();
				alert("occupancyalias cannot be empty");
				return false;
			}

			return true;
		};


		/* READ DATA */


		$scope.listOccupancies = (occupancycode = 0) => {

			if (occupancycode == 0) {
				commonInitFactory.listOccupancies((response) => {
					$scope.occupancies = response;
					$scope.setDataTable($scope.occupancies);
				});
			} else {
				commonInitFactory.listOccupancies((response) => {
					$scope.occupancies.forEach((o, x) => {
						if (o.occupancycode == occupancycode) {
							$scope.occupancies[x] = response;
						}
					});
					$scope.setDataTable($scope.occupancies);
				}, occupancycode);
			}

		};
		$scope.listOccupancies();



	}]);

