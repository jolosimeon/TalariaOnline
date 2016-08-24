
<%@page import="model.Model"%>
<%@page import="objects.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="objects.User"%>
<%@page import="objects.ShoppingCart" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Talaria Online</title>
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
        <script type="text/javascript" src="js/ajaxsearch.js"></script>

    </head>
    <body onload = "init()">

        <%
            String userName = null;
            String id = null;
            Cookie[] cookies = request.getCookies();
            ArrayList<Product> listProducts = new ArrayList<Product>();
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
                    {
                        userName = cookie.getValue();
                    }
                    if (cookie.getName().equals("user_id"))
                    {
                        id = cookie.getValue();
                    }
                }
            }

            if (userName != null)
            {
                session = request.getSession();
                session.setAttribute("user", Model.getUser(id));
                if (session.getAttribute("cart") == null) {
                	session.setAttribute("cart", new ShoppingCart());
                }
            }

            String categorydisp = request.getParameter("categ");
            if (categorydisp == null || categorydisp.equals(""))
            {
                listProducts = Model.getAllProducts();
            } else
            {
                listProducts = Model.getAllProductsByCategory(Integer.valueOf(categorydisp));
            }

             session = request.getSession();
             User user = (User) session.getAttribute("user");
             if (user != null)
             {
             	userName = user.getFirstName() + " " + user.getMiddleInitial() + ". " + user.getLastName();
             }
             String error = request.getParameter("error");
        %>
        <%if (error != null) { %>
			<div class="alert alert-error">
			   <a href="#" class="close" data-dismiss="alert">
			      &times;
			   </a>
			   <strong>Invalid password!</strong> Error logging to Organization.
			</div>
		<%} %>
        <nav class="navbar navbar-custom">
            <div class="container-fluid">
                <!--  div class="dropdown navbar-header">
                    <button class="menu-button dropdown-toggle" type="button" id="categories" data-toggle="dropdown" ><span class="glyphicon glyphicon-align-justify"></button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="categories">
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="index.jsp">Dashboard</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#contact">Contact Us</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#settings">Settings</a></li>
                    </ul>
                </div-->
                <div class="navbar-header">
                    <a class="navbar-link navbar-brand" href="index.jsp">Talaria Online</a>
                </div>
                <div>


                    <!--<form style="margin: 0; padding: 0;" action = "searchItems" name = "searchForm">--> <input style = "display:inline;" type="text" name = "searchText" class="navbar-search navbar-searchbar" placeholder="Search Items" id="search-field" onkeyup="searchItems()">
                    <!--table id="complete-table" class="popupBox" /-->
                    <button style = "display:inline;" type="submit" class="navbar-search navbar-searchbutton"><span class="glyphicon glyphicon-search"></span></button><!--</form>-->
                    <ul class="navbar-right">
                        <% if (userName != null)
                            {%>
                        <li><a class="navbar-acct" href="home-orders.jsp"><span class="glyphicon glyphicon-shopping-cart navbar-acct"></span> Shopping Cart </a></li>
                   <li><a class="navbar-acct" href = "" data-toggle="dropdown" class="dropdown-toggle"><span class="glyphicon glyphicon-user navbar-acct"></span> <%=userName %> </a>
                       
                       <ul class="dropdown-menu" role="menu" aria-labelledby="ordersort">
                            <li role="presentation"><button type="button" tabindex="-1" class="login-as" onclick="location.href='change-pw.jsp'"><span class="glyphicon glyphicon-inbox navbar-acct"></span>Change Password</button></li>
                            <div></div>
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
                            <!--  li class="nav-header"></li>-->
                            <li class="nav-header">Categories</li>
                                <% for (int i = 0; i < listProdTypes.size(); i++)
                                    {
                                        String name = listProdTypes.get(i);
                                %>
                            <li><a href="index.jsp?categ=<%=i+1%>"><%=name%></a></li>
                                <% } %>
                              <% if (user != null && user.getUserType() == 3) { %>
	                             <li class="nav-header"></li>
	                             <li class="nav-header">Product Options</li>
	                             <li><a href="addproduct.jsp">Add New Product</a></li>
	                             <% } %>
	                             <% if (user != null && user.getUserType() == 4) { %>
	                             <li class="nav-header"></li>
	                             <li class="nav-header">Accounting Options</li>
	                             <li><a href="viewsales.jsp">View Financial Records</a></li>
	                             <% } %>
	                             <% if (user != null && user.getUserType() == 2) { %>
	                             <li class="nav-header"></li>
	                             <li class="nav-header">Admin Options</li>
	                             <li><a href="createaccount.jsp">Create a Manager Account</a></li>
	                             <% } %>
                        </ul>
                    </div><!--/.well -->
                </div><!--/span-->

                <div class="span9">
                    <div class="hero-unit">
                        <% if (userName != null)
                            {%>
                        <h1>Hello, <%=userName%>!</h1>
                        <p>Good morning friend, you are so awesome. Everything is awesome. Everything is cool when you are part of a team.</p>
                        <% } else
                        { %>
                        <h1>Hello, GUEST!</h1>
                        <p>Why don't you register, guest? Login now to order items!</p>
                        <% } %>
                    </div>
  					<div id = "showItems">
                    <%
                        for (int ctr = 0; ctr < listProducts.size();)
                        { %>
                    <div class="row-fluid">
                        <% for (int col = 0; col < 3 && ctr < listProducts.size(); col++)
                            {%>
                        <div class="span4">
                            <div class="card">
                                <h2 class="card-heading simple"><%=listProducts.get(ctr).getName()%></h2>
                                <div class="card-body">
                                    <p><%=listProducts.get(ctr).getDescription()%></p>
                                    <p><% if (userName != null)
                                        {%> 
                                        <a class="btn" href="items.jsp?item=<%=listProducts.get(ctr).getId()%>">View details »</a>
                                        <% } %></p> 
                                </div>
                            </div>
                        </div>
                        <%ctr++;
                            }%>
                    </div>    
                    <%}%>
                    
                    </div>
                  
                </div><!--/.fluid-container-->
                <!--div id="footer">
                    <div class="wrap">
                        <p></p>
                    </div>
                </div-->
                
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

                </body>
                </html>