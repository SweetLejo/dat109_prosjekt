<%--
  Created by IntelliJ IDEA.
  User: oyvin
  Date: 10.04.2024
  Time: 12:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Slett prosjekt</title>
</head>
<body>
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

<H2>Slett prosjekt</H2>
<p style="color:red;">${feilmeldinger}</p>
<p style="color:green;">${suksessmelding}</p>

<form action="${pageContext.request.contextPath}/slettprosjekt" method="post" id="slettProsjektForm">
    <fieldset>
        <label>Prosjekt Id<br>
            <input type="text" name="prosjekt_id" id="prosjekt_id" value="${prosjekt.prosjekt_id}"/><br>
            <p style="color:red;" id="feilmelding-prosjekt-id"></p>
        </label>
        <button type="submit" id="slettProsjektBtn">Slett prosjekt</button>
    </fieldset>
</form>

<h3>Registrerte prosjekter</h3>
<table>
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

<script>
    function idvalidering() {
        let prosjekt_idElement = document.getElementById("prosjekt_id");
        if (!prosjekt_idElement) {
            return;
        }
        prosjekt_idElement.setCustomValidity("");
        prosjekt_idElement.checkValidity();

        let prosjekt_id = prosjekt_idElement.value;
        let prosjekt_idRegEx = /^[0-9]{6}$/;

        if (!prosjekt_idRegEx.test(prosjekt_id)) {
            prosjekt_idElement.setCustomValidity("Prosjekt ID må være 6 siffer");
            document.getElementById("feilmelding-prosjekt-id").innerText = "Prosjekt ID må være 6 siffer";
        } else {
            // Skjul feilmelding når format på prosjekt-id er gyldig
            document.getElementById("feilmelding-prosjekt-id").innerText = "";
        }
    }

    function slettProsjekt() {
        var confirmDelete = confirm("Er du sikker på at du vil slette prosjektet? Dette vil også slette alle tilhørende timer");
        if (confirmDelete === true) {

            document.getElementById("slettProsjektForm").submit();
        } else {
            event.preventDefault(); // For å forhindre standard oppførsel av knappen (form submission)
        }
    }

    document.getElementById("slettProsjektBtn").addEventListener("click", slettProsjekt);
    document.getElementById("prosjekt_id").addEventListener("blur", idvalidering);
</script>


</body>
</html>
