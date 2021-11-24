<!DOCTYPE html>
<html>
<head>
	<title>Green Grass</title>
	<meta charset="utf-8"/>


  <link rel="stylesheet" href="/static/styleCart.css">
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
  <main>
      <div class="name">
          <h1>Корзина</h1>
      </div>
      <div class="deleteAll">
          <form action="/basket/clear" method="post" enctype="application/x-www-form-urlencoded">
          <button class="deleteall-button" type="submit"> <span>Очистить корзину</span></button>
          </form>
      </div>
    <div class="catalog">
      <#list session_comp! as comp>

      <div class="cell">
        <div class="cell_inside">
         <div class="product-wrap">
          <div class="product-item">
            <img src=${comp.im.image!}>

          </div>
          <div class="product-title">
            <div class="wrapper__name">
              <p>${comp.name!} </p>
            </div>
            <span class="product-price">${comp.price!}</span>
            <div class="product-buttons">
                <form action="/basket" method="post" enctype="application/x-www-form-urlencoded">
                    <input type="hidden"  name="_id" value=${comp._id.value.toHexString()}>
                    <input type="submit" class="buttonDelete" value="Удалить"/>
                </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <#else>
    Basket empty
    </#list>
  </div>
</main>



    <div class="buyall">
        <form action="/createOrder" method="get" enctype="application/x-www-form-urlencoded" id="form1"></form>
        <button class="buyall-button" form="form1" > <span>Оформить заказ</span></button>
    </div>
</body>
</html>
