var icons;
$(document).ready(function () {
  var scope = angular.element($("#pageurlCtrl")).scope();
  scope.$apply(function () {
    scope.listURLs();
  });
});

app.controller("pageurlCtrl", [
  "$scope",
  "$sce","$timeout",
  function ($scope, $sce,$timeout) {
	$scope.parenticonPressed=false;
    $scope.icons = [];
    $scope.header = [];
    $scope.submenu = [];
    $scope.subsubmenu = [];
    $scope.URLs = [];
    $scope.url = new Pageurls();
    $scope.trustHTML = (post) => {
      return $sce.trustAsHtml(post);
    };
    $scope.reset = function () {
      $scope.url = new Pageurls();
    };
    $scope.getIcons=()=>{
    	jQuery.getJSON('resources/js/vendor/bootstrap/vendor/fontawesome-free/icon.json',(data)=>{
    		$timeout(() => {
    			$scope.icons=data;
          	  },0);
	      });
    };
    $scope.getIcons();
    $scope.getSubmenu = function () {
    	$timeout(() => {
    	  let contains={};
  		  $scope.submenu=[];    		  
  		  let header=JSON.parse(JSON.stringify($scope.header)); 
  		  let head=header.filter((x)=>{
  			  return x.parent=$scope.url.parent;
  		  })[0];
  		  $scope.url.parenticon=head.parenticon;
  		  $scope.url.parentorder=head.parentorder;
  		  $scope.URLs.forEach((x, o) => {
  			  if(x.parent==$scope.url.parent){
	  			  if(contains[x.submenu]!==true){
	  				  contains[x.submenu]=true;
	  				  if(x.submenu!==''){
	  					  $scope.submenu.push(x);
	  				  }
	  			  }
	  		  }
  		  });
  	  	},0);
    };
    
    $scope.getSubsubmenu = function () {
    	$timeout(() => {
  		  let header=JSON.parse(JSON.stringify($scope.submenu)); 
  		  let head=header.filter((x)=>{
  			  return x.submenu=$scope.url.submenu;
  		  })[0];
  		  $scope.url.submenuicon=head.submenuicon;
  		  $scope.url.submenuorder=head.submenuorder;
  	  	},0);
    };
    
    $scope.save = function () {
      jQuery.ajax({
        type: "POST",
        url: "./saveurl.htm",
        data: angular.toJson($scope.url),
        contentType: "application/json; charset=utf-8",
        success: function (response) {
          alert(response);
          $scope.reset();
          $scope.listURLs();
        },
        error: function (xhr) {
          alert(xhr.status + " = " + xhr);
          alert(
            "Sorry, there was an error while trying to process the request."
          );
        },
      });
    };
    
    $scope.edit = function (index) {
      $scope.url = $scope.URLs[index];
      jQuery("html, body").animate(
        {
          scrollTop: 0,
        },
        2000
      );
    };
    $scope.listURLs = function () {
      jQuery.ajax({
        type: "GET",
        url: "./listUrls.htm",
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            $scope.URLs = response;
        },
        error: function (xhr) {
          alert(xhr.status + " = " + xhr);
          alert(
            "Sorry, there was an error while trying to process the request."
          );
        },
      }).then(()=>{
    	  $timeout(() => {
    		  let contains={};
    		  $scope.header=[];    		  
    		  $scope.URLs.forEach((x, o) => {
    			  if(contains[x.parent]!==true){
    				  contains[x.parent]=true;
    				  $scope.header.push(x);
    			  }
    		  });
    	  },0);
      });
    };
  },
]);
angular.element(document).ready(function () {
  dTable = $("#displayRecordsTable");
  dTable.DataTable({
    searching: false,
    bPaginate: false,
    bLengthChange: false,
    bFilter: true,
    bInfo: false,
    bAutoWidth: false,
    oLanguage: {
      sZeroRecords: "",
      sEmptyTable: "",
    },
  });
});
