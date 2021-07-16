<!-- @author Decent Khongstia -->
<!-- <div class="card mb-4">
	<div class="card-body">
		<div class="card-title h5">Site Report Details</div>
		<div class="form-group" ng-repeat="D in SiteReportDetails">
			<div class="card bg-light text-dark">
		   		<div class="card-body">
		   			<div class="row">
		   				<label class="col"><small>File</small></label>
		   				<label class="col"><small>Uploaded Date</small></label>
		   				<label class="col"><small></small></label>
		   			</div>
		   			<div class="row">
		   				<label class="col">{{(D.enclosurename == null || D.enclosurename == "")?'Site Report': D.enclosurename}}</label>
		   				<label class="col">{{D.entrydate}}</label>
		   				<div class="col">
		   					<button type="button" class="btn btn-sm btn-warning text-capitalize" data-toggle="modal" data-target="#fileModal" ng-click="viewFile(2, D.appenclosurecode)">View File</button>
		   				</div>
		   			</div>
		   		</div>
	 		</div>
		</div>
	</div>
</div> -->
<div class="card mb-4">
	<div class="card-body">
		<div class="card-title h5">Site Inspection Questionnaires</div>
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-1" style="text-align: center;">Slno</label>
				<label class="col">Questionnaire</label>
				<label class="col-sm-1">Response</label>
				<label class="col">Remarks</label>
			</div>
			<div class="form-group row"
				ng-repeat="Q in Questionnaires | filter: {aqcode: '!!'} ">
				<label class="col-sm-1" style="text-align: center;">{{($index+1)}}</label> 
				<label class="col">{{Q.questiondescription}}</label>
				<label class="col-sm-1">{{Q.response}}</label>
				<label class="col">{{(Q.remarks != null && Q.remarks != '')?Q.remarks:'NA'}}</label>
			</div>
		</div>
	</div>
</div>