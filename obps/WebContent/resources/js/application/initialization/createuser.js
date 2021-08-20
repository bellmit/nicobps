app.controller('createuserCtrl', ['$scope', '$sce', '$compile','$timeout','commonInitFactory', 'commonInitService', 
	function ($scope, $sce, $compile,$timeout,commonInitFactory, commonInitService) {
	var scope = angular.element($("#createuserCtrl")).scope();
	commonInitService.success();
	/* Common Ajax Params */
	var successMsg = "Success: User created or updated successfully"; 
	var errorMsg = "Error: Unable to perform action";
	$scope.errorCallback = "";
	$scope.method = "POST";
	$scope.successCallback = "";
	$scope.urlEndpoint = "";
	
	/*------------------------*/
	
	$scope.actionButton = 1;
	$scope.cellcode = null;
	$scope.cells = [];
	$scope.officecode = null;
	$scope.offices = [];
	
    $scope.user = new Userlogins();
    $scope.users = [];
    $scope.repassword = "";
    
    $scope.trustHTML = function (post) {
        return $sce.trustAsHtml(post);
    };
// $scope.officeDesc = function (offc) {
// return offc.officename1+((offc.officename2)?(',
// '+offc.officename2):'')+((offc.officename3)?(', '+offc.officename3):'');
// };
    
    $scope.edit = function (usercode) {
    	$scope.actionButton = 2;
    	$scope.user = new Userlogins();
    	$scope.user.repasswords = "";
        $scope.users.forEach((o, x) => {
        	if (o.usercode == usercode){
        		$scope.user = o;
        	}
        });
        $scope.listOfficeCells($scope.user.officecode);
        jQuery('html, body').animate({
            scrollTop: 0
        }, 2000);
    };

    $scope.reset = function () {
    	$scope.user = new Userlogins();
    	$scope.actionButton = 1;
    };

    $scope.save = function () {
    	
// if($scope.userForm.$invalid)
// return false;
//		$scope.validateUserForm();
//		scope.repasswordMsg=""; 
    	$scope.user.userpassword=SHA256($scope.user.userpassword);
    	$scope.user.repassword=SHA256($scope.user.repassword);
        $scope.method = "POST";
        $scope.urlEndpoint = "./createuser.htm";
    	
        commonInitService.save($scope.method, $scope.urlEndpoint, $scope.user, function(response){
        
       
       	if(response.data=="Success"){
       		MsgBox("The users has been created successfully. Please assign the necessary privilleges from the User Access Control link.");
       		$scope.reset();
       		$scope.listUsers();
       	}else if(response.data=="exist"){
       		MsgBox("User Already exists");
       		$scope.user.userpassword="";
    	$scope.user.repassword="";
       		$scope.listUsers();
       	}else if(response.data=="Error"){
       		MsgBox("Error");
       		$scope.user.userpassword="";
    	$scope.user.repassword="";
       		$scope.listUsers();
       	}else {
       		MsgBox(response.data);
       		$scope.user.userpassword="";
    		$scope.user.repassword="";
       		$scope.listUsers();
       	}
       	
       	
       		
        }, 
        function(res) {
        console.log(res);
        alert((res.msg.length>0)?res.msg:errorMsg)
        $scope.reset();
       		$scope.listUsers();
        }
        );
    };
    
    $scope.toggleUserStatus= function (usercode) {
    	$scope.users.forEach((o, x) => {
        	if (o.usercode == usercode){
        		$scope.user = o;
        	}
        });
    	$scope.method = "POST";
    	$scope.urlEndpoint = "./updateuser/status";
    	commonInitService.save($scope.method, "./ableuser.htm", $scope.user, () => {$scope.reset();$scope.listUsers(usercode);}, () =>{alert("failed")});
    	
    };
    
    $scope.update = () => {
//	    if($scope.userForm.$invalid)
//             return false;             
    	$scope.user.userpassword=SHA256($scope.user.userpassword);
    	$scope.user.repassword=SHA256($scope.user.repassword);
     
	    $scope.method = "POST";
    	$scope.urlEndpoint = "./updateuser.htm";
    	commonInitService.save($scope.method, $scope.urlEndpoint, $scope.user, function(response){
        
       
       	if(response.data=="Success"){
       		MsgBox("The user has been updated successfully.");
       		$scope.reset();
       		$scope.listUsers();
       	}else if(response.data=="exist"){
       		MsgBox("Please choose a different username; The username  already exists");
       		$scope.user.userpassword="";
    	$scope.user.repassword="";
       		$scope.listUsers();
       	}else if(response.data=="Error"){
       		MsgBox("Please choose a different username; The username  already exists");
       		$scope.user.userpassword="";
    	$scope.user.repassword="";
       		$scope.listUsers();
       	}else {
       		MsgBox(response.data);
       		$scope.user.userpassword="";
    		$scope.user.repassword="";
       		$scope.listUsers();
       	}
       	
       	
       		
        }, 
        function(res) {
        console.log(res);
        alert((res.msg.length>0)?res.msg:errorMsg)
        $scope.reset();
       		$scope.listUsers();
        }
        );
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
                    "data": "usercode"
                },
                {
                    "title": "Full Name",
                    "data": "fullname"
                }, 
                {
                    "title": "User Name",
                    "data": "username"
                },
                {
                    "title": "Mobile No",
                    "data": "mobileno"
                }, 
                {
                    "title": "Designation",
                    "data": "designation"
                },
                {
                    "title": "Action",
                    "sortable": false,
                    "data": "usercode",
                    "render": function (data, type, row, meta) {
                    	let status = row.enabled == 'Y'?'Disable':'Enable';
                    	let div = '<div style="text-align:center"><button style="padding:.1em; margin-right: .5em" value="Edit" ng-click="edit(' + data + ')" class="btn btn-primary b-btn" >Edit</button>';
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
    
    
    $scope.validateUserForm = function() {

alert($scope.user.designation.designationid)
alert($scope.user.username)
alert($scope.user.password)
alert($scope.user.repassword)
     
        if($scope.user.designation.designationid <= 0 || $scope.user.designation.designationid === null){
            jQuery("#designationid").focus();
            alert("Please select designation");
            return false;
        }
        if($scope.user.username === "" || $scope.user.username === null){
            jQuery("#username").focus();
            alert("username cannot be empty");
            return false;
        }
        if($scope.user.password === "" || $scope.user.password === null){
            jQuery("#passwords").focus();
            alert("password cannot be empty");
            return false;
        }
        if($scope.user.password === "" || $scope.user.repassword === null){
            jQuery("#repassword").focus();
            alert("password cannot be empty");
            return false;
        }
        if($scope.user.repassword !== $scope.user.password){
            jQuery("#repassword").focus();
            alert("passwords do not matches");
           
                return false;
            }
            return true;
        };
        

        /* READ DATA */

        $scope.listUsers = (officecode=$scope.user.officecode) => {
        	if(officecode)
        	commonInitService.httpSendJSON('GET','./listOfficeUsers.htm?officecode='+officecode,"",(response)=>{
        		$scope.users=response;
        		$scope.setDataTable($scope.users);
        	});
        	
        };
        $timeout(()=>{$scope.listUsers()},0);
        
    }]);

