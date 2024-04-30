<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="no">
<head>
    <title>Endre passord</title>
    <script src="js/endrepassordvalidering.js" defer></script>
    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    
</head>
<body>

<h2>Endre passord</h2>
<p style="color:red;">${endrePassordFeilmelding}</p>

<fieldset>
	<form method="post" action="${pageContext.request.contextPath}/endrepassord">
		<table class="center">
    		<tr>
       			<td>
       	 			<label for="gammeltpassord">Gammelt passord</label> 
       	 		</td>
       	 		<td>
       	 			<input type="password" name="gammeltpassord" id="gammeltpassord"/>
       	 		</td>
       	 	</tr>
       	 	<tr>
       	 		<td>
       	 			<label for="passord">Nytt passord:</label> 
       	 		</td>
       	 		<td>
					<input type="password" name="nyttpassord" id="nyttpassord"/>
				</td>
       	 	</tr>
       	 	<tr>
       	 		<td>
        			<label for="passord">Bekreft nytt passord:</label>
        		</td>
       	 		<td>
        			<input type="password" name="gjentattnyttpassord" id="gjentattnyttpassord"/>
        		</td>
       	 	</tr>
		</table>  
		<table class="center">
			<tr>
       	 		<td>
        			<button class="center button-base" id="submit-btn" type="submit">Endre passord</button>
	        	</td>
	    	</tr>
		</table>
	</form>

	</fieldset>

</body>
</html>