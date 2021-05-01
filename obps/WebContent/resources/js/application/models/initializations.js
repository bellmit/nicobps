
function Pageurls() {
	var Pageurls = {
		"urlcode" : -1,
		"pageurl" : "",
		"subsubmenu" : "",
		"subsubmenuicon" : "",
		"submenu" : "",
		"submenuicon" : "",
		"parent" : "",
		"parenticon" : "",
		"checked" : "",
		"ordering" : ""
	};
	return Pageurls;
}

function Userlogins() {
	var Userlogins = {
		"usertype" : "F",
		"designation" : "",
		"usercode" : 0,
		"username" : "",
		"userpassword" : "",
		"entrydate" : new Date(),
		"enabled" : "Y",
		"mobileno" : '',
		"fullname" : "",
	};
	return Userlogins;
}
function Userpages() {
	var Userpages = {
		"userpagecode" : 0,
		"usercode" : 0,
		"url" : new Pageurls()
	};
	return Userpages;
}
