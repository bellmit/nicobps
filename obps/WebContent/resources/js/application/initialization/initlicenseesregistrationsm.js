app.controller('createlicenseesregCtrl', ['$scope', '$sce', '$compile','$timeout','commonInitFactory', 'commonInitService', 
	function ($scope, $sce, $compile,$timeout,commonInitFactory, commonInitService) {
	var scope = angular.element($("#createlicenseesregCtrl")).scope();
	commonInitService.success();
	/* Common Ajax Params */
	var successMsg = "Success: Licensee Registration created or updated successfully";
	var errorMsg = "Error: Unable to perform action";
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
	
	
	 $scope.licensees = new Licenseesregistration();
$scope.licenseesreg=[];


//    $scope.user = new Userlogins();
//    $scope.users = [];
//    $scope.repassword = "";
    
    $scope.trustHTML = function (post) {
        return $sce.trustAsHtml(post);
    };
//    $scope.officeDesc = function (offc) {
//    	return offc.officename1+((offc.officename2)?(', '+offc.officename2):'')+((offc.officename3)?(', '+offc.officename3):'');
//    };
    
    $scope.edit = function (licenseeregistrationcode) {
    	$scope.actionButton = 2;
    	$scope.licensees = new Licenseesregistration();
 
        $scope.licenseesreg.forEach((o, x) => {
        	if (o.licenseeregistrationcode == licenseeregistrationcode){
        		$scope.licensees = o;
        	}
        });
//        $scope.listOfficeCells($scope.user.officecode);
        jQuery('html, body').animate({
            scrollTop: 0
        }, 2000);
    };

    $scope.reset = function () {
    	$scope.licensees = new Licenseesregistration();
    	$scope.actionButton = 1;
    };

    $scope.save = function () {
    	
        if($scope.licenseeForm.$invalid)
            return false;
   
        $scope.method = "POST";
        $scope.urlEndpoint = "./initlicenseesregistrationsm.htm";
    	
        commonInitService.save($scope.method, $scope.urlEndpoint, $scope.licensees, () => {$scope.reset();$scope.listLicensees(); alert(successMsg)}, () =>{alert(errorMsg)});
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
	    if($scope.licenseeForm.$invalid)
             return false;
	    $scope.method = "POST";
    	$scope.urlEndpoint = "./updatelicenseesregistrationsm.htm";
    	commonInitService.save($scope.method, $scope.urlEndpoint, $scope.licensees, () => {$scope.reset();$scope.listLicensees(), alert(successMsg)}, () => {alert(errorMsg)});
    }
     
/*-----------------------------------------------------------------------------------------------------------------------------------*/
    
    $scope.setDataTable = function (obj) {
        jQuery("#displayRecords").html("");
        jQuery("#displayRecords").html("<table id='displayRecordsTable' style='width:100%' border='1'>\n\
                                                </table>");
        jQuery('#displayRecordsTable').DataTable({
            data: obj,
            columns: [
                {
                    "title": "Slno",
                    "data": "licenseeregistrationcode"
                },
                {
                    "title": "Licensees Description",
                    "data": "licenseedescription"
                }, 
                
                {
                    "title": "Action",
                    "sortable": false,
                    "data": "licenseeregistrationcode",
                    "render": function (data, type, row, meta) {
                    	let status = row.enabled == 'Y'?'Disable':'Enable';
                    	let div = '<div style="text-align:center"><button style="padding:.1em; margin-right: .5em" value="Edit" ng-click="edit(' + data + ')" class="button-primary">Edit</button>';
//                    		div += '<button style="padding:.1em; margin-right: .5em" value="Edit" ng-click="toggleUserStatus(' + data + ')" class="button-primary">'+status+'</button></div>';
                        return div;
                    }
                }
            ],
            "sortable": false,
            "columnDefs": [
                {"width": "2%", "targets": 0}
            ],
            "bLengthChange": false,
            createdRow: function (row, data, dataIndex) {
                $compile(angular.element(row).contents())($scope);
            }
        });
    };
    
    
    $scope.validateLicenseeForm = function() {

      
        if($scope.licensees.licenseedescription === "" || $scope.licensees.licenseedescription === null){
            jQuery("#licenseedescription").focus();
            alert("licenseedescription cannot be empty");
            return false;
        }
      
            return true;
        };
        

        /* READ DATA */
     $scope.listLicensees = (licenseeregistrationcode = 0) => {
        	
        	if(licenseeregistrationcode == 0){
        		commonInitFactory.listLicensees((response)=>{
            		$scope.licenseesreg=response;
            		$scope.setDataTable($scope.licenseesreg);
            	});
        	}else{
        		commonInitFactory.listLicensees((response)=>{
        			$scope.licenseesreg.forEach((o, x) => {
        				if(o.licenseeregistrationcode == licenseeregistrationcode){
        					$scope.licenseesreg[x] = response;
        				}
        			});
            		$scope.setDataTable($scope.licenseesreg);
            	}, licenseeregistrationcode);
        	}
        	
        };
        $scope.listLicensees();


        
    }]);

