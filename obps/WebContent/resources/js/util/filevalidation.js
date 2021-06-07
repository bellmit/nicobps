//@author Decent Khongstia
var getExtension = function (filename) {
  var parts = filename.split('.');
  return parts[parts.length - 1];
};

var isApplication = function (filename) {
  var ext = getExtension(filename);
  switch (ext.toLowerCase()) {
    case 'pdf':
//    case 'gif':
//    case 'bmp':
//    case 'png':
      //etc
      return true;
  }
  return false;
};

var isImage = function (filename) {
  var ext = getExtension(filename);
  switch (ext.toLowerCase()) {
    case 'jpg':
    case 'jpeg':
//    case 'gif':
//    case 'bmp':
    case 'png':
      //etc
      return true;
  }
  return false;
};

var isVideo = function (filename) {
  var ext = getExtension(filename);
  switch (ext.toLowerCase()) {
    case 'm4v':
    case 'avi':
    case 'mpg':
    case 'mp4':
      // etc
      return true;
  }
  return false;
};


var validateFile = function(){
    var flag = 0;
    $('input[type="file"]').each(function () {
        var fp = $(this);
        var items = fp[0].files;
        var lg = fp[0].files.length; // get length
        var required = fp.siblings("#mandatorycode").val();
        console.log("required: ", required);
        if (lg > 0 || fp.val()!=="") {     
            var fileName = items[0].name; // get file name
            var fileSize = items[0].size; // get file size 
            var fileType = fp.val().toLowerCase(); // get file type
            var allowedFiletype = "";
            try{
                allowedFiletype = new RegExp(fp.siblings("#filetype").val().trim());
            }catch (exception) {
                allowedFiletype = "";
            }
            if (fileSize > 1048576){   
                flag = 1;
                fp.siblings("#enclosureMsg").html("File size should not exceed 1 MB").show();
            }else{
                fp.siblings("#enclosureMsg").html("File size should not exceed 1 MB").hide();
                if (fileType.match(allowedFiletype) || allowedFiletype === "") {   
                    fp.siblings("#enclosureMsg").html("Invalid file type").hide();
                }
                else {
                    flag=1;
                    fp.siblings("#enclosureMsg").html("Invalid file type").show();
                }
            }
        }
        else{
            if(required==="N"){
                return;
            }else{
                fp.siblings("#enclosureMsg").html("Choose a  File").show();
                flag = 1;
            }
        }
        
    });
    if (flag === 1) {
        return false;
    }
    else{
        return true;
    }
};


var handleFileSelect = function (evt, obj, render) {
    console.log("handleFileSelect")
    var files = evt.target.files; // FileList object
    
    var isAccept = false;
    // Loop through the FileList and render image files as thumbnails.
    for (var i = 0, f; f = files[i]; i++) {
        var flag = false;//image
        var id = $(obj).attr("id");
        ID = $(obj).attr("id");
        // Only process image files.
        if (!f.type.match('image.*')) {
            flag = true;//pdf
            if(!f.type.match('pdf.*')){
                alert("File type not allowed");
                $(obj).val("");
                $("#view"+id).empty();
                return isAccept;
            }
        }
        
        $("#view"+id).empty();
		
		if(!render) return isAccept;


        var reader = new FileReader();

        // Closure to capture the file information.
        reader.onload = (function (theFile) {
            return function (e) {
                // Render thumbnail.
                var span = document.createElement('span');
                if (flag) {
                    span.innerHTML = ['<img class="thumb" src="images/Pdf-icon.png" alt="', escape(theFile.name), '"/><div class="caption"><p>',theFile.name,'</p></div>'].join('');
                }else{
                    span.innerHTML = ['<img class="thumb" src="', e.target.result,'" alt="', escape(theFile.name), '"/>'].join('');
                }
                $("#view"+id).empty().append(span);
            };
        })(f);

        // Read in the image file as a data URL.
        reader.readAsDataURL(f);

        $("#progress"+id).css("width","0%");
        $("#progress"+id).html("0%");

        reader = new FileReader();
        reader.onerror = function (evt) {
            switch (evt.target.error.code) {
                case evt.target.error.NOT_FOUND_ERR:
                    alert('File Not Found!');
                    break;
                case evt.target.error.NOT_READABLE_ERR:
                    alert('File is not readable');
                    break;
                case evt.target.error.ABORT_ERR:
                    break; // noop
                default:
                    alert('An error occurred reading this file.');
            }
        };
        reader.onprogress = function (evt) {
            // evt is an ProgressEvent.
            if (evt.lengthComputable) {
                var percentLoaded = Math.round((evt.loaded / evt.total) * 100);
                // Increase the progress bar length.
                if (percentLoaded < 100) {
                    $("#progress" + ID).css("width", percentLoaded + "%");
                    $("#progress" + ID).html(percentLoaded + "%");
                }
            }
        };

        reader.onabort = function(e) {
          alert('File read cancelled');
        };
        reader.onloadstart = function(e) {
            $("#progress_bar"+id).addClass("loading");
        };
        reader.onload = function(e) {
          // Ensure that the progress bar displays 100% at the end.
            $("#progress"+id).css("width","100%");
            $("#progress"+id).html("100%");
            setTimeout(function(){
                $("#progress_bar"+id).removeClass("loading");
                $("#progress_bar"+id).removeClass("percent");
            },2000);
        };

        reader.readAsBinaryString(evt.target.files[0]);
        isAccept = true;
    }
    return isAccept;
};