/**
 * @author Decent Khongstia
 */

app.controller("CommonCtrl", [
  "$scope",
  "$http",
  "$timeout",
  "$window",
  "commonInitService",
  "bpaService",
  function ($scope, $http, $timeout, $window, CIS, BS) {
    console.log("Apply");

    let data = "";
    $scope.serverResponseError = false;
    $scope.serverResponseFail = false;
    $scope.serverResponseInfo = false;
    $scope.serverResponseSuccess = false;

    $scope.googleMapApiKey = 'AIzaSyC4u5cL3l3jl7dpRnoIcl8drvgpK5uB_bc';
    $scope.BPA = new BPA();
    $scope.EDCR = new EdcrDetail();
    $scope.gmapAddress = '';
    $scope.occupancy = '';
    $scope.planInfo = '';
    $scope.planDetail = '';
    $scope.planVirtualBuilding = '';
    $scope.serverMsg = '';

    $scope.Officelocations = [];
    $scope.OwnerSubtypes = Ownershipsubtypes;
    $scope.Ownertypes = [];
    $scope.Relationshiptypes = [];
    $scope.Salutations = [];

    /*GET*/
    BS.getEdcrDetails((response) => {
      let edcr = new EdcrDetail();
      $scope.EDCR = edcr.init(response);
      $scope.planInfo = edcr.extractPlanInfo($scope.EDCR);
      $scope.gmapAddress = ($scope.planInfo.district).toLocaleLowerCase();
      $scope.occupancy = edcr.extractPlanOccupancy($scope.EDCR);
      $scope.BPA = $scope.BPA.extractFromEdcrObject($scope.EDCR);
    }, EDCRNUMBER);

    $scope.listOfficelocations = () => {
      BS.listOfficelocations((response) => {
        $scope.Officelocations = response;
      }, (data = ""));
    };
    $scope.listOfficelocations();

    $scope.listOwnershiptypes = () => {
      BS.listOwnershiptypes((response) => {
        $scope.Ownertypes = response;
      }, (data = ""));
    };
    $scope.listOwnershiptypes();

    $scope.listRelationshiptypes = () => {
      BS.listRelationshiptypes((response) => {
        $scope.Relationshiptypes = response;
      }, (data = ""));
    };
    $scope.listRelationshiptypes();

    $scope.listSalutations = () => {
      BS.listSalutations((response) => {
        $scope.Salutations = response;
      }, (data = ""));
    };
    $scope.listSalutations();

    /*ACTION*/
    $scope.addOwner = () => {
      if($scope.BPA.ownershipsubtype == "Multiple Owner")
        $scope.BPA.ownerdetails.push(new OwnerDetail());
      else
        $scope.BPA.ownerdetails = [$scope.BPA.ownerdetails[0]];
    };

    $scope.removeOwner = () => {
        $scope.BPA.ownerdetails.pop();
    };

    $scope.setGoogleMapLocation = (location) => {
      $scope.BPA.plotgiscoordinates = location.lat + "," + location.lng;
    };

    $scope.toggleGmapModal = () => {
      $('#gmapModal').on('shown.bs.modal', function () {
        $('#gmapModal').trigger('focus')
      })
    };

    $scope.validateForm = () => {
      let flag = true;
      let bpa = $scope.bpaform;
      bpa.plotaddressline1.$touched = true;
      if (
        bpa.plotaddressline1.$modelValue == null ||
        bpa.plotaddressline1.$modelValue == ""
      ) {
        bpa.plotaddressline1.$error.invalid = true;
        flag = false;
      }

      bpa.plottownvillage.$touched = true;
      if (
        bpa.plottownvillage.$modelValue == null ||
        bpa.plottownvillage.$modelValue == ""
      ) {
        bpa.plottownvillage.$error.invalid = true;
        flag = false;
      }

      bpa.plotgiscoordinates.$touched = true;
      if (
        bpa.plotgiscoordinates.$modelValue == null ||
        bpa.plotgiscoordinates.$modelValue == ""
      ) {
        bpa.plotgiscoordinates.$error.invalid = true;
        flag = false;
      }

      bpa.officelocationcode.$touched = true;
      if (
        bpa.officelocationcode.$modelValue == null ||
        bpa.officelocationcode.$modelValue == "" ||
        bpa.officelocationcode.$modelValue == "-1"
      ) {
        bpa.officelocationcode.$error.invalid = true;
        flag = false;
      }

      bpa.plotno.$touched = true;
      if (
        bpa.plotno.$modelValue == null ||
        bpa.plotno.$modelValue == "" ||
        bpa.plotno.$modelValue == "-1"
      ) {
        bpa.plotno.$error.invalid = true;
        flag = false;
      }

      bpa.holdingno.$touched = true;
      if (
        bpa.holdingno.$modelValue == null ||
        bpa.holdingno.$modelValue == "" ||
        bpa.holdingno.$modelValue == "-1"
      ) {
        bpa.holdingno.$error.invalid = true;
        flag = false;
      }

      bpa.landregistrationno.$touched = true;
      if (
        bpa.landregistrationno.$modelValue == null ||
        bpa.landregistrationno.$modelValue == "" ||
        bpa.landregistrationno.$modelValue == "-1"
      ) {
        bpa.landregistrationno.$error.invalid = true;
        flag = false;
      }

      bpa.landregdetails.$touched = true;
      if (
        bpa.landregdetails.$modelValue == null ||
        bpa.landregdetails.$modelValue == "" ||
        bpa.landregdetails.$modelValue == "-1"
      ) {
        bpa.landregdetails.$error.invalid = true;
        flag = false;
      }

      bpa.ownershiptypecode.$touched = true;
      if (
        bpa.ownershiptypecode.$modelValue == null ||
        bpa.ownershiptypecode.$modelValue == "" ||
        bpa.ownershiptypecode.$modelValue == "-1"
      ) {
        bpa.ownershiptypecode.$error.invalid = true;
        flag = false;
      }

      bpa.ownershipsubtype.$touched = true;
      if (
        bpa.ownershipsubtype.$modelValue == null ||
        bpa.ownershipsubtype.$modelValue == "" ||
        bpa.ownershipsubtype.$modelValue == "-1"
      ) {
        bpa.ownershipsubtype.$error.invalid = true;
        flag = false;
      }

      let len = $scope.BPA.ownerdetails.length;
      for (let i = 0; i < len; i++) {
        bpa["salutationcode" + i].$touched = true;
        if (
          bpa["salutationcode" + i].$modelValue == null ||
          bpa["salutationcode" + i].$modelValue == "" ||
          bpa["salutationcode" + i].$modelValue == "-1"
        ) {
          bpa["salutationcode" + i].$error.invalid = true;
          flag = false;
        }

        bpa["ownername" + i].$touched = true;
        if (
          bpa["ownername" + i].$modelValue == null ||
          bpa["ownername" + i].$modelValue == "" ||
          bpa["ownername" + i].$modelValue == "-1"
        ) {
          bpa["ownername" + i].$error.invalid = true;
          flag = false;
        }

        bpa["mobileno" + i].$touched = true;
        if (
          bpa["mobileno" + i].$modelValue == null ||
          bpa["mobileno" + i].$modelValue == "" ||
          !PATTERN_MOBILE.test(bpa["mobileno" + i].$modelValue)
        ) {
          bpa["mobileno" + i].$error.invalid = true;
          flag = false;
        }

        bpa["emailid" + i].$touched = true;
        if (
          bpa["emailid" + i].$modelValue == null ||
          bpa["emailid" + i].$modelValue == "" ||
          !PATTERN_EMAIL.test(bpa["emailid" + i].$modelValue)
        ) {
          bpa["emailid" + i].$error.invalid = true;
          flag = false;
        }

        bpa["relationname" + i].$touched = true;
        if (
          bpa["relationname" + i].$modelValue == null ||
          bpa["relationname" + i].$modelValue == "" ||
          bpa["relationname" + i].$modelValue == "-1"
        ) {
          bpa["relationname" + i].$error.invalid = true;
          flag = false;
        }

        bpa["relationshiptype" + i].$touched = true;
        if (
          bpa["relationshiptype" + i].$modelValue == null ||
          bpa["relationshiptype" + i].$modelValue == "" ||
          bpa["relationshiptype" + i].$modelValue == "-1"
        ) {
          bpa["relationshiptype" + i].$error.invalid = true;
          flag = false;
        }

        bpa["address" + i].$touched = true;
        if (
          bpa["address" + i].$modelValue == null ||
          bpa["address" + i].$modelValue == "" ||
          bpa["address" + i].$modelValue == "-1"
        ) {
          bpa["address" + i].$error.invalid = true;
          flag = false;
        }
      }

      bpa.plotpincode.$touched = true;
      if (
        bpa.plotpincode.$modelValue == null ||
        bpa.plotpincode.$modelValue == "" ||
        !PATTERN_PINCODE.test(bpa.plotpincode.$modelValue)
      ) {
        bpa.plotpincode.$error.invalid = true;
        // $timeout(() => (bpa.plotpincode.$error.invalid = false), 3000);
        flag = false;
      }
      return flag;
    };

    /*CREATE*/
    $scope.save = () => {
      let BPA = {}, valid = false;
      BPA = $scope.BPA.init($scope.BPA);
      valid = $scope.validateForm();
      if(!valid) return;

      valid = $window.confirm("Are you sure you want to submit?");
      if(!valid) return;

      CIS.save("POST", "./savebpa.htm", BPA, (success) => {
        $scope.serverMsg = success.msg;
        if(success.code == '201'){
          $scope.serverResponseSuccess = true;
          try{
            $scope.serverMsg += "\nNext Process: "+success.nextProcess.value;
            $timeout(() => {
              let url = success.nextProcess.key+"?applicationcode="+success.nextProcess.value1;
              $window.location.href = url;
            },4500);
          }catch(e){}
          
        }else{
          $scope.serverResponseFail = true;
        }
      }, (error) => {
        $scope.serverMsg = error.msg;
        $scope.serverResponseError = true;
      });

      $timeout(() => {
        $scope.serverResponseError = false;
        $scope.serverResponseFail= false;
        $scope.serverResponseInfo= false;
        $scope.serverResponseSuccess = false;
        $scope.serverMsg = "";
        // $scope.BPA = new BPA();
      }, 5000);
    };
  },
]);
