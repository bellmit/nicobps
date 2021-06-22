<!-- @author Decent Khongstia -->
<div class="card mb-4">
	<div class="card-body">
		<div class="card-title h5">Scrutiny Details</div>
		<div class="form-group">
<!-- 			<div class="card-title"> -->
				<label class="col-sm-12">Proposed Building Details<hr></label>
<!-- 			</div> -->
			<div class="form-group" ng-repeat="B in Blocks">
				<div class="row col-sm-12">
					<div class="col font-weight-bold">Block {{B.number}}</div>
				</div>
				<div class="col-sm-12">
					<div class="row col-sm-12 font-weight-bold">
						<div class="col">Floor Description</div>
						<div class="col">Level</div>
						<div class="col">Sub Occupancy</div>
						<div class="col">Build Up Area</div>
						<div class="col">Floor Area</div>
					</div>
					<div class="col-sm-12"><hr style="font-size: 8px"></div>
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