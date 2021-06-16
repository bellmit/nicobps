<!DOCTYPE html>
<!-- @author Decent Khongstia -->
<div class="card mb-4">
	<div class="card-body">
		<div class="card-title h5">Document Details</div>
		<div class="form-group" ng-repeat="D in DocumentDetails">
			<div class="card bg-light text-dark">
		   		<div class="card-body">
		   			<div class="row">
		   				<label class="col">File</label>
		   				<label class="col">Uploaded Date</label>
		   				<label class="col"></label>
		   			</div>
		   			<div class="row">
		   				<label class="col">{{D.enclosurename}}</label>
		   				<label class="col">{{D.uploaddate}}</label>
		   				<div class="col">
		   					<button type="button" class="btn btn-warning text-capitalize" ng-click="viewFile(D.appenclosurecode)">View File</button>
		   				</div>
		   			</div>
		   		</div>
	 		</div>
		</div>
	</div>
</div>