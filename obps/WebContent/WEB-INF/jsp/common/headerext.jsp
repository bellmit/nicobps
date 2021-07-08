<!-- <div style="display:none;">
	<h1>Heading1</h1><h2>Heading2</h2>
</div> -->

<!-- Accessibility -->
<div class="container d-flex clearfix" id="b-accessibility">
	<div class="b-ministryname">
		<div class="text-right d-inline-block font-weight-bold b-acc-goi my-sm-1 pr-sm-2">				
			<span>Government of India</span>
		</div>

		<div class="font-weight-bold d-inline-block b-acc-ministry my-sm-1 pl-sm-1">				
			<span>Ministry of Housing and Urban Affairs</span>
		</div>
	</div>		
</div>


<!-- Header -->
<div class="container clearfix" id="b-header">
	<div class="float-left d-flex h-100">
		<img src="resources/js/vendor/bootstrap/images/emblem-dark.png" class="align-self-center b-emblem-image" title="National Emblem of India" alt="emblem of india logo">
	</div>

	<div class="float-left d-flex h-100">
		<h2 class="align-self-center pl-3 b-appname"><span>Online Building Permission System</span></h2>
	</div>
</div>

<style type="text/css">
	.bar1, .bar2, .bar3 {
	    width: 25px;
	    height: 3px;
	    background-color: #fff;
	    margin: 5px 0;
	    transition: 0.4s;
	}

	.change .bar1 {
	  -webkit-transform: rotate(-45deg) translate(-5px, 5px);
	  transform: rotate(-45deg) translate(-5px, 5px);
	}

	.change .bar2 {opacity: 0;}

	.change .bar3 {
	  -webkit-transform: rotate(45deg) translate(-5px, -7px);
	  transform: rotate(45deg) translate(-5px, -7px);
	}
</style>

<!-- Global Navigation -->
<div class="globalnav-bg">
	<div class="container">
		<nav class="navbar navbar-expand-sm navbar-dark px-0">
			<div class="d-flex w-100 b-nav-mobile">
				<button class="navbar-toggler align-self-center b-btn-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar" onclick="myFunction(this)">
					<span style="display:none;">Menu</span>
					<div>
					  <div class="bar1"></div>
					  <div class="bar2"></div>
					  <div class="bar3"></div>
					</div>
				</button>
				<!-- <button class="btn btn-outline-light align-self-center ml-auto b-btn-login" type="button" data-toggle="modal" data-target="#login-modal">
					Log In
				</button>  -->
			</div>
			
			<div class="collapse navbar-collapse" id="collapsibleNavbar">
				<ul class="navbar-nav main-menu d-flex">
					<li class="nav-item d-block"> <a href="login.htm" class="nav-link lk active">Home</a> </li>
					<li class="nav-item d-block"> <a href="signup.htm" class="nav-link lk">Pre-Registrations of Architects/License Technical Persons</a></li>
					<!-- <li class="nav-item d-block"> <a href="uploadenclosures.htm" class="nav-link lk">Upload Enclosures</a></li> -->
					
					<li class="nav-item d-block"> <a href="contactus.htm" class="nav-link lk">Contact Us</a></li>
					<!-- <li class="nav-item d-block"> <a href="contactus.html" class="nav-link">Contact Us</a></li> -->
					<!-- <li class="nav-item d-block ml-auto b-loginbut" data-toggle="modal" data-target="#login-modal">
						<a class="nav-link" href="javascript:void(0);">Log In</a>
					</li>  -->   
				</ul>
			</div>
		</nav>
	</div>		
	<script>
		function myFunction(x) {
		  x.classList.toggle("change");
		}
	</script>		
	
	
	<script> 
	    var pathname = window.location.pathname; 
	    jQuery(".lk").removeClass("active");
	    jQuery(".lk").each(function() {
	        var hrf = jQuery(this).attr("href");	        
	        if (pathname.indexOf(hrf) !== -1) {
	            jQuery(this).addClass("active");	            
	        }       
	    });
	</script>	
			
</div>