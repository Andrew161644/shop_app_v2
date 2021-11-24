<!DOCTYPE html>
<html>
<head>
  <title>Gross Food</title>
  <meta charset="utf-8"/>

  <!-- <LINK REL="STYLESHEET" HREF="CSS/STYLE.CSS">-->	
  <link rel="stylesheet" href="/static/styleAccount.css">
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
  <h1>Личный кабинет</h1>
</div>


  <div class="wrapper-content">
      <form action="/accaunt" method="post" enctype="application/x-www-form-urlencoded">
          <div class="wrapper-content__form">
              <h2>Покупатель</h2>
              <div class="wrapper-content__name">
                  <input type="text" class="name1" name="username" value="${user.username}" placeholder="Фамилия"/>
                  <input type="text" class="name2" name="password" value="${user.password}" placeholder="Пароль"/>
              </div>
          </div>
          <h2>Ваш адрес</h2>

          <div class="wrapper-content__address">

              <input type="text" class="address1" name="street" value="${user.adress.street}" placeholder="Улица"/>
              <input type="text" class="address2" name="house" value="${user.adress.house}" placeholder="Дом"/>
              <input type="text" class="address3" name="room" value="${user.adress.room}" placeholder="Кв"/>

          </div>

          <div class="wrapper-content__button">
              <button class="buttonsave" type="submit"><span>Сохранить изменения</span></button>
          </div>
      </form>

      <div class="wrapper-content__select">
          <h2>Список идентификаторов заказов</h2>

        <div class="wrapper-content__list">
            <#list user.orders! as order>
                <div class="list__elem">
                    <span>${order._id.value.toHexString()}</span>
                </div>
            </#list>

        </div>
    </div>
</div>

</body>
</html>
