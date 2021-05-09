var signatures = {
	"JVBER": "application/pdf",
	"/9J/4": "image/jpeg",
	"IVBOR": "image/png"
};
var applicationStatus={
		1:"Registered ",
		2:"Application Fees Pending",
		3:"Verifications Pending",
		4:"Registration Fees Pending",
		5:"Approval Pending",
		6:"Approved"
};
function detectMimeType(b64) {
  return signatures[b64.toUpperCase().substr(0,5)];
}
app.controller("CommonCtrl", [
  "$scope","$http","$timeout","commonInitService",
  function ($scope,$http,$timeout,commonInitService) {
	  $scope.applicant={};
	  $scope.Licensees=[];
	  
	  $scope.getStatus=(processcode)=>{
		  return applicationStatus[processcode];
	  };
	  $scope.viewDetails=(applicant)=>{
		  $scope.applicant=applicant;
		  jQuery.fancybox.close();
		  jQuery.fancybox({
				href : '#detailView',
				'autoSize' : false,
				'transitionIn' : 'elastic',
				'transitionOut' : 'elastic',
				'speedIn' : 600,
				'speedOut' : 200,
				'overlayShow' : false,
				'modal' : false,
				'width':'100%',
			});
	  },	  
	  $scope.verify=(usercode,endpoint="./verifyStakeHolder.htm")=>{
		  let data ="usercode="+usercode;
		  commonInitService.http("POST", endpoint, data, function(response) {
			  if(response){
	        		MsgBox("Applicant verification complete.");
	        		$scope.listLicensees();
	        		jQuery.fancybox.close();
	        	}else{
	        		MsgBox("Error! Please try again.");		        		
	        	}
		  }, function() {
			  alert("Sorry, there was an error while trying to process the request.");
		  });
//		  jQuery.ajax({
//		        type: "POST",
//		        url: endpoint,
//		        data: "usercode="+usercode,
//		        success: function (response) {	
//		        	if(response){
//		        		MsgBox("Applicant verification complete.");
//		        		$scope.listLicensees();
//		        		jQuery.fancybox.close();
//		        	}else{
//		        		MsgBox("Error! Please try again.");		        		
//		        	}
//		        },
//		        error: function (xhr) {
//		          alert(xhr.status + " = " + xhr);
//		          alert(
//		            "Sorry, there was an error while trying to process the request."
//		          );
//		        },
//	      });
	  },
	  $scope.approve=(usercode)=>{
		  $scope.verify(usercode,"./approveStakeHolder.htm");
	  },
	  $scope.getEnclosure=(usercode,enclosurecode)=>{
		  jQuery.ajax({
		        type: "POST",
		        url: "./getEnclosure.htm",
		        data: "enclosurecode="+enclosurecode+"&usercode="+usercode,
		        success: function (response) {	
		        	if(response.length>0){
		        		jQuery('#enclosureWindow').html('<iframe src="data:'+detectMimeType(response)+';base64,'+response+'"' 
		        				+'style="width:100%;height:calc(80vh);" frameborder="0"></iframe>');		        		
		        	}else{
		        		jQuery('#enclosureWindow').html('<div class="card text-white bg-warning mb-3" style="width: 100%;">'
		        				  +'<div class="card-body"><h5 class="card-title">Enclosure not submitted.</h5></div>'
		        				+'</div>');
		        	}					  
		        },
		        error: function (xhr) {
		          alert(xhr.status + " = " + xhr);
		          alert(
		            "Sorry, there was an error while trying to process the request."
		          );
		        },
	      });
	  },
	  $scope.listLicensees=function(){
		  $http.post("./listLicensees.htm").success(
			  function(response, status, headers, config) {
					  $scope.Licensees=response
			  });		  
	  },
	  $http.post("./listEnclosures.htm").success(
			  function(response, status, headers, config) {
					  $scope.Enclosures=response
			  });	
	  $scope.listLicensees();
  },
]);