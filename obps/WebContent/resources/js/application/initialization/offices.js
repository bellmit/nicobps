 var signatures = {
	"JVBER": "application/pdf",
	"/9J/4": "image/jpeg",
	"IVBOR": "image/png"
};
 function detectMimeType(b64) {
	return signatures[b64.toUpperCase().substr(0, 5)]; 
};

var logocode;
var extension;
var filesize;
const addFile=()=>{	
	
	  var file = document.querySelector("#logo").files[0];
	  filesize = file.size;
      
	  if (file) 
	  {
	  	var filename = $("#logo").val();
	  	extension = filename.replace(/^.*\./, '');
	  	if (extension == filename) {
            extension = '';
        } else {
            extension = extension.toLowerCase();
        }
       
	    var reader = new FileReader();
		reader.readAsDataURL(file);
		reader.addEventListener("load",function() 
		{				
			var scope = angular.element(jQuery("#officesCtrl")).scope();
			scope.$apply(function() {               	            	
				scope.offices.logo	= (reader.result).replace('data:image/jpeg;base64,','');
				scope.offices.logo	= scope.offices.logo.replace('data:image/jpg;base64,','');
				scope.offices.logo	= scope.offices.logo.replace('data:image/png;base64,','');
				scope.offices.extension=extension;
				scope.offices.filesize=filesize;
				
				
			});	
		}, false);		
	  }	   

}
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
	jQuery('#viewlogo').hide();
	
	/*------------------------*/
	$scope.offices = new Offices();
	$scope.officereg=[];
	
	
	   
    $scope.trustHTML = function (post) {
		
       return $sce.trustAsHtml(post);
 };
 $scope.edit = function (officecode) {
		 jQuery('#enclosureWindow').html("");
 		jQuery('#viewlogo').show()
 		logocode=officecode;
		
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
    jQuery('#viewlogo').hide();
  	jQuery('#enclosureWindow').html("");
  	$scope.offices = new Offices();
	$scope.actionButton =1;
	jQuery('#logo').val("");
};
$scope.showFile = (data, successCallback, errorCallback)=>{

		$.ajax({
            type: "POST",
            url: "./showFile.htm",
//            dataType: "json",
            async: false,
//            contentType: "application/json; charset=utf-8",
            data:{"officecode":logocode},
            success: function (response) {
           
			if(response!=""){
				jQuery('#enclosureWindow').html('<img src="data:' + detectMimeType(response) + ';base64,' + response + '"' +
							'style="width:150px;height:150px;" frameborder="0"/>');
			}
			else
				jQuery('#enclosureWindow').html('<label style="color:red">Logo Not Uploaded</label>');
          	
            	
            },
            error: function (xhr) {
            	alert(data);
                console.log("Error: ", xhr);
                alert("Sorry, there was an error while trying to process the request.");
                errorCallback(xhr.responseJSON);
            }            
        });
	} 
$scope.save = function () {
		
				
   		if($scope.offices.emailidpassword)
   	    $scope.offices.emailidpassword=SHA256($scope.offices.emailidpassword);
        if($scope.offices.smspassword)  
    	$scope.offices.smspassword=SHA256($scope.offices.smspassword);
    	$scope.method = "POST";
        $scope.urlEndpoint = "./initoffices.htm";
    	$timeout(()=>{console.log($scope.offices.logo)},0);
        commonInitService.save1($scope.method, $scope.urlEndpoint, $scope.offices, function (response) {
		
				if (response.data=="Success") {
					MsgBox("Offices Inserted successfully.");
					$scope.reset();
					$scope.listOffices();
				} else if (response.data=="exist") {
					MsgBox("Offices name already exist");
					$scope.listOffices();
				}else if (response.data=="officename1") {
					MsgBox("Special Characters allowed in Office Name1 are , . / ( ) - _ ");
					$scope.listOffices();
				
				}else if (response.data=="officename2") {
					MsgBox("Special Characters allowed in Office Name2 are , . / ( ) - _ ");
					$scope.listOffices();
				}else if (response.data=="officename3") {
					MsgBox("Special Characters allowed in Office Name3 are , . / ( ) - _ ");
					$scope.listOffices();
				}
				else if (response.data=="offshort") {
					MsgBox("No Special Characters or Numbers allowed in Office ShortHand Name");
					$scope.listOffices();
				
				}else if (response.data=="offsignatory") {
					MsgBox("Special Characters allowed in Signatory Name are , . / ( ) - _ ");
					$scope.listOffices();
				}
				else if (response.data=="officeshortdes") {
					MsgBox("Special Characters allowed in Signatory Designation are , . / ( ) - _ ");
					$scope.listOffices();
				}
				else if (response.data=="sms") {
					MsgBox("Special Characters allowed in SMS Username are , . / ( ) - _");
					$scope.listOffices();
				}
				else if (response.data=="1") {
					MsgBox("Office Name 1 Cannot exceed more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="2") {
					MsgBox("Office Name 2 Cannot exceed more than 255 characters");
					$scope.listOffices();
				}
				else if (response.data=="3") {
					MsgBox("Office Name 3 Cannot exceed more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="4") {
					MsgBox("Office Short Hand Name Cannot exceed more than 50 characters");
					$scope.listOffices();
				}
				else if (response.data=="5") {
					MsgBox("Signatory Name Cannot exceed more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="6") {
					MsgBox("Signatory Designation Cannot exceed more than 255 characters");
					$scope.listOffices();
				}
				else if (response.data=="7") {
					MsgBox("sms username Cannot exceed more than 25 characters");
					$scope.listOffices();
				
				}else if (response.data=="8") {
					MsgBox("sms password Cannot exceed more than 100 characters");
					$scope.listOffices();
				}
				else if (response.data=="9") {
					MsgBox("emailid Cannot be more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="10") {
					MsgBox("emailidpassword Cannot exceed more than 255 characters");
					$scope.listOffices();
				}else if (response.data=="emailidnotcorrect") {
					MsgBox("Please Enter correct emailid");
					$scope.listOffices();
				}
				else if (response.data=="tenantid") {
					MsgBox("Special Characters allowed in Tenant id are - _");
					$scope.listOffices();
				}
				else if (response.data=="tenantidlength") {
					MsgBox("Tenant ID Cannot exceed more than 255 characters ");
					$scope.listOffices();
				}
				else if (response.data=="stateid") {
					MsgBox("Special Characters allowed in State id are - _");
					$scope.listOffices();
				}
				else if (response.data=="stateidlength") {
					MsgBox("State ID Cannot exceed more than 255 characters  ");
					$scope.listOffices();
				}
				else if(response.data=="filetypeerror"){
				MsgBox("Invalid File Type. Only jpeg,jpg and png files are allowed");
				}
				else if(response.data=="filesizeerror"){
				MsgBox("File Size must not exceed 5MB");
				}
				else if(response.data=="Error"){
				MsgBox("Office Already exists");
				}
				else{
				MsgBox("Error");
				}
				jQuery('#enclosureWindow').html("");
				
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
					MsgBox("Offices Inserted successfully.");
					$scope.reset();
					$scope.listOffices();
				} else if (response.data=="exist") {
					MsgBox("Offices name already exist");
					$scope.listOffices();
				}else if (response.data=="officename1") {
					MsgBox("Special Characters allowed in Office Name1 are , . / ( ) - _ ");
					$scope.listOffices();
				
				}else if (response.data=="officename2") {
					MsgBox("Special Characters allowed in Office Name2 are , . / ( ) - _ ");
					$scope.listOffices();
				}else if (response.data=="officename3") {
					MsgBox("Special Characters allowed in Office Name3 are , . / ( ) - _ ");
					$scope.listOffices();
				}
				else if (response.data=="offshort") {
					MsgBox("No Special Characters or Numbers allowed in Office ShortHand Name");
					$scope.listOffices();
				
				}else if (response.data=="offsignatory") {
					MsgBox("Special Characters allowed in Signatory Name are , . / ( ) - _ ");
					$scope.listOffices();
				}
				else if (response.data=="officeshortdes") {
					MsgBox("Special Characters allowed in Signatory Designation are , . / ( ) - _ ");
					$scope.listOffices();
				}
				else if (response.data=="sms") {
					MsgBox("Special Characters allowed in SMS Username are , . / ( ) - _");
					$scope.listOffices();
				}
				else if (response.data=="1") {
					MsgBox("Office Name 1 Cannot exceed more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="2") {
					MsgBox("Office Name 2 Cannot exceed more than 255 characters");
					$scope.listOffices();
				}
				else if (response.data=="3") {
					MsgBox("Office Name 3 Cannot exceed more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="4") {
					MsgBox("Office Short Hand Name Cannot exceed more than 50 characters");
					$scope.listOffices();
				}
				else if (response.data=="5") {
					MsgBox("Signatory Name Cannot exceed more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="6") {
					MsgBox("Signatory Designation Cannot exceed more than 255 characters");
					$scope.listOffices();
				}
				else if (response.data=="7") {
					MsgBox("sms username Cannot exceed more than 25 characters");
					$scope.listOffices();
				
				}else if (response.data=="8") {
					MsgBox("sms password Cannot exceed more than 100 characters");
					$scope.listOffices();
				}
				else if (response.data=="9") {
					MsgBox("emailid Cannot be more than 255 characters");
					$scope.listOffices();
				
				}else if (response.data=="10") {
					MsgBox("emailidpassword Cannot exceed more than 255 characters");
					$scope.listOffices();
				}else if (response.data=="emailidnotcorrect") {
					MsgBox("Please Enter correct emailid");
					$scope.listOffices();
				}
				else if (response.data=="tenantid") {
					MsgBox("Special Characters allowed in Tenant id are - _");
					$scope.listOffices();
				}
				else if (response.data=="tenantidlength") {
					MsgBox("Tenant ID Cannot exceed more than 255 characters ");
					$scope.listOffices();
				}
				else if (response.data=="stateid") {
					MsgBox("Special Characters allowed in State id are - _");
					$scope.listOffices();
				}
				else if (response.data=="stateidlength") {
					MsgBox("State ID Cannot exceed more than 255 characters  ");
					$scope.listOffices();
				}
				else if(response.data=="filetypeerror"){
				MsgBox("Invalid File Type. Only jpeg,jpg and png files are allowed");
				}
				else if(response.data=="filesizeerror"){
				MsgBox("File Size must not exceed 5MB");
				}
				else if(response.data=="Error"){
				MsgBox("Office Already exists");
				}
				else{
				MsgBox("Error");
				}
				jQuery('#enclosureWindow').html("");
				
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
                    	let div = '<div style="text-align:center"><button  style="padding:.1em; margin-right: .5em" value="Edit" ng-click="edit(' + data + ')" class="button-primary">Edit</button>';
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
