/**
 * @author Decent Khongstia
 */

app.directive("testButton",[ "$compile", "bpaService",
	function renderButton($compile, BS) {
	  return {
	    template: '<button ng-click="add()">{{name}}</button><hr/>',
	    link: linkFn
	  }

	  function linkFn(scope, elem, attr) {
	    scope.name = 'Add Widget';
	    scope.add = () => {
	      const newScope = scope.$new(true);
	      newScope.export = (data) => alert(data);
	      const templ = '<div>' +
	                      '<widget></widget>' +
	                      '<button ng-click="export(this.stuff)">Export</button>' +
	                    '</div>';
	      const compiledTempl = $compile(templ)(newScope);
	      elem.append(compiledTempl);
	    }
	  }
	}
]);

/*BUTTON*/
app.directive("forwardButton", function forwardButton($compile) {
	return {
	    template: '<button class="dropdown-item btn-outline-primary" type="button" data-toggle="modal"' 
    		  	+ 'data-target="#commonModal" ng-click="setModalTitle(1)">Forward</button>',
	    link: action
	  }
	function action (scope, elem, attr) {
	};
});

app.directive("rejectButton", function rejectButton($compile) {
	return {
	    template: '<button class="dropdown-item btn-outline-danger" type="button" data-toggle="modal"' 
    		  	+ 'data-target="#commonModal" ng-click="setModalTitle(2)">Reject</button>',
	    link: action
	  }
	function action (scope, elem, attr) {
	};
});

app.directive("returnFromCitizenButton", function rejectButton($compile) {
	return {
		template: '<button class="dropdown-item btn-outline-primary" type="button" data-toggle="modal"' 
			+ 'data-target="#commonModal" ng-click="setModalTitle(4)">Send</button>',
			link: action
	}
	function action (scope, elem, attr) {
	};
});

app.directive("sendToCitizenButton", function rejectButton($compile) {
	return {
		template: '<button class="dropdown-item btn-outline-warning" type="button" data-toggle="modal"' 
			+ 'data-target="#commonModal" ng-click="setModalTitle(3)">Send To Citizen</button>',
			link: action
	}
	function action (scope, elem, attr) {
	};
});

app.directive("modalButton", ["$compile", "$timeout", "$window", "commonInitService", function modalButton($compile, $timeout, $window, CIS) {
	return {
		template: '<button type="button" class="btn btn-outline-primary" ng-click="modalAction(modal.remarks)">{{modal.actionname}}</button>',
			link: action
	}
	function action (scope, elem, attr) {
		scope.modalAction = (r) => {
			switch (scope.modal.action) {
				case 1:
					scope.forward();
					break;
				case 2:
					scope.reject();
					break;
				case 3:
					scope.sendToCitizen();
					break;
				case 4:
					scope.returnFromCitizen();
					break;
			}
		}
		
		scope.clearAfterCreateProcess = () => {
			$timeout(() => {
				scope.serverResponseError = false;
				scope.serverResponseFail = false;
				scope.serverResponseInfo = false;
				scope.serverResponseSuccess = false;
				scope.serverMsg = "";
			}, Timeout.ThreeSecond);
		};
		
		scope.reject = () => {
			let data = {}, valid = false;

			if (scope.modal.remarks == null || scope.modal.remarks == "") {
				alert("Please enter remarks");
				return false;
			}
			data.applicationcode = scope.bpa.applicationcode;
			data.remarks = scope.modal.remarks;
			valid = $window.confirm("Are you sure you want to reject?");
			if (!valid) return;

			$('#commonModal').modal('hide');
			CIS.save("POST", ProcessingUrl.bpaReject, data, (success) => {
				scope.serverMsg = success.msg;
				if (success.code == '201') {
					scope.serverResponseSuccess = true;
					$timeout(() => { $window.location.reload(); }, Timeout.Reload);
				} else {
					scope.serverResponseFail = true;
				}
			}, (error) => {
				try {
					scope.serverMsg = error.msg;
				} catch (e) {
					scope.serverMsg = "Internal server error";
				}
				scope.serverResponseError = true;
			});

			scope.clearAfterCreateProcess();
		};//End reject
		
		scope.sendToCitizen = () => {
			let data = {}, valid = false;

			if (scope.modal.remarks == null || scope.modal.remarks == "") {
				alert("Please enter remarks");
				return false;
			}
			data.applicationcode = scope.bpa.applicationcode;
			data.remarks = scope.modal.remarks;
			valid = $window.confirm("Are you sure you want to Send to Citizen?");
			if (!valid) return;

			$('#commonModal').modal('hide');
			CIS.save("POST", ProcessingUrl.bpaSendToCitizen, data, (success) => {
				scope.serverMsg = success.msg;
				if (success.code == '201') {
					scope.serverResponseSuccess = true;
					$timeout(() => { $window.location.reload(); }, Timeout.Reload);
				} else {
					scope.serverResponseFail = true;
				}
			}, (error) => {
				try {
					scope.serverMsg = error.msg;
				} catch (e) {
					scope.serverMsg = "Internal server error";
				}
				scope.serverResponseError = true;
			});
			
			scope.clearAfterCreateProcess();
		};//End sendToCitizen 
		
	};
}]);

