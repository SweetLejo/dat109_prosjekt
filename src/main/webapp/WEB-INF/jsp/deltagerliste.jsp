<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="no">
<head>
    <title>Brukervalg</title>
    <script defer src="js/deltagerliste.js"></script>
    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>




<br>

<div class="tab">
    <button class="tablinks fa" onclick="expandView(event, 'Min-side')"> Min Side </button>
    <button class="tablinks fa" onclick="expandView(event, 'Brukere')">Brukere</button>
    <button class="tablinks fa" onclick="expandView(event, 'Prosjekter')">Prosjekter</button>
</div>


<div id="Min-side" class="tabcontent">

    <div class = "center">
        <table>
            <tr>
                <th>
                    Innlogget som: ${bruker.fornavn} ${bruker.etternavn}
                </th>
            </tr>
            <tr>
                <th>
                    Ditt telefonnommer: ${bruker.mobil}
                </th>
            </tr>
        </table>
    </div>


    <div class = "center">
        <h2>Timehåndteringsvalg: </h2>
    <table class="navbar">
        <tr>
            <td>
                <form action="${pageContext.request.contextPath}/registrertime" method="get">
                    <button class="button-base" type="submit">Registrer time</button>
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/redigertimer" method="get">
                    <button class="button-base" type="submit">Rediger/slett time</button>
                </form>
            </td>
        </tr>
    </table>
    </div>

    <div class = "center">
        <h2>Innstillinger: </h2>
    <table class="navbar">
        <tr>
            <td>
                <form action="${pageContext.request.contextPath}/endrepassord" method="get">
                    <button class="button-base" type="submit">Endre passord</button>
                </form>
            </td>
        </tr>
    </table>
    </div>

    <div class="center">
    <table class="db_data">
        <tr>
            <th> Prosjekt: </th>
            <th> Antall timer: </th>
        </tr>
        <c:forEach var="time" items="${timeliste}">
            <tr>
                <c:choose>
                    <c:when test="${time.bruker.mobil eq bruker.mobil}">
                        <td>${time.prosjekt.navn}</td>
                        <td>${time.antallTimer}</td>
                    </c:when>
                </c:choose>

            </tr>
        </c:forEach>
    </table>
    </div>
    <div class="new-line center">
        <c:set var="totalTimer" value="0" /> <!-- Initial counter variable for the sum -->

        <c:forEach var="time" items="${timeliste}">
            <c:if test="${time.bruker.mobil eq bruker.mobil}">
                <!-- If condition is true, add antallTimer to totalTimer -->
                <c:set var="totalTimer" value="${totalTimer + time.antallTimer}" />
            </c:if>
        </c:forEach>

        <!-- Display the sum -->
        <p>Totalt antall timer: ${totalTimer}</p>
    </div>


</div>




<div id="Brukere" class="tabcontent">
    <div class="center">
    <input type="text" class="search-input" onkeyup="searchTable('Brukere', 0)" placeholder="Søk etter navn..." title="Type in a name">
    </div>
    <div class="center">
        <table class = "db_data" id="BrukereTable">
        <tr>
            <th>Navn</th>
            <th>Mobil</th>
        </tr>
        <c:forEach var="ansatt" items="${ansatte}">
            <tr style=<c:if test="${ansatt.mobil eq bruker.mobil}">"background-color:#418941"</c:if>>
                <td>${ansatt.fornavn} ${ansatt.etternavn}</td>
                <td>${ansatt.mobil}</td>
            </tr>
        </c:forEach>
    </table>
    </div>
</div>

<div id="Prosjekter" class="tabcontent">

    <div class="center">
    <h2>Prosjektvalg: </h2>
    <table class="navbar">
        <tr>
            <td>
                <button class="button-base" onclick="location.href='${pageContext.request.contextPath}/opprettprosjekt'" type="button">Opprett prosjekt</button>
            </td>
            <td>
                <button class="button-base" onclick="location.href='${pageContext.request.contextPath}/redigerProsjekt'" type="button">Rediger prosjekt</button>
            </td>
            <td>
                <button class="button-base" onclick="location.href='${pageContext.request.contextPath}/slettprosjekt'" type="button">Slett prosjekt</button>
            </td>
        </tr>
    </table>
    </div>


    <div class="center">
    <input type="text" class="search-input" onkeyup="searchTable('Prosjekter', 1)" placeholder="Søk etter prosjekt..." title="Type in a project name">
    </div>
    <div class="center">
    <table class = "db_data" id="ProsjekterTable">
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

</div>


<div class="center">
    <form action="${pageContext.request.contextPath}/logut" method="post">
        <button class="button-base logout fa fa-sign-out center" type="submit"> Logg ut </button>
    </form>
</div>
</body>
</html>
