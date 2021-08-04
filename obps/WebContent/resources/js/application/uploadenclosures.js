jQuery(document).ready(function () {  
        
    jQuery("#userresponsecaptcha").val("");            
    
    jQuery('.chkbox').each(function(){
        var id="file_"+jQuery(this).attr("id").split("_")[1];
        if(jQuery(this).prop("checked")==true){
            jQuery("#"+id).prop("disabled",false);
        }else{
            jQuery("#"+id).val("");
            jQuery("#"+id).prop("disabled",true);            
        }        
    });     
    
    jQuery('.chkbox').click(function(){
        var id="file_"+jQuery(this).attr("id").split("_")[1];
        if(jQuery(this).prop("checked")==true){
            jQuery("#"+id).prop("disabled",false);
        }else{
            jQuery("#"+id).val("");
            jQuery("#"+id).prop("disabled",true);            
        }        
    });       
    jQuery('.file').change(function(){
        var fileContent = jQuery(this).val();
        var checkimg = fileContent.toLowerCase();
        if (!checkimg.match(PATTERN_FILE_ENC))
        {
            showMsg(jQuery(this).attr('id'), "Enclosoures should be of jpg/jpeg/png/pdf type");
        }else{
            showMsg(jQuery(this).attr('id'), "");
        }        
    });
    
    /*
    jQuery(".error").click(function(){
        jQuery(this).text("");
    });
    */    
}); 

function onbeforeSubmit()
{    
    var count=0;
    var status=0; 

	if(jQuery('.chkbox').length>0)
	//if(jQuery('.chk_Y').length>0)    
    {
        jQuery('.chkbox').each(function(){
        //jQuery('.chk_Y').each(function(){
            var id="file_"+jQuery(this).attr("id").split("_")[1];
            if(jQuery(this).prop("checked")==true)
            {
                count++;
                var fileContent = jQuery("#"+id).val();
                if (fileContent === '')
                {
                    showMsg(id, "Please browse your enclosoures");
                    status++;
                }
                else if (fileContent !== '')
                {
                    var checkimg = fileContent.toLowerCase();
                    if (!checkimg.match(PATTERN_FILE_ENC))
                    {
                        showMsg(id, "Enclosure should be of jpg/jpeg/png/pdf type");
                        status++;
                    }else{
                        showMsg(id, "");
                    }
                }
            }else{
                showMsg(id, "");
            }
        });   
        /*   
        if(count!=jQuery('.chk_Y').length){
            showMsg("UploadEnc", "Please select all mandatory enclosures"); 
            status=1;
        }else{
            showMsg("UploadEnc", "");      
        }  
        */
        
        if(count==0){
            showMsg("UploadEnc", "Please select enclosures"); 
            status=1;
        }else{
            showMsg("UploadEnc", "");      
        }        
          
        if(status>0){
            return false;        
        }        
    }   
  
	if(jQuery.trim(jQuery("#userresponsecaptcha").val())===""){
    	showMsg("UploadEnc", "Please enter captcha"); 
    	 return false;   
    }    

   
    return true;        
}