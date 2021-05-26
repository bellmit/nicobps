
app.controller("initofficespaymentmodesCtrl", [
  "$scope",
  "$sce","$compile",
  function ($scope, $sce,$compile) {

    $scope.office = new Offices();
    $scope.offices = [];
    $scope.paymentmodes = [];
    $scope.trustHTML = function (post) {
      return $sce.trustAsHtml(post);
    };
    $scope.reset = function () {
      $scope.office = new Offices();
     
    };

    $scope.save = function () {
      var mapuserpagespages = [];
      jQuery.each($scope.paymentmodes, function (i, v) {
        if (v.checked) {
        
          mapuserpagespages.push({
            
            officecode: $scope.office.officecode,
            paymentmodecode: v,
          });
        }
      });
      if (mapuserpagespages.length === 0) {
        alert("Select atleast one Payment");
        return;
      }
      jQuery.ajax({
        type: "POST",
        url: "./saveOfficePayment.htm",
        data: angular.toJson(mapuserpagespages),
        // dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (response) {
          alert(response);
          $scope.reset();
          $scope.listOffices();
          
        },
        error: function (xhr) {
          alert(xhr.status + " = " + xhr);
          alert(
            "Sorry, there was an error while trying to process the request."
          );
        },
      });
    };
  
    $scope.mappedPaymentModes = function (officecode) {
  	
    	jQuery.each($scope.paymentmodes, function (i, v) {
    		
    		v.checked = false;
    	});
    	
    	$scope.office =$scope.offices.filter(obj=>{
    		return obj.officecode==officecode;
    	})[0];
    	
    	$scope.checks();
    	jQuery("html, body").animate({
	    	scrollTop: 0,
	    },1000);
    };
    $scope.listOffices = function () {
   
      jQuery.ajax({
        type: "GET",
        url: "./listOfficesAndPaymentModes.htm",
        // dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (response) {
         
          var scope = angular.element($("#initofficespaymentmodesCtrl")).scope();
         
          scope.$apply(function () {
       
            scope.offices = response;
            
            scope.setDataTable(response);
          });
        },
        error: function (xhr) {
          alert(xhr.status + " = " + xhr);
          alert(
            "Sorry, there was an error while trying to process the request."
          );
        },
      });
    };
    $scope.listPaymentModes = function () {
  	
      jQuery.ajax({
        type: "GET",
        url: "./listPaymentModes.htm",
        // dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (response) {
          var scope = angular.element($("#initofficespaymentmodesCtrl")).scope();
          scope.$apply(function () {
          
            scope.paymentmodes = response;
           
           
          });
        $scope.checks()
        },
        error: function (xhr) {
          alert(xhr.status + " = " + xhr);
          alert(
            "Sorry, there was an error while trying to process the request."
          );
        },
      });
    };
    $scope.checks = function () {

      var response;
      
      jQuery.each($scope.paymentmodes, function (i0, v0) {
  		
       response = false;
       jQuery.each($scope.office.mappedpaymentmodes, function (i, v) {
       	
          if (v0.paymentmodecode === v.paymentmodecode) {
            response = true;
            return false;
          }
        });
        v0.checked = response;
      });
    };
    $scope.setDataTable = function (obj) {
        jQuery("#displayRecords").html("");
        jQuery("#displayRecords").html("<table id='displayRecordsTable' style='width:100%' border='1'></table>");
        jQuery('#displayRecordsTable').DataTable({
            data: obj,
            columns: [
            {
                    "title": "Sl No",
                    "data": "officecode"
                }, 
                {
                    "title": "Office Name",
                    "data": "officename1"
                }, 
                {
                    "title": "Action",
                    "sortable": false,
                    "render": function (data, type, row, meta) {
                    	let div = '<button style="padding:5px" ng-click="mappedPaymentModes('+row.officecode+')">Map Payments</button>';
                        return div;
                    }
                }
            ],
            "sortable": false,
            "columnDefs": [
                {"width": "2%", "targets": 0}
            ],
            "bLengthChange": false,
            createdRow: function (row, data, dataIndex) {
                $compile(angular.element(row).contents())($scope);
            }
        });
    };
//    

  $scope.listOffices();
    $scope.listPaymentModes();
  },
]);