//Page
app.directive("basicDetails",["$compile", "bpaService", function($compile, BS) {
	return {
    	template: '<ng-include src="\'basicdetails.htm\'"></ng-include>',
	    link: action
	}
	function action(scope, elem, attr) {
		scope.basicDetail = {}
		BS.getBpaApplicationDetailsV2((response) => {
			scope.basicDetail.address = response.plotaddressline1 + " " + response.plotaddressline2;
			scope.basicDetail.citytown = response.plotvillagetown;
			scope.basicDetail.pincode = response.plotpincode;
			scope.basicDetail.officelocationname = response.locationname;
			scope.basicDetail.plotidentifier1 = response.plotidentifier1;
			scope.basicDetail.holdingno = response.holdingno;
			scope.basicDetail.landregistrationno = response.landregistrationno;
			scope.basicDetail.landregistrationdetails = response.landregistrationdetails;
			scope.basicDetail.ownershiptypename = response.ownershiptypename;
			scope.basicDetail.ownershipsubtype = response.ownershipsubtype;
			scope.OwnerDetails = response.ownerdetails;
			scope.DocumentDetails = response.documentdetails;
		}, APPCODE);
	};
}]);

app.directive("commonProcessingDocumentDetails",["$compile", "bpaService", function($compile, BS) {
	return {
		template: '<ng-include src="\'commonprocessingdocumentdetails.htm\'"></ng-include>',
		link: action
	}
	function action(scope, elem, attr) {
		scope.addRemoveMoreFile = (opt) => {
			switch(opt){
				case 1:
					scope.BPAEnclosures.push({code:null, name:'', file:null, error:false, errormsg: null});
					break;
				default:
					scope.BPAEnclosures.pop();
			}
			
		};

		scope.filterEnclosure = (opt, code) => {
			if(opt == 1){
				scope.BPAEnclosures.find( l => l.code == code).error = false;
				if(scope.BPAEnclosures.find( l => l.code == code).name == null || scope.BPAEnclosures.find( l => l.code == code).name == ""){
					scope.BPAEnclosures.find( l => l.code == code).error = true;
					scope.BPAEnclosures.find( l => l.code == code).errormsg = `Please select enclosure first`;
					return;
				}
				if(scope.BPAEnclosures.find( l => l.code == code).file == null || scope.BPAEnclosures.find( l => l.code == code).file == ""){
					scope.BPAEnclosures.find( l => l.code == code).error = true;
					scope.BPAEnclosures.find( l => l.code == code).errormsg = `Please upload ${scope.BPAEnclosures.find( l => l.code == code).name} first`;
					return;
				}
				scope.addRemoveMoreFile(opt);
				scope.Enclosures.find( e => e.enclosurecode == code).selected = true;
				scope.BPAEnclosures.find( l => l.code == code).flag = true;
				
			}else{
				scope.addRemoveMoreFile(opt);
				scope.BPAEnclosures[scope.BPAEnclosures.length-1].flag = false;
				scope.Enclosures.find( e => e.enclosurecode == scope.BPAEnclosures[scope.BPAEnclosures.length-1].code).selected = false;
				
			}
		};
		
		scope.setLabel = (index, code) => {
			if(index != null && code!= null){
				scope.BPAEnclosures[index].name = scope.Enclosures.find( e => e.enclosurecode == code).enclosurename;
				scope.BPAEnclosures[index].code = code;
				scope.BPAEnclosures[index].error = false;
			}else{
				if(scope.BPAEnclosures[index].file != null && scope.BPAEnclosures[index].file != '')
					scope.BPAEnclosures[index].error = false;
			}	
		}
	};
}]);

