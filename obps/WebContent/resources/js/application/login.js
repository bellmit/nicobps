
$(document).ready(function() {

	$('#loginForm').on('submit', function() {
		
		beforeSubmit();
	});

	$('#jcaptchaimg').on('click', function() {
		
		changeCaptchaLogin();
	});
});
function beforeSubmit() {
	jQuery('#password').val(
		SHA256(jQuery('#password').val()));
	//jQuery("#loginForm").submit();
	return true;
}
function changeCaptchaLogin() {
	var src = "./jcaptchalogin.jpg?date="
		+ new Date();
	jQuery("#jcaptchaimg").attr("src", src);
}

