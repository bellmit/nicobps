app.controller('enclosuresCtrl', ['$scope', '$sce', '$compile','$timeout','commonInitFactory', 'commonInitService', 
	function ($scope, $sce, $compile,$timeout,commonInitFactory, commonInitService) {
	var scope = angular.element($("#enclosuresCtrl")).scope();
	commonInitService.success();
	/* Common Ajax Params */
	var successMsg = "Success: Enclosures created or updated successfully";
	var errorMsg = "Error: Unable to perform action";
	$scope.errorCallback = "";
	$scope.method = "POST";
	$scope.successCallback = "";
	$scope.urlEndpoint = "";
	$scope.actionButton = 1;
	
	/*------------------------*/
	$scope.enclosures = new Enclosures();
	$scope.enclosurereg=[];
	
	   
    $scope.trustHTML = function (post) {
		
       return $sce.trustAsHtml(post);
 };

   $scope.edit = function (enclosurecode) {
    	$scope.actionButton = 2;
    	$scope.enclosures = new Enclosures();
 
        $scope.enclosurereg.forEach((o, x) => {
        	if (o.enclosurecode == enclosurecode){
        		$scope.enclosures = o;
        		
        	}
        }); 
   jQuery('html, body').animate({
            scrollTop: 0
        }, 2000);
	};
  $scope.reset = function () {
  	$scope.enclosures = new Enclosures();
	$scope.actionButton =1;
};
	
   $scope.save = function () {
    	
     if($scope.enclosureForm.$invalid)
        return false;
       $scope.method = "POST";
        $scope.urlEndpoint = "./initenclosures.htm";
    	

		commonInitService.save1($scope.method, $scope.urlEndpoint, $scope.enclosures, function (response) {
		
				if (response.data=="Success") {
					MsgBox("Enclosures inserted successfully.");
					$scope.reset();
					$scope.listEnclosures();
				} else if (response.data=="exist") {
					MsgBox("Enclosure name already exist");
					$scope.listEnclosures();
				}else if (response.data=="m1") {
					MsgBox("No Special Characters or Numbers allowed in Enclosure Name");
					$scope.listEnclosures();
				
				}else if (response.data=="m2") {
					MsgBox("No Special Characters or Numbers allowed in Enclosure Description");
					$scope.listEnclosures();
				}else if (response.data=="50") {
					MsgBox("Enclosure Name Cannot be more than 50 characters");
					$scope.listEnclosures();
				
				}else if (response.data=="255") {
					MsgBox("Enclosure Description Cannot be more than 50 characters");
					$scope.listEnclosures();
				}
				else if(response.data=="Error"){
				alert("Error");
				}
			}, function () {
				
				alert("Error");
			});
       
        
    	
  };
 


   
  
    
 $scope.update = () => {
	  if($scope.enclosureForm.$invalid)
          return false;
           
          
	  $scope.method = "POST";
   $scope.urlEndpoint = "./updateinitenclosures.htm";

commonInitService.save1($scope.method, $scope.urlEndpoint, $scope.enclosures, function (response) {
		
				if (response.data=="Success") {
					MsgBox("Enclosures Updated successfully.");
					$scope.reset();
					$scope.listEnclosures();
				} else if (response.data=="exist") {
					MsgBox("Enclosure name already exist");
					$scope.listEnclosures();
				}else if (response.data=="m1") {
					MsgBox("No Special Characters or Numbers allowed in Enclosure Name");
					$scope.listEnclosures();
				
				}else if (response.data=="m2") {
					MsgBox("No Special Characters or Numbers allowed in Enclosure Description");
					$scope.listEnclosures();
				}else if (response.data=="50") {
					MsgBox("Enclosure Name Cannot be more than 50 characters");
					$scope.listEnclosures();
				
				}else if (response.data=="255") {
					MsgBox("Enclosure Description Cannot be more than 50 characters");
					$scope.listEnclosures();
				}
				else if(response.data=="Error"){
				alert("Enclosure Name Already exists");
				}
			}, function () {
				
				alert("Error");
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
                    "data": "enclosurecode"
                },
{
                    "title": "Enclosure Name",
                    "data": "enclosurename"
                },
                {
                    "title": "Enclosure Description",
                    "data": "enclosuredescription"
                }, 

                
                {
                    "title": "Action",
                    "sortable": false,
                    "data": "enclosurecode",
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
   $scope.enclosureForm = function() {

      
        if($scope.enclosures.enclosuredescription === "" || $scope.enclosures.enclosuredescription === null){
            jQuery("#enclosuredescription").focus();
            alert("enclosuredescription cannot be empty");
            return false;
        }
      
            return true;
        };

      /* READ DATA */
     $scope.listEnclosures = (enclosurecode = 0) => {
        	
        	if(enclosurecode == 0){
        		commonInitFactory.listEnclosures((response)=>{
            		$scope.enclosurereg=response;
            		$scope.setDataTable($scope.enclosurereg);
            	});
        	}else{
        		commonInitFactory.listEnclosures((response)=>{
        			$scope.enclosurereg.forEach((o, x) => {
        				if(o.enclosurecode == enclosurecode){
        					$scope.enclosurereg[x] = response;

        				}
        			});
            		$scope.setDataTable($scope.enclosurereg);
            	}, enclosurecode);
        	}
        	
        };
        $scope.listEnclosures();
  

        

      
        
        
    }]);
