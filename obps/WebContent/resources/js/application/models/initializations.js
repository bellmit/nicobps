
function Pageurls() {
	var Pageurls = {
		"urlcode": -1,
		"pageurl": "",
		"subsubmenu": "",
		"subsubmenuicon": "",
		"submenu": "",
		"submenuicon": "",
		"parent": "",
		"parenticon": "",
		"checked": "",
		"ordering": ""
	};
	return Pageurls;
}

function Userlogins() {
	var Userlogins = {
		"usertype": "F",
		"designation": "",
		"usercode": 0,
		"username": "",
		"userpassword": "",
		"entrydate": new Date(),
		"enabled": "Y",
		"mobileno": '',
		"fullname": "",
	};
	return Userlogins;
}
function Userpages() {
	var Userpages = {
		"userpagecode": 0,
		"usercode": 0,
		"url": new Pageurls()
	};
	return Userpages;
}

function Licenseesregistration() {
	var Licenseesregistration = {
		"licenseeregistrationcode": 0,
		"licenseedescription": "",
		"enabled": "Y"
	};
	return Licenseesregistration;
}


function FeeType() {
	var FeeType = {
		"feetypecode": 0,
		"feetypedescription": "",
		"enabled": "Y"
	};
	return FeeType;
}
function Filetype() {
	var Filetype = {
		"filetypecode": 0,
		"filetypedescription": "",
		"enabled": "Y",
		"isChecked":false
	};
	return Filetype;
}

function Questionaire() {
	var Questionaire = {
		"questioncode": 0,
		"questiondescription": "",
		"enabled": "Y"
	};
	return Questionaire;
}

function OfficeQuestionaires() {
	var OfficeQuestionaires = {
		"officecode": 0,
		"questioncode": 0,
		};
	return OfficeQuestionaires;
}


function Occupancy() {
	var Occupancy = {
		"occupancycode": "",
		"occupancyname": "",
		"occupancyalias": ""
	};
	return Occupancy;
}
function SubOccupancy() {
	var SubOccupancy = {
		"occupancycode": "",
		"suboccupancycode": "",
		"suboccupancyname": "",
		"description": "",
		"occupancies": new Occupancy()
		
	};
	return SubOccupancy;
}

function Usage() {
	var Usage = {
		"suboccupancycode": "",
		"usagecode": "",
		"usagename": "",
		"description": "",
		"suboccupancies": new SubOccupancy()
		
	};
	return Usage;
}

function LicenseeTypes() {
	var LicenseeTypes = {
		"licenseetypecode": 0,
		"licenseetypename": "",
		"enabled": "Y"
	};
	return LicenseeTypes;
}

function Office() {
	var Office = {
		"officecode": 0,
		"officename": "",
		"department":new Department()
	};
	return Office;
}

function Department()
{
    var Department = {
        departmentcode: 0,
        departmentname: "",
     
    };
    return Department;
}

function FeeMaster() {
	var FeeMaster = {
		"feecode": 0,
		"feeamount": 0,
		"calculated": "N",
		"enabled": "Y",
		"entrydate": new Date(),
		"officecode":0,
		"licenseetypecode":0,
		"feetypecode":0,
		"officename1":"",
		"feetypes":new FeeType(),
		"offices": new Office(),
		"licenseetypes" : new LicenseeTypes(),
       

		
	};
	return FeeMaster;
}


function OfficeLocations(){
var OfficeLocations = {
		"officecode": 0,
		"locationcode": 0,
		"locationname": "",
		"nomenclature": "",
		"wardlocationlgdcode": "",
		"wardlocationno":0,
		
	};
	return OfficeLocations;

}
/*Bankit*/
function Enclosures(){
var Enclosures={
"enclosurecode":0,
"enclosurename":"",
"enclosuredescription":"",
"filetypes":""
}
};

function Modules(){
var Modules={
"modulecode":0,
"modulename":""

}
};

function ModulesEnclosures(){
var ModulesEnclosures={
"modulecode":0,
"enclosurecode":0

}
};

function PaymentModes(){
var PaymentModes={
"paymentmodecode":0,
"paymentmodedescription":"",
"mode":""


}
};

function OfficePaymentModes(){
var OfficePaymentModes={
"officecode":0,
"paymentmodecode":0

}
};


function Offices(){ 
var Offices={
"officecode":0,
"officename1":"",
"officename2":"",
"officename3":"",
"officeshortname":"",
"officelgdcode":"",
"signatoryname":"",
"signatorydesignation":"",
"senderemailid":"",
"emailidpassword":"",
"smsusername":"",
"smspassword":"",
"smssenderid":"",
"isregisteringoffice":"Y", 
"registeringofficecode":0,
"enabled":"",
"stateid":"",
"tenantid":"",
"logo":"",
}
};


/*BAnkit*/
