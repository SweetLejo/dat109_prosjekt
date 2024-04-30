<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="no">

<head>
    <title>Timeregistrering</title>
    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<script src="js/timevalidering.js" defer></script>
<div class="center">
<table class="navbar">
    <tr>
                <td>
                    <form action="${pageContext.request.contextPath}/deltagerliste" method="get">
                        <button class="button-base" type="submit">Deltagerliste</button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/logut" method="post">
                        <button class="fa fa-sign-out button-base logout" type="submit">Logg ut</button>
                    </form>
                </td>
    </tr>
</table>
</div>

<p class="feilmelding">${feilmelding}</p>

<div class="flex-container center">
<fieldset id="rot">
    <legend>Timeregistrering</legend>
    <form action="${pageContext.request.contextPath}/registrertime" method="post">
        <div class="new-line center">
            <label>Prosjekt ID<br>
                <input type="text" name="prosjekt_id" id="prosjekt_id" value="${prosjekt_id}"/><br></label>
        </div>
        <div class="new-line center">
        <label>Antall timer<br>
            <input type="text" name="antallTimer" id="antallTimer" value="${antallTimer}"/><br></label>
        </div>
        <div class="new-line center">
        <button class="button-base make-user" id="submit-btn" type="submit">Registrer</button>
        </div>
    </form>

    <c:if test="${prosjekt != null}">

        <h3>Vellykket registrering</h3>
        <p>Prosjekt ID: ${time.prosjekt.prosjekt_id}</p>
        <p>Prosjektnavn: ${prosjekt.navn}</p>
        <p>Antall timer: ${time.antallTimer}</p>

    </c:if>

</fieldset>
<table class="db_data">
    <tr>
        <th>Prosjekt id</th>
        <th>Navn</th>
    </tr>
    <c:forEach var="prosjekt" items="${prosjekter}">
        <tr>
            <td>${prosjekt.prosjekt_id}</td>
            <td>${prosjekt.navn}</td>
        </tr>
    </c:forEach>
</table>

</div>

</body>
</html>
