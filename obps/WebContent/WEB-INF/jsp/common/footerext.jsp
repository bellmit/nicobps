
<!----------- Footer ------------>
<div class="footer-bs">
    <footer class="container">
        <div class="row">
<!--         	<div class="row col-md-7 col-sm-12 footer-nav"> -->
<!--             	<p class="col-md-12">Quick Links</p> -->
<!--             	<div class="col-sm-6"> -->
<!--                     <ul class="list"> -->
<!--                         <li><a href="#">Information</a></li> -->
<!--                         <li><a href="contactus.htm">Contact Us</a></li>                         -->
<!--                     </ul> -->
<!--                 </div> -->
<!--                 <div class="col-sm-6"> -->
<!--                     <ul class="list"> -->
<!--                     	<li data-toggle="modal" data-target="#feedback-modal"><a href="javascript:void(0)">Feedback</a></li>                        -->
<!--                         <li><a href="#">FAQ</a></li> -->
<!--                         <li data-toggle="modal" data-target="#signup-modal"><a href="javascript:void(0);">FAQ</a></li>                         -->
<!--                     </ul> -->
<!--                 </div> -->
<!--             </div> -->
        	<div class="col-md-4 col-sm-4 footer-social d-flex">
       			<div class="d-inline-block align-self-center">
         			<p class="bg-light"><img src="resources/js/vendor/bootstrap/images/NIC.png" alt="NIC logo"  height="70"></p> 
        		</div>
            </div>
        	<div class="col-md-4 col-sm-4 footer-social d-flex">
       			<div class="d-inline-block align-self-center">
         			<p class="bg-light mb-0"><img src="resources/js/vendor/bootstrap/images/egovlogo.jpg" height="100"></p>         		
        		</div>
            </div>            
        	
        	<div class="col-md-4 col-sm-4 footer-social d-flex">
       			<div class="d-inline-block align-self-center">
         			<p class="bg-light mb-0"><img src="resources/js/vendor/bootstrap/images/niualogo.png" height="100"></p>         		
        		</div>
            </div>  <div class="col-md-4 col-sm-4 footer-social d-flex">
       			<div class="d-inline-block align-self-center">
         			<p class="bg-light mb-0"><img src="resources/js/vendor/bootstrap/images/tcpologo.jpg" height="100"></p>         		
        		</div>
            </div>            
        	
        	<div class="col-md-2 col-sm-4 footer-ns d-flex">
       			<a class="backtotop align-self-center d-flex text-center text-decoration-none text-white" title="Back to top" href="#b-accessibility">
       				<span style="display:none;">Back to top</span>
            		<span style="font-size: 24px;" class="fas fa-angle-up align-self-center mx-auto"></span>
            	</a>
            </div>
        </div>
        <div class="text-center mt-5">
        	This site is designed and hosted by <a class="text-light font-weight-bold" href="https://www.nic.in/">National Informatics Centre</a>, <a class="text-light font-weight-bold" href="https://meity.gov.in/">Ministry of Electronics and Information Technology</a>, <a class="text-light font-weight-bold" href="https://www.india.gov.in/">Govt. of India.</a>
        </div>
    </footer>
</div>

<!-- Signup Modal -->
<div class="modal fade" id="signup-modal">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- Modal Header -->
			<div class="modal-header text-center d-block p-5 border-bottom-0">
				<h2 class="modal-title">Sign Up</h2>
				<button type="button" class="close position-absolute" style="right: 15px; top: 8px;" data-dismiss="modal">&times;</button>
			</div>
			<!-- Modal body -->
			<div class="modal-body">
				<form action="" autocomplete="off">
					<div class="form-group">
						<label for="signup-name">Name:</label>
						<input type="text" class="form-control" id="signup-name" placeholder="Enter name" name="signup-name">
					</div>
					<div class="form-group">
						<label for="signup-email">Email:</label>
						<input type="email" class="form-control" id="signup-email" placeholder="Enter email" name="signup-email">
					</div>
					<div class="form-group">
						<label for="signup-mobile">Mobile no.:</label>
						<input type="number" class="form-control" id="signup-mobile" placeholder="Enter mobile no." name="signup-mobile">
					</div>
					<div class="form-group">
						<label for="signup-pwd">Password:</label>
						<input type="password" class="form-control" id="signup-pwd" placeholder="Enter password" name="signup-pwd">
					</div>
					<!-- <p class="text-right b-already-reg">Already Registered? <a href="" data-toggle="modal" data-target="#login-modal" data-dismiss="modal">Log In</a></p> -->
					<div class="text-center py-4">
						<button type="submit" class="btn btn-primary b-btn">Sign Up</button>
					</div>						
				</form>
			</div>
		</div>
	</div>
</div>


<!-- Login Modal -->
<!-- <div class="modal fade" id="login-modal">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			Modal Header
			<div class="modal-header text-center d-block p-5 border-bottom-0">
				<h2 class="modal-title">Log In</h2>
				<button type="button" class="close position-absolute" style="right: 15px; top: 8px;" data-dismiss="modal">&times;</button>
			</div>
			Modal body
			<div class="modal-body">
				<form action="dashboard.html" autocomplete="off" method="POST">
					<div class="form-group">
						<label for="login-email">Email:</label>
						<input type="email" class="form-control" id="login-email" placeholder="Enter email" name="login-email">
					</div>
					<div class="form-group">
						<label for="login-pwd">Password:</label>
						<input type="password" class="form-control" id="login-pwd" placeholder="Enter password" name="login-pwd">
					</div>
					<div class="form-group form-check">
						<label class="form-check-label" for="login-rem">
							<input class="form-check-input" type="checkbox" id="login-rem" name="remember"> Remember me
						</label>
					</div>
					<p class="text-right b-notreg">Don't have an account? <a href="" data-toggle="modal" data-target="#signup-modal" data-dismiss="modal">Login</a></p>
					<div class="text-center py-4">
						<button type="submit" class="btn btn-primary b-btn">Log In</button>
					</div>						
				</form>
			</div>
		</div>
	</div>
</div> -->

<!-- Feedback Modal -->
<div class="modal fade" id="feedback-modal">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- Modal Header -->
			<div class="modal-header text-center d-block p-5 border-bottom-0">
				<h2 class="modal-title">Feedback</h2>
				<button type="button" class="close position-absolute" style="right: 15px; top: 8px;" data-dismiss="modal">&times;</button>
			</div>
			<!-- Modal body -->
			<div class="modal-body">
				<form action="" autocomplete="off">
					<div class="form-group">
						<label for="fdbk-name">Name:</label>
						<input type="text" class="form-control" id="fdbk-name" placeholder="Enter name" name="fdbk-name">
					</div>
					<div class="form-group">
						<label for="fdbk-email">Email:</label>
						<input type="email" class="form-control" id="fdbk-email" placeholder="Enter email" name="fdbk-email">
					</div>
					<div class="form-group">
						<label for="fdbk-subject">Subject:</label>
						<select class="form-control" id="fdbk-subject" name="fdbk-subject">
							<option>Application issue</option>
							<option>Design issue</option>
							<option>Any other</option>
						</select>
					</div>
					<div class="form-group">
						<label for="fdbk-comment">Comment:</label>
						<textarea class="form-control" id="fdbk-comment" placeholder="Enter feedback" name="fdbk-comment" rows="5" style="resize: none;"></textarea>
					</div>

					<div class="text-center py-4">
						<button type="submit" class="btn btn-primary b-btn">Submit</button>
					</div>						
				</form>
			</div>
		</div>
	</div>
</div>
