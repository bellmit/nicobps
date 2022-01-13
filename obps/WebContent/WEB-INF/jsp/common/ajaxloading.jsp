<style>
.ajax-loading{
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    /*padding-top: 20%;  Location of the box*/ 
    left: 0;
    top: 0;
    width: 100%;  
    height: 100%;
    overflow: scroll; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}  
.ajax-loading img {
    position: absolute;
    top:0; left:0; right:0; bottom:0;
    margin: auto;
}
</style>
<div id="ajaxLoading" class="ajax-loading">
    <div class="ajax-loading-content" style="text-align: center;">
        <img src="resources/images/c-processing.gif" alt="LOADING....." height="100px" width="100px"/>
    </div>
</div>