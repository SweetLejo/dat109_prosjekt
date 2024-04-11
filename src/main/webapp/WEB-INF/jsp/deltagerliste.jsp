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

<h2>Prosjektvalg</h2>
<table class="navbar">
    <tr>
        <td>
    		<button onclick="location.href='${pageContext.request.contextPath}/opprettprosjekt'" type="button">Opprett prosjekt</button>
        </td>
        <td>
            <button onclick="location.href='${pageContext.request.contextPath}/redigerProsjekt'" type="button">Rediger prosjekt</button>
        </td>
        <td>
            <button onclick="location.href='${pageContext.request.contextPath}/slettprosjekt'" type="button">Slett prosjekt</button>
        </td>
    </tr>
</table>


<br>

<div class="tab">
    <button class="tablinks fa" onclick="expandView(event, 'Brukere')">Brukere online</button>
    <button class="tablinks fa" onclick="expandView(event, 'Prosjekter')">Registrerte prosjekter</button>
    <button class="tablinks fa" onclick="expandView(event, 'Timer')">Registrerte timer</button>
    <button class="tablinks fa" onclick="expandView(event, 'Min-side')"> Min Side </button>
</div>

<!-- Tab content -->

<div id="Min-side" class="tabcontent">
        <div class="center">
            <p> Innlogget som: ${bruker.fornavn} ${bruker.etternavn}</p>
        </div>
        <div class="center">
            <p> Ditt telefonnommer: ${bruker.mobil} </p>
        </div>

            <table class="center">
                <tr>
                    <th> Antall timer: </th>
                    <th> Prosjekt: </th>
                </tr>
                <c:forEach var="time" items="${timeliste}">
                    <tr>
                        <c:choose>
                            <c:when test="${time.bruker.mobil eq bruker.mobil}">
                                <td>${time.antallTimer}</td>
                                <td>${time.prosjekt.navn}</td>
                            </c:when>
                        </c:choose>

                    </tr>
                </c:forEach>
            </table>

        <div class = "center">
            <h2>Timehåndteringsvalg</h2>
        </div>
        <table class="navbar center">
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


<div id="Brukere" class="tabcontent">
    <div class="center">
    <input type="text" class="search-input" onkeyup="searchTable('Brukere', 0)" placeholder="Søk etter navn..." title="Type in a name">
    </div>
    <div class="center">
        <table class = "db_data" id="BrukereTable">
        <tr>
            <th align="left">Navn</th>
            <th align="left">Mobil</th>
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
    <input type="text" class="search-input" onkeyup="searchTable('Prosjekter', 1)" placeholder="Søk etter prosjekt..." title="Type in a project name">
    </div>
    <div class="center">
    <table class = "db_data" id="ProsjekterTable">
        <tr>
            <th align="left">Prosjekt id</th>
            <th align="left">Navn</th>
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

<div id="Timer" class="tabcontent">
    <div class="center">
    <input type="text" class="search-input" onkeyup="searchTable('Timer', 1)" placeholder="Søk etter timer..." title="Type in a time entry">
    </div>
    <div class = "center">
    <table class = "db_data" id="TimerTable">
        <tr>
            <th align="left">Time id</th>
            <th align="left">Antall</th>
            <th align="left">Mobil</th>
            <th align="left">Prosjekt id</th>
        </tr>
        </tr>
        <c:forEach var="time" items="${timeliste}">
            <tr>
                <td>${time.time_id}</td>
                <td>${time.antallTimer}</td>
                <td>${time.bruker.mobil}</td>
                <td>${time.prosjekt.prosjekt_id}</td>
            </tr>
        </c:forEach>
    </table>
    </div>
</div>

<br>

<div class="center">
    <form action="${pageContext.request.contextPath}/logut" method="post">
        <button class="button-base logout fa fa-sign-out center" type="submit"> Logg ut </button>
    </form>
</div>
</body>
</html>
