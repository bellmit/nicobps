<div class="card mb-4" ng-if="Enclosures != null && Enclosures.length > 0">
	<div class="card-header">Documents</div>
	<div class="card-body">
		<div class="form-group row"
			ng-repeat="R in BPAEnclosures track by $index">
			<div class="col-sm-7" ng-hide="R.flag === true">
				<select class="form-control custom-form-control"
					name="officelocationcode" ng-model="enclosurecode"
					ng-options="O.enclosurecode as (O.enclosurename) for O in Enclosures| filter: selected === false"
					ng-change="setLabel($index, enclosurecode)" required>
					<option selected="selected" disabled="disabled" value="">Select
						Options</option>
				</select> 
				<label style="color: red" ng-if="R.error === true" )>{{R.errormsg}}</label>
			</div>
			<label class="col-sm-7" ng-show="R.flag === true">{{($index+1)+": "+R.name}}<span class="fa fa-asterisk"></span>
			</label>
			<div class="col-sm-5">
				<div class="row mb-5">
					<div class="btn btn-outline-primary btn-sm float-left">
						<input type="file" name="report_{{$index}}" ng-model="R.file"
							file-model="R.file" ng-change="setLabel($index, null, R.file)"
							required> <span class="col" style="color: red"></span> <span
							class="col" style="color: red" ng-if="bpa.error[$index]">Required</span>
					</div>
					<div class="plus"
						ng-if="BPAEnclosures.length >= 1 && BPAEnclosures.length == $index+1 && ($index+1 ) < Enclosures.length ">
						<i class="fa fa-plus" title="Add more file"
							ng-click="filterEnclosure(1, enclosurecode)"></i>
					</div>
					<div class="minus"
						ng-if="BPAEnclosures.length >= 2 && BPAEnclosures.length == $index+1">
						<i class="fa fa-minus" title="Remove file"
							ng-click="filterEnclosure(2, enclosurecode)"></i>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>