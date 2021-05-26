
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

function Occupancy() {
	var Occupancy = {
		"occupancycode": "",
		"occupancyname": "",
		"occupancyalias": ""
	};
	return Occupancy;
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
