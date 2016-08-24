<%@page import="objects.User"%>
<%@page import="objects.Product"%>
<%@page import="objects.ShoppingCart"%>
<%@page import="model.Model"%>
<%@page import="java.util.ArrayList"%>

 <%
  String userName = null, id  = null;
  Cookie[] cookies = request.getCookies();
  ArrayList<String> listProdTypes = new ArrayList<String>();
  listProdTypes.add("Boots");
  listProdTypes.add("Shoes");
  listProdTypes.add("Sandals");
  listProdTypes.add("Slippers");
  
  if (cookies != null)
  {
    for (Cookie cookie : cookies)
    {
        if (cookie.getName().equals("user_name"))
            userName = cookie.getValue();
        
         if (cookie.getName().equals("user_id"))
            id = cookie.getValue();
    }
  }
  
  if (userName == null)
      response.sendRedirect("login.jsp");
  
  else
  {
      session = request.getSession();
      session.setAttribute("user", Model.getUser(id));
  }
  User user = Model.getUser(id);
  String prod_id = request.getParameter("item");
  Product product = Model.getProductById(Integer.valueOf(prod_id));
  %>


<html lang="en"><head>
      <meta charset="utf-8">
      <title>View Item - <%=product.getName()%></title>
      <!-- Always force latest IE rendering engine (even in intranet) & Chrome Frame -->
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <meta name="description" content="">
      <meta name="author" content="">

    <!-- Le styles -->
      <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js">
      </script>
      <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js">
      </script>
	  <script src = 'scripts.js'> </script>

      <link href="http://fonts.googleapis.com/css?family=Roboto:400,300,700" rel="stylesheet" type="text/css">
      <link href="css/bootplus.css" rel="stylesheet">
      <style type="text/css">
      body {
   
        padding-bottom: 40px;
      }
      .hero-unit {
          padding: 60px;
      }
      @media (max-width: 980px) {
        /* Enable use of floated navbar text */
        .navbar-text.pull-right {
          float: none;
          padding-left: 5px;
          padding-right: 5px;
        }
      }
      </style>
      <link href="css/bootplus-responsive.css" rel="stylesheet">
      <link rel="stylesheet" type="text/css" href="css/styles.css">
      <link rel="stylesheet" type="text/css" href="css/footer_style.css">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
                                   <link rel="shortcut icon" href="../assets/ico/favicon.png">
   </head>

   <body>

        <nav class="navbar navbar-custom">
            <div class="container-fluid">
                <!--   div class="dropdown navbar-header">
                    <button class="menu-button dropdown-toggle" type="button" id="categories" data-toggle="dropdown" ><span class="glyphicon glyphicon-align-justify"></button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="categories">
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="index.jsp">Dashboard</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#contact">Contact Us</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#settings">Settings</a></li>
                    </ul>
                </div>-->
                <div class="navbar-header">
                    <a class="navbar-link navbar-brand" href="index.jsp">Talaria Online</a>
                </div>
                <div>


                    <!--<form style="margin: 0; padding: 0;" action = "searchItems" name = "searchForm"> <input style = "display:inline;" type="text" name = "searchText" class="navbar-search navbar-searchbar" placeholder="Search Items" id="search-field" onkeyup="searchItems()">
                    <!--table id="complete-table" class="popupBox" /
                    <button style = "display:inline;" type="submit" class="navbar-search navbar-searchbutton"><span class="glyphicon glyphicon-search"></span></button><!--</form>-->
                    <ul class="navbar-right">
                        <% if (userName != null)
                            {%>
                        <li><a class="navbar-acct" href="home-orders.jsp"><span class="glyphicon glyphicon-shopping-cart navbar-acct"></span> Shopping Cart </a></li>
                   <li><a class="navbar-acct" href = "" data-toggle="dropdown" class="dropdown-toggle"><span class="glyphicon glyphicon-user navbar-acct"></span> <%=userName %> </a>
                       
                       <ul class="dropdown-menu" role="menu" aria-labelledby="ordersort">
                            <li role="presentation"><button type="button" tabindex="-1" class="login-as" onclick="location.href='change-pw.jsp'"><span class="glyphicon glyphicon-inbox navbar-acct"></span>Change Password</button></li>
                            <li role="presentation"><button type="button" tabindex="-1" class="login-as" onclick="location.href='Logout'"><span class="glyphicon glyphicon-off navbar-acct"></span> Log Out</button></li>
                        </ul>
                  </li>
                            <%} else
                            {%>
                        <li><a class="navbar-acct" href="login.jsp"> <span class="glyphicon glyphicon-user navbar-acct"></span> Log In</a></li>
                        <li><a class="navbar-acct" href="signup.jsp">Register Now!</a></li>
                            <% } %>

                    </ul>
                </div>
            </div>
        </nav>

    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span3">
          <div class="sidebar-nav">
            <ul class="nav nav-list">
              <li class="nav-header">Categories</li>
                                <% for (int i = 0; i < listProdTypes.size(); i++)
                                    {
                                        String name = listProdTypes.get(i);
                                %>
                            <li><a href="index.jsp?categ=<%=i+1%>"><%=name%></a></li>
                                <% } %>
            </ul>
          </div><!--/.well -->
        </div><!--/span-->

        <div class="span9">
          <div class="image-left"></div>
            <div>
          <div class="hero-unit">
              <h1><%=product.getName().toUpperCase()%></h1>
              <h3><%=product.getPrice()%></h3>
              <p><%=product.getDescription()%></p>
            </div>
          </div>

          <div class="row-fluid">
              <div class="hero-unit">
              <%if (user.getUserType() == 1) {%>
              		<div>
                    <form action = "AddToShoppingCart" method ="post"> 
                  <h3>Purchase Item</h3>
                  <hr>
                  Name: <%=user.getFirstName()%> <%=user.getMiddleInitial()%> <%=user.getLastName()%><br>
                  E-Mail: <%=user.getEmail()%><br>
                  Billing Address: <%=user.getBillingAddress()%><br>
                  <!--   Contact # :  <input type="text" name="contact" required><br>-->
                  Quantity: <input type="number" id = "prod_qty" name="prod_qty" min="1" value="0" required><br>
                  <input type = "hidden" name ="prod_id" id = "prod_id" value ="<%=prod_id%>">
                  <input type = "hidden" name ="prod_price" id = "prod_price" value ="<%=product.getPrice()%>">
                  <hr>
                  <!--<h3>Sub-Total:</h3> -->
                  <button class="btn-primary btn-right btn-action" type = "submit"><a style="color:white"> Add to Cart </a></button>
                    </form>
                </div>
              <%} else if (user.getUserType() == 3) {%>
              		<div>
	                    <form id = "editItem" name = "editItem" action = "EditProduct" method ="post"> 
	                    	  <h3>Modify Product</h3>
			                  <input class="btn btn-action" type="submit" value="Edit" name="Edit" id="editprod" />
			                  </form>
			                  <button type="button" tabindex="-1" data-toggle="modal" data-target="#confirm-delete" class="btn btn-action">Delete</button>
			                  <hr>
                </div>
              <%} %>
              </div>
          </div><!--/row-->
        </div><!--/span-->
      </div><!--/row-->

    </div>

    </div><!--/.fluid-container-->
    
    <!--  <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>-->
	<script src="http://jqueryvalidation.org/files/dist/jquery.validate.min.js"></script>
	<script src="http://jqueryvalidation.org/files/dist/additional-methods.min.js"></script>
	
	<!-- Modal -->
    <div class="modal modal-alert-custom fade" id="delete-product" tabindex="-1" role="dialog" aria-labelledby="checkerLabel1" aria-hidden="true">
