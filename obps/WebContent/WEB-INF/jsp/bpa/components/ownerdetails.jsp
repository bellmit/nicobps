<!-- @author Decent Khongstia -->
<div class="card mb-4">
	<div class="card-body">
		<div class="card-title h5">Owner Information</div>
		<div class="form-group">
			<div class="row">
				<div class="col">
					<label class="col-sm-12"><small>Ownership type</small></label> <label
						class="col-sm-12">{{basicDetail.ownershiptypename}}</label>
				</div>
				<div class="col">
					<label class="col-sm-12"><small>Ownership Subtype</small></label> <label
						class="col-sm-12">{{basicDetail.ownershipsubtype}}</label>
				</div>
			</div>
		</div>
		<div class="card pt-2" ng-repeat="O in OwnerDetails">
			<div class="col h6" ng-if="OwnerDetails.length > 1">
				{{'Owner '+($index+1)+':'}}
				<hr style="padding: 0; margin: 0 0 0.6rem 0;" />
			</div>
			<div class="row">
				<div class="col-sm-3">
					<label class="col-sm-12"><small>Owner Name</small></label> <label
						class="col-sm-12">{{O.ownername}}</label>
				</div>
				<div class="col-sm-3">
					<label class="col-sm-12"><small>Mobile No.</small></label> <label
						class="col-sm-12">{{O.mobileno}}</label>
				</div>
				<div class="col-sm-3">
					<label class="col-sm-12"><small>Email</small></label> <label
						class="col-sm-12">{{(O.emailid != null && O.emailid !=
						'')?O.emailid: 'NA'}}</label>
				</div>
				<div class="col-sm-3">
					<label class="col-sm-12"><small>Guardian's Name</small></label> <label
						class="col-sm-12">{{O.relationname}}</label>
				</div>
				<div class="col-sm-3">
					<label class="col-sm-12"><small>Relationship</small>
					</l	abel> <label class="col-sm-12">{{O.relationshiptypename}}</label>
				</div>
				<div class="col-sm-3">
					<label class="col-sm-12"><small>Correspondence
							Address</small></label> <label class="col-sm-12">{{O.address}}</label>
				</div>
				<div class="col-sm-3">
					<label class="col-sm-12"><small>Permanent Address</small></label> <label
						class="col-sm-12">{{O.peraddress}}</label>
				</div>
			</div>
		</div>
	</div>
</div>
