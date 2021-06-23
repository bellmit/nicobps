 <!-- @author Decent Khongstia  -->
<div class="card mb-4">
	<div class="card-body">
		<div class="card-title row">
			<label class="col-sm-6 h5">Task Status</label>
			<label class="col-sm-6 text-right"></label>
		</div>
		<div class="form-group row">
			<div class="col">
				<label class="col"><small>Date</small></label>
				<label class="col">{{taskStatus.taskdate}}</label>
			</div>
			<div class="col">
				<label class="col"><small>Updated By</small></label>	
				<label class="col">{{taskStatus.updatedby}}</label>
			</div>
			<div class="col">
				<label class="col"><small>Status</small></label>
				<label class="col">{{taskStatus.status}}</label>
			</div>
 			<div class="col"> 
 				<label class="col"><small>Assigned to</small></label> 
 				<label class="col">{{(taskStatus.assignee != null && taskStatus.assignee != "")? taskStatus.assignee: 'NA'}}</label> 
 			</div> 
			<div class="col">
				<label class="col"><small>Remarks</small></label>
				<label class="col">{{taskStatus.remarks}}</label>
			</div>
		</div>
	</div>
</div>