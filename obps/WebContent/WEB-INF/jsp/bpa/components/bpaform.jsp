<!-- @author Decent Khongstia -->
<style type="text/css">
hr {
	margin: 0;
}
</style>
<div class="card">
	<div class="card-body">
		<h5 class="card-title">Basic Details</h5>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<label class="col-sm-12">Building Plan Scrutiny Number<span
						class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control"
							name="edcrnumber" ng-model="BPA.edcrnumber" value="VALUE"
							readonly required />
						<div class="col"
							ng-if="bpaform.edcrnumber.$touched || bpaform.edcrnumber.$dirty"
							style="color: red">
							<span ng-show="bpaform.edcrnumber.$error.required">Required</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="form-group">
					<label class="col">Occupancy<span class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control"
							name="occupancy" ng-model="occupancy.type" readonly required />
						<div class="col"
							ng-if="bpaform.occupancy.$touched || bpaform.occupancy.$dirty"
							style="color: red">
							<span ng-show="bpaform.occupancy.$error.required">Required</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<label class="col-sm-12">Plot Area<span
						class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control"
							name="plotarea" ng-model="planInfo.plotArea" value="VALUE"
							readonly required />
						<div class="col"
							ng-if="bpaform.plotarea.$touched || bpaform.plotarea.$dirty"
							style="color: red">
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


		<div class="row">
			<div class="col">
				<div class="form-group">
					<!-- 					<label class="col-sm-12">Name of Engineer on Record </label> -->
					<!-- 	<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control" name="nameofengineer"
							ng-model="BPA.additionalinfo.nameofengineer" value="VALUE" required/>						
					</div> -->
					<!-- 					modified -->
					<div class="col">
						<div class="form-group">
							<label class="col-sm-12 pl-0">Name of Engineer on Record <span
								class="fa fa-asterisk"></span></label>
							<div class="col-sm-12 pl-0">
								<select class="form-control custom-form-control"
									name="nameofengineer"
									ng-model="BPA.additionalinfo.nameofengineer"
									ng-options="E.key as E.value for E in Engineers" required>
									<option selected readonly value="">Select Engineer</option>
								</select>
								<div class="col" ng-if="bpaform.nameofengineer.$touched"
									style="color: red">
									<span ng-show="bpaform.nameofengineer.$error.required">Required</span>
								</div>
							</div>
						</div>
					</div>
					<!-- 			end		modified -->
				</div>
			</div>
			<div class="col"></div>
		</div>

	</div>
