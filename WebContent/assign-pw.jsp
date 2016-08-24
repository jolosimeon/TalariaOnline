<%@page import="java.util.ArrayList"%>
<%@page import="model.Model"%>
<%@page import="objects.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Assign Passwords</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js">
	</script>
 	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js">
 	</script>
 	<link rel="stylesheet" type="text/css" href="css/styles.css">
 	<link rel="stylesheet" href="http://jqueryvalidation.org/files/demo/site-demos.css">
 </head>
 <body>
 <%
            String userName = null;
            String id = null;
            Cookie[] cookies = request.getCookies();
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
            }


             session = request.getSession();
             User user = (User) session.getAttribute("user");
             if (user != null)
             {
             	userName = user.getFirstName() + " " + user.getMiddleInitial() + ". " + user.getLastName();
             }
             String error = request.getParameter("error");
             
             ArrayList<User> users = Model.getAllUsers();
        %>
 
 		<%ArrayList<String> listUserTypes = new ArrayList<String>();
 		  listUserTypes.add("Customer");
 		  listUserTypes.add("Administrator");
		  listUserTypes.add("Product Manager");
		  listUserTypes.add("Accounting Manager");
 		%>
     	<nav class="navbar navbar-custom" style="margin-bottom:0px;">
          <div class="navbar-header">
                <a class="navbar-link navbar-brand" href="index.jsp">Talaria Online</a>
            </div>
      </nav>
	  <div class="container pagecontent">
	        <div class="headroom"></div>        
	        <div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
	          <div class="panel panel-default">
	            <div class="panel-body">
	              <h3 class="thin text-center">Assign Temporary Password</h3>
	            <table class="table">
	              <thead>
	                <tr>
	                  <th>Username</th>
	                  <th>Role</th>
	                  <th>Password</th>
	                </tr>
	              </thead>
	              <tbody>
	              <%for (int i = 0; i < users.size(); i++) {
	            	 %>
	                <tr>
	                <form action = "AssignPassword" method = "post">
	                  <td><%=users.get(i).getUsername() %></td>
	                  <td><%=listUserTypes.get(users.get(i).getUserType()-1) %>
	                  <td><input type="password" required name = "pw"><input type = "hidden" name = "username" value = "<%=users.get(i).getUsername()%>"></td>
	                  <td><button class="btn btn-default btn-xs" type="submit" >Change</button></td>
	                 </form>
	                </tr>
	                <%} %>
	              </tbody>
	            </table>
	          </div>
            </div>
          </div>

    </div>
    </div>
    <!--  <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>-->
	<script src="http://jqueryvalidation.org/files/dist/jquery.validate.min.js"></script>
	<script src="http://jqueryvalidation.org/files/dist/additional-methods.min.js"></script>
    
    <!--<div class="row navbar-fixed-bottom">
      <div class = "col-md-12">
        <hr class = "break"/>
        <ul>
          <li class = "footer">? 2014</li>
          <li class = "footer"><a href = "#aboutus">About Us</a></li>
          <li class = "footer"><a href = "#contactus">Contact Us</a></li>
        </ul>
        <p style = "padding-left:4em">Icons from Glyphicons Free.</p>
    </div>
  </div>-->
 </body>
</html>