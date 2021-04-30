<!DOCTYPE html>
<html lang="en">
<head>
	<title>OBPS | Upload Enclosures</title>
	<!-- <meta charset="utf-8"> -->
    <meta name="viewport" content="width=device-width, initial-scale=1">        
    <%@include file="../common/headerfiles.jsp" %>     
	<script src="resources/js/application/uploadenclosures.js"></script>  		  	  
</head>

<body>
	<%@include file="../common/headerext.jsp" %> 

	<!-- Breadcrumb -->
	<div class="bg-light">
		<div class="container">
			<ul class="breadcrumb bg-light pl-0">
				<li><a href="login.htm">Home</a></li>
				<li>Upload Enclosures</li>
			</ul>
		</div>
	</div>

	<div class="mb-5">
		<div class="container">		
			<div class="mt-5">

				  <form class="ng-scope" ng-app="applicationApp" ng-controller="applicationController" id="applicationForm" name="applicationForm" autocomplete="off">
				      <div class="row">	        				     	
				     	<div class="col-md-12 py-12 px-12">
				     		<h5 style="border-bottom:3px solid #005776">License Details</h5>								
								<table id="entrytable">			
								   <tr>
									   <td class='flathead' style="width:50%">Registered With <span class="mandatory">*</span></td>                                       
									   <td class='flathead' style="width:50%">Registration No. <span class="mandatory">*</span></td>                                       
								   </tr>											
								   <tr ng-repeat="L in listEnclosures" id="tr{{$index}}">
									   <td>                                   		
											<input type="checkbox" ng-model="L.ischecked" id="ischecked_{{$index}}" name="ischecked" />
											{{ L.enclosurename}}
									   </td>                                       
									   <td ng-if="L.ischecked==true">
											<input type="file" id="file_{{$index}}" name="file_{{$index}}"  onchange="addFile(this.id)" />
									   </td>    
									   <td ng-if="L.ischecked==false">
											<input type="file" id="file_{{$index}}" name="file_{{$index}}" disabled />
									   </td>    
								   </tr>
							</table>         			        							     			     																													 													   
				     	</div>	
				     		
				     	<div class="col-md-12 py-12 px-12">
				     						     		
				     		<table id="entrytable" style="border-top:3px solid #005776;border-bottom:3px solid #005776">		     				     					     		
				     			<tr>
				     				<td></td>	
			     					<td>
										<div class="form-group">
											<label for="jcaptcha" class="">Captcha <span class="mandatory">*</span></label>
											<img src="./jcaptcha.jpg" id="jcaptchaimg" onclick="changeCaptcha();" title="Click To Reload" style="cursor: pointer;"/>
											<input type="text" ng-model="applicationEnclosures.userresponsecaptcha" id="jcaptcha" name="jcaptcha" value="" class="form-control"  autocomplete="off" >
											<span id="jcaptchaMsg" class="formErrorContent"></span>
											<div style="text-align: center;padding-top:10px ">
												<input type="button" value="Submit" ng-click="submitDetails();" class="btn btn-primary b-btn">
												<input type="button" value="Reset" ng-click="resetDetails();" class="btn btn-primary b-btn">
											</div>	
											<br/>											
											<div id="successMsg" class="formErrorContent" style="width:100%"></div>	
										</div>					     				
				     				</td>
				     				<td></td>	
				     			</tr>							     						     		
				     		</table>			     	
				     	     
				     	</div>		     
				     	        	       	        				    		    
				     </div> 
			     </form>	
			     
			</div>
		</div>
	</div>
	

	<%@include file="../common/footerext.jsp" %> 
</body>
</html>


