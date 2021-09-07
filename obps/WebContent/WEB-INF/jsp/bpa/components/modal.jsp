<!-- @author Decent Khongstia -->
<div class="row">
	<div class="col" ng-if="serverMsg">
		<span class="alert alert-danger" style="display: block"
			ng-if="serverResponseError">{{serverMsg}}</span> <span
			class="alert alert-danger" style="display: block"
			ng-if="serverResponseFail">{{serverMsg}}</span> <span
			class="alert alert-info" style="display: block"
			ng-if="serverResponseInfo">{{serverMsg}}</span> <span
			class="alert alert-success" style="display: block"
			ng-if="serverResponseSuccess">{{serverMsg}}</span>
	</div>
</div>
 <div class="modal fade" id="commonModal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="commonModal" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="commonModalTitle">{{modal.title}}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<div class="col" ng-if="NextProcesses != null && NextProcesses.length > 1 && modal.action == 1">
   			<div class="col row">
      			<small class="col-sm-6">Next Process</small>
      			<small class="col-sm-6">Assigned To</small>
      			<hr>
   			</div>
      		<div ng-repeat="N in NextProcesses">
      			<div class="col row">
	      			<label class="col-sm-6">
	      				<input type="checkbox" ng-model="nextuser_N.toprocesscode" ng-click="listUsers(nextuser_N.toprocesscode, $index, N.toprocesscode)">
	      				{{N.processname}}
      				</label>
	      			<select class="col-sm-6 form-control custom-form-control" ng-model="modal.process[$index].tousercode"
      					ng-options="U.key as U.value for U in Users[N.toprocesscode]" ng-change="onc($index)" 
      					ng-disabled="!nextuser_N.toprocesscode">
						<option value="" selected disabled>Select User</option>
	      			</select>
      			</div>
      		</div>
      		<div class="col row"><br></div>
      	</div>
      	<div class="col" ng-if="Users != null && Users.length > 0 
      		&& (NextProcesses == null || NextProcesses.length <= 1) && modal.action == 1">
      		<label class="col">
      			Assignee Name<span class="fa fa-asterisk"></span>
      		</label>
      		<div class="col">
      			<select class="form-control custom-form-control"
      				ng-model="modal.usercode"
      				ng-options="U.key as U.value for U in Users">
					<option value="" selected disabled>Select User</option>
      			</select>
      		</div>
      	</div>
      	<div class="col">
      		<label class="col">
      			Remarks<span class="fa fa-asterisk"></span>
      		</label>
      		<div class="col">
      			<textarea class="form-control" style="width: 100%; height: 100px; min-height:100px; max-height: 250px;" 
      				ng-model="modal.remarks" pattern-address></textarea>
      		</div>
      	</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
        <modal-button></modal-button>
      </div>
    </div>
  </div>
 </div>