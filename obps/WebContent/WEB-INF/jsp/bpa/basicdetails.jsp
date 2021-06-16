<!-- @author Decent Khongstia -->
<!DOCTYPE html>
<div class="card mb-4">
	<div class="card-body">
		<div class="card-title h5">Basic Detail</div>
		<label class="col-sm-12">Basic Details<hr></label>
		<div class="form-group">
			<div class="row">
				<div class="col">
					<label class="col-sm-12"><small>Application code</small></label>
					<label class="col-sm-12">{{basicDetail.applicationcode}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>Building Plan Scrutiny Number</small></label>
					<label class="col-sm-12">{{basicDetail.edcrnumber}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>Occupancy</small></label>
					<label class="col-sm-12">{{basicDetail.occupancy}}</label>
				</div>
			</div>	
		</div>
		<label class="col-sm-12">Location Details<hr></label>
		<div class="form-group">
			<div class="row">
				<div class="col">
					<label class="col-sm-12"><small>Address</small></label>
					<label class="col-sm-12">{{basicDetail.address}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>City/Town/Village</small></label>
					<label class="col-sm-12">{{basicDetail.citytown}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>Ward/Block/Sub-district</small></label>
					<label class="col-sm-12">{{basicDetail.officelocationname}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>Pincode</small></label>
					<label class="col-sm-12">{{basicDetail.pincode}}</label>
				</div>
			</div>
		</div>
		<label class="col-sm-12">Plot Details<hr></label>
		<div class="form-group">
			<div class="row ">
				<div class="col">
					<label class="col-sm-12"><small>Plot Area</small></label>
					<label class="col-sm-12">{{basicDetail.plotarea}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>Plot No./Khata No.</small></label>
					<label class="col-sm-12">{{basicDetail.plotidentifier1}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>Holding No.</small></label>
					<label class="col-sm-12">{{basicDetail.holdingno}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>Land Registration No.</small></label>
					<label class="col-sm-12">{{basicDetail.landregistrationno}}</label>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label class="col-sm-12"><small>Land Registration Details</small></label>
					<label class="col-sm-12">{{basicDetail.landregistrationdetails}}</label>
				</div>
			</div>
		</div>
	</div>
</div>
