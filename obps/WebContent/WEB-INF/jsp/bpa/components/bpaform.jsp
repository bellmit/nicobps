<!-- @author Decent Khongstia -->
<div class="card">
	<div class="card-body">
		<h5 class="card-title">Basic Details</h5>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<label class="col-sm-12">Building Plan Scrutiny Number<span class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control" name="edcrnumber"
							ng-model="BPA.edcrnumber" value="VALUE" readonly required/>
						<div class="col" ng-if="bpaform.edcrnumber.$touched || bpaform.edcrnumber.$dirty" style="color:red">
							<span ng-show="bpaform.edcrnumber.$error.required">Required</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="form-group">
					<label class="col">Occupancy<span class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control" name="occupancy"
							ng-model="occupancy.type" readonly required/>
						<div class="col" ng-if="bpaform.occupancy.$touched || bpaform.occupancy.$dirty" style="color:red">
							<span ng-show="bpaform.occupancy.$error.required">Required</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<label class="col-sm-12">Plot Area<span class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control" name="plotarea"
							ng-model="planInfo.plotArea" value="VALUE" readonly required/>
						<div class="col" ng-if="bpaform.plotarea.$touched || bpaform.plotarea.$dirty" style="color:red">
							<span ng-show="bpaform.plotarea.$error.required">Required</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="form-group">
					<label class="col">Uploaded By</label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control"
							ng-model="planInfo.applicantName" readonly />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<br />
<div class="card">
	<div class="card-body">
		<h5 class="card-title">Location Details</h5>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<label class="col-sm-12">Addressline1<span class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control" name="plotaddressline1"
							ng-model="BPA.plotaddressline1" maxlength="99" pattern-address required/>
						<div class="col" ng-if="bpaform.plotaddressline1.$touched || bpaform.plotaddressline1.$dirty" style="color:red">
							<span ng-show="bpaform.plotaddressline1.$error.required">Address Required</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="form-group">
					<label class="col">Addressline2</label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control" name="plotaddressline2"
							ng-model="BPA.plotaddressline2" maxlength="99" pattern-address/>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<label class="col-sm-12">Town/Village<span class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control" name="plottownvillage"
							ng-model="BPA.plotvillagetown" maxlength="99" pattern-alpha required/>
						<div class="col" ng-if="bpaform.plottownvillage.$touched || bpaform.plottownvillage.$dirty" style="color:red">
							<span ng-show="bpaform.plottownvillage.$error.required">Required</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="form-group">
					<label class="col">Pincode<span class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control" name="plotpincode"
							ng-model="BPA.plotpincode" maxlength="6" pattern-number pattern-pincode required>
<!-- 							ng-model="BPA.plotpincode" maxlength="6" pattern-number required> -->
						<div class="col" ng-if="bpaform.plotpincode.$touched || bpaform.plotpincode.$dirty" style="color:red">
							<span ng-show="bpaform.plotpincode.$error.required">Required<br/></span>
							<span ng-show="bpaform.plotpincode.$error.invalid">Invalid Pincode<br/></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<label class="col-sm-12">GIS Coordinates</label>
					<div class="col-sm-12">
						<div class="input-group">
							<input type="text" class="form-control custom-form-control" name="plotgiscoordinates"
								ng-model="BPA.plotgiscoordinates" maxlength="99" readonly 
								data-toggle="modal" data-target="#gmapModal"/>
<!-- 								ng-mousedown="toggleGmapModal()"  -->
							<div class="input-group-append">
								<button type="button" class="btn btn-outline-default" data-toggle="modal" data-target="#gmapModal">
									<i class="fa fa-location"></i>
								</button>
							</div>
						</div>
						<div class="col" ng-if="bpaform.plotgiscoordinates.$touched" style="color:red">
<!-- 							<span ng-show="bpaform.plotgiscoordinates.$error.required">Required</span> -->
						</div>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="form-group">
					<label class="col">Ward/Block/Sub-District<span class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<select class="form-control custom-form-control" name="officelocationcode"
							ng-model="BPA.officelocationcode"
							ng-options="O.key as (O.value) for O in Officelocations"
							required>
							<option selected="selected" disabled="disabled" value="">Select Options</option>
						</select>
						<div class="col" ng-if="bpaform.officelocationcode.$touched" style="color:red">
							<span ng-show="bpaform.officelocationcode.$error.required">Required</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<br>
