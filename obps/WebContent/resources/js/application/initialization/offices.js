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

    	if($scope.offices.emailidpassword)  { 	
    	alert("asdasda")
   	    $scope.offices.emailidpassword=SHA256($scope.offices.emailidpassword);}
       if($scope.offices.smspassword)  
    	$scope.offices.smspassword=SHA256($scope.offices.smspassword);
    	$scope.method = "POST";
        $scope.urlEndpoint = "./initoffices.htm";
    	
        commonInitService.save($scope.method, $scope.urlEndpoint, $scope.offices, () => {$scope.reset();$scope.listOffices(); alert(successMsg)}, () =>{alert(errorMsg)});
  };

  $scope.update = () => {
	  if($scope.officeForm.$invalid)
          return false;
          $scope.offices.emailidpassword=SHA256($scope.offices.emailidpassword);
    	$scope.offices.smspassword=SHA256($scope.offices.smspassword);
	  $scope.method = "POST";
   $scope.urlEndpoint = "./updateinitoffices.htm";
 	commonInitService.save($scope.method, $scope.urlEndpoint, $scope.offices, () => {$scope.reset();$scope.listOffices(), alert(successMsg)}, () => {alert(errorMsg)});
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