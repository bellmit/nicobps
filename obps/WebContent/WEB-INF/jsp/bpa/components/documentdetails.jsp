<!-- @author Decent Khongstia -->
<div class="card mb-4">
	<div class="card-body">
		<div class="card-title h5">Document Details</div>
		<div class="form-group" ng-repeat="D in DocumentDetails">
			<div class="card bg-light text-dark">
		   		<div class="card-body">
		   			<div class="row">
		   				<label class="col"><small>File</small></label>
		   				<label class="col"><small>Uploaded Date</small></label>
		   				<label class="col"><small></small></label>
		   			</div>
		   			<div class="row">
		   				<label class="col">{{D.enclosurename}}</label>
		   				<label class="col">{{D.uploaddate}}</label>
		   				<div class="col">
		   					<button type="button" class="btn btn-sm btn-warning text-capitalize" data-toggle="modal" data-target="#fileModal" 
		   					ng-click="viewFile(1, D.appenclosurecode)">View File</button>
		   				</div>
		   			</div>
		   		</div>
	 		</div>
		</div>
	</div>
</div>