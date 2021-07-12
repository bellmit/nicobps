app.controller('createfeetypeCtrl', ['$scope', '$sce', '$compile','$timeout','commonInitFactory', 'commonInitService', 
	function ($scope, $sce, $compile,$timeout,commonInitFactory, commonInitService) {
	var scope = angular.element($("#createfeetypeCtrl")).scope();
	commonInitService.success();
	/* Common Ajax Params */
	var successMsg = "Success: Fee Type created or updated successfully";
	var errorMsg = "Error: Unable to perform action or Already Existing Data";
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
	
	
	 $scope.feetype = new FeeType();
$scope.feetypes=[];


//    $scope.user = new Userlogins();
//    $scope.users = [];
//    $scope.repassword = "";
    
    $scope.trustHTML = function (post) {
        return $sce.trustAsHtml(post);
    };
//    $scope.officeDesc = function (offc) {
//    	return offc.officename1+((offc.officename2)?(', '+offc.officename2):'')+((offc.officename3)?(', '+offc.officename3):'');
//    };
    
    $scope.edit = function (feetypecode) {
    	$scope.actionButton = 2;
    	$scope.feetype = new FeeType();
 
        $scope.feetypes.forEach((o, x) => {
        	if (o.feetypecode == feetypecode){
        		$scope.feetype = o;
        	}
        });
//        $scope.listOfficeCells($scope.user.officecode);
        jQuery('html, body').animate({
            scrollTop: 0
        }, 2000);
    };

    $scope.reset = function () {
       $scope.listFeeTypes();
    	$scope.feetype = new FeeType();
    	$scope.actionButton = 1;
    };

    $scope.save = function () {
    	
        if($scope.feetypeForm.$invalid)
            return false;
   
        $scope.method = "POST";
        $scope.urlEndpoint = "./initfeetypes.htm";
    	
        commonInitService.save($scope.method, $scope.urlEndpoint, $scope.feetype, (response) => { 
			
			if(response.data=="feetypedescriptionsizeerror") 
				MsgBox("Fee Type Description Cannot Exceed More Than 255 Characters");
			else if(response.data=="feetypedescriptionnull")
				MsgBox("Fee Type Description Cannot be Null");
			else if(response.data=="Error")
				MsgBox("Error Inserting Data");
			else if(response.data=="Success"){
				MsgBox("Successfully Inserted Data");
				$scope.reset(); 
			}
			else if(response.data=="exist"){
				MsgBox("Fee Type Already Exists");
				
			}
				
			else{
				MsgBox("Error");			
				
			}
			$scope.listFeeTypes();  
			
			}, () => {
			 alert("Error") 
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
	    if($scope.feetypeForm.$invalid)
             return false;
	    $scope.method = "POST";
    	$scope.urlEndpoint = "./updateinitfeetypes.htm";
    	commonInitService.save($scope.method, $scope.urlEndpoint, $scope.feetype, (response) => { 
			
			if(response.data=="feetypedescriptionsizeerror") 
				MsgBox("Fee Type Description Cannot Exceed More Than 255 Characters");
			else if(response.data=="feetypedescriptionnull")
				MsgBox("Fee Type Description Cannot be Null");
			else if(response.data=="Error")
				MsgBox("Error Inserting Data");
			else if(response.data=="Success"){
				MsgBox("Successfully Inserted Data");
				$scope.reset(); 
			}
			else if(response.data=="exist"){
				MsgBox("Fee Type Already Exists");
				 
			}
				
			else{
				MsgBox("Error");			
				
			}
			$scope.listFeeTypes();  
			
			}, () => {
			 alert("Error") 
			 });
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
                    "data": "feetypecode"
                },
                {
                    "title": "Fee Type Description",
                    "data": "feetypedescription"
                }, 
                
                {
                    "title": "Action",
                    "sortable": false,
                    "data": "feetypecode",
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
    
    
    $scope.validateFeeTypeForm = function() {

      
        if($scope.feetype.feetypedescription === "" || $scope.feetype.feetypedescription === null){
            jQuery("#feetypedescription").focus();
            alert("licenseedescription cannot be empty");
            return false;
        }
      
            return true;
        };
        

        /* READ DATA */
     $scope.listFeeTypes = (feetypecode = 0) => {
        	
        	if(feetypecode == 0){
        		commonInitFactory.listFeeTypes((response)=>{
            		$scope.feetypes=response;
            		$scope.setDataTable($scope.feetypes);
            	});
        	}else{
        		commonInitFactory.listFeeTypes((response)=>{
        			$scope.feetypes.forEach((o, x) => {
        				if(o.feetypecode == feetypecode){
        					$scope.feetypes[x] = response;
        				}
        			});
            		$scope.setDataTable($scope.feetypes);
            	}, feetypecode);
        	}
        	
        };
        $scope.listFeeTypes();


        
    }]);

