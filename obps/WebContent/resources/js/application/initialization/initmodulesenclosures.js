
app.controller("initmodulesenclosuresCtrl", [
  "$scope",
  "$sce","$compile",
  function ($scope, $sce,$compile) {

    $scope.module = new Modules();
    $scope.modules = [];
    $scope.enclosures = [];
    $scope.trustHTML = function (post) {
      return $sce.trustAsHtml(post);
    };
    $scope.reset = function () {
      $scope.module = new Modules();
    };

    $scope.save = function () {
      var mapuserpagespages = [];
      jQuery.each($scope.enclosures, function (i, v) {
        if (v.checked) {
        
          mapuserpagespages.push({
            
            modulecode: $scope.module.modulecode,
            enclosurecode: v,
          });
        }
      });
      if (mapuserpagespages.length === 0) {
        alert("Select atleast one Enclosure");
        return;
      }
      jQuery.ajax({
        type: "POST",
        url: "./saveModuleEnclosures.htm",
        data: angular.toJson(mapuserpagespages),
        // dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (response) {
          alert(response);
          $scope.reset();
          $scope.listModules();
        },
        error: function (xhr) {
          alert(xhr.status + " = " + xhr);
          alert(
            "Sorry, there was an error while trying to process the request."
          );
        },
      });
    };
  
    $scope.mappedEnclosures = function (modulecode) {
  
    	jQuery.each($scope.enclosures, function (i, v) {
    		
    		v.checked = false;
    	});
    	
    	$scope.module =$scope.modules.filter(obj=>{
    		return obj.modulecode==modulecode;
    	})[0];
    	
    	$scope.checks();
    	jQuery("html, body").animate({
	    	scrollTop: 0,
	    },1000);
    };
    $scope.listModules = function () {
   
      jQuery.ajax({
        type: "GET",
        url: "./listModulesAndEnclosures.htm",
        // dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (response) {
         
          var scope = angular.element($("#initmodulesenclosuresCtrl")).scope();
         
          scope.$apply(function () {
       
            scope.modules = response;
            
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
    $scope.listEnclosures = function () {
  
      jQuery.ajax({
        type: "GET",
        url: "./listEnclosures.htm",
        // dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (response) {
          var scope = angular.element($("#initmodulesenclosuresCtrl")).scope();
          scope.$apply(function () {
            scope.enclosures = response;
           
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
      
      jQuery.each($scope.enclosures, function (i0, v0) {
  
       response = false;
       jQuery.each($scope.module.mappedenclosures, function (i, v) {
       
          if (v0.enclosurecode === v.enclosurecode) {
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
                    "data": "modulecode"
                }, 
                {
                    "title": "Module Name",
                    "data": "modulename"
                }, 
                {
                    "title": "Action",
                    "sortable": false,
                    "render": function (data, type, row, meta) {
                    	let div = '<button style="padding:5px" ng-click="mappedEnclosures('+row.modulecode+')">Map Enclosures</button>';
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
    

   $scope.listModules();
    $scope.listEnclosures();
  },
]);
