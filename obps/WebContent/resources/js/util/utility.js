//----------Ajax CSRF Starts-----------//
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
jQuery(document).ajaxSend(function(event, xhr, options) {
  xhr.setRequestHeader(header, token);
  xhr.setRequestHeader('X-Request', 'XMLHttpRequest');
});
//----------Ajax CSRF Ends-----------//

jQuery(document).ready(function() {            
    jQuery(".formErrorContent").click(function() {		
  		jQuery(this).css('display','none');
	});        
});

function showMsg(id,Msg)
{
  var idMsg=id+"Msg";
  if(Msg!=='')
  {
   jQuery('#'+idMsg+'').html("* "+Msg).show();
   jQuery('#'+id+'').focus();
  }else{
   jQuery('#'+idMsg+'').html("").hide();
  }
}
//----------Password Strength Starts-----------//
function PasswordStrength(userid,password)
{       
    var bpos = "";
    var perc = 0 ;
    jQuery('#'+password).keyup(function(){
            jQuery('#results').html(passwordStrength(jQuery('#'+password).val(),jQuery('#'+userid).val())) ;
            perc = passwordStrengthPercent(jQuery('#'+password).val(),jQuery('#'+userid).val());

            bpos=" jQuery('#colorbar').css( {backgroundPosition: \"0px -" ;
            bpos = bpos + perc + "px";
            bpos = bpos + "\" } );";
            bpos=bpos +" jQuery('#colorbar').css( {width: \"" ;
            bpos = bpos + (perc * 2) + "px";
            bpos = bpos + "\" } );";
            eval(bpos);
            jQuery('#percent').html(" " + perc  + "% ");
    })
    
    jQuery('#'+userid).keyup(function(){
            jQuery('#results').html(passwordStrength(jQuery('#'+password).val(),jQuery('#'+userid).val())) ;
            perc = passwordStrengthPercent(jQuery('#'+password).val(),jQuery('#'+userid).val());

            bpos=" jQuery('#colorbar').css( {backgroundPosition: \"0px -" ;
            bpos = bpos + perc + "px";
            bpos = bpos + "\" } );";
            bpos=bpos +" jQuery('#colorbar').css( {width: \"" ;
            bpos = bpos + (perc * 2) + "px";
            bpos = bpos + "\" } );";
            eval(bpos);
            jQuery('#percent').html(" " + perc  + "% ");
    })    
}    
//----------Password Strength Ends-----------//    
function changeCaptcha()
{    
    var src = "./jcaptcha.jpg?date="+new Date();
    jQuery("#jcaptchaimg").attr("src",src); 
}







