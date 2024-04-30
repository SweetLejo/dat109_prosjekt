<%--
  Created by IntelliJ IDEA.
  User: ivermalme
  Date: 10/04/2024
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="no">

<head>
    <title>Prosjekt redigering</title>
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

<h2>Prosjekt redigering</h2>
<p style="color:red;">${feilmeldinger}</p>
    <p style="color:green;">${suksessmelding}</p>


<fieldset id="rot">

    <c:choose>
        <c:when test="${prosjekt eq null}">

            <form action="${pageContext.request.contextPath}/redigerProsjekt" method="post">

                <label>Prosjekt ID<br>
                    <input type="text" name="prosjekt_id" id="prosjekt_id" value="${prosjekt_id}"/><br>
                </label>
                <label>Nytt prosjektnavn<br>
                    <input type="text" name="prosjektnavn" id="prosjektnavn"/><br>
                </label>

                <br>
                <button id="redigerProsjekt" type="submit">Lagre endringer</button>
            </form>
        </c:when>
    </c:choose>

</fieldset>

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
    function showInputFields() {
        document.getElementById("input-fields").style.display = "block";
    }
</script>

</body>
</html>





