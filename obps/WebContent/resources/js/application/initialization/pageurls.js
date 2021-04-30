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
    $scope.header = header;
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
    	jQuery.getJSON('resources/vendor/googlenexusMenu/fonts/icomoon/icon.json',(data)=>{
    		$timeout(() => {
    			$scope.icons=data;
          	  },0);
	      });
    };
    $scope.getIcons();
    $scope.getSubmenu = function () {
      jQuery.ajax({
        type: "GET",
        url: "./getSubmenu",
        data: "val=" + $scope.url.parent,
        // dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (response) {
          $timeout(() => {
        	  $scope.submenu = response
        	  },0);
        },
        error: function (xhr) {
          alert(xhr.status + " = " + xhr);
          alert(
            "Sorry, there was an error while trying to process the request."
          );
        },
      });
    };
    $scope.save = function () {
      jQuery.ajax({
        type: "POST",
        url: "./saveMenu.htm",
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
        url: "./listUrls",
        contentType: "application/json; charset=utf-8",
        success: function (response) {
          var scope = angular.element($("#pageurlCtrl")).scope();
          scope.$apply(function () {
            scope.URLs = response;
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
