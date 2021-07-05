app.controller('officesCtrl', ['$scope', '$sce', '$compile','$timeout','commonInitFactory', 'commonInitService', 
	function ($scope, $sce, $compile,$timeout,commonInitFactory, commonInitService) {
	var scope = angular.element($("#officesCtrl")).scope();
	commonInitService.success();
	/* Common Ajax Params */
	var successMsg = "Success: Offices created or updated successfully";
	var errorMsg = "Error: Unable to perform action";
	$scope.errorCallback = "";
	$scope.method = "POST";
	$scope.successCallback = "";
	$scope.urlEndpoint = "";
	$scope.actionButton = 1;
	
	
	/*------------------------*/
	$scope.offices = new Offices();
	$scope.officereg=[];
	
	
	   
    $scope.trustHTML = function (post) {
		
       return $sce.trustAsHtml(post);
 };
 $scope.edit = function (officecode) {
 		
    	$scope.actionButton = 2;
    	$scope.offices = new Offices();
    	
 
        $scope.officereg.forEach((o, x) => {
        	if (o.officecode == officecode){
        		$scope.offices = o;
        		
        	}
        	
        }); 
        $scope.offices.smspassword="";
        $scope.offices.emailidpassword="";
   jQuery('html, body').animate({
            scrollTop: 0
        }, 2000);
	};
  $scope.reset = function () {
  	$scope.offices = new Offices();
	$scope.actionButton =1;
};
$scope.save = function () {
		
   		if($scope.offices.emailidpassword)
   	    $scope.offices.emailidpassword=SHA256($scope.offices.emailidpassword);
        if($scope.offices.smspassword)  
    	$scope.offices.smspassword=SHA256($scope.offices.smspassword);
    	$scope.method = "POST";
        $scope.urlEndpoint = "./initoffices.htm";
    	
        commonInitService.save1($scope.method, $scope.urlEndpoint, $scope.offices, function (response) {
		
				if (response.data=="Success") {
					MsgBox("Offices Inserted successfully.");
					$scope.reset();
					$scope.listOffices();
				} else if (response.data=="exist") {
					MsgBox("Offices name already exist");
					$scope.listOffices();
				}else if (response.data=="officename1") {
					MsgBox("No Special Characters or Numbers allowed in Office Name1");
					$scope.listOffices();
				
				}else if (response.data=="officename2") {
					MsgBox("No Special Characters or Numbers allowed in Office Name2");
					$scope.listOffices();
				}else if (response.data=="officename3") {
					MsgBox("No Special Characters or Numbers allowed in Office Name3");
					$scope.listOffices();
				}
				else if (response.data=="offshort") {
					MsgBox("No Special Characters or Numbers allowed in Office ShortHand Name");
					$scope.listOffices();
				
				}else if (response.data=="offsignatory") {
					MsgBox("No Special Characters or Numbers allowed in Signatory Name");
					$scope.listOffices();
				}
				else if (response.data=="officeshortdes") {
					MsgBox("No Special Characters or Numbers allowed in Signatory Designation");
					$scope.listOffices();
				}
				else if (response.data=="sms") {
					MsgBox("No Special Characters or Numbers allowed in SMS Username");
					$scope.listOffices();
				}
				else if (response.data=="1") {
					MsgBox("Office Name 1 Cannot be more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="2") {
					MsgBox("Office Name 2 Cannot be more than 255 characters");
					$scope.listOffices();
				}
				else if (response.data=="3") {
					MsgBox("Office Name 3 Cannot be more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="4") {
					MsgBox("Office Short Hand Name Cannot be more than 50 characters");
					$scope.listOffices();
				}
				else if (response.data=="5") {
					MsgBox("Signatory Name Cannot be more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="6") {
					MsgBox("Signatory Designation Cannot be more than 255 characters");
					$scope.listOffices();
				}
				else if (response.data=="7") {
					MsgBox("sms username Cannot be more than 25 characters");
					$scope.listOffices();
				
				}else if (response.data=="8") {
					MsgBox("sms password Cannot be more than 100 characters");
					$scope.listOffices();
				}
				else if (response.data=="9") {
					MsgBox("emailid Cannot be more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="10") {
					MsgBox("emailidpassword Cannot be more than 255 characters");
					$scope.listOffices();
				}else if (response.data=="emailidnotcorrect") {
					MsgBox("Please Enter correct emailid");
					$scope.listOffices();
				}
				
				else if(response.data=="Error"){
				alert("Office Already exists");
				}
				$scope.offices.emailidpassword="";
				$scope.offices.smspassword="";
				
			}, function () {
				
				alert("Error");
				$scope.reset();
			});

  };

  $scope.update = () => {
	  if($scope.officeForm.$invalid)
          return false;
                   
if($scope.offices.emailidpassword)  { 	
    	
   	    $scope.offices.emailidpassword=SHA256($scope.offices.emailidpassword);}
       if($scope.offices.smspassword)  
    	$scope.offices.smspassword=SHA256($scope.offices.smspassword);
    	$scope.method = "POST";
        $scope.urlEndpoint = "./updateinitoffices.htm";
         commonInitService.save1($scope.method, $scope.urlEndpoint, $scope.offices, function (response) {
		
				if (response.data=="Success") {
					MsgBox("Offices Updated successfully.");
					$scope.reset();
					$scope.listOffices();
				} else if (response.data=="exist") {
					MsgBox("Offices name already exist");
					$scope.listOffices();
				}else if (response.data=="officename1") {
					MsgBox("No Special Characters or Numbers allowed in Office Name1");
					$scope.listOffices();
				
				}else if (response.data=="officename2") {
					MsgBox("No Special Characters or Numbers allowed in Office Name2");
					$scope.listOffices();
				}else if (response.data=="officename3") {
					MsgBox("No Special Characters or Numbers allowed in Office Name3");
					$scope.listOffices();
				}
				else if (response.data=="offshort") {
					MsgBox("No Special Characters or Numbers allowed in Office ShortHand Name");
					$scope.listOffices();
				
				}else if (response.data=="offsignatory") {
					MsgBox("No Special Characters or Numbers allowed in Signatory Name");
					$scope.listOffices();
				}
				else if (response.data=="officeshortdes") {
					MsgBox("No Special Characters or Numbers allowed in Signatory Designation");
					$scope.listOffices();
				}
				else if (response.data=="sms") {
					MsgBox("No Special Characters or Numbers allowed in SMS Username");
					$scope.listOffices();
				}
				else if (response.data=="1") {
					MsgBox("Office Name 1 Cannot be more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="2") {
					MsgBox("Office Name 2 Cannot be more than 255 characters");
					$scope.listOffices();
				}
				else if (response.data=="3") {
					MsgBox("Office Name 3 Cannot be more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="4") {
					MsgBox("Office Short Hand Name Cannot be more than 50 characters");
					$scope.listOffices();
				}
				else if (response.data=="5") {
					MsgBox("Signatory Name Cannot be more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="6") {
					MsgBox("Signatory Designation Cannot be more than 255 characters");
					$scope.listOffices();
				}
				else if (response.data=="7") {
					MsgBox("sms username Cannot be more than 25 characters");
					$scope.listOffices();
				
				}else if (response.data=="8") {
					MsgBox("sms password Cannot be more than 100 characters");
					$scope.listOffices();
				}
				else if (response.data=="9") {
					MsgBox("emailid Cannot be more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="10") {
					MsgBox("emailidpassword Cannot be more than 255 characters");
					$scope.listOffices();
				}else if (response.data=="emailidnotcorrect") {
					MsgBox("Please Enter correct emailid");
					$scope.listOffices();
				}
				
				else if(response.data=="Error"){
				alert("Office Already exists");
				}
				$scope.offices.emailidpassword="";
				$scope.offices.smspassword="";
				
			}, function () {
				
				alert("Error");
				$scope.reset();
			});
 	
  }
  
   $scope.setDataTable = function (obj) {
	
        jQuery("#displayRecords").html("");
        jQuery("#displayRecords").html("<table id='displayRecordsTable' style='width:1000px' border='1'>\n\
                                                </table>");
        jQuery('#displayRecordsTable').DataTable({
            data: obj,
            columns: [
                {
                    "title": "Slno",
                    "data": "officecode"
                },
				{
                    "title": "Office Name1",
                    "data": "officename1"
                },
                {
                    "title": "Office Name2",
                    "data": "officename2"
                },
              //  {
               //     "title": "Office Name3",
               //     "data": "officename3"
              //  },
                {
                    "title": "Office ShortHand Name",
                    "data": "officeshortname"
                }, 
              //  {
             //       "title": "Signatory Name",
             //       "data": "signatoryname"
             //   },
				//{
              //      "title": "Signatory Designation",
              //      "data": "signatorydesignation"
             //   },
                {
                    "title": "Email id",
                    "data": "emailid"
                },
              //  {
              //      "title": "Email id Password",
              //      "data": "emailidpassword"
             //   },
             //   {
              //      "title": "SMS User Name",
             //       "data": "officeshortname"
             //   },
             //   {
             //       "title": "SMS Password",
             //       "data": "smspassword"
            //    },

                
                {
                    "title": "Action",
                    "sortable": false,
                    "data": "officecode",
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
    
    $scope.officeForm = function() {

      
        if($scope.offices.officeName1 === "" || $scope.offices.officeName1 === null){
            jQuery("#officeName1").focus();
            alert("officeName1 cannot be empty");
            
            return false;
          
        }
      
            return true;
        };
        
        
        
       
        /* READ DATA */
     $scope.listOffices = (officecode = 0) => {
        	
        	if(officecode == 0){
        		commonInitFactory.listOffices((response)=>{
            		$scope.officereg=response;
            		$scope.setDataTable($scope.officereg);
            	});
        	}else{
        		commonInitFactory.listOffices((response)=>{
        			$scope.officereg.forEach((o, x) => {
        				if(o.officecode == enclosurecode){
        					$scope.officereg[x] = response;

        				}
        			});
            		$scope.setDataTable($scope.officereg);
            	}, officecode);
        	}
        	
        };
        $scope.listOffices();
    
  

        

      
        
        
    }]);