<!--       <div class="modal-dialog"> -->
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"></span></button>
            <h4 class="modal-title" id="checkerLabel1">Delete Product</h4>
          </div>
          <div class="modal-body">
            <table class="table">
              <thead>
                <tr>
                  <th><%=product.getName()%></th>
                </tr>
              </thead>
              <tbody>
              <%if (user != null && user.getUserType() == 3) {%> <!-- 3 -> Product Manager -->
                <tr>
                <form action = "deleteProduct" method = "post">
                  <td>Are you sure you want to delete this product?</td>
                  <td><button class="btn btn-default btn-xs" type="submit" >Yes</button> <button class="btn btn-default btn-xs" type="submit" >No</button></td>
                 </form>
                </tr>
                <%} %>
              </tbody>
            </table>
          </div>
        </div>
    </div>
    
   <!--  <script>
	$( "#orderItem" ).validate({
	  rules: {
	    item_qty : {
	    	remote : {
	            url: "checkQuantity.jsp",
	            type: "post",
	            data: {
	              item_id: function() {
	                return $( "#item_id" ).val();
	              }
	            } 
	    	}
	  },
	  messages: {
          item_qty: {
              remote: "Too many items."
          }
	  }
	  }
	});
	</script> -->

    <!--
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap-transition.js"></script>
    <script src="js/bootstrap-alert.js"></script>
    <script src="js/bootstrap-modal.js"></script>
    <script src="js/bootstrap-dropdown.js"></script>
    <script src="js/bootstrap-scrollspy.js"></script>
    <script src="js/bootstrap-tab.js"></script>
    <script src="js/bootstrap-tooltip.js"></script>
    <script src="js/bootstrap-popover.js"></script>
    <script src="js/bootstrap-button.js"></script>
    <script src="js/bootstrap-collapse.js"></script>
    <script src="js/bootstrap-carousel.js"></script>
    <script src="js/bootstrap-typeahead.js"></script>

   

</body></html>
