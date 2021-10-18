app.controller("initofficequestionairesCtrl", [
  "$scope",
  "$sce","$compile",
  function ($scope, $sce,$compile) {
	
	 $scope.office = new Offices();
    $scope.offices = [];
    $scope.questionaires = [];
    $scope.trustHTML = function (post) {
      return $sce.trustAsHtml(post);
    };
    $scope.reset = function () {
      $scope.office = new Offices();
    
    
	 jQuery.each($scope.questionaires, function (i, v) {
    		
    		v.checked = false;
    	});
     
    };

 $scope.listOffices = function () {
   
      jQuery.ajax({
        type: "GET",
        url: "./listOfficesAndQuestionaires.htm",
        // dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (response) {
         
          var scope = angular.element($("#initofficesquestionairesCtrl")).scope();
         
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
    $scope.listQuestionaires = function () {
  	
      jQuery.ajax({
        type: "GET",
        url: "./listQuestionaires.htm",
        // dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (response) {
          var scope = angular.element($("#initofficesquestionairesCtrl")).scope();
          scope.$apply(function () {
          
            scope.questionaires = response;
           
           
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
      
console.log($scope.office.mappedquestioniares);
      jQuery.each($scope.questionaires, function (i0, v0) {
  		
       response = false;
       jQuery.each($scope.office.mappedquestionaires, function (i, v) {
       	
          if (v0.questioncode === v.questioncode) {
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
                    	let div = '<button style="padding:5px" ng-click="mappedQuestions('+row.officecode+')" class="btn btn-primary b-btn">Map Questionnaires</button>';
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
$scope.save = function () {
      var mapofficequestions = [];
      jQuery.each($scope.questionaires, function (i, v) {
        if (v.checked) {
        
          mapofficequestions.push({
            
            officecode: $scope.office.officecode,
            questioncode: v,
          });
        }
      });
      if (mapofficequestions.length === 0) {
        alert("Select atleast one Questions");
        return;
      }
      jQuery.ajax({
        type: "POST",
        url: "./saveOfficeQuestioniares.htm",
        data: angular.toJson(mapofficequestions),
        // dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (response) {
          if(response=="questionsnull")
          	alert("Please Select atleast 1 question ");
          else if(response=="officecodenull")
          	alert("Please Select atleast 1 Office");
          else if(response=='Mapped')
          	alert("Mapped");
          else if(response=='Failed')
          	alert("Failed");
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
  
    $scope.mappedQuestions = function (officecode) {
  console.log("here");
    	jQuery.each($scope.questionaires, function (i, v) {
    		
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

  $scope.listOffices();
    $scope.listQuestionaires();

	
	}]);