<div class="card">
	<div class="card-body">
		<h5 class="card-title">Plot Details</h5>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<label class="col">Plot No./Khata No.</label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control" name="plotno"
							ng-model="BPA.plotidentifier1" maxlength="10" pattern-alpha-numeric/>
						<div class="col" ng-if="bpaform.plotno.$touched" style="color:red">
<!-- 							<span ng-show="bpaform.plotno.$error.required">Required</span> -->
						</div>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="form-group">
					<label class="col-sm-12">Holding No./House Identification
						No.</label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control" name="holdingno"
							ng-model="BPA.holdingno" maxlength="5" pattern-alpha-numeric/>
						<div class="col" ng-if="bpaform.holdingno.$touched" style="color:red">
<!-- 							<span ng-show="bpaform.holdingno.$error.required">Required</span> -->
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<label class="col">Land Registration No</label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control" name="landregistrationno"
							ng-model="BPA.landregistrationno" maxlength="20" pattern-alpha-numeric/>
						<div class="col" ng-if="bpaform.landregistrationno.$touched" style="color:red">
<!-- 							<span ng-show="bpaform.landregistrationno.$error.required">Required</span> -->
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<label class="col-sm-12">Office where land is registered</label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control" name="landregdetails"
							ng-model="BPA.landregistrationdetails" maxlength="99" pattern-alpha-numeric/>
						<div class="col" ng-if="bpaform.landregdetails.$touched" style="color:red">
<!-- 							<span ng-show="bpaform.landregdetails.$error.required">Required</span> -->
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</div>
<br />
<div class="card">
	<div class="card-body">
		<h5 class="card-title">Owner Details</h5>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<label class="col-sm-12">Owner Type<span class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<select class="form-control custom-form-control" name="ownershiptypecode"
							ng-model="BPA.ownershiptypecode"
							ng-options="O.key as O.value for O in Ownertypes"
							required>
							<option selected readonly value="">Select Owner Type</option>
						</select>
						<div class="col" ng-if="bpaform.ownershiptypecode.$touched" style="color:red">
							<span ng-show="bpaform.ownershiptypecode.$error.required">Required</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="form-group">
					<label class="col">Type of Owner - Subtype<span class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<select class="form-control custom-form-control" name="ownershipsubtype"
							ng-model="BPA.ownershipsubtype"
							ng-options="value as value for (key, value) in OwnerSubtypes"
							ng-change="addOwner()"
							required>
							<option selected readonly value="">Select Owner Subtype</option>
						</select>
						<div class="col" ng-if="bpaform.ownershipsubtype.$touched" style="color:red">
							<span ng-show="bpaform.ownershipsubtype.$error.required">Required</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="card-body"
			style="border: 0.5px solid rgba(0, 0, 0, 0.125)">
			<div ng-repeat="OD in BPA.ownerdetails">
				<div class="row">
					<h5 class="card-title">Owner Information: {{$index+1}}</h5>
					<hr/>
					<div class="minus" ng-if="BPA.ownerdetails.length > 2 && BPA.ownerdetails.length == $index+1"><i class="fa fa-minus" title="Remove Owner" ng-click="removeOwner()"></i></div>
					<div class="plus" ng-if="BPA.ownerdetails.length > 1 && BPA.ownerdetails.length == $index+1"><i class="fa fa-plus" title="Add More Owner" ng-click="addOwner()"></i></div>
				</div>
				<div class="row">
					<div class="col">
						<div class="form-group">
							<label class="col-sm-12">Salutation<span class="fa fa-asterisk"></span></label>
							<div class="col-sm-12">
								<select class="form-control custom-form-control" name="salutationcode{{$index}}"
									ng-model="OD.salutationcode"
									ng-options="S.key as S.value for S in Salutations"
									required>
									<option selected readonly value="">Select Salutation</option>
								</select>
								<div class="col" ng-if="bpaform['salutationcode'+($index)].$touched" style="color:red">
									<span ng-show="bpaform['salutationcode'+($index)].$error.required">Required</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col">
						<div class="form-group">
							<label class="col">Owner Name<span class="fa fa-asterisk"></span></label>
							<div class="col-sm-12">
								<input type="text" class="form-control custom-form-control" name="ownername{{$index}}"
									ng-model="OD.ownername" maxlength="50" pattern-alpha required>
								<div class="col" ng-if="bpaform['ownername'+($index)].$touched" style="color:red">
									<span ng-show="bpaform['ownername'+($index)].$error.required">Required</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<div class="form-group">
							<label class="col-sm-12">Mobile No.<span class="fa fa-asterisk"></span></label>
							<div class="col-sm-12">
								<input type="text" class="form-control custom-form-control" name="mobileno{{$index}}"
									ng-model="OD.mobileno" maxlength="10" pattern-number pattern-mobile required> 
								<div class="col" ng-if="bpaform['mobileno'+($index)].$touched || bpaform['mobileno'+($index)].$dirty" style="color:red">
									<span ng-show="bpaform['mobileno'+($index)].$error.required">Required<br></span>
									<span ng-show="bpaform['mobileno'+($index)].$error.invalid">Invalid mobile no.</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col">
						<div class="form-group">
							<label class="col">Email</label>
							<div class="col-sm-12">
								<input type="text" class="form-control custom-form-control" name="emailid{{$index}}"
									ng-model="OD.emailid" maxlength="99" pattern-email>
								<div class="col" ng-if="bpaform['emailid'+($index)].$touched || bpaform['emailid'+($index)].$dirty" style="color:red">
<!-- 									<span ng-show="bpaform['emailid'+($index)].$error.required">Required<br></span> -->
									<span ng-show="bpaform['emailid'+($index)].$error.invalid">Invalid emailid</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<div class="form-group">
							<label class="col-sm-12">Father's/Mother's/Guardian Name<span class="fa fa-asterisk"></span></label>
							<div class="col-sm-12">
								<input type="text" class="form-control custom-form-control"  name="relationname{{$index}}"
									ng-model="OD.relationname" maxlength="50" pattern-alpha required>
								<div class="col" ng-if="bpaform['relationname'+($index)].$touched" style="color:red">
									<span ng-show="bpaform['relationname'+($index)].$error.required">Required</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col">
						<div class="form-group">
							<label class="col">Relationship<span class="fa fa-asterisk"></span></label>
							<div class="col-sm-12">
								<div class="row">
									<div class="form-check" style="margin: 0 1em"
										ng-repeat="R in Relationshiptypes">
										<input class="form-check-input" type="radio"
											name="relationshiptype{{$parent.$index}}" id="relationshiptype{{$parent.$index}}{{$index}}"
											ng-model="OD.relationshiptypecode" value="{{R.key}}"
											ng-value="R.key" required> 
										<label class="form-check-label" for="relationshiptype{{$parent.$index}}{{$index}}">{{R.value}}</label>
									</div>
								</div>
							</div>
							<div class="col" ng-if="bpaform['relationshiptype'+($index)].$touched" style="color:red">
								<span ng-show="bpaform['relationshiptype'+($index)].$error.required">Required</span>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
							<label class="col-sm-12">Residential Address<span class="fa fa-asterisk"></span></label>
							<div class="col-sm-12">
								<input type="text" class="form-control custom-form-control"  name="address{{$index}}"
									ng-model="OD.address" maxlength="99" pattern-address required>
								<div class="col" ng-if="bpaform['address'+($index)].$touched" style="color:red">
									<span ng-show="bpaform['address'+($index)].$error.required">Required</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<br/>
<div class="card">
	<div class="card-body">
		<div style="text-align:center; margin: auto;">
			<input type="button" class="btn btn-primary" value="Save" ng-click="save()">
		</div>
		<div class="col" ng-if="serverMsg">
   			<br/>
   			<span class="alert alert-danger" style="display: block" ng-if="serverResponseError">{{serverMsg}}</span>
   			<span class="alert alert-danger" style="display: block" ng-if="serverResponseFail">{{serverMsg}}</span>
   			<span class="alert alert-info" style="display: block" ng-if="serverResponseInfo">{{serverMsg}}</span>
   			<span class="alert alert-success" style="display: block" ng-if="serverResponseSuccess">{{serverMsg}}</span>
   		</div>
	</div>
</div>
<br/>