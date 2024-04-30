<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="no">

<head>
    <title>Timeredigering</title>
    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<script src="js/timevalidering.js" defer></script>
<table class="navbar">
    <tr>
        <c:choose>
            <c:when test="${bruker eq null}">
                <td>
                    <form action="${pageContext.request.contextPath}/login" method="get">
                        <button type="submit">Logg inn</button>
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

<h2>Timeredigering</h2>
<p style="color:red;">${feilmelding}</p>

<fieldset id="rot">

    <c:choose>
        <c:when test="${time eq null}">

            <form action="${pageContext.request.contextPath}/velgtime" method="post">

                <label>Time ID<br>
                    <input type="text" name="time_id" id="time_id" value="${time_id}"/><br></label>

                <br>
                <button id="submit-btn" type="submit">Velg time</button>

            </form>

        </c:when>
        <c:otherwise>

            <form action="${pageContext.request.contextPath}/redigertime" method="post">

                <label>Nytt antall timer<br>
                    <input type="text" name="antallTimer" id="antallTimer" value="${antallTimer}"/><br></label>

                <br>
                <button id="redigertime" type="submit">Lagre endringer</button>

            </form>

            <form action="${pageContext.request.contextPath}/sletttime" method="post">

                <br>
                <button type="submit">Slett time</button>

            </form>

        </c:otherwise>
    </c:choose>
</fieldset>

<h3>Registrerte timer</h3>
<table class="db_data">
    <tr>
        <th>Bruker</th>
        <th>Time ID</th>
        <th>Antall timer</th>
        <th>Prosjekt ID</th>
        <th>Prosjekt navn</th>
    </tr>
    </tr>
    <c:forEach var="time" items="${timeliste}">
        <tr>
            <td>${time.bruker.mobil}</td>
            <td>${time.time_id}</td>
            <td>${time.antallTimer}</td>
            <td>${time.prosjekt.prosjekt_id}</td>
            <td>${time.prosjekt.navn}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
