<#macro page title>
    <!DOCTYPE html>
    <html lang="en">
      <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Blog Management System">
        <meta name="author" content="1lubo">

        <title>${title}</title>
        <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/5.3.2/css/bootstrap.min.css"/>
        <!-- Custom styles for this template -->
        <link href="/css/bloggestx.css" rel="stylesheet">
        <script src="/webjars/jquery/3.7.1/jquery.min.js"></script>
      </head>
      <body>
        <!-- Navigation -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
          <div class="container">
            <a class="navbar-brand" href="/article">Blog Management System</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-targer="#navbarResponsive"
                    aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
              <ul class="navbar-nav ms-auto">
                <li class="nav-item active">
                  <a class="nav-link" href="/article">Home
                    <span class="visually-hidden">(current)</span>
                  </a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/article/new">Write Article</a>
                </li>
                <li class="nav-item">
                  <#if user??>
                    <a class="nav-link" href="#">Welcome, ${user.username}</a>
                  <#else>
                      <a class="nav-link" href="/login">Login</a>
                  </#if>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/logout">Logout</a>
                </li>
              </ul>
            </div>
          </div>
        </nav>
        <!-- Page header with logo and tagline-->
        <header class="py-5 bg-light border-bottom mb-4">
          <div class="container">
            <div class="text-center my-5">
              <h1 class="fw-bolder">Welcome to Blog Home!</h1>
              <p class="lead mb-0">A Bootstrap 5 starter layout for your next blog homepage</p>
            </div>
          </div>
        </header>
        <!-- Page content -->
        <div class="container">
         <div class="row">
            <#if message??>
              <div id="success-alert" class="alert alert-success">
              ${message}
          </div>
              <script type="text/javascript">
              $( document ).ready(function () {
                  $("#success-alert").fadeTo(2000, 500).slideUp(500,
                      function () {
                          $("#success-alert").slideUp(500);
                      });
              });
          </script>
            </#if>
           <#nested>
              <!-- Sidebar Widgets Column -->
              <div class="col-lg-4">
             <!-- Search Widget -->
             <div class="card mb-4">
               <h5 class="card-header">Search</h5>
               <div class="card-body">
                 <form action="article">
                   <div class="input-group">
                     <input type="text" name="q" class="form-control" placeholder="Search for...">
                     <span class="input-group-text">
                       <input type="submit" class="btn btn-secondary" value="Go!"/>
                     </span>
                   </div>
                 </form>
               </div>
             </div>
             <!-- Categories widget-->
             <div class="card mb-4">
               <div class="card-header">Categories</div>
               <div class="card-body">
                 <div class="row">
                   <div class="col-sm-6">
                     <ul class="list-unstyled mb-0">
                       <li><a href="#!">Web Design</a></li>
                       <li><a href="#!">HTML</a></li>
                       <li><a href="#!">Freebies</a></li>
                     </ul>
                   </div>
                   <div class="col-sm-6">
                     <ul class="list-unstyled mb-0">
                       <li><a href="#!">JavaScript</a></li>
                       <li><a href="#!">CSS</a></li>
                       <li><a href="#!">Tutorials</a></li>
                     </ul>
                   </div>
                 </div>
               </div>
             </div>
             <!-- Side widget-->
             <div class="card mb-4">
               <div class="card-header">Side Widget</div>
               <div class="card-body">You can put anything you want inside of these side widgets. They are easy to use, and feature the Bootstrap 5 card component!</div>
             </div>
           </div>
         </div>
         <!-- /.row -->
       </div>
        <!-- Footer -->
        <footer class="py-5 bg-dark">
        <div class="container">
          <p class="m-0 text-center text-white">Copyright &copy; 1lubo</p>
        </div>
        <!-- container -->
      </footer>
      <!-- Bootstrap core JavaScript -->
      <script src="/webjars/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
      </body>
    </html>
</#macro>