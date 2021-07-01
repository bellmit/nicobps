<!-- @author Decent Khongstia -->
<h3 class="mt-4"
	style="font-size: 32px; border-bottom: 3px solid #005776">
	{{taskStatus.nextprocessname}}
</h3>
<div class="card-body">
	<label class="h4">Application details</label> 
	<label class="h6 alert alert-dark">
		Application No. <core:out value="${applicationcode}"></core:out>
	</label>
</div>
<task-status></task-status>
<basic-details></basic-details>
<scrutiny-details></scrutiny-details>
<owner-details></owner-details>
<document-details></document-details>
<site-report-details></site-report-details>
<file-view-modal></file-view-modal>
<script src="resources/js/util/filevalidation.js" type="text/javascript"></script>
