var SAVE = "SAVE";
var EDIT = "EDIT";
var CREATED = "CREATED";
var ALREADY_EXISTS = "ALREADY_EXISTS";
var ALREADY_REPORTED = "ALREADY_REPORTED";

app.service("commonInitService", function($http) {
	this.success = (elementID, msg, ...arg) => {
		console.info("commonInitService.success");
		console.info(elementID, msg, ...arg);
	};

	this.danger = (elementID, msg, ...arg) => {
		console.info("commonInitService.danger");
		console.info(elementID, msg, ...arg);
	};
	
	this.save = (method, endpoint, data, successCallback, errorCallback)=>{
		$.ajax({
            type: method,
            url: endpoint,
            dataType: "json",
            async: false,
            contentType: "application/json; charset=utf-8",
            data: angular.toJson(data, true),
            success: function (res) {
           console.log(res.code);
            	if(res.code === 201 || res.code === 200  || res.code === 203 || res === true){
            		successCallback(res);
            	}else{
            		errorCallback(res);
            	}
            },
            error: function (xhr) {
                console.log("Error: ", xhr)
                let response = xhr.responseJSON;
                
                if(response == null || response.code == null || (response.code >= 500 && response.code <=599))
                	alert("Sorry, there was an error while trying to process the request.");
                
                errorCallback(response);
            }            
        });
	}
	this.httpSendJSON = (method, endpoint, data, successCallback, errorCallback)=>{
		$.ajax({
			type: method,
			url: endpoint,
			dataType: "json",
			async: false,
			contentType: "application/json; charset=utf-8",
			data: angular.toJson(data),
			success: function (res) {
				successCallback(res);
			},
			error: function (xhr) {
				MsgBox("Unable to process request.");
				errorCallback(xhr);
			}            
		});
	}  
	this.save1 = (method, endpoint, data, successCallback, errorCallback)=>{

	
		$.ajax({
            type: method,
            url: endpoint,
            dataType: "json",
            async: false,
            contentType: "application/json; charset=utf-8",
            data: angular.toJson(data),
            success: function (res) {
           
            		successCallback(res);
            
            },
            error: function (res) {
              
                alert("Sorry, there was an error while trying to process the request.");
                errorCallback(res);
            }            
        });
	}
	this.http = (method, endpoint, data, successCallback, errorCallback)=>{
		$.ajax({
			type: method,
			url: endpoint,
//			dataType: "json",
			async: false,
//			contentType: "application/json; charset=utf-8",
			data: data,
			success: function (res,status,xhr) {
				successCallback(res,xhr);
			},
			error: function (xhr) {
				MsgBox("Unable to process request.");
				errorCallback(xhr);
			}            
		});
	}
	
});