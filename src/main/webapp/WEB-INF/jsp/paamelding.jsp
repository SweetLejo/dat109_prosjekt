<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="no">
<head>

    <title>Påmelding</title>

    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

</head>

<body>
<table class="navbar">
    <tr>
        <c:choose>
            <c:when test="${bruker eq null}">
                <td>
                    <form action="${pageContext.request.contextPath}/login" method="get">
                        <button class="button-base sign-in fa fa-sign-in" type="submit"> Logg inn </button>
                    </form>
                </td>
            </c:when>
            <c:otherwise>
                <td>
                    <form action="${pageContext.request.contextPath}/deltagerliste" method="get">
                        <button type="submit">Deltagerliste</button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/logut" method="post">
                        <button type="submit">Logg ut</button>
                    </form>
                </td>
                <td>
                    Du er logget inn som ${bruker.fornavn} ${bruker.etternavn}
                </td>
            </c:otherwise>
        </c:choose>
    </tr>
</table>

<h2>Brukerkonto detaljer</h2>
<p style="color:red;">${feilmeldinger}</p>

<form action="${pageContext.request.contextPath}/paamelding" method="post">
    <fieldset id="rot">

        <label>Fornavn <br>
            <input type="text" name="fornavn" id="fornavn" value="${ansatt.fornavn}"/><br></label>

        <label>Etternavn<br>
            <input type="text" name="etternavn" id="etternavn" value="${ansatt.etternavn}"/><br></label>

        <label>Mobil (8 siffer)<br>
            <input type="text" name="mobil" id="mobil" value="${ansatt.mobil}"/><br></label>

        <label>Passord<br>
            <input type="password" name="passord" id="passord"/><br></label>
            
        <label>Passord repetert<br>
            <input type="password" name="passordRepetert" id="passordRepetert"/><br></label>


        <br>
        <button class="button-base fa fa-sign-in make-user" id="submit-btn" type="submit"> Opprett bruker </button>
        
    </fieldset>
</form>
<script src="js/validering.js" defer></script>
</body>
</html>
