<%@page import="objects.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="objects.User"%>
<%@page import="objects.Sale"%>
<%@page import="model.Model"%>
<%@page import="objects.HTMLInputFilter"%>
<html lang="en"><head>
      <meta charset="utf-8">
      <title>View Financial Records</title>
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
      <style type="text/css">
      body {
        margin-left: 0px;
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
       
       <%
  String userName = null, id = null;
  		  ArrayList<String> listProdTypes = new ArrayList<String>();
  		  listProdTypes.add("Boots");
  		  listProdTypes.add("Shoes");
  		  listProdTypes.add("Sandals");
  		  listProdTypes.add("Slippers");
  Cookie[] cookies = request.getCookies();
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
  ArrayList<String> viewModes = new ArrayList<String>();
  viewModes.add("Total Sales");
  viewModes.add("Product Type");
  viewModes.add("Sales per Product");
  
  ArrayList<Sale> listSales = new ArrayList<Sale>();
  %>
       <nav class="navbar navbar-custom">
            <div class="container-fluid">
                <!--div class="dropdown navbar-header">
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
                        <% if (userName != null && user.getUserType() == 4)
                            {%>
                        <li><a class="navbar-acct" href="home-orders.jsp"><span class="glyphicon glyphicon-shopping-cart navbar-acct"></span> Shopping Cart </a></li>
                   <li><a class="navbar-acct" href = "" data-toggle="dropdown" class="dropdown-toggle"><span class="glyphicon glyphicon-user navbar-acct"></span> <%=userName %> </a>
                       
                       <ul class="dropdown-menu" role="menu" aria-labelledby="ordersort">
                            <li role="presentation"><button type="button" tabindex="-1" class="login-as" onclick="location.href='change-pw.jsp'"><span class="glyphicon glyphicon-inbox navbar-acct"></span>Change Password</button></li>
                            <div></div>
                            <li role="presentation"><button type="button" tabindex="-1" class="login-as" onclick="location.href='logout'"><span class="glyphicon glyphicon-off navbar-acct"></span> Log Out</button></li>
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

        <div class="container pagecontent">
        <div class="headroom"></div>        
        <div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
        <h4>View Sales</h4>
          <div class="panel panel-default">
            <div class="panel-body">
              <hr>
            <form id = "signUpForm" action = "editproduct" method = "post" class="form-ediit">
                <div class=" row top-margin">
                  <div class="col-sm-4">
                    <label class="control-label">View by<span class="text-danger">*</span></label>
                    <select name = "viewmode">
                    	<%for (int i=0; i < viewModes.size(); i++) { %>

                    		<option value= "<%String.valueOf(i);%>" ><%=viewModes.get(i) %></option>

                    	<%} %>
                    </select>
                  </div>
               </div>
                <div class=" row top-margin">
                     <div class="col-lg-8">
                        <div class="col-lg-12 text-right">
                        <button class="btn btn-action" type="submit">Refresh</button>
                        </div>
                    </div>
                </div>
                <hr>
              </form>
              <div class=" row top-margin">
                	<%    if (request.getParameter("viewmode") == null)
                		listSales = Model.getAllTotalSalesPerProduct(); 
                	else if (Integer.parseInt(request.getParameter("viewmode")) == 1)
                				listSales = Model.getAllTotalSalesPerProduct(); 		// total sales ???
                		  else if (Integer.parseInt(request.getParameter("viewmode")) == 2)
                			  listSales = Model.getAllTotalSalesPerType(); 		// product type
                		  else if (Integer.parseInt(request.getParameter("viewmode")) == 3)
                			  listSales = Model.getAllTotalSalesPerProduct();  		// sales per product  
                		  %>
	                     <div class="col-sm-6">
	                    	<table>
	                    		<thead>
	                    			<tr>
	                    				<th>Prod. ID</th>
	                    				<th>Product Name</th>
	                    				<th>Qty. Sold</th>
	                    				<th>Unit Price</th>
	                    				<th>Total Sales</th>
	                    			</tr>
	                    		</thead>
	                    		<tbody>
	                    				<% for (int i=0; i<listSales.size(); i++) {%>
		                    				<tr>
		                    						<td><%listSales.get(i).getProductId(); %></td>
		                    						<td><%listSales.get(i).getProductName(); %></td>
		                    						<td><%listSales.get(i).getTotalQuantitySold(); %></td>
		                    						<td><%Model.getProductById(listSales.get(i).getProductId()).getPrice();%></td>
		                    						<td><%listSales.get(i).getTotalSales(); %></td>
		                    				</tr>
		                    			<%} %>
	                    		</tbody>
	                    	</table>
	                  	</div>
                </div>
            </div>
          </div>

    </div>
    </div>

     <div id="footer">
      <div class="wrap">
        <p></p>
      </div>
    </div>



    <!-- Le javascript
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
