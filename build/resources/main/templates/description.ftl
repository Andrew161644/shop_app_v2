<!DOCTYPE html>
<html>
<head>
    <title>Green Grass</title>
    <meta charset="utf-8"/>

    <!-- <LINK REL="STYLESHEET" HREF="CSS/STYLE.CSS">-->
    <link rel="stylesheet" href="/static/styleDescrip.css">
    <link rel="icon" href="/static/img/icon.svg" sizes="48x48">
</head>
<body>

<header>
    <div class="logo">
        <a href="/">
            <img class="logoImg" src="/static/img/Гросс.png" width="80px" height="80px" alt="#" class="image">
        </a>


    </div>
    </div>
    <div class="bottom-header">
        <ul class="bottom-header__actions actions-header">
            <li>
                <a href="/accaunt" class="actions-header__acc"> <span>Личный кабинет</span></a>
            </li>
            <li>
                <a href="/login"  class="actions-header__login"> <span>Вход</span></a>
            </li>
            <li><a href="/registration" class="actions-header__reg"> <span>Регистрация</span></a></li>
            <li><a href="/basket" class="actions-header__cart"> <span>Корзина</span></a></li>

        </ul>

    </div>
</header>
<main class="page">


    <div class="page__container">
        <aside class="page__side">
            <nav class="page__menu menu-page">
                <div class="menu-page__header">
                    <div class="menu-page__title">
                        Каталог товаров
                    </div>


                </div>
                <div class="menu-page__body">
                    <ul class="menu-page__list">
                        <li><a href="/?cat=all" class="menu-page__link">Все товары</a></li>
                        <#list cats! as cat>
                            <li><a href="/?cat=${cat._id.value.toHexString()}" class="menu-page__link">${cat.name}</a></li>
                        <#else>
                            No categories
                        </#list>

                    </ul>

                </div>

                <div class="menu-page__body">

                </div>
            </nav>
        </aside>

        <div class="page__content">

            <div class="catalog">

                <div class="cell">
                    <div class="cell__inside">
                        <div class="cell__name">
                            <p>Описание товара</p>
                        </div>
                        <div class="cell__wrap product">
                            <div class="product__item">
                                <img src=${component.im.image}>

                            </div>
                            <div class="product__title" >
                                <div class="title__name">
                                    <h1>${component.name}</h1>
                                </div>

                                    <#list component.specifications! as k , v>
                                        <div class="product__discription">${k} -- ${v}</div>
                                    </#list>
                                <span class="product__price">${component.price}</span>
                                <span class="product__price">${component.status}</span>
                                <div class="product__buttons">
                                    <form action="/" method="post" enctype="application/x-www-form-urlencoded" id="form1"></form>
                                    <button type="submit" class="buttonBuy" form="form1" name="comp" value=${component._id.value.toHexString()}><span>В корзину</span></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>


</body>
</html>
