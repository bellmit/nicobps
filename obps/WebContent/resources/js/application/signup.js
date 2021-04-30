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
        userresponsecaptcha:""            
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
        	$scope.signupDetails.userpassword=SHA256($scope.signupDetails.userpassword);        	
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
        
});

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
    var flag=confirm(" Click OK to register. Click Cancel if you want to review your entries");
    if(!flag) {return false;}      
    jQuery.ajax({
        url: "submitSignupDetails.htm",        
        data: signupDetails,
        type: "POST",
        success: function(data)
        {        
        	alert("* "+data);
        	window.location="uploadenclosuresext.htm";  
        	/*                                 
	            jQuery('#successMsg').html("* "+data).show();  
	            loadForm();    
	            jQuery("#percent").text("0%");
	            jQuery("#results").text("");
	            jQuery("#colorbar").css('width',0);
            */                                   
        },
        error: function(request, status, error) { 
        	alert(status+" : "+JSON.stringify(request));         
            jQuery('#successMsg').html("* Error : "+request.responseText).show();           
        }
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


