<!-- @author Decent Khongstia -->
<style type="text/css">
	.iframe{
		width: -webkit-fill-available;
		width: -moz-available;
		width: 100%;
		height: calc(80vh);
	}
	
	@media (min-width: 992px){
		.modal-lg{
			width: 60%;
		}
	}
	.img{
		display: flex;
		height: auto;
		margin: auto;
		width: 90%;
	}
</style>
 <div class="modal fade" id="fileModal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="fileModal" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header col-sm-12" style="padding: 0px 1px;">
        <h5 class="modal-title" id="commonModalTitle">{{fileModal.title}}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
   		<iframe ng-src="{{fileModal.src}}" class="iframe" ng-if="fileModal.mimetype == 'application/pdf'"></iframe>
		<div style="height: calc(80vh); overflow-y: scroll" ng-if="fileModal.mimetype != 'application/pdf'">
			<img class="img" ng-src="{{fileModal.src}}" ng-if="fileModal.mimetype != 'application/pdf'"/>
		</div>
    </div>
  </div>
</div>