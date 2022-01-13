jQuery(document).ajaxSend(function() {
	jQuery("#ajaxLoading").fadeIn();
});
jQuery(document).ajaxComplete(function() {
	jQuery("#ajaxLoading").fadeOut();
});
$(document).ready(function() {
	$("#nav_but").on('click', function() {
		myFunction(this);
	})

})
var focused;
var MsgCallBack;
function MsgBox(text, modal, callback) {
	if (callback !== undefined) {
		MsgCallBack = callback;
	}
	modal = modal == null ? true : modal;
	if (modal) {
		jQuery("#MsgBox").find("span").css("width", "800px");
	} else {
		jQuery("#MsgBox").find("span").css("width", "auto");
	}
	jQuery('#msgboxbuttons').html("<button id='msgboxbutton'" +
		"style='margin: 30px auto 5px auto; width: 70px; border-radius: 5px; border: #34495e solid 2px; text-align: center;'"
		+ "onclick='jQuery.fancybox.close();msgboxbuttonpressed(MsgCallBack);'>OK</button>")
	jQuery.fancybox.close();
	jQuery("#MsgBox").find("span").html(text);
	jQuery.fancybox({
		href: '#MsgBox',
		'autoSize': modal,
		'transitionIn': 'elastic',
		'transitionOut': 'elastic',
		'speedIn': 600,
		'speedOut': 200,
		'overlayShow': false,
		'modal': modal,
	});
	focused = jQuery(':focus');
	jQuery("#msgboxbutton").focus();
	if (modal) {
		jQuery("#MsgBox").find("button").css('display', '');
	} else {
		jQuery("#MsgBox").find("button").css('display', 'none');
	}
}

function ConfirmBox(text, callback) {
	if (callback !== undefined) {
		MsgCallBack = callback;
	}
	jQuery.fancybox.close();
	jQuery("#MsgBox").find("span").html(text);
	jQuery('#msgboxbuttons').html("<button " +
		"style='margin: 30px auto 5px auto; width: 70px; border-radius: 5px; border: #34495e solid 2px; text-align: center;'"
		+ "onclick='jQuery.fancybox.close();msgboxbuttonpressed(MsgCallBack,true);'>Yes</button>" +
		"<button " +
		"style='margin: 30px auto 5px auto; width: 70px; border-radius: 5px; border: #34495e solid 2px; text-align: center;'"
		+ "onclick='jQuery.fancybox.close();msgboxbuttonpressed(MsgCallBack,false);'>No</button>")
	jQuery.fancybox({
		href: '#MsgBox',
		'autoSize': true,
		'transitionIn': 'elastic',
		'transitionOut': 'elastic',
		'speedIn': 600,
		'speedOut': 200,
		'overlayShow': false,
		'modal': true,
	});
	focused = jQuery(':focus');
}
function msgboxbuttonpressed(callback, response) {
	jQuery('#MsgBox').find('span').html('');
	if (callback && {}.toString.call(callback) === '[object Function]') {
		callback(response);
	}
};

function myFunction(x) {
	x.classList.toggle("change");
}

var pathname = window.location.pathname;
jQuery(".lk").removeClass("active");
jQuery(".lk").each(function() {
	var hrf = jQuery(this).attr("href");
	if (pathname.indexOf(hrf) !== -1) {
		jQuery(this).addClass("active");
	}
});
