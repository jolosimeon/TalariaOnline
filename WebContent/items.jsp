<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="objects.User"%>
<%@page import="objects.Product"%>
<%@page import="objects.ShoppingCart"%>
<%@page import="model.Model"%>
<%@page import="java.util.ArrayList"%>
<%@page import="objects.HTMLInputFilter"%>

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

      <link href="http://fonts.googleapis.com/css?family=Roboto:400,300,700" rel="stylesheet" type="text/css">
      <link href="css/bootplus.css" rel="stylesheet">

	  <script src="js/star-rating.js" type="text/javascript"></script>
	  <link href="css/star-rating.css" media="all" rel="stylesheet" type="text/css" />
	  
	  <script src="js/krajee-theme.js" type="text/javascript"></script>
	  <link href="css/krajee-theme.css" media="all" rel="stylesheet" type="text/css" />
	  
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
              <input id="input-1-ltr-star-xs" name="input-1-ltr-star-xs" class="kv-ltr-theme-uni-star rating-loading" value="<%=product.getAveStars() %>" dir="ltr" data-size="s" data-readonly="true">
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
          
            <div class="panel-body">
            <form id = "editForm" action = "EditProduct" method = "post" class="form-signin">
                <div class=" row top-margin">
                  <div class="col-sm-4">
                    <label class="control-label">Category<span class="text-danger">*</span></label>
                    <select class = "form-control" name="type" required>
                    	<%for (int i=0; i < listProdTypes.size(); i++) { 
                    		if (i+1 == product.getType()) {%>
                    			<option value= "<%=i+1 %>" selected="selected"><%=listProdTypes.get(i) %></option>
                    		<% }else {%>
                    			<option value= "<%=i+1 %>"><%=listProdTypes.get(i) %></option>
                    	<%}} %>
                    </select>
                  </div>
                  <input type = "hidden" name ="prod_id" id = "prod_id" value ="<%=prod_id%>">
                  <div class="col-sm-6">
                    <label class="control-label">Name<span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name = "prod_name" value = <%=product.getName()%> required>
                  </div>
                  <div class="col-sm-6">
                    <label>Price<span class="text-danger">*</span> </label>
                    <input type="number" class="form-control" name = "price" id = "unitprice" value=<%=product.getPrice()%> required>
                  </div>
                  <div class="col-sm-12">
                    <label>Description<span class="text-danger">*</span> </label>
                    <textarea rows = "4" cols = "50" class="form-control" name = "desc" id = "desc" required><%=product.getDescription()%></textarea>
                  </div>
                </div>
                <hr>
                <div class="row">
                     <div class="col-lg-8">
                        <div class="col-lg-12 text-right">
                        <button class="btn btn-action" type="submit">Edit Product</button>
                        <button type="button" tabindex="-1" data-toggle="modal" data-target="#confirm-delete" class="btn btn-action">Delete Product</button>
                        </div>
                    </div>
            
                </div>
              </form>
            </div>
             </div>
              <%} %>
              
              </div>
          </div><!--/row-->
          
          <div class = "row-fluid">
          <div class="panel panel-default">
          <div class="panel-heading">
          	<h3 style="color:sienna;">Reviews</h3>
          </div>
          <div class="panel-body">
              <%if (product.getReviewList().size() == 0) { %>
              <div style="margin-left: 15px;">
          	  <h2>No Reviews yet!</h2>
          	  </div>
          	  <%} else {%>
          	  <%for (int i = 0; i < product.getReviewList().size(); i++) { %>
	              <div class="row">
					<div class="col-sm-1" style="margin-left: 20px;">
					<div class="thumbnail">
					<img class="img-responsive user-photo" src="https://ssl.gstatic.com/accounts/ui/avatar_2x.png">
					</div><!-- /thumbnail -->
					</div><!-- /col-sm-1 -->
					
					<div class="col-sm-8">
					<div class="panel panel-default">
					<div class="panel-heading">
					<strong><%=product.getReviewList().get(i).getUsername() %></strong> <span class="text-muted">reviewed:</span><input class="kv-ltr-theme-uni-star rating-loading" value="<%=product.getReviewList().get(i).getStars()%>" dir="ltr" data-size="s" data-readonly="true">
					</div>
					<div class="panel-body">
					<%=product.getReviewList().get(i).getDetails() %>
					</div><!-- /panel-body -->
					</div><!-- /panel panel-default -->
					</div><!-- /col-sm-5 -->
				  </div>
			  <%}} %>
			  <%if (Model.boughtItem(user.getUsername(), Integer.valueOf(prod_id))) { %>
			  <br><br>
			    <hr>
			    <div class="col-sm-12">
			  	<h3>Leave a Review</h3>
			  	<form id = "addreviewForm" action = "AddReview" method = "post" class="form-signin">
			  	<input id="new_star" name="new_star" class="kv-ltr-theme-uni-star rating-loading" value="0" dir="ltr" data-size="s" data-step="1">
			  	<br>
                    <label>Details<span class="text-danger">*</span></label>
                    <textarea rows = "4" cols = "50" class="form-control" name = "details" id = "details" placeholder = "Review Details" required></textarea>
                 	<input type = "hidden" name ="prod_id" id = "prod_id" value ="<%=prod_id%>">
                    <input type = "hidden" name ="username" id = "username" value ="<%=user.getUsername()%>">
                 	<button class="btn-primary btn-right btn-action" type = "submit"><a style="color:white">Add Review</a></button>
                 </form>
                </div>
			  <% } %>
			</div>
          </div>
          </div>
        </div><!--/span-->
      </div><!--/row-->

    </div>

    </div><!--/.fluid-container-->
    
    <!--  <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>-->
	<script src="http://jqueryvalidation.org/files/dist/jquery.validate.min.js"></script>
	<script src="http://jqueryvalidation.org/files/dist/additional-methods.min.js"></script>
	<script type="text/javascript" src="js/script.js"></script>
	<script>
$(document).on('ready', function(){
    $('.kv-ltr-theme-uni-star').rating({
        hoverOnClear: false,
        theme: 'krajee-uni'
    });
});
</script>
	<!-- Modal -->
    <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="checkerLabel1" aria-hidden="true" style="height:180px;">
<!--       <div class="modal-dialog"> -->
        <div class="modal-content">
          <div class="modal-body">
            <table class="table">
              <thead>
                <tr>
                  <th>Delete Product: <%=product.getName()%></th>
                </tr>
              </thead>
              <tbody>
              <%if (user != null && user.getUserType() == 3) {%> <!-- 3 -> Product Manager -->
                <tr>
                <form action = "DeleteProduct" method = "post">
                  <td>Are you sure you want to delete this product?</td>
                  <input type = "hidden" name ="prod_id" id = "prod_id" value ="<%=prod_id%>">
                  <td><button class="btn btn-default btn-xs" type="submit" >Yes</button> <button class="btn btn-default btn-xs" data-dismiss="modal">No</button></td>
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