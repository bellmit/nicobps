$(document).ready(function() {
	$('#loginForm').on('submit', function() {
		beforeSubmit();
	});

	$('.reloadCaptchaButton').on('click', function() {
		changeCaptchaLogin();
	});
});
function beforeSubmit() {
	$('#password').val(
		SHA256($('#password').val()));
	//jQuery("#loginForm").submit();
	return true;
}
function changeCaptchaLogin() {
	var src = "./jcaptchalogin.jpg?date="
		+ new Date();
	$("#jcaptchaimg").attr("src", src);
}