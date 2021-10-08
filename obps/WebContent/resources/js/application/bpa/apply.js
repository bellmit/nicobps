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
    $scope.Engineers = [];
    $scope.Relationshiptypes = [];
    $scope.Salutations = [];
    $scope.States = [];

    /* GET */
    BS.getEdcrDetails((response) => {
      let edcr = new EdcrDetail();
      $scope.EDCR = edcr.init(response);
      $scope.planInfo = edcr.extractPlanInfo($scope.EDCR);
      $scope.gmapAddress = ($scope.planInfo.district).toLocaleLowerCase();
      $scope.occupancy = edcr.extractPlanOccupancy($scope.EDCR);
      $scope.BPA = $scope.BPA.extractFromEdcrObject($scope.EDCR);
      $scope.listOfficelocations();
    }, EDCRNUMBER);

    $scope.listOfficelocations = () => {
      BS.listOfficelocations((response) => {
        $scope.Officelocations = response;
      }, (data = $scope.EDCR.officecode));
    };

    $scope.listOwnershiptypes = () => {
      BS.listOwnershiptypes((response) => {
        $scope.Ownertypes = response;
      }, (data = ""));
    };
    $scope.listOwnershiptypes();
    
    $scope.listEngineers = () => {
      BS.listEngineers((response) => {
        $scope.Engineers = response;
      }, (data = ""));
    };
    $scope.listEngineers();

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

    BS.listStates((response) => {
    	$scope.States = response;
    }, (data = ""));
    
    $scope.listDistricts = (opt, statecode) => {
    	BS.listDistricts((response) => {
    		switch(opt){
    			case 1:
    				$scope.PreDistricts = response;
    				break;
    			case 2:
    				$scope.PerDistricts = response;
    				break;
    				
    		}
        }, (data = statecode));
    };

    /* ACTION */
    $scope.addOwner = () => {
      if($scope.BPA.ownershipsubtype == "Multiple Owner")
        $scope.BPA.ownerdetails.push(new OwnerDetail());
      else
        $scope.BPA.ownerdetails = [$scope.BPA.ownerdetails[0]];
    };

    $scope.removeOwner = () => {
        $scope.BPA.ownerdetails.pop();
    };
    
    $scope.sameAddr = (index) => {
    	let isSameAddress = $scope.bpaform['isSameAddress'+index].$modelValue;
    	if(isSameAddress && $scope.BPA.ownerdetails != null && $scope.BPA.ownerdetails[index] != null){
    		$scope.BPA.ownerdetails[index].peraddressline1 = $scope.BPA.ownerdetails[index].preaddressline1;
    		$scope.BPA.ownerdetails[index].peraddressline2 = $scope.BPA.ownerdetails[index].preaddressline2;
    		$scope.BPA.ownerdetails[index].pertownvillage= $scope.BPA.ownerdetails[index].pretownvillage;
    		$scope.BPA.ownerdetails[index].perstatecode = $scope.BPA.ownerdetails[index].prestatecode;
    		$scope.listDistricts(2,$scope.BPA.ownerdetails[index].perstatecode);
    		$scope.BPA.ownerdetails[index].perdistrictcode = $scope.BPA.ownerdetails[index].predistrictcode ;
    		$scope.BPA.ownerdetails[index].perpincode = $scope.BPA.ownerdetails[index].prepincode  ;
    	}
    }
    
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
      bpa.edcrnumber.$touched = true;
      bpa.plotarea.$touched = true;
      bpa.occupancy.$touched = true;
      bpa.plotaddressline1.$touched = true;
      bpa.nameofengineer.$touched = true;
      
      
      if (
        bpa.nameofengineer.$modelValue == null ||
        bpa.nameofengineer.$modelValue == ""
      ) {
        bpa.nameofengineer.$error.invalid = true;
        flag = false;
      }
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
      /*
		 * if ( bpa.plotgiscoordinates.$modelValue == null ||
		 * bpa.plotgiscoordinates.$modelValue == "" ) {
		 * bpa.plotgiscoordinates.$error.invalid = true; flag = false; }
		 */

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
      /*
		 * if ( bpa.plotno.$modelValue == null || bpa.plotno.$modelValue == "" ||
		 * bpa.plotno.$modelValue == "-1" ) { bpa.plotno.$error.invalid = true;
		 * flag = false; }
		 */

      bpa.holdingno.$touched = true;
      /*
		 * if ( bpa.holdingno.$modelValue == null || bpa.holdingno.$modelValue == "" ||
		 * bpa.holdingno.$modelValue == "-1" ) { bpa.holdingno.$error.invalid =
		 * true; flag = false; }
		 */

      bpa.landregistrationno.$touched = true;
      /*
		 * if ( bpa.landregistrationno.$modelValue == null ||
		 * bpa.landregistrationno.$modelValue == "" ||
		 * bpa.landregistrationno.$modelValue == "-1" ) {
		 * bpa.landregistrationno.$error.invalid = true; flag = false; }
		 */

      bpa.landregdetails.$touched = true;
      /*
		 * if ( bpa.landregdetails.$modelValue == null ||
		 * bpa.landregdetails.$modelValue == "" ||
		 * bpa.landregdetails.$modelValue == "-1" ) {
		 * bpa.landregdetails.$error.invalid = true; flag = false; }
		 */

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
          bpa["emailid" + i].$modelValue != null && bpa["emailid" + i].$modelValue != "" &&
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

        bpa["preaddressline1" + i].$touched = true;
        if (
          bpa["preaddressline1" + i].$modelValue == null ||
          bpa["preaddressline1" + i].$modelValue == "" ||
          bpa["preaddressline1" + i].$modelValue == "-1"
        ) {
          bpa["preaddressline1" + i].$error.invalid = true;
          flag = false;
        }
        
        bpa["pretownvillage" + i].$touched = true;
        if (
        		bpa["pretownvillage" + i].$modelValue == null ||
        		bpa["pretownvillage" + i].$modelValue == "" ||
        		bpa["pretownvillage" + i].$modelValue == "-1"
        ) {
        	bpa["pretownvillage" + i].$error.invalid = true;
        	flag = false;
        }
        
        bpa["prestatecode" + i].$touched = true;
        if (
        		bpa["prestatecode" + i].$modelValue == null ||
        		bpa["prestatecode" + i].$modelValue == "" ||
        		bpa["prestatecode" + i].$modelValue == "-1"
        ) {
        	bpa["prestatecode" + i].$error.invalid = true;
        	flag = false;
        }
        
        bpa["predistrictcode" + i].$touched = true;
        if (
        		bpa["predistrictcode" + i].$modelValue == null ||
        		bpa["predistrictcode" + i].$modelValue == "" ||
        		bpa["predistrictcode" + i].$modelValue == "-1"
        ) {
        	bpa["predistrictcode" + i].$error.invalid = true;
        	flag = false;
        }
        
        bpa["prepincode" + i].$touched = true;
        if (
        		bpa["prepincode" + i].$modelValue == null ||
        		bpa["prepincode" + i].$modelValue == "" ||
        		bpa["prepincode" + i].$modelValue == "-1"
        ) {
        	bpa["prepincode" + i].$error.invalid = true;
        	flag = false;
        }
        
        
        bpa["peraddressline1" + i].$touched = true;
        if (
        		bpa["peraddressline1" + i].$modelValue == null ||
        		bpa["peraddressline1" + i].$modelValue == "" ||
        		bpa["peraddressline1" + i].$modelValue == "-1"
        ) {
        	bpa["peraddressline1" + i].$error.invalid = true;
        	flag = false;
        }
        
        bpa["pertownvillage" + i].$touched = true;
        if (
        		bpa["pertownvillage" + i].$modelValue == null ||
        		bpa["pertownvillage" + i].$modelValue == "" ||
        		bpa["pertownvillage" + i].$modelValue == "-1"
        ) {
        	bpa["pertownvillage" + i].$error.invalid = true;
        	flag = false;
        }
        
        bpa["perstatecode" + i].$touched = true;
        if (
        		bpa["perstatecode" + i].$modelValue == null ||
        		bpa["perstatecode" + i].$modelValue == "" ||
        		bpa["perstatecode" + i].$modelValue == "-1"
        ) {
        	bpa["perstatecode" + i].$error.invalid = true;
        	flag = false;
        }
        
        bpa["perdistrictcode" + i].$touched = true;
        if (
        		bpa["perdistrictcode" + i].$modelValue == null ||
        		bpa["perdistrictcode" + i].$modelValue == "" ||
        		bpa["perdistrictcode" + i].$modelValue == "-1"
        ) {
        	bpa["perdistrictcode" + i].$error.invalid = true;
        	flag = false;
        }
        
        bpa["perpincode" + i].$touched = true;
        if (
        		bpa["perpincode" + i].$modelValue == null ||
        		bpa["perpincode" + i].$modelValue == "" ||
        		bpa["perpincode" + i].$modelValue == "-1"
        ) {
        	bpa["perpincode" + i].$error.invalid = true;
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

    /* CREATE */
    $scope.save = () => {
      let BPA = {}, valid = false;
      $scope.BPA.additionalinfo.extrafield = "Testing";
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
            if (success.nextProcess != null && success.nextProcess.key != null && success.nextProcess.key != '') {
            	$timeout(() => {
                    let url = success.nextProcess.key+"?applicationcode="+success.nextProcess.value1;
                    $window.location.href = url;
                  },Timeout.Reload);
            } else
                $timeout(() => { $window.location.reload(); }, Timeout.Reload);
            
          }catch(e){}
          
        }else{
          $scope.serverResponseFail = true;
        }
      }, (error) => {
    	  if(error != null && error.msg != null)
    		  $scope.serverMsg = error.msg;
    	  $scope.serverResponseError = true;
      });

      $timeout(() => {
        $scope.serverResponseError = false;
        $scope.serverResponseFail= false;
        $scope.serverResponseInfo= false;
        $scope.serverResponseSuccess = false;
        $scope.serverMsg = "";
      }, 5000);
    };
  },
]);
