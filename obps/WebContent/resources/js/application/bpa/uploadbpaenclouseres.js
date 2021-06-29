jQuery(document).ready(function () {
  
});

var app = angular.module("applicationApp", ["checklist-model"]);
app.controller('applicationController', function ($scope) {

    $scope.listBpaEnclosures = [];
    $scope.applicationEnclosures = {};
    loadForm();

    $scope.submitDetails = function () {
        var applicationcode = jQuery("#appcode").val();
        $scope.applicationEnclosures.applicationcode = JSON.stringify(applicationcode);
        $scope.applicationEnclosures.listBpaEnclosures = JSON.stringify($scope.listBpaEnclosures);
        if (validateDetails($scope.applicationEnclosures)) {
            submitBpaEnclosureDetails($scope.applicationEnclosures);
        }
    };

});
function validateDetails(applicationEnclosures) {
    return true;
}

function addFile(id) {
    var index = id.split("_")[1];
    var scope = angular.element(jQuery("#applicationForm")).scope();
    scope.$apply(function () {
        var file = document.querySelector("#" + id).files[0];
        if (file) {
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.addEventListener("load", function () {
                scope.listBpaEnclosures[index].filecontant = reader.result;
            }, false);
        }
    });
}

function loadForm() {

    jQuery.ajax({
        url: "initUploadBpaEnclosuresForm.htm",
        dataType: 'json',
        type: "GET",
        success: function (data) {
            var scope = angular.element(jQuery("#applicationForm")).scope();
            scope.$apply(function () {
                scope.listBpaEnclosures = [];
                data.listBpaEnclosures.forEach((row) => {
                    var item = {};
                    item.ischecked = false;
                    item.enclosurecode = row.key;
                    item.enclosurename = row.value;
                    item.filecontant = "";
                    scope.listBpaEnclosures.push(item);
                })
            });
        },
        error: function (request, status, error) {
            //alert(request.responseText);
        }
    });
}

function submitBpaEnclosureDetails(applicationEnclosures) {

    var flag = confirm(" Click OK to register. Click Cancel if you want to review your entries");
    if (!flag) { return false; }
    jQuery.ajax({
        url: "submitBpaEnclosureDetails.htm",
        data: applicationEnclosures,
        type: "POST",
        success: function (data) {
            var scope = angular.element(jQuery("#applicationForm")).scope();
            scope.$apply(function () {
                scope.applicationEnclosures = {};
            });

            jQuery('#successMsg').html("* " + data).show();
            window.location="bpasiteinspection.htm"; 		                          
        },
        error: function (request, status, error) {
            alert("inn error");
            alert(status + " : " + JSON.stringify(request));
            jQuery('#successMsg').html("* " + request.responseText).show();
        }
    });
}




