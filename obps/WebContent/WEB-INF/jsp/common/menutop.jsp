<nav class="navbar navbar-expand-lg navbar-light dashboard-bgcolor border-bottom">
  <button class="btn text-white" id="menu-toggle">
  	<span style="display:none;">Menu</span>
  	<span class="fas fa-bars" style="font-size: 1.4rem"></span>
  </button>

  <!-- Online users -->
  <!-- 
  <div class="d-inline-block px-4 py-2 dropdown">
      <div class="dropdown-toggle" data-toggle="dropdown">
       <span class="fas fa-users text-white" style="font-size: 20px; cursor: pointer;"></span>
      </div>
      <div class="dropdown-menu b-dropmenu-db">
       <a class="dropdown-item" href="#">User action</a>
       <a class="dropdown-item" href="#">Another user action</a>
       <a class="dropdown-item" href="#">Another user action</a>
       <a class="dropdown-item" href="#">Another user action</a>
      </div>
  </div> 
  -->
  
  <button class="navbar-toggler b-dropmenubtn" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
  	<span class="far fa-caret-square-down" style="font-size: 30px; color: #FFF"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
      <!-- 
      <li class="nav-item">
        <a class="nav-link text-white" href="#">Notification</a>
      </li>
      <li class="nav-item">
        <a class="nav-link text-white" href="#">Inbox</a>
      </li> 
      -->
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle text-white" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">          
          <span class="fas fa-user-cog text-white" style="font-size: 20px; cursor: pointer;"></span>
        </a>
        <div class="dropdown-menu dropdown-menu-right text-left b-dropmenu-db" aria-labelledby="navbarDropdown">
          <!-- <a class="dropdown-item" href="#">Edit Profile</a> -->         
          <a class="dropdown-item" href="changepassword.htm">Change Password</a>   
          <a class="dropdown-item" href=" profile.htm">View Profile</a>   
                  
          <div class="dropdown-divider"></div>
          <!-- <a class="dropdown-item" href="#" data-toggle="modal" data-target="#signout-modal">Logout</a> -->  
          <a class="dropdown-item" href="logout.htm">Logout</a>                 
        </div>
      </li>      
    </ul>
  </div>
</nav>
 

<!-- Breadcrumb -->
<ul class="breadcrumb">
<!--     
	<li><a href="home.htm">Home</a></li>
    <li>Page-1</li>
    <li>Page-2</li> 
-->
	<li>Logged in as : ${user.fullname} <span id='un' style="display:none;">${user.username}</span></li>
	
</ul>

 <!-- Menu Toggle Script -->
 <script>
   $("#menu-toggle").click(function(e) {
     e.preventDefault();
     $("#wrapper").toggleClass("toggled");
   });
 </script>

<!-- ////////////////////////////////////////////// -->  
<!-- Signup Modal -->
<!-- <div class="modal fade" id="signout-modal">
	<div class="modal-dialog modal-dialog-centered">
	<div class="modal-content">

		Modal Header
		<div class="modal-header text-center d-block p-5 border-bottom-0">
			<h3 class="modal-title">Sign Out?</h3>
			<button type="button" class="close position-absolute" style="right: 15px; top: 8px;" data-dismiss="modal">&times;</button>
		</div>

		Modal body
			<div class="modal-body">
				<p class="text-center">Are you sure you want to Sign Out?</p>
				<div class="text-center py-4">
					<form action="logout.htm">
						<button type="submit" class="btn btn-primary b-btn mx-3">Sign Out</button>
						<button class="btn btn-secondary mx-3" data-dismiss="modal">Cancel</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>   -->
  