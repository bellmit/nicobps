function signupDetails() {
	var signupDetails = {
		username: '',
		userpassword: "",
		userpasswordconfirm: "",

		userresponsecaptcha: ""
	};
	return signupDetails;
}

jQuery(document).ready(function() {
	PasswordStrength("username", "userpassword");

	jQuery("#jcaptchaimg").on('click', function() {
		changeCaptcha();
	});
});

var app = angular.module("applicationApp", ["checklist-model"]);
app.controller('applicationController', function($scope) {
	$scope.signupDetails = new signupDetails();
	$scope.signupDetails.username = jQuery("#un").text();

	$scope.submitDetails = function() {
		if (validateDetails($scope.signupDetails)) {
			$scope.signupDetails.userpassword = SHA256($scope.signupDetails.userpassword);
			updatePassword($scope.signupDetails);
		}
	};
});

function validateDetails(signupDetails) {
	if (signupDetails.userpassword == "") {
		showMsg("userpassword", "This field is required");
		return false;
	} else {
		showMsg("userpassword", "");
	}

	var strength = (jQuery('#percent').text()).split('%');
	if (strength[0] < 50) {
		showMsg("userpassword", "Please use a strong password");
		return false;
	} else {
		showMsg("userpassword", "");
	}

	if (signupDetails.userpasswordconfirm == "") {
		showMsg("userpasswordconfirm", "This field is required");
		return false;
	} else {
		showMsg("userpasswordconfirm", "");
	}

	if (signupDetails.userpassword !== signupDetails.userpasswordconfirm) {
		showMsg("userpasswordconfirm", "Confirm password is not matching");
		return false;
	} else {
		showMsg("userpasswordconfirm", "");
	}

	return true;
}


function updatePassword(signupDetails) {
	jQuery.ajax({
		url: "updatePassword.htm",
		data: signupDetails,
		type: "POST",
		success: function(data) {
			var scope = angular.element(jQuery("#applicationForm")).scope();
			scope.$apply(function() {
				scope.signupDetails = {};
				scope.signupDetails.username = jQuery("#un").text();
				jQuery('#successMsg').html("* " + data).show();
				jQuery("#percent").text("0%");
				jQuery("#results").text("");
				jQuery("#colorbar").css('width', 0);
			});
		},
		error: function(request, status, error) {
			//alert(status+" : "+JSON.stringify(request));         
			jQuery('#successMsg').html("* Error : " + request.responseText).show();
		}
	});
}



