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
	//$scope.filetypes= new Filetype();
	
	$scope.filetypelist = [];
	   
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

    $scope.checks();
   jQuery('html, body').animate({
            scrollTop: 0
        }, 2000);
	};
  $scope.reset = function () {
	
  	$scope.enclosures = new Enclosures();
	$scope.actionButton =1;
//	$scope.filetypes= new Filetype();
	$scope.filetypelist = [];
	jQuery.each($scope.filetypes, function(i, v) {
	
				v.checked = false;
			});
	   
};

 $scope.checks = function() {
		
			var response;

			jQuery.each($scope.filetypes, function(i0, v0) {

				response = false;
					
					console.log('filetypedescription' + v0.filetypedescription);
						console.log('$scope.enclosures.filetypes' + $scope.enclosures.filetypes);
								if ($scope.enclosures.filetypes != null){
									if ($scope.enclosures.filetypes.includes(v0.filetypedescription)) {
								response = true;
								$scope.filetypelist.push(v0.filetypedescription);
								
							}else{
								response = false;
							}
						}else{
								response = false;
							}
					
				
				v0.checked = response;
			});
		};
		
		$scope.validate =()=>{
			 var file = [];


 
				if (($scope.enclosures.enclosurename!="" && $scope.enclosures.enclosurename!= undefined) &&  ($scope.enclosures.filetypes!="" && $scope.enclosures.filetypes!=undefined ) ){
					console.log("1");
					file.push({
						enclosurecode: $scope.enclosures.enclosurecode,
						enclosurename: $scope.enclosures.enclosurename,
						enclosuredescription:$scope.enclosures.enclosuredescription,
						filetypes: $scope.enclosures.filetypes
					});
				}
			
			if (file.length === 0) {
				alert("Please fill or check inputs!! ");
				return false;
			}
			
			return true;
		}
   $scope.save = function () {
    	
     if($scope.enclosureForm.$invalid)
        return false;

	   if(!$scope.validate())
			return false;


       $scope.method = "POST";
        $scope.urlEndpoint = "./initenclosures.htm";
    	

		commonInitService.save1($scope.method, $scope.urlEndpoint, $scope.enclosures, function (response) {
		
				if (response.data=="Success") {
					MsgBox("Enclosures inserted successfully.");
					$scope.reset();
					$scope.listEnclosures();
					 $scope.listfiletypes();
				} else if (response.data=="exist") {
					MsgBox("Enclosure name already exist");
					$scope.listEnclosures();
					 $scope.listfiletypes();
				}
				else if(response.data=="Error"){
				alert("Error");
				}
				else
					MsgBox(response.data);
			}, function () {
				
				alert("Error");
			});
       
        
    	
  };
 


   
  
    
 $scope.update = () => {
	  if($scope.enclosureForm.$invalid)
          return false;
           
           if(!$scope.validate())
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
				}
				else if(response.data=="Error"){
				alert("Error");
				}
				else
					MsgBox(response.data);
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
                    "title": "Enclosure Types",
                    "data": "filetypes"
                }, 

                
                {
                    "title": "Action",
                    "sortable": false,
                    "data": "enclosurecode",
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
    $scope.listfiletypes = () => {
//			commonInitFactory.listFiletypes((response)=>{
//				console.log("listFiletypes ::" + response.toString());
//            		//$scope.filetypes=response;
//
//    });

       jQuery.ajax({
				type: "GET",
				url: "./listFiletypes.htm",
				// dataType: "json",
				contentType: "application/json; charset=utf-8",
				success: function(response) {
					var scope = angular.element($("#enclosuresCtrl")).scope();
					scope.$apply(function() {
						scope.filetypes = response;

					});
					
				},
				error: function(xhr) {
					alert(xhr.status + " = " + xhr);
					alert(
						"Sorry, there was an error while trying to process the request."
					);
				},
			});


	};
	
	
	
	$scope.addfiletypes = function(filetype) {
		 let uniqueFiletypes = [...new Set($scope.filetypelist)];

         console.log("unique :: "+uniqueFiletypes);
         $scope.filetypelist= uniqueFiletypes;

	    if(filetype.checked) {
	        $scope.filetypelist.push(filetype.filetypedescription);
	    } else {
		  
	        var toDel = $scope.filetypelist.indexOf(filetype.filetypedescription);
	        $scope.filetypelist.splice(toDel, 1);
	    }

       //  console.log("filetypeslist :" +$scope.filetypelist.toString());
        
        $scope.enclosures.filetypes=$scope.filetypelist.toString();
       //  console.log( " enclosures : "+$scope.enclosures.filetypes.toString());
   }

        $scope.listEnclosures();
        $scope.listfiletypes();
  

        

      
        
        
    }]);
