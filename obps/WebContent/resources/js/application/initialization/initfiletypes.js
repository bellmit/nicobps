app.controller('createfiletypeCtrl', ['$scope', '$sce', '$compile','$timeout','commonInitFactory', 'commonInitService', 
	function ($scope, $sce, $compile,$timeout,commonInitFactory, commonInitService) {
	var scope = angular.element($("#createfiletypeCtrl")).scope();
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
	
	
	 $scope.filetype = new Filetype();
$scope.filetypes=[];


//    $scope.user = new Userlogins();
//    $scope.users = [];
//    $scope.repassword = "";
    
    $scope.trustHTML = function (post) {
        return $sce.trustAsHtml(post);
    };
//    $scope.officeDesc = function (offc) {
//    	return offc.officename1+((offc.officename2)?(', '+offc.officename2):'')+((offc.officename3)?(', '+offc.officename3):'');
//    };
    
    $scope.edit = function (filetypecode) {
    	$scope.actionButton = 2;
    	$scope.filetype = new Filetype();
 
        $scope.filetypes.forEach((o, x) => {
        	if (o.filetypecode == filetypecode){
        		$scope.filetype = o;
        	}
        });
//        $scope.listOfficeCells($scope.user.officecode);
        jQuery('html, body').animate({
            scrollTop: 0
        }, 2000);
    };

    $scope.reset = function () {
       $scope.listFileTypes();
    	$scope.filetype = new Filetype();
    	$scope.actionButton = 1;
    };

    $scope.save = function () {
    	
        if($scope.filetypeForm.$invalid)
            return false;
   
        $scope.method = "POST";
        $scope.urlEndpoint = "./initfiletypes.htm";
    	
        commonInitService.save($scope.method, $scope.urlEndpoint, $scope.filetype, (response) => { 
			
			if(response.data=="Error")
				MsgBox("Error Inserting Data");
			else if(response.data=="Success"){
				MsgBox("Successfully Inserted Data");
				$scope.reset(); 
			}
			else if(response.data=="exist"){
				MsgBox("Fee Type Already Exists");
				
			}
				
			else{
				MsgBox(response.data);			
				
			}
			$scope.listFileTypes();  
			
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
	    if($scope.filetypeForm.$invalid)
             return false;
	    $scope.method = "POST";
    	$scope.urlEndpoint = "./updateinitfiletypes.htm";
    	commonInitService.save($scope.method, $scope.urlEndpoint, $scope.filetype, (response) => { 
			if(response.data=="Error")
				MsgBox("Error Inserting Data");
			 else if(response.data=="Success"){
				MsgBox("Successfully Updated Data");
				$scope.reset(); 
			}
			else if(response.data=="exist"){
				MsgBox("Fee Type Already Exists");
				 
			}
				
			else{
				MsgBox(response.data);			
				
			}
			$scope.listFileTypes();  
			
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
                    "data": "filetypecode"
                },
                {
                    "title": "File Type Description",
                    "data": "filetypedescription"
                }, 
                
                {
                    "title": "Action",
                    "sortable": false,
                    "data": "filetypecode",
                    "render": function (data, type, row, meta) {
                    	let status = row.enabled == 'Y'?'Disable':'Enable';
                    	let div = '<div style="text-align:center"><button style="padding:.1em; margin-right: .5em" value="Edit" ng-click="edit(' + data + ')" class="btn btn-primary b-btn" >Edit</button>';
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
    
    
    $scope.validatefiletypeForm = function() {

      
        if($scope.filetype.filetypedescription === "" || $scope.filetype.filetypedescription === null){
            jQuery("#filetypedescription").focus();
            alert("filetypedescription cannot be empty");
            return false;
        }
      
            return true;
        };
        

        /* READ DATA */
     $scope.listFileTypes = (filetypecode = 0) => {
        	
        	if(filetypecode == 0){
        		commonInitFactory.listFileTypes((response)=>{
            		$scope.filetypes=response;
            		$scope.setDataTable($scope.filetypes);
            	});
        	}else{
        		commonInitFactory.listFileTypes((response)=>{
        			$scope.filetypes.forEach((o, x) => {
        				if(o.filetypecode == filetypecode){
        					$scope.filetypes[x] = response;
        				}
        			});
            		$scope.setDataTable($scope.filetypes);
            	}, filetypecode);
        	}
        	
        };
        $scope.listFileTypes();


        
    }]);

/**
 * 
 */