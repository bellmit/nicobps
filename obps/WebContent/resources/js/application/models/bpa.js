/**
 * @author Decent Khongstia
 */
const Ownershipsubtypes = {
  1: "Single Owner",
  2: "Multiple Owner",
};

const FileMimeType = {
  "JVBER": "application/pdf",
  "/9J/4": "image/jpeg",
  "IVBOR": "image/png"
};

const FeeType = {
	FormAndScrutinizationFee: "FormScrutinizationFee",
	FormAndEarthquakeFee: "FormAndEarthquakeFee",
	TDRFee: "TDRFee",
	PermitFee: "PermitFee",
}

const ProcessingUrl = {
  bpaApprove: "approvebpapplication.htm",
  bpaMakePayment: "bpamakepayment.htm",
  bpaProcess: "processbpapplication.htm",
  bpaReject: "rejectbpapplication.htm",
  bpaReturnFromCitizen: "returnfromcitizenbpapplication.htm",
  bpaSendToCitizen: "sendtocitizenbpapplication.htm",
  bpaSiteInspection: "savebpasiteinspection.htm",
};

const Timeout = {
	OneSecond: 1000,
	Reload: 2900,
	ThreeSecond: 3000,
	TwoSecond: 2000,
};

const ComboResponse = [
	{key: 'Y', value: 'YES', enabled: true},
	{key: 'N', value: 'NO', enabled: true},
	{key: 'X', value: 'Not Applicable', enabled: true}
];

var ApplicationFlow = function () {
  var ApplicationFlow = {
    applicationcode: '',
    tousercode: '',
    remarks: '',
    init: (af) => {
      this.applicationcode = af.applicationcode;
      this.tousercode = af.tousercode;
      this.remarks = af.remarks;
      return this;
    }
  }

  return ApplicationFlow;
};

var BPA = function () {
  let todaysDate = new Date().toLocaleDateString();
  var BPA = {
    applicationcode: "",
    edcrnumber: "",
    ownershiptypecode: "",
    ownershipsubtype: "",
    plotaddressline1: "",
    plotaddressline2: "",
    plotvillagetown: "",
    plotdistrictcode: null,
    plotpincode: null,
    plotgiscoordinates: "",
    officelocationcode: null,
    landregistrationdetails: "",
    landregistrationno: "",
    plotidentifier1: "",
    plotidentifier2: "",
    plotidentifier3: "",
    holdingno: "",
    entrydate: todaysDate,
    // ownerdetail: new OwnerDetail(),
    ownerdetails: [new OwnerDetail()],
    enclosures: [],
    additionalinfo: {},
    init: (bpa) => {
      this.applicationcode = bpa.applicationcode;
      this.edcrnumber = bpa.edcrnumber;
      this.ownershiptypecode = bpa.ownershiptypecode;
      this.ownershipsubtype = bpa.ownershipsubtype;
      this.plotaddressline1 = bpa.plotaddressline1;
      this.plotaddressline2 = bpa.plotaddressline2;
      this.plotvillagetown = bpa.plotvillagetown;
      this.plotdistrictcode = bpa.plotdistrictcode;
      this.plotpincode = bpa.plotpincode;
      this.plotgiscoordinates = bpa.plotgiscoordinates;
      this.officelocationcode = bpa.officelocationcode;
      this.landregistrationdetails = bpa.landregistrationdetails;
      this.landregistrationno = bpa.landregistrationno;
      this.plotidentifier1 = bpa.plotidentifier1;
      this.plotidentifier2 = bpa.plotidentifier2;
      this.plotidentifier3 = bpa.plotidentifier3;
      this.holdingno = bpa.holdingno;      
      let ods = [];
      if(bpa.ownerdetails != null && bpa.ownerdetails.length > 0){
    	  bpa.ownerdetails.forEach((o,x) => ods.push(o.init(o)));
    	  bpa.ownerdetails = ods;
    	  this.ownerdetails = bpa.ownerdetails;
      }
      this.additionalinfo = angular.toJson(bpa.additionalinfo, true);
      return this;
    },
    extractFromEdcrObject: (edcr) => {
      let bpa = BPA;
      try {
        bpa.edcrnumber = edcr.edcrnumber;
        bpa.applicationcode = edcr.planinfoobject.applicationNumber;
        bpa.plotidentifier1 = edcr.planinfoobject.planDetail.planInformation.plotNo;
        bpa.plotidentifier2 = edcr.planinfoobject.planDetail.planInformation.khataNo;
      } catch (e) { }
      return bpa;
    }
  };

  return BPA;
};

