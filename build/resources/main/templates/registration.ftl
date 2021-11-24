<!DOCTYPE html>
<html>
<head>
    <title>Green Grass</title>
    <meta charset="utf-8"/>

    <!-- <LINK REL="STYLESHEET" HREF="CSS/STYLE.CSS">-->
    <link rel="stylesheet" href="/static/styleReg.css">
    <link rel="icon" href="/static/img/icon.svg" sizes="48x48">
</head>
<body>

<header>
    <div class="logo">
        <a href="/">
            <img class="logoImg" src="/static/img/Гросс.png" width="80px" height="80px" alt="#" class="image">
        </a>

    </div>
</header>
<form action="/registration" method="post" enctype="application/x-www-form-urlencoded">
    <div class="name">
        <h1>Регистрация</h1>
    </div>
    <div class="wrapper-content">
        <div class="wrapper-content__acc">
            <h2>Логин</h2>
            <input type="text" name="username" class="account"/>
        </div>
        <div class="wrapper-content__pass">
            <h2>Пароль</h2>
            <input type="password" name="password" class="password"/>
        </div>
        <div class="wrapper-content__button">
            <div><input class="buttonjoin" type="submit" value="Зарегистрироваться"/></div>
        </div>
    </div>
</form>
</body>
</html>
