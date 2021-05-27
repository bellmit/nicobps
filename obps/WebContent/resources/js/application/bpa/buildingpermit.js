/**
 * @author Decent Khongstia
 */

app.controller("CommonCtrl", [
  "$scope",
  "$http",
  "$timeout",
  "$compile",
  "$window",
  "commonInitService",
  "bpaService",
  function ($scope, $http, $timeout, $compile, $window, CIS, BS) {
    console.log("BuildingPermitApplication");

    $scope.ScrutinzedApp = [];
    /*GET_DATA*/

    $scope.listAppScrutinyDetailsForBPA = () => {
      BS.listAppScrutinyDetailsForBPA((response) => {
        $scope.ScrutinzedApp = response;
        console.log("ScrutinzedApp: ", $scope.ScrutinzedApp);
        $scope.setAppEdcrDetailsTable($scope.ScrutinzedApp);
      }, (data = ""));
    };
    $scope.listAppScrutinyDetailsForBPA();

    $scope.setAppEdcrDetailsTable = (tData) => {
      $("#displayRecords").html("");
      $("#displayRecords").html("<table id='displayRecordsTable' style='width: 100%; margin: auto;' border='1' class='table table table-bordered  table-hover'></table>");
      var table = jQuery('#displayRecordsTable').DataTable({
        "data": tData,
        "columns": [
          {
            "title": "Slno",
            "data": "edrnumber",
            "width": "2%",
            render: function (data, type, row, meta) {
              return meta.row + 1;
            }
          }, {
            "title": "EDCR Number",
            "data": "edcrnumber"
          }, {
            "title": "Next Process",
            "data": "processname",
            render: (data, type, row, meta) => {
              if(data == null || data == '') return "Apply for BPA";
              else return data;
            }
          /* }, {
            "title": "Action",
            "data": "edcrnumber",
            render: function (data, type, row, meta) {
              var buttons = "";
              // buttons += '<button style="margin: 0.1em 0" class="btn btn-danger btn-md" type="button" data-toggle="modal" ng-click="deleteDetails(\'' + data + '\')">Delete</button>';
              return buttons;
            } */
          }
        ],
        "stateSave": true,
        "createdRow": function (row, data, dataIndex) {
          $compile(angular.element(row).contents())($scope);
        }
      });

      // $('#displayRecordsTable tbody').on('click', 'tr', function () {
      table.on('click', 'tr', function () {
            var data = table.row(this).data();
            console.log(data.edcrnumber);
            if(data.pageurl == null || data.pageurl == '')
              $window.location.href = "applybuildingpermit.htm?edcrnumber="+data.edcrnumber;
            else
              $window.location.href = data.pageurl+"?edcrnumber="+data.edcrnumber;
      });
    }
  },
]);
