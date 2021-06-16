<!-- @author Decent Khongstia -->
<!DOCTYPE html>
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
				<label class="col">{{taskStatus.fullname}}</label>
			</div>
			<div class="col">
				<label class="col"><small>Status</small></label>
				<label class="col">{{taskStatus.status}}</label>
			</div>
<!-- 			<div class="col"> -->
<!-- 				<label class="col"><small>Current Owner</small></label> -->
<!-- 				<label class="col">NA</label> -->
<!-- 			</div> -->
			<div class="col">
				<label class="col"><small>Remarks</small></label>
				<label class="col">{{taskStatus.remarks}}</label>
			</div>
		</div>
	</div>
</div>