<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Welcome</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>

<body>
<h1>Add User</h1>
<form action="/user/edit" method="post">
    First name:
    <br><input type="text" name="firstName" value="${user.firstName}"><br>
    Last name:
    <br><input type="text" name="lastName" value="${user.lastName}"><br>
    Age:
    <br><input type="text" name="age" value="${user.age}"><br>
    Gender:
    <br><input type="text" name="gender" value="${user.gender}"><br>
    <input type="hidden" name="id" value="${user.id}">
    <input type="submit" value="Submit">
</form>
</body>
</html>