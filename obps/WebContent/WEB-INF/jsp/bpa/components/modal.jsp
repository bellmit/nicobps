<!-- @author Decent Khongstia -->
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
      	<div class="col" ng-if="Users != null && Users.length > 0">
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
      			<textarea class="form-control" style="width: 100%; height: 100px; min-height:100px; max-height: 250px;" ng-model="modal.remarks"></textarea>
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