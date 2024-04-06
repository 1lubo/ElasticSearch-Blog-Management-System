<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="description" content="Blog Management System" />
  <meta name="author" content="1lubo" />
  <title>BloggestX Login</title>
  <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/5.3.2/css/bootstrap.min.css"/>
  <!-- Custom styles for this template -->
  <link href="/css/bloggestx.css" rel="stylesheet">
  <script src="/webjars/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<#if message??>
  <div class="row justify-content-center">
    <div id="alert-danger" class="col-12 col-md-8 col-lg-6 col-xl-5 alert alert-danger">
        ${message}
    </div>
  </div>
  <script type="text/javascript">
      $( document ).ready(function () {
          $("#alert-danger").fadeTo(2000, 500).slideUp(500,
              function () {
                  $("#alert-danger").slideUp(500);
              });
      });
  </script>
</#if>
<section class="vh-100 gradient-custom">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-12 col-md-8 col-lg-6 col-xl-5">
        <div class="card bg-dark text-white" style="border-radius: 1rem;">
          <div class="card-body p-5 text-center">
            <a class="mb-0" href="/article">< Back to Home Page</a>

            <div class="mb-md-5 mt-md-4 pb-5">

              <h2 class="fw-bold mb-2 text-uppercase">Create an account</h2>
              <p class="text-white-50 mb-5">Please enter the required information!</p>
              <form action="signup" method="post">

                <div class="form-outline form-white mb-4">
                  <input type="text" name="username" id="username" class="form-control form-control-lg" />
                  <label class="form-label" for="username">Username</label>
                </div>

                <div class="form-outline form-white mb-4">
                  <input type="password" name="password" id="password" class="form-control form-control-lg" />
                  <label class="form-label" for="password">Password</label>
                </div>

                <div class="form-check d-flex justify-content-center mb-5">
                  <input class="form-check-input me-2" type="checkbox" value="" id="tos" />
                  <label class="form-check-label" for="tos">
                    I agree all statements in <a href="#!" class="text-white-50"><u>Terms of service</u></a>
                  </label>
                </div>

                <input class="btn btn-outline-light btn-lg px-5" type="submit" value="Register">
              </form>
            </div>

            <div>
              <p class="mb-0">Already have an account? <a href="login" class="text-white-50 fw-bold">Sign In</a>
              </p>
            </div>

          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<script src="/webjars/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
</body>