</div>
<br />
<div class="card">
	<div class="card-body">
		<h5 class="card-title">Site Location Details</h5>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<label class="col-sm-12">Addressline1<span
						class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control"
							name="plotaddressline1" ng-model="BPA.plotaddressline1"
							maxlength="99" pattern-address required />
						<div class="col"
							ng-if="bpaform.plotaddressline1.$touched || bpaform.plotaddressline1.$dirty"
							style="color: red">
							<span ng-show="bpaform.plotaddressline1.$error.required">Address
								Required</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="form-group">
					<label class="col">Addressline2</label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control"
							name="plotaddressline2" ng-model="BPA.plotaddressline2"
							maxlength="99" pattern-address />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<label class="col-sm-12">Town/Village<span
						class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control"
							name="plottownvillage" ng-model="BPA.plotvillagetown"
							maxlength="99" pattern-alpha required />
						<div class="col"
							ng-if="bpaform.plottownvillage.$touched || bpaform.plottownvillage.$dirty"
							style="color: red">
							<span ng-show="bpaform.plottownvillage.$error.required">Required</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="form-group">
					<label class="col">Pincode<span class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control"
							name="plotpincode" ng-model="BPA.plotpincode" maxlength="6"
							pattern-number pattern-pincode required>
						<!-- 							ng-model="BPA.plotpincode" maxlength="6" pattern-number required> -->
						<div class="col"
							ng-if="bpaform.plotpincode.$touched || bpaform.plotpincode.$dirty"
							style="color: red">
							<span ng-show="bpaform.plotpincode.$error.required">Required<br /></span>
							<span ng-show="bpaform.plotpincode.$error.invalid">Invalid
								Pincode<br />
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			
<!-- 			<div class="col"> -->
<!-- 				<div class="form-group"> -->
<!-- 					<label class="col-sm-12">GIS Coordinates</label> -->
<!-- 					<div class="col-sm-12"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<input type="text" class="form-control custom-form-control" -->
<!-- 								name="plotgiscoordinates" ng-model="BPA.plotgiscoordinates" -->
<!-- 								maxlength="99" readonly data-toggle="modal" -->
<!-- 								data-target="#gmapModal" /> -->
<!-- 															ng-mousedown="toggleGmapModal()"  -->
<!-- 							<div class="input-group-append"> -->
<!-- 								<button type="button" class="btn btn-outline-default" -->
<!-- 									data-toggle="modal" data-target="#gmapModal"> -->
<!-- 									<i class="fa fa-location"></i> -->
<!-- 								</button> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="col" ng-if="bpaform.plotgiscoordinates.$touched" -->
<!-- 							style="color: red"> -->
<!-- 														<span ng-show="bpaform.plotgiscoordinates.$error.required">Required</span> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
			 
			<div class="col">
				<div class="form-group">
					<label class="col">Ward/Block/Sub-District<span
						class="fa fa-asterisk"></span></label>
					<div class="col-sm-6">
						<select class="form-control custom-form-control"
							name="officelocationcode" ng-model="BPA.officelocationcode"
							ng-options="O.key as (O.value) for O in Officelocations" required>
							<option selected="selected" disabled="disabled" value="">Select
								Options</option>
						</select>
						<div class="col" ng-if="bpaform.officelocationcode.$touched"
							style="color: red">
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
						<input type="text" class="form-control custom-form-control"
							name="plotno" ng-model="BPA.plotidentifier1" maxlength="10"
							pattern-alpha-numeric-slash-dash-brackets-period />
						<div class="col" ng-if="bpaform.plotno.$touched"
							style="color: red">
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
						<input type="text" class="form-control custom-form-control"
							name="holdingno" ng-model="BPA.holdingno" maxlength="5"
							pattern-alpha-numeric-slash-dash-brackets-period />
						<div class="col" ng-if="bpaform.holdingno.$touched"
							style="color: red">
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
						<input type="text" class="form-control custom-form-control"
							name="landregistrationno" ng-model="BPA.landregistrationno"
							maxlength="20" pattern-alpha-numeric />
						<div class="col" ng-if="bpaform.landregistrationno.$touched"
							style="color: red">
							<!-- 							<span ng-show="bpaform.landregistrationno.$error.required">Required</span> -->
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<label class="col-sm-12">Office where land is registered</label>
					<div class="col-sm-12">
						<input type="text" class="form-control custom-form-control"
							name="landregdetails" ng-model="BPA.landregistrationdetails"
							maxlength="99" pattern-alpha-numeric />
						<div class="col" ng-if="bpaform.landregdetails.$touched"
							style="color: red">
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
					<label class="col-sm-12">Owner Type<span
						class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<select class="form-control custom-form-control"
							name="ownershiptypecode" ng-model="BPA.ownershiptypecode"
							ng-options="O.key as O.value for O in Ownertypes" required>
							<option selected readonly value="">Select Owner Type</option>
						</select>
						<div class="col" ng-if="bpaform.ownershiptypecode.$touched"
							style="color: red">
							<span ng-show="bpaform.ownershiptypecode.$error.required">Required</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="form-group">
					<label class="col">Type of Owner - Subtype<span
						class="fa fa-asterisk"></span></label>
					<div class="col-sm-12">
						<select class="form-control custom-form-control"
							name="ownershipsubtype" ng-model="BPA.ownershipsubtype"
							ng-options="value as value for (key, value) in OwnerSubtypes"
							ng-change="addOwner()" required>
							<option selected readonly value="">Select Owner Subtype</option>
						</select>
						<div class="col" ng-if="bpaform.ownershipsubtype.$touched"
							style="color: red">
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
					<hr />
					<div class="minus"
						ng-if="BPA.ownerdetails.length > 2 && BPA.ownerdetails.length == $index+1">
						<i class="fa fa-minus" title="Remove Owner"
							ng-click="removeOwner()"></i>
					</div>
					<div class="plus"
						ng-if="BPA.ownerdetails.length > 1 && BPA.ownerdetails.length == $index+1">
						<i class="fa fa-plus" title="Add More Owner" ng-click="addOwner()"></i>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<div class="form-group">
							<label class="col-sm-12">Salutation<span
								class="fa fa-asterisk"></span></label>
							<div class="col-sm-12">
								<select class="form-control custom-form-control"
									name="salutationcode{{$index}}" ng-model="OD.salutationcode"
									ng-options="S.key as S.value for S in Salutations" required>
									<option selected readonly value="">Select Salutation</option>
								</select>
								<div class="col"
									ng-if="bpaform['salutationcode'+($index)].$touched"
									style="color: red">
									<span
										ng-show="bpaform['salutationcode'+($index)].$error.required">Required</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col">
						<div class="form-group">
							<label class="col">Owner Name<span class="fa fa-asterisk"></span></label>
							<div class="col-sm-12">
								<input type="text" class="form-control custom-form-control"
									name="ownername{{$index}}" ng-model="OD.ownername"
									maxlength="50" pattern-alpha required>
								<div class="col" ng-if="bpaform['ownername'+($index)].$touched"
									style="color: red">
									<span ng-show="bpaform['ownername'+($index)].$error.required">Required</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<div class="form-group">
							<label class="col-sm-12">Mobile No.<span
								class="fa fa-asterisk"></span></label>
							<div class="col-sm-12">
								<input type="text" class="form-control custom-form-control"
									name="mobileno{{$index}}" ng-model="OD.mobileno" maxlength="10"
									pattern-number pattern-mobile required>
								<div class="col"
									ng-if="bpaform['mobileno'+($index)].$touched || bpaform['mobileno'+($index)].$dirty"
									style="color: red">
									<span ng-show="bpaform['mobileno'+($index)].$error.required">Required<br></span>
									<span ng-show="bpaform['mobileno'+($index)].$error.invalid">Invalid
										mobile no.</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col">
						<div class="form-group">
							<label class="col">Email</label>
							<div class="col-sm-12">
								<input type="text" class="form-control custom-form-control"
									name="emailid{{$index}}" ng-model="OD.emailid" maxlength="99"
									pattern-email>
								<div class="col"
									ng-if="bpaform['emailid'+($index)].$touched || bpaform['emailid'+($index)].$dirty"
									style="color: red">
									<!-- 									<span ng-show="bpaform['emailid'+($index)].$error.required">Required<br></span> -->
									<span ng-show="bpaform['emailid'+($index)].$error.invalid">Invalid
										emailid</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<div class="form-group">
							<label class="col-sm-12">Father's/Mother's/Guardian Name<span
								class="fa fa-asterisk"></span></label>
							<div class="col-sm-12">
								<input type="text" class="form-control custom-form-control"
									name="relationname{{$index}}" ng-model="OD.relationname"
									maxlength="50" pattern-alpha required>
								<div class="col"
									ng-if="bpaform['relationname'+($index)].$touched"
									style="color: red">
									<span
										ng-show="bpaform['relationname'+($index)].$error.required">Required</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col">
						<div class="form-group">
							<label class="col">Relationship<span
								class="fa fa-asterisk"></span></label>
							<div class="col-sm-12">
								<div class="row">
									<div class="form-check" style="margin: 0 1em"
										ng-repeat="R in Relationshiptypes">
										<input class="form-check-input" type="radio"
											name="relationshiptype{{$parent.$index}}"
											id="relationshiptype{{$parent.$index}}{{$index}}"
											ng-model="OD.relationshiptypecode" value="{{R.key}}"
											ng-value="R.key" required> <label
											class="form-check-label"
											for="relationshiptype{{$parent.$index}}{{$index}}">{{R.value}}</label>
									</div>
								</div>
							</div>
							<div class="col"
								ng-if="bpaform['relationshiptype'+($index)].$touched"
								style="color: red">
								<span
									ng-show="bpaform['relationshiptype'+($index)].$error.required">Required</span>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<label class="col-sm-12">Present Address<span
							class="fa fa-asterisk"></span>
							<hr></label>
						<div class="card custom-card-border-less">
							<div class="card-body">
								<div class="row">
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12">Addressline1<span
												class="fa fa-asterisk"></span></label>
											<div class="col-sm-12">
												<input type="text" class="form-control custom-form-control"
													name="preaddressline1{{$index}}"
													ng-model="OD.preaddressline1" maxlength="99"
													pattern-address required>
												<div class="col"
													ng-if="bpaform['preaddressline1'+($index)].$touched"
													style="color: red">
													<span
														ng-show="bpaform['preaddressline1'+($index)].$error.required">Required</span>
												</div>
											</div>
										</div>
									</div>
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12">Addressline2</label>
											<div class="col-sm-12">
												<input type="text" class="form-control custom-form-control"
													name="preaddressline2{{$index}}"
													ng-model="OD.preaddressline2" maxlength="99"
													pattern-address>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12">Town/Village<span
												class="fa fa-asterisk"></span></label>
											<div class="col-sm-12">
												<input type="text" class="form-control custom-form-control"
													name="pretownvillage{{$index}}"
													ng-model="OD.pretownvillage" maxlength="99" pattern-alpha
													required />
												<div class="col"
													ng-if="bpaform['pretownvillage'+($index)].$touched"
													style="color: red">
													<span
														ng-show="bpaform['pretownvillage'+($index)].$error.required">Required</span>
												</div>
											</div>
										</div>
									</div>
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12">State<span
												class="fa fa-asterisk"></span></label>
											<div class="col-sm-12">
												<select class="form-control custom-form-control"
													name="prestatecode{{$index}}" ng-model="OD.prestatecode"
													ng-options="O.key as (O.value) for O in States"
													ng-change="listDistricts(1, OD.prestatecode)" required>
													<option selected="selected" disabled="disabled" value="">Select
														State</option>
												</select>
												<div class="col"
													ng-if="bpaform['prestatecode'+($index)].$touched"
													style="color: red">
													<span
														ng-show="bpaform['prestatecode'+($index)].$error.required">Required</span>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12">District<span
												class="fa fa-asterisk"></span></label>
											<div class="col-sm-12">
												<select class="form-control custom-form-control"
													name="predistrictcode{{$index}}"
													ng-model="OD.predistrictcode"
													ng-options="O.key as (O.value) for O in PreDistricts"
													required>
													<option selected="selected" disabled="disabled" value="">Select
														District</option>
												</select>
												<div class="col"
													ng-if="bpaform['predistrictcode'+($index)].$touched"
													style="color: red">
													<span
														ng-show="bpaform['predistrictcode'+($index)].$error.required">Required</span>
												</div>
											</div>
										</div>
									</div>
									<div class="col">
										<div class="form-group">
											<label class="col">Pincode<span
												class="fa fa-asterisk"></span></label>
											<div class="col-sm-12">
												<input type="text" class="form-control custom-form-control"
													name="prepincode{{$index}}" ng-model="OD.prepincode"
													maxlength="6" pattern-number pattern-pincode required>
												<div class="col"
													ng-if="bpaform['prepincode'+($index)].$touched || bpaform['prepincode'+($index)].$dirty"
													style="color: red">
													<span
														ng-show="bpaform['prepincode'+($index)].$error.required">Required</span>
													<span
														ng-show="bpaform['prepincode'+($index)].$error.invalid">Invalid
														Pincode<br />
													</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col">
						<label class="col-sm-12">Permanent Address<span
							class="fa fa-asterisk"></span>
							<hr></label> <span class="col-sm-12 text-right"><input
							type="checkbox" id="sameAsPresent" name="isSameAddress{{$index}}"
							ng-model="isSameAddress_$index" ng-click="sameAddr($index)" )>
							<label for="sameAsPresent"> <small>same as
									present address</small></label></span>
						<div class="card custom-card-border-less">
							<div class="card-body">
								<div class="row">
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12">Addressline1<span
												class="fa fa-asterisk"></span></label>
											<div class="col-sm-12">
												<input type="text" class="form-control custom-form-control"
													name="peraddressline1{{$index}}"
													ng-disabled="isSameAddress_$index"
													ng-model="OD.peraddressline1" maxlength="99"
													pattern-address required>
												<div class="col"
													ng-if="bpaform['peraddressline1'+($index)].$touched"
													style="color: red">
													<span
														ng-show="bpaform['peraddressline1'+($index)].$error.required">Required</span>
												</div>
											</div>
										</div>
									</div>
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12">Addressline2</label>
											<div class="col-sm-12">
												<input type="text" class="form-control custom-form-control"
													name="peraddressline2{{$index}}"
													ng-disabled="isSameAddress_$index"
													ng-model="OD.peraddressline2" maxlength="99"
													pattern-address>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12">Town/Village<span
												class="fa fa-asterisk"></span></label>
											<div class="col-sm-12">
												<input type="text" class="form-control custom-form-control"
													name="pertownvillage{{$index}}"
													ng-disabled="isSameAddress_$index"
													ng-model="OD.pertownvillage" maxlength="99" pattern-alpha
													required />
												<div class="col"
													ng-if="bpaform['pertownvillage'+($index)].$touched"
													style="color: red">
													<span
														ng-show="bpaform['pertownvillage'+($index)].$error.required">Required</span>
												</div>
											</div>
										</div>
									</div>
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12">State<span
												class="fa fa-asterisk"></span></label>
											<div class="col-sm-12">
												<select class="form-control custom-form-control"
													name="perstatecode{{$index}}" ng-model="OD.perstatecode"
													ng-options="O.key as (O.value) for O in States"
													ng-change="listDistricts(2, OD.perstatecode)"
													ng-disabled="isSameAddress_$index" required>
													<option selected="selected" disabled="disabled" value="">Select
														State</option>
												</select>
												<div class="col"
													ng-if="bpaform['perstatecode'+($index)].$touched"
													style="color: red">
													<span
														ng-show="bpaform['perstatecode'+($index)].$error.required">Required</span>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<div class="form-group">
											<label class="col-sm-12">District<span
												class="fa fa-asterisk"></span></label>
											<div class="col-sm-12">
												<select class="form-control custom-form-control"
													name="perdistrictcode{{$index}}"
													ng-model="OD.perdistrictcode"
													ng-options="O.key as (O.value) for O in PerDistricts"
													ng-disabled="isSameAddress_$index" required>
													<option selected="selected" disabled="disabled" value="">Select
														District</option>
												</select>
												<div class="col"
													ng-if="bpaform['perdistrictcode'+($index)].$touched"
													style="color: red">
													<span
														ng-show="bpaform['perdistrictcode'+($index)].$error.required">Required</span>
												</div>
											</div>
										</div>
									</div>
									<div class="col">
										<div class="form-group">
											<label class="col">Pincode<span
												class="fa fa-asterisk"></span></label>
											<div class="col-sm-12">
												<input type="text" class="form-control custom-form-control"
													name="perpincode{{$index}}"
													ng-disabled="isSameAddress_$index" ng-model="OD.perpincode"
													maxlength="6" pattern-number pattern-pincode required>
												<div class="col"
													ng-if="bpaform['perpincode'+($index)].$touched || bpaform['perpincode'+($index)].$dirty"
													style="color: red">
													<span
														ng-show="bpaform['perpincode'+($index)].$error.required">Required</span>
													<span
														ng-show="bpaform['perpincode'+($index)].$error.invalid">Invalid
														Pincode<br />
													</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
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
		<h5 class="card-title">Payment</h5>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<div class="row">
						<label class="col-sm-6"> Do you want to avail Tatkal ?
							(Additional Charges : Rs 5 per square feet) <span
							class="fa fa-asterisk"></span>
						</label> <select class="form-control custom-form-control  col-sm-3"
							name="istatkal" id="istatkal" ng-model="BPA.istatkal" required>
							<option value="N" selected>NO</option>
							<option value="Y">YES</option>
						</select>
						<div class="col" ng-if="bpaform.istatkal.$touched"
							style="color: red">
							<span ng-show="bpaform.istatkal.$error.required">Required</span>
						</div>
					</div>
					<div class="col-sm-6 " id="addch"
						ng-show="BPA.istatkal == 'Y'">
						<table class="table">
							<thead>
								<tr>

								</tr>
							</thead>
							<tbody>

								<tr>

									<td>BuiltUpArea(sqm)</td>
									<td>-</td>
									<td>{{totalbuiltuparea}} sqm</td>
								</tr>
								<tr>

									<td>BuiltUpArea(sqft)</td>
									<td>-</td>
									<td>{{totalbuiltuparea_ft}} sqft</td>
								</tr>
								<tr class="table-secondary">

									<td>Additional Charges Per Square Feet</td>
									<td>-</td>
									<td>{{additionalcharges}}</td>
								</tr>
							</tbody>
						</table>
					</div>

				</div>
			</div>

		</div>

	</div>
</div>
<br />
<div class="card">
	<div class="card-body">
		<div style="text-align: center; margin: auto;">
			<input type="button" class="btn btn-primary" value="Save and Next"
				ng-click="save()">
		</div>
		<div class="col" ng-if="serverMsg">
			<br /> <span class="alert alert-danger" style="display: block"
				ng-if="serverResponseError">{{serverMsg}}</span> <span
				class="alert alert-danger" style="display: block"
				ng-if="serverResponseFail">{{serverMsg}}</span> <span
				class="alert alert-info" style="display: block"
				ng-if="serverResponseInfo">{{serverMsg}}</span> <span
				class="alert alert-success" style="display: block"
				ng-if="serverResponseSuccess">{{serverMsg}}</span>
		</div>
	</div>
</div>
<br />