<!DOCTYPE html>
<html>
<header>
	<title>Green Grass</title>
	<meta charset="utf-8"/>


  <link rel="stylesheet" href="/static/style.css">
  <link rel="icon" href="/static/img/icon.svg" sizes="48x48">
    <script>
        function showWindow(id){
            window.location.assign('/info?_id=' + id)
        }
    </script>
</header>
<body>

	<header>
		<div class="logo">
			<a href="/">
				<img class="logoImg" src="/static/img/Гросс.png" width="80px" height="80px" alt="#" class="image">
			</a>

			
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
        <nav class="search">
            <form action="/find" method="get" enctype="application/x-www-form-urlencoded">

            <input type="text" class="search-imput" name="name" placeholder="Поиск товара" />
            <button class="search-button" type="submit"> <span>Найти</span></button>

            </form>
        </nav>
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
                    <#list components! as comp>

                            <div class="cell">
                                <div class="cell_inside">
                                    <div class="product-wrap">
                                        <div class="product-item">
                                            <img src=${comp.im.image!}>
                                        </div>
                                        <div class="product-title">
                                            <div class="wrapper__name">
                                                <p>${comp.name!}</p>
                                            </div>
                                            <span class="product-price">${comp.price!}</span>
                                            <div class="product-buttons">
                                                <form action="/" method="post" enctype="application/x-www-form-urlencoded" id="form1"></form>
                                                <button type="submit" class="buttonBuy" form="form1" name="_id" value=${comp._id.value.toHexString()}><span>В корзину</span></button>
                                                <button class="buttonDescrip" onclick=showWindow("${comp._id.value.toHexString()}") >
                                                    <span >Описание</span>
                                                </button>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                    <#else>
                        No components
                    </#list>


                </div>
            </div>
        </div>
    </main>


</body>
</html>