var EdcrDetail = function () {
  var EdcrDetail = {
    usercode: null,
    officecode: null,
    edcrnumber: '',
    planinfoobject: '',
    status: '',
    dxffile: '',
    scrutinyreport: '',
    entrydate: '',
    init: (edcr) => {
      this.usercode = edcr.usercode;
      this.officecode = edcr.officecode;
      this.edcrnumber = edcr.edcrnumber;
      try {
        if (typeof edcr.planinfoobject == 'string')
          this.planinfoobject = JSON.parse(edcr.planinfoobject);
        else
          this.planinfoobject = edcr.planinfoobject;
      } catch (e) {
        this.planinfoobject = edcr.planinfoobject;
      }
      this.status = edcr.status;
      this.entrydate = edcr.entrydate;
      return this;
    },
    extractPlanInfo: (edcr) => {
      if (edcr.planinfoobject != null && typeof edcr.planinfoobject == 'object')
        return edcr.planinfoobject.planDetail.planInformation;
      else if (edcr.planinfoobject != null && typeof edcr.planinfoobject == 'string')
        return JSON.parse(edcr.planinfoobject).planDetail.planInformation;
    },
    extractPlanDetail: (edcr) => {
      if (edcr.planinfoobject != null && typeof edcr.planinfoobject == 'object')
        return edcr.planinfoobject.planDetail;
      else if (edcr.planinfoobject != null && typeof edcr.planinfoobject == 'string')
        return JSON.parse(edcr.planinfoobject).planDetail;
    },
    extractPlanVirtualBuilding: (edcr) => {
      if (edcr.planinfoobject != null && typeof edcr.planinfoobject == 'object')
        return edcr.planinfoobject.planDetail.virtualBuilding;
      else if (edcr.planinfoobject != null && typeof edcr.planinfoobject == 'string')
        return JSON.parse(edcr.planinfoobject).planDetail.virtualBuilding;
    },
    extractPlanOccupancy: (edcr) => {
      let occ = '', occupancyDetails = {}, occupancyTypes = [], subOccupancyTypes = [];

      if (edcr.planinfoobject != null && typeof edcr.planinfoobject == 'object')
        occ = edcr.planinfoobject.planDetail.virtualBuilding.occupancyTypes;
      else if (edcr.planinfoobject != null && typeof edcr.planinfoobject == 'string')
        occ = JSON.parse(edcr.planinfoobject).planDetail.virtualBuilding.occupancyTypes;
      occ.forEach((o, x) => {
        occupancyTypes.push(o.type.name);
        subOccupancyTypes.push(o.subtype.name);
      });
      occupancyDetails.type = occupancyTypes;
      occupancyDetails.subtype = subOccupancyTypes;
      return occupancyDetails;
    },
    extractPlanBlocks: (edcr) => {
      let blks = '';

      if (edcr.planinfoobject != null && typeof edcr.planinfoobject == 'object')
        blks = edcr.planinfoobject.planDetail.blocks;
      else if (edcr.planinfoobject != null && typeof edcr.planinfoobject == 'string')
        blks = JSON.parse(edcr.planinfoobject).planDetail.blocks;
      return blks;
    }
  }

  return EdcrDetail;
};

var OwnerDetail = function () {
  let todaysDate = new Date().toLocaleDateString();
  var OwnerDetail = {
    ownerdetailcode: "",
    applicationcode: "",
    salutationcode: null,
    ownername: "",
    relationshiptypecode: null,
    relationname: "",
    mobileno: "",
    emailid: "",
    address: "",
    preaddressline1: "",
    preaddressline2: "",
    pretownvillage: "",
    prestatecode: "",
    predistrictcode: "",
    prepincode: "",
    peraddressline1: "",
    peraddressline2: "",
    pertownvillage: "",
    perstatecode: "",
    perdistrictcode: "",
    perpincode: "",
    additionalinfo: {},
    entrydate: todaysDate,
    init: (od) => {
      this.ownerdetailcode = od.ownerdetailcode;
      this.applicationcode = od.applicationcode;
      this.salutationcode = od.salutationcode;
      this.ownername = od.ownername;
      this.relationshiptypecode = od.relationshiptypecode;
      this.relationname = od.relationname;
      this.mobileno = od.mobileno;
      this.emailid = od.emailid;
      this.address = od.address;
      this.preaddressline1 = od.preaddressline1;
      this.preaddressline2 = od.preaddressline2;
      this.pretownvillage = od.pretownvillage;
      this.prestatecode = od.prestatecode;
      this.predistrictcode = od.predistrictcode;
      this.prepincode = od.prepincode;
      this.peraddressline1 = od.peraddressline1;
      this.peraddressline2 = od.peraddressline2;
      this.pertownvillage = od.pertownvillage;
      this.perstatecode = od.perstatecode;
      this.perdistrictcode = od.perdistrictcode;
      this.perpincode = od.perpincode;
      this.additionalinfo = angular.toJson(od.additionalinfo, true);
      this.init = od.init;
      return this;
    },
  };

  return OwnerDetail;
};

var Questionnaire = function () {
	var Questionnaire = {
		description: '',
		questioncode: null,
		remarks: '',
		response: false,
		init: (obj) => {
			try{
				this.questioncode = obj.questioncode;
				this.remarks = obj.remarks;
				this.response = obj.response;
			}catch (e) {
			}
			return this;
		}
	}
	return Questionnaire;
}

var SiteInspection = function () {

  var SiteInspection = {
    applicationcode: '',
    questionnaires: [new Questionnaire()],
    remarks: "",
    reports: new Array(1),
    error: [],
    tousercode: null,
    init: (obj) => {
      this.applicationcode = obj.applicationcode;
      this.questionnaires = obj.questionnaires;
      this.remarks = obj.remarks;
      this.reports = obj.reports;
      this.tousercode = obj.tousercode;
      return this;
    },
    initQuestionnaires: (obj) => {
    	let questionnaires = new Array();
    	try{
        	if(obj != null && obj.length > 0){
        		obj.forEach((o, x) => {
        			let qnaire = new Questionnaire();
        			qnaire.description = o.questiondescription; 
    				qnaire.questioncode = o.questioncode;
    				qnaire.response = o.response?'Y':'N';
    				qnaire.remarks = o.remarks;
    				questionnaires .push(qnaire);
        		});
        	}
    	}catch (e) {
		}
        return questionnaires;
    }
  }
  return SiteInspection;
};


var Modal = function () {
  var Modal = {
    action: 1,
    actionname: "Forward",
    title: 'Forward Application',
    usercode: null,
    process: [new ProcessFlow()],
    remarks: '',
  }

  return Modal;
}

var ModalFile = function () {
  var ModalFile = {
    title: null,
    src: null,
    mimetype:null,
  }

  return ModalFile;
}

var TaskStatus = function () {
  var TaskStatus = {
    taskdate: null,
    username: '',
    status: '',
    remarks: '',
    nextprocessname: '',
  }
  return TaskStatus;
}

var ProcessFlow = function () {
  var ProcessFlow = {
    applicationcode: '',
    fromusercode: null,
    tousercode: null,
    fromprocesscode: null,
    toprocesscode: null,
    remarks: '',
    enclosures: [],
    init: (obj) => {
      this.applicationcode = obj.applicationcode;
      this.fromusercode = obj.fromusercode;
      this.tousercode = obj.tousercode;
      this.fromprocesscode = obj.fromprocesscode;
      this.toprocesscode = obj.toprocesscode;
      this.remarks = obj.remarks;
      this.enclosures = obj.enclosures;
      return this;
    }
  }
  return ProcessFlow;
}