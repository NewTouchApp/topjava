<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<form id="form1" name="form1" method="post" action="users">
    <table width="300" border="1">
        <tr>
            <td><label>User Selection </label>&nbsp;</td>
            <td><label>
                <select name="user" size="2" multiple="multiple" tabindex="1">
                    <option value="1">One</option>
                    <option value="2">Two</option>
                </select>
            </label>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td><input type="submit" name="Submit" value="Authorised" tabindex="2" /></td>
        </tr>
    </table>
</form>
</body>
</html>