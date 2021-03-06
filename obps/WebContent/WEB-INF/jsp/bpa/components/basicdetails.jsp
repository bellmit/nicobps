<!-- @author Decent Khongstia -->
<div class="card mb-4">
	<div class="card-body">
		<div class="card-title h5">Basic Detail</div>
		<label class="col-sm-12">Basic Details
			<hr>
		</label>
		<div class="form-group">
			<div class="row">
				<div class="col-sm-4">
					<label class="col-sm-12"><small>Application Code</small></label> <label
						class="col-sm-12">{{basicDetail.applicationcode}}</label>
				</div>
				<div class="col-sm-4">
					<label class="col-sm-12"><small>Building Plan
							Scrutiny Number</small></label> <label class="col-sm-12">{{basicDetail.edcrnumber}}</label>
				</div>
				<div class="col-sm-4">
					<label class="col-sm-12"><small>Occupancy</small></label> <label
						class="col-sm-12">{{basicDetail.occupancy}}</label>
				</div>
				<div class="col-sm-4">
					<label class="col-sm-12"><small>Name of Engineer on
							Record</small></label> <label class="col-sm-12">{{basicDetail.additionalinfo}}</label>
				</div>
				<div class="col-sm-4">
					<label class="col-sm-12"><small>Is Tatkal </small></label> <label
						class="col-sm-12">{{basicDetail.istatkal == 'Y' ? 'YES'
						:'NO'}}</label>
				</div>
				<div class="col-sm-4">
					<label class="col-sm-12"><small>Reference
							Application Code</small></label> <label class="col-sm-12">{{basicDetail.referenceapplicationcode != null ? basicDetail.referenceapplicationcode : 'NA' }}</label>
				</div>
			</div>
		</div>
		<label class="col-sm-12">Location Details
			<hr>
		</label>
		<div class="form-group">
			<div class="row">
				<div class="col">
					<label class="col-sm-12"><small>Address</small></label> <label
						class="col-sm-12">{{basicDetail.address}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>City/Town/Village</small></label> <label
						class="col-sm-12">{{basicDetail.citytown}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>Ward/Block/Sub-district</small></label>
					<label class="col-sm-12">{{basicDetail.officelocationname}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>Pincode</small></label> <label
						class="col-sm-12">{{basicDetail.pincode}}</label>
				</div>
			</div>
		</div>
		<label class="col-sm-12">Plot Details
			<hr>
		</label>
		<div class="form-group">
			<div class="row ">
				<div class="col">
					<label class="col-sm-12"><small>Plot Area</small></label> <label
						class="col-sm-12">{{basicDetail.plotarea}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>Plot No./Khata No.</small></label>
					<label class="col-sm-12">{{(basicDetail.plotidentifier1 !=
						null && basicDetail.plotidentifier1 !=
						'')?basicDetail.plotidentifier1: 'NA'}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>Holding No.</small></label> <label
						class="col-sm-12">{{(basicDetail.holdingno != null &&
						basicDetail.holdingno != '')?basicDetail.holdingno: 'NA'}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>Land Registration
							No.</small></label> <label class="col-sm-12">{{(basicDetail.landregistrationno
						!= null && basicDetail.landregistrationno !=
						'')?basicDetail.landregistrationno: 'NA'}}</label>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label class="col-sm-12"><small>Land Registration
							Details</small></label> <label class="col-sm-12">{{(basicDetail.landregistrationdetails
						!= null && basicDetail.landregistrationdetails !=
						'')?basicDetail.landregistrationdetails: 'NA'}}</label>
				</div>
			</div>
		</div>
	</div>
</div>