app.directive("commonProcessingAction",["$compile", "bpaService", function($compile, BS) {
	return {
		template: '',
		link: action
	}
	function action(scope, elem, attr) {
		scope.qp = 'commonprocessingaction.htm?applicationcode='+APPCODE;
		const templ = '<ng-include src="\'commonprocessingaction.htm?applicationcode='+APPCODE+'\'"></ng-include>'; 
		const compiledTempl = $compile(templ)(scope);
		elem.append(compiledTempl);
		scope.setModalTitle = (opt) => {
			scope.modal = new Modal();
			console.log("opt: ", opt);
			switch(opt){
				case 1:
					scope.modal.action = 1;
					scope.modal.actionname = "Forward";
					scope.modal.title = 'Forward Application';
					break;
				case 2:
					scope.modal.action = 2;
					scope.modal.actionname = "Reject";
					scope.modal.title = 'Reject Application';
					break;
				case 3:
					scope.modal.action = 3;
					scope.modal.actionname = "Send";
					scope.modal.title = 'Send To Citizen';
					break;
				case 4:
					scope.modal.action = 4;
					scope.modal.actionname = "Send";
					scope.modal.title = 'Return From Citizen';
					break;
			}
		}
	};
}]);

app.directive("documentDetails",["$compile", "bpaService", function($compile, BS) {
	return {
		template: '<ng-include src="\'documentdetails.htm\'"></ng-include>',
		link: action
	}
	function action(scope, elem, attr) {
	};
}]);

app.directive("ownerDetails",["$compile", "bpaService", function($compile, BS) {
	return {
		template: '<ng-include src="\'ownerdetails.htm\'"></ng-include>',
		link: action
	}
	function action(scope, elem, attr) {
	};
}]);

app.directive("scrutinyDetails",["$compile", "bpaService", function($compile, BS) {
	return {
		template: '<ng-include src="\'scrutinydetails.htm\'"></ng-include>',
		link: action
	}
	function action(scope, elem, attr) {
		BS.getEdcrDetailsV3((response) => {
			let edcr = new EdcrDetail();
			scope.EDCR = edcr.init(response);
			scope.planInfo = edcr.extractPlanInfo(scope.EDCR);
			scope.gmapAddress = (scope.planInfo.district).toLocaleLowerCase();
			scope.occupancy = edcr.extractPlanOccupancy(scope.EDCR);
			scope.Blocks = edcr.extractPlanBlocks(scope.EDCR);
			scope.basicDetail.applicationcode = APPCODE;
			scope.basicDetail.edcrnumber = scope.EDCR.edcrnumber;
			scope.basicDetail.occupancy = (scope.occupancy != null && scope.occupancy.type != null) ? (scope.occupancy.type).join(",") : "NA";
			scope.basicDetail.plotarea = scope.planInfo.plotArea;
		}, APPCODE);
		
		scope.getFloorArea = (occupanies) => {
			return BS.getFloorTotalArea(occupanies);
		};

		scope.getFloorBuiltUpArea = (occupanies) => {
			return BS.getFloorTotalBuiltUpArea(occupanies);
		};

		scope.getFloorName = (floorNumber) => {
			return BS.getFloorName(floorNumber);
		};

		scope.getFloorSubOccupancy = (data) => {
			return BS.getFloorSubOccupancy(data);
		};

	};
}]);

app.directive("siteInspectionDetails",["$compile", "bpaService", function ($compile, BS) {
	return {
		template: '<ng-include src="\'siteinspectiondetails.htm\'"></ng-include>',
		link: action
	}
	function action(scope, elem, attr) {
		BS.listSiteInspectionQuestionnaires((response) => {
			scope.Questionnaires = response;
			console.log("Questionnaires:: ",scope.Questionnaires);
		}, APPCODE);

	};
}]);

app.directive("taskStatus",["$compile", "bpaService", function ($compile, BS) {
	return {
		template: '<ng-include src="\'processtrackstatus.htm\'"></ng-include>',
		link: action
	}
	function action(scope, elem, attr) {
		scope.taskStatus = new TaskStatus();
		
		BS.getCurrentProcessTaskStatus((response) => {
			scope.taskStatus = response;
		}, APPCODE);
	};
}]);


//MODAL
app.directive("fileViewModal",["$compile", "$sce", "bpaService", function($compile, $sce, BS) {
	return {
		template: '<ng-include src="\'fileviewmodal.htm\'"></ng-include>',
		link: action
	}
	function action(scope, elem, attr) {
		scope.fileModal = new ModalFile();
		scope.viewFile = (opt, data) => {
			console.log(data);
			let fileContent = "";
			switch (opt) {
				case 2:
					scope.SiteReportDetails.forEach((o, x) => {
						if (o.appenclosurecode == data) {
							scope.fileModal.mimetype = BS.detectMimeType(o.enclosureimage)
							fileContent = 'data:' + scope.fileModal.mimetype + ';base64,' + o.enclosureimage;
						}
					});
					break;
				default:
					scope.DocumentDetails.forEach((o, x) => {
						if (o.appenclosurecode == data) {
							scope.fileModal.title = o.enclosurename;
							scope.fileModal.mimetype = BS.detectMimeType(o.enclosureimage)
							console.log("MimeType: ", BS.detectMimeType(o.enclosureimage));
							fileContent = 'data:' + scope.fileModal.mimetype + ';base64,' + o.enclosureimage;
						}
					});
				break;
			}
			scope.fileModal.src = $sce.trustAsResourceUrl(fileContent);
		}
	};
}]);

app.directive("modalAction",["$compile", "bpaService", function taskStatus($compile, BS) {
	return {
		template: '<ng-include src="\'modal.htm\'"></ng-include>',
		link: action
	}
	function action(scope, elem, attr) {
		BS.listNextProcessingUsers((response) => {
			scope.Users = response;
		}, APPCODE);
		BS.listNextProcess((response) => {
			scope.NextProcesses = response;
		}, APPCODE);
		scope.listUsers = (flag, index, processcode) => {
			try{
				if(flag){
					scope.modal.process[index] = new ProcessFlow();
					scope.modal.process[index].toprocesscode = processcode;
				} else
					scope.modal.process.splice(index, 1);
				
				console.log("modal.process: ",scope.modal.process);
			}catch (e) {
				scope.modal.process.push(new ProcessFlow());
			}
			 
			BS.listNextProcessingUsersByProcesscode((response) => {
				scope.Users[processcode] = response;
				console.log("scope.modal: ",scope.modal);
			}, APPCODE, processcode);
		}
		
		scope.onc = (index) => {
			console.log("scope.modal.process[index]:: ",scope.modal.process[index]);
		}
		
	};
}]);

