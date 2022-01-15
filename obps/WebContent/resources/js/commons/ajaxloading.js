jQuery(document).ajaxSend(function() {
	jQuery("#ajaxLoading").fadeIn();
});
jQuery(document).ajaxComplete(function(event,xhr,options) {
	jQuery("#ajaxLoading").fadeOut();
});