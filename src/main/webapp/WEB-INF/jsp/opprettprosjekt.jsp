<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="no">
<head>

    <title>Opprett prosjekt</title>
</head>

<body>
<form action="/${pageContext.request.contextPath}/paamelding" method="post">
    <fieldset id="rot">

        <label>Prosjekt Id<br>
            <input type="text" name="id" id="prosjektid" value="${prosjekt.id}"/><br>
        </label>

        <label>Navn<br>
            <input type="text" name="navn" id="prosjektnavn" value="${prosjekt.navn}"/><br>
        </label>

        <br>
        <button onclick="location.href='${pageContext.request.contextPath}/prosjektopprettet'" type="button">Opprett prosjekt</button>

    </fieldset>
</form>
</body>
</html>
