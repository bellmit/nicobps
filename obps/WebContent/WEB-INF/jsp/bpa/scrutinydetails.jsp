<!-- @author Decent Khongstia -->
<div class="card mb-4">
	<div class="card-body">
		<div class="card-title h5">Scrutiny Details</div>
		<div class="form-group">
			<label class="col-sm-12">
				<small>Building Plan Scrutiny Details</small>
				<hr style="margin: 0"/>
			</label>
			<div class="row col-sm-12">
				<small class="col">eDCR Number</small> <small class="col">Scrutiny
					Report</small>
			</div>
			<div class="row col-sm-12">
				<label class="col">{{basicDetail.edcrnumber}}</label> 
				<label class="col">
					<a ng-href="{{EDCR.planinfoobject.planReport}}">ScrutinyReport.pdf</a> <br>
				</label>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-12"><small>Proposed Building Details</small>
				<hr style="margin: 0"/>
			</label>
			<div class="card form-group px-2 py-3" ng-repeat="B in Blocks">
				<div class="row col-sm-12">
					<div class="col font-weight-normal">Block {{B.number}}</div>
				</div>
				<div class="col-sm-12">
					<div class="row col-sm-12 font-weight-bold">
						<div class="col">Floor Description</div>
						<div class="col">Level</div>
						<div class="col">Sub Occupancy</div>
						<div class="col">Build Up Area</div>
						<div class="col">Floor Area</div>
					</div>
					<div class="col-sm-12">
						<hr style="margin:0 0 .5em 0; border-color: rgba(0,0,0,0.135)">
					</div>
					<div class="row col-sm-12" ng-repeat="F in B.building.floors">
						<div class="col">{{getFloorName(F.number)}}</div>
						<div class="col">{{F.number}}</div>
						<div class="col">{{getFloorSubOccupancy(F.occupancies)}}</div>
						<div class="col">{{getFloorBuiltUpArea(F.occupancies)}}</div>
						<div class="col">{{getFloorArea(F.occupancies)}}</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>