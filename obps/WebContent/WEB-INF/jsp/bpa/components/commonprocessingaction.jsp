<!-- @author Decent Khongstia -->
<div class="card mb-4">
	<div class="card-body">
		<div class="row">
			<div class="col">
				<div class="form-group">
					<div class="col-sm-12">
						<div class="btn-group dropup mt-5">
							<button type="button"
								class="btn btn-primary btn-lg dropdown-toggle"
								data-toggle="dropdown" style="min-width: 10rem">Action</button>
							<div class="dropdown-menu">
								<forward-button></forward-button>
								<reject-button></reject-button>
								<send-to-citizen-button></send-to-citizen-button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- <div class="row">
		serverMsg = {{serverMsg}}
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
		</div> -->
		<modal-action></modal-action>
	</div>
</div>