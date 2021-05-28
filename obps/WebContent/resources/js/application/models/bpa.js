/**
 * @author Decent Khongstia
 */
const Ownershipsubtypes = {
  1: "Single Owner",
  2: "Multiple Owner",
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
      //   this.ownerdetail = bpa.ownerdetail;
      this.ownerdetails = bpa.ownerdetails;
      return this;
    },
    extractFromEdcrObject: (edcr) => {
      let bpa = BPA;
      try{
        bpa.edcrnumber =  edcr.edcrnumber;
        bpa.applicationcode = edcr.planinfoobject.applicationNumber;
        bpa.plotidentifier1 = edcr.planinfoobject.planDetail.planInformation.plotNo;
        bpa.plotidentifier2 = edcr.planinfoobject.planDetail.planInformation.khataNo;
      }catch(e){}
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
      try{
        if(typeof edcr.planinfoobject == 'string')
          this.planinfoobject= JSON.parse(edcr.planinfoobject);
        else
          this.planinfoobject= edcr.planinfoobject;
      }catch(e){
        this.planinfoobject= edcr.planinfoobject;
      }
      this.status = edcr.status;
      this.entrydate = edcr.entrydate;
      return this;
    }, 
    extractPlanInfo: (edcr) => {
      if(edcr.planinfoobject != null && typeof edcr.planinfoobject == 'object')
        return edcr.planinfoobject.planDetail.planInformation;
      else if(edcr.planinfoobject != null && typeof edcr.planinfoobject == 'string')
        return JSON.parse(edcr.planinfoobject).planDetail.planInformation;
    }, 
    extractPlanDetail: (edcr) => {
      if(edcr.planinfoobject != null && typeof edcr.planinfoobject == 'object')
        return edcr.planinfoobject.planDetail;
      else if(edcr.planinfoobject != null && typeof edcr.planinfoobject == 'string')
        return JSON.parse(edcr.planinfoobject).planDetail;
    },
    extractPlanVirtualBuilding: (edcr) => {
      if(edcr.planinfoobject != null && typeof edcr.planinfoobject == 'object')
        return edcr.planinfoobject.planDetail.virtualBuilding;
      else if(edcr.planinfoobject != null && typeof edcr.planinfoobject == 'string')
        return JSON.parse(edcr.planinfoobject).planDetail.virtualBuilding;
    },
    extractPlanOccupancy: (edcr) => {
      let occ = '', occupancyDetails = {}, occupancyTypes = [], subOccupancyTypes = [];
      
      if(edcr.planinfoobject != null && typeof edcr.planinfoobject == 'object')
        occ = edcr.planinfoobject.planDetail.virtualBuilding.occupancyTypes;
      else if(edcr.planinfoobject != null && typeof edcr.planinfoobject == 'string')
        occ = JSON.parse(edcr.planinfoobject).planDetail.virtualBuilding.occupancyTypes;
      occ.forEach((o, x) => {
        occupancyTypes.push(o.type.name);
        subOccupancyTypes.push(o.subtype.name);
      });
      occupancyDetails.type = occupancyTypes;
      occupancyDetails.subtype = subOccupancyTypes;
      return occupancyDetails;
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

      return this;
    },
  };

  return OwnerDetail;
};
