<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Welcome</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>

<body>
<h1>Welcome</h1>
<a href="/userAdd" class="button">Add User</a>
<table class="container">
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Age</th>
        <th>Gender</th>

    </tr>
            <#list userList as row>
            <tr>
                <td>${row.firstName}</td>
                <td>${row.lastName}</td>
                <td>${row.age}</td>
                <td>${row.gender}</td>
            </tr>
            </#list>
</table>
</body>

</html>