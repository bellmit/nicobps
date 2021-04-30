var SAVE = "SAVE";
var EDIT = "EDIT";
var CREATED = "CREATED";
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
            data: angular.toJson(data),
            success: function (res) {
            	if(res.response === CREATED||res === true){
            		successCallback(res);
            	}else{
            		errorCallback(res);
            	}
            },
            error: function (xhr) {
                console.log("saveERROR")
                alert("Sorry, there was an error while trying to process the request.");
                errorCallback();
            }            
        });
	}
	this.http = (method, endpoint, data, successCallback, errorCallback)=>{
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
	
});