<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Blog Management System" />
    <meta name="author" content="1lubo" />
    <title>Error : ${status}</title>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/5.3.2/css/bootstrap.min.css"/>
    <!-- Custom styles for this template -->
    <link href="/css/bloggestx.css" rel="stylesheet">
  </head>
  <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container">
        <a class="navbar-brand" href="/">Blog Management System</a>
      </div>
    </nav>
    <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <!-- Title -->
            <h1 class="mt-4">${status}</h1>
            <p>${exception.message}</p>
          </div>
        </div>
    </div>
  </body>
</html>