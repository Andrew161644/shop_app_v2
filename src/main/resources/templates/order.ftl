<!DOCTYPE html>
<html>
<head>
    <title>Green Grass</title>
    <meta charset="utf-8"/>

    <!-- <LINK REL="STYLESHEET" HREF="CSS/STYLE.CSS">-->
    <link rel="stylesheet" href="/static/styleOrder.css">
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
<div class="name">
    <h1>Оформление заказа</h1>
</div>
<form action="/newOrder" method="post" enctype="application/x-www-form-urlencoded">
    <div class="wrapper-content">
        <h2>Ваш адрес</h2>
        <div class="wrapper-content__address">

            <input type="text" class="address1" name="street" value="${user.adress.street}" placeholder="Улица"/>
            <input type="text" class="address2" name="house" value="${user.adress.house}" placeholder="Дом"/>
            <input type="text" class="address3" name="room" value="${user.adress.room}" placeholder="Кв"/>
        </div>


        <div class="wrapper-content__form">
            <h2>Способ доставки</h2>
            <div class="radio-container">
                <div class="form-item radio-btn nth-2">
                    <input type="radio" name="option1" id="radio1" checked>
                    <label for="radio1">Доставка</label>
                </div>
                <div class="form-item radio-btn nth-2">
                    <input type="radio" name="option1" id="radio2">
                    <label for="radio2">Самовывоз</label>
                </div>
            </div>
        </div>

        <div class="wrapper-content__select">
            <h2>Пункт доставки/самовыдачи</h2>


            <div class="select-deliv">
                <select>
                    <option value="1">Вариант 1</option>
                    <option value="2">Вариант 2</option>
                </select>
            </div>


        </div>
        <div class="wrapper-content__form">
            <h2>Способ оплаты</h2>
            <div class="radio-container">
                <div class="form-item radio-btn nth-2">
                    <input type="radio" name="option2" id="radio3" checked>
                    <label for="radio3">Картой</label>
                </div>
                <div class="form-item radio-btn nth-2">
                    <input type="radio" name="option2" id="radio4">
                    <label for="radio4">Наличными</label>
                </div>
            </div>
        </div>


        <div class="wrapper-content__button">
            <button class="buttonbuy" type="submit"><span>Купить</span></button>
        </div>
    </div>

</form>


</body>
</html>
