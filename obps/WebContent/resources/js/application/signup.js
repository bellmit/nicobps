function signupDetails()
{
    var signupDetails = {
        usercode:"",
        username:"", 
        userpassword:"",
        userpasswordconfirm:"",
        fullname:"",        
        mobileno:"",
        designation:"",
                
        licenseetypecode:"",
        firmindividual:"",
        firmname:"",
        applicantsname:"",
        gender:"",
        
        preaddressline1:"",
        preaddressline2:"",
        previllagetown:"",
        prestatecode:"",
        predistrictcode:"",
        prepincode:"",        
        
        peraddressline1:"",
        peraddressline2:"",
        pervillagetown:"",
        perstatecode:"",
        perdistrictcode:"",
		perpincode:"",  

		listLicenseesregistrationsm:"",
		userresponsecaptcha:"",

		issms:"N",
		isemail:"",		
		isotp:"N",
		emailotp:"",
		mobileotp:""  		            
    };
    return signupDetails;
}

jQuery(document).ready(function() {        
    PasswordStrength("username","userpassword");     
});

var app = angular.module("applicationApp", ["checklist-model"]);
app.controller('applicationController', function($scope)
{			
	$scope.signupDetails = new signupDetails();
    $scope.listGenders = new Gender();
    $scope.listLicenseetypes= new CommonMap();
    $scope.listStates = new CommonMap();
    $scope.listDistricts = new CommonMap();
    $scope.listDistrictsPre = new CommonMap();
    $scope.listDistrictsPer = new CommonMap();
	$scope.listLicenseesregistrationsm = [];
    
    loadForm();
    
	$scope.enableFirmName = function() {  
		$scope.signupDetails.firmname="";
		if($scope.signupDetails.firmindividual=="F"){
			jQuery("#firmname").attr("readonly",false);
		}else{
			jQuery("#firmname").attr("readonly",true);
		}		
	}     
    
    $scope.populateCombo = function(status) {    	    
        $scope.resetCombo(status);                       
        switch(status){     
            case 1 : if($scope.signupDetails.prestatecode!==null){
                        $scope.listCombo(status,$scope.signupDetails.prestatecode);                    
                     }break;                 
            case 2 : if($scope.signupDetails.perstatecode!==null){
                        $scope.listCombo(status,$scope.signupDetails.perstatecode);                    
                     }break;                           
        }                
    };     
    $scope.resetCombo = function(status) { 
        switch(status){    
            case 1 :{       
            			$scope.signupDetails.predistrictcode="";          
						$scope.listDistrictsPre = new CommonMap();
                    }break;        
            case 2 :{              
            			$scope.signupDetails.perdistrictcode="";   
        			    $scope.listDistrictsPer = new CommonMap();
                    }break;                                     
        }          
    };	     
	$scope.listCombo = function(status) {  		
	    switch(status){  
	        case 1 : $scope.listDistrictsPre=$scope.listDistricts.filter((x) => x.value1 == $scope.signupDetails.prestatecode);break;                                                                                                                                       
	        case 2 : $scope.listDistrictsPer=$scope.listDistricts.filter((x) => x.value1 == $scope.signupDetails.perstatecode);break;                                                                                                                              
	 	}
	}        
    
	$scope.chechThis = function(index) {  			
        $scope.listLicenseesregistrationsm[index].registrationdescription="";
	}     
        
    $scope.submitDetails = function() {     	
        $scope.signupDetails.listLicenseesregistrationsm=JSON.stringify($scope.listLicenseesregistrationsm);
        if(validateDetails($scope.signupDetails))
        {
			var index = $scope.listLicenseetypes.findIndex((x) => x.key == $scope.signupDetails.licenseetypecode); 
			if(index!=-1)
			{
				$scope.signupDetails.designation=$scope.listLicenseetypes[index].value;    	
				//$scope.signupDetails.designation=$scope.listLicenseetypes[$scope.listLicenseetypes.map(function(d){return d["key"]}).indexOf($scope.signupDetails.licenseetypecode)].value;
			}    			
			if($scope.signupDetails.isotp==="Y")
			{       		        
				$scope.signupDetails.userpassword=SHA256($scope.signupDetails.userpassword); 
			}       	
        	submitSignupDetails($scope.signupDetails);
        }                                  
    };     
    
    $scope.resetDetails = function() {           
		$scope.signupDetails = new signupDetails();
	    $scope.listGenders = new Gender();
	    $scope.listLicenseetypes= new CommonMap();
	    $scope.listStates = new CommonMap();
	    $scope.listDistricts = new CommonMap();
	    $scope.listDistrictsPre = new CommonMap();
	    $scope.listDistrictsPer = new CommonMap();
	    $scope.listLicenseesregistrationsm = new CommonMap();
	    loadForm();          
    };          
		
    $scope.copyAddress = function(status) {   
         if(status){
			$scope.listDistrictsPer=$scope.listDistrictsPre
			$scope.signupDetails.peraddressline1 = $scope.signupDetails.preaddressline1;
			$scope.signupDetails.peraddressline2 = $scope.signupDetails.preaddressline2;
			$scope.signupDetails.pervillagetown = $scope.signupDetails.previllagetown;
			$scope.signupDetails.perstatecode = $scope.signupDetails.prestatecode;
			$scope.signupDetails.perdistrictcode = $scope.signupDetails.predistrictcode;
			$scope.signupDetails.perpincode = $scope.signupDetails.prepincode;			
         }else{
			$scope.listDistrictsPer=new CommonMap();
			$scope.signupDetails.peraddressline1 = "";
			$scope.signupDetails.peraddressline2 = "";
			$scope.signupDetails.pervillagetown = "";
			$scope.signupDetails.perstatecode = "";
			$scope.signupDetails.perdistrictcode = "";
			$scope.signupDetails.perpincode = "";				 
		 }
    };	

	$scope.otpcount=0;	
    $scope.resendOTP = function() {           
	    jQuery.ajax({
	        url: "resendOTP.htm",   
	        data:{'mobileno' : $scope.signupDetails.mobileno,'username': $scope.signupDetails.username},       
	        type: "POST",
	        success: function(data)
	        {   
	        	$scope.otpcount++;   
	        	//alert(data) 
	        	jQuery("#ajaxLoading").fadeOut();                                
	        },
	        error: function(request, status, error) { 
	        	//alert(status+" : "+JSON.stringify(request));  
	        	jQuery("#ajaxLoading").fadeOut();            
	        }
	    });          
    }; 	


});


function copyAddr(obj){
    var scope = angular.element(jQuery("#applicationForm")).scope();
    scope.$apply(function() { 
        if(jQuery(obj).prop('checked')){            
            scope.copyAddress(true); 
        }else{
            scope.copyAddress(false); 
        }
    });            
}

function validateDetails(signupDetails)
{
	if(signupDetails.licenseetypecode==""){
		showMsg("licenseetypecode","This field is required");
		return false;
	}else{
		showMsg("licenseetypecode","");
	}	
	
	if(signupDetails.firmindividual==""){
		showMsg("firmindividual","This field is required");
		return false;
	}else{
		showMsg("firmindividual","");
	}		

	if(signupDetails.firmindividual=='F' && signupDetails.firmname=="")
	{
		showMsg("firmname","This field is required");
		return false;
	}else{
		showMsg("firmname","");
	}
	
	if(signupDetails.fullname==""){
		showMsg("fullname","This field is required");
		return false;
	}else{
		showMsg("fullname","");
	}		

	if(signupDetails.gender==""){
		showMsg("gender","This field is required");
		return false;
	}else{
		showMsg("gender","");
	}	

	if(signupDetails.username==""){
		showMsg("username","This field is required");
		return false;
	}else if(!signupDetails.username.match(PATTERN_EMAIL)){
		showMsg("username","Invalid emal id");
		return false;
	}else {
		showMsg("username","");
	}	
      	
	if(signupDetails.mobileno==""){
		showMsg("mobileno","This field is required");
		return false;
	}else if(!signupDetails.mobileno.match(PATTERN_MOBILE)){
		showMsg("mobileno","Invalid email id");
		return false;
	}else{
		showMsg("mobileno","");
	}	
	
	if(signupDetails.userpassword==""){
		showMsg("userpassword","This field is required");
		return false;
	}else{
		showMsg("userpassword","");
	}
	
    var strength =(jQuery('#percent').text()).split('%');    
    if(strength[0]<50)
    {                
		showMsg("userpassword","Please use a strong password");
		return false;        
    }else{
		showMsg("userpassword","");	
	}
	
	if(signupDetails.userpasswordconfirm==""){
		showMsg("userpasswordconfirm","This field is required");
		return false;
	}else{
		showMsg("userpasswordconfirm","");
	}	
	
	if(signupDetails.userpassword!==signupDetails.userpasswordconfirm){
		showMsg("userpasswordconfirm","Confirm password is not matching");
		return false;
	}else{
		showMsg("userpasswordconfirm","");
	}
	
	//////////////////////////////////////	
	
	if(signupDetails.previllagetown==""){
		showMsg("previllagetown","This field is required");
		return false;
	}else{
		showMsg("previllagetown","");
	}	
	
	if(signupDetails.prestatecode==""){
		showMsg("prestatecode","This field is required");
		return false;
	}else{
		showMsg("prestatecode","");
	}	
		
	if(signupDetails.predistrictcode==""){
		showMsg("predistrictcode","This field is required");
		return false;
	}else{
		showMsg("predistrictcode","");
	}	
	
	if(signupDetails.prepincode==""){
		showMsg("prepincode","This field is required");
		return false;
	}else if(!signupDetails.prepincode.match(PATTERN_PINCODE)){
		showMsg("prepincode","Invalid pincode");
		return false;
	}else{
		showMsg("prepincode","");
	}
	//////////////////////////////////////
	
	if(signupDetails.pervillagetown==""){
		showMsg("pervillagetown","This field is required");
		return false;
	}else{
		showMsg("pervillagetown","");
	}	
	
	
	if(signupDetails.perstatecode==""){
		showMsg("perstatecode","This field is required");
		return false;
	}else{
		showMsg("perstatecode","");
	}	
			
	if(signupDetails.perdistrictcode==""){
		showMsg("perdistrictcode","This field is required");
		return false;
	}else{
		showMsg("perdistrictcode","");
	}	
	
	if(signupDetails.perpincode==""){
		showMsg("perpincode","This field is required");
		return false;
	}else if(!signupDetails.perpincode.match(PATTERN_PINCODE)){
		showMsg("perpincode","Invalid pincode");
		return false;
	}else{
		showMsg("perpincode","");
	}	

	if(JSON.parse(signupDetails.listLicenseesregistrationsm).length>0)
	{
	    var isSelected = 0;
	    var objectArray = Object.entries(JSON.parse(signupDetails.listLicenseesregistrationsm));    
	    objectArray.forEach(([key, value]) => {
	      if (value.ischecked == true) {
	        isSelected++;
	      }
	    });
	    
	    if (isSelected == 0) {		
			jQuery('#listLicenseesregistrationsmMsg').html("* This field is required").show();
			return false;
	    } else {
	        jQuery('#listLicenseesregistrationsmMsg').html("").hide();        
	    }		
	}

	if(signupDetails.isotp==="Y")
	{
		if(signupDetails.isemail==="Y"){
			if(signupDetails.emailotp==""){
				showMsg("emailotp","Enter email OTP");
				return false;
			}else{
				showMsg("emailotp","");
			}
		}
		if(signupDetails.issms==="Y"){
			if(signupDetails.mobileotp==""){
				showMsg("mobileotp","Enter mobile OTP");
				return false;
			}else{
				showMsg("mobileotp","");
			}
		}				
	}

  	
	if(signupDetails.userresponsecaptcha==""){
		showMsg("jcaptcha","This field is required");
		return false;
	}else{
		showMsg("jcaptcha","");
	}	  	
  	
	return true;	
}


function loadForm() {	
    jQuery.ajax({
        url: "initSignupForm.htm",
        dataType: 'json',
        type: "GET",
        success: function(data)
        {        	    
            var scope = angular.element(jQuery("#applicationForm")).scope();
            scope.$apply(function() {   
            	scope.signupDetails = new signupDetails();  
            	scope.listLicenseetypes = data.listLicenseetypes;             	
                scope.listStates = data.listStates;        
                scope.listDistricts = data.listDistricts;    
				
				scope.signupDetails.issms = data.issms;
				scope.signupDetails.isemail = data.isemail;

                scope.listLicenseesregistrationsm=[];
                data.listLicenseesregistrationsm.forEach((row)=>{
                  var item={};
                  item.ischecked=false;
                  item.licenseeregistrationcode=row.key;
                  item.licenseedescription=row.value;
                  item.registrationdescription="";
			      scope.listLicenseesregistrationsm.push(item);
			    })          
            });            
        },
        error: function(request, status, error) {
            //alert(request.responseText);
        }
    });
}

function submitSignupDetails(signupDetails)
{       	
    var flag=false;
    ConfirmBox(" Click OK to register. Click Cancel if you want to review your entries",(response)=>{
    	if(!response) {return false;}     
		    jQuery.ajax({
		        url: "submitSignupDetails.htm",        
		        data: signupDetails,
		        type: "POST",
		        success: function(data)
		        {       
					jQuery("#ajaxLoading").fadeOut();
					var scope = angular.element(jQuery("#applicationForm")).scope();
					var msg="";				
					if(data==="0"){
						msg="Please enter the otp sent to your email/mobile!";	
						jQuery('#successMsg').html(msg).show();    
						scope.$apply(function() {   
							signupDetails.isotp="Y";        
							signupDetails.userresponsecaptcha="";
						});			
						changeCaptcha()			
					}else if(data==="1"){
						msg="The application has been submitted succesfully, please upload the required documents. Alternatively, you may login and upload at a later stage.";	
						MsgBox(msg,true,()=>{
							window.location="uploadenclosuresext.htm";
						});							 				
					}				
					
					//if(data==="1"){			
					//}			
		        	/*                                 
			            jQuery('#successMsg').html("* "+data).show();  
			            loadForm();    
			            jQuery("#percent").text("0%");
			            jQuery("#results").text("");
			            jQuery("#colorbar").css('width',0);
		            */                                   
		        },
		        error: function(request, status, error) { 
					jQuery("#ajaxLoading").fadeOut();
		        	//alert(status+" : "+JSON.stringify(request));         
		            jQuery('#successMsg').html("* Error : "+request.responseText).show();           
		        }
		    }); 
    });
}	

//=================================================================
/*
function chechThisJQ(obj)
{    
	var index=jQuery(obj).attr("id").split("_")[1];
	var registrationdescription ="registrationdescription_"+index;
	if(jQuery(obj).prop('checked')){
		jQuery("#"+registrationdescription).attr('disabled',false);
	}else{
		jQuery("#"+registrationdescription).attr('disabled',true);
	}   	
    var scope = angular.element(jQuery("#applicationForm")).scope();
    scope.$apply(function() {   
    	scope.signupDetails.listLicenseesregistrationsm[index].registrationdescription="";
    }); 			 
}	
*/


