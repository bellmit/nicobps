jQuery(document).ready(function() {        
    
});

var app = angular.module("applicationApp", ["checklist-model"]);
app.controller('applicationController', function($scope)
{			
    $scope.listEnclosures = [];   
    $scope.applicationEnclosures = {};   
    loadForm();
    	
    $scope.submitDetails = function() {  
  	    
    	$scope.applicationEnclosures.listEnclosures=JSON.stringify($scope.listEnclosures);   
    	//alert("list enclosures"+$scope.applicationEnclosures.listEnclosures)  	        
        if(validateDetails($scope.applicationEnclosures))
        {      	      	     
        	submitEnclosureDetails($scope.applicationEnclosures);
        }                                  
    };     
             
});

function validateDetails(applicationEnclosures)
{
	return true;
}

function addFile(id)  
{	
	var index = id.split("_")[1];				   
	var scope = angular.element(jQuery("#applicationForm")).scope();
	scope.$apply(function() {               	            	
	  var file = document.querySelector("#"+id).files[0];			  
	  if (file) 
	  {
	    var reader = new FileReader();
		reader.readAsDataURL(file);
		reader.addEventListener("load",function() 
		{				
			scope.listEnclosures[index].filecontant=reader.result;										
		}, false);				
	  }	      	  
	});   
}    	

function loadForm() {	
    jQuery.ajax({
        url: "initUploadEnclosuresForm.htm",
        dataType: 'json',
        type: "GET",
        success: function(data)
        {        	    
            var scope = angular.element(jQuery("#applicationForm")).scope();
            scope.$apply(function() {               	            	
            	scope.listEnclosures=[];
                data.listEnclosures.forEach((row)=>{
                  var item={};
                  item.ischecked=false;
                  item.enclosurecode=row.key;
                  item.enclosurename=row.value;
				  item.usercode=row.value1;       				  
                  item.filecontant="";               
			      scope.listEnclosures.push(item);
			    })          	                    
            });            
        },
        error: function(request, status, error) {
            //alert(request.responseText);
        }
    });
}

function submitEnclosureDetails(applicationEnclosures)
{       			
    var flag=confirm(" Click OK to register. Click Cancel if you want to review your entries");
    if(!flag) {return false;}      
    jQuery.ajax({
        url: "submitEnclosureDetails.htm",        
        data: applicationEnclosures,
        type: "POST",
        success: function(data)
        {                   		 
            var scope = angular.element(jQuery("#applicationForm")).scope();
            scope.$apply(function() {               	            	
            	scope.applicationEnclosures = {};       	                    
            });          
        	                
            jQuery('#successMsg').html("* "+data).show();  
            loadForm();                                     
        },
        error: function(request, status, error) {    
        	//alert(status+" : "+JSON.stringify(request));        
            jQuery('#successMsg').html("* "+request.responseText).show();           
        }
    }); 
}	




