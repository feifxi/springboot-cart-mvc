const checkoutBtn = document.querySelector('.checkoutBtn');
if(checkoutBtn){
    checkoutBtn.addEventListener('click',()=>{
        alert("You're broke")
    })
}


function updateCart(productCode, options){
    const xHttp = new XMLHttpRequest();
    // Handle response
    xHttp.onload = function () {
        if (this.status == 200){
            const cart = JSON.parse(this.responseText)
            const cartItem = cart.allItem.find(ci => ci.product.productCode == productCode);
            const { quantity } = cart;
            // Update Navbar cart
            updateCartDOM(quantity);

            // Update Cart Section
            const itemQuantity = document.getElementById(`quantity-${productCode}`)
            const cartItemTotal = document.getElementById(`cartItem-total-${productCode}`)
            const cartTotalSummary = document.getElementById('cart-total-summary')

            if (!cartItem) {
                // remove cart item from DOM
                const removeItem = document.getElementById(productCode)
                document.querySelector('.cart-item-list').removeChild(removeItem)
            }
            else {
                // Update Quantity Number and Total Price of CartItem
                itemQuantity.textContent = cartItem.quantity;
                cartItemTotal.textContent = '$' + cartItem.totalString;
            }
            cartTotalSummary.textContent = 'Total : $' + cart.cartTotalString;
            // there is no item in cart
            if (quantity <= 0){
                document.getElementById('cart-noItem').classList.remove('hide')
                document.getElementById('cart-summary').classList.add('hide')
            }
        }
        else {
            window.location = '/login'
        }
    }
    // Send Request
    if (options === 'add') xHttp.open('GET', `cart/add?productCode=${productCode}`);
    else if (options === 'remove') xHttp.open('GET', `cart/remove?productCode=${productCode}`);
    xHttp.send();
}


function updateCartDOM(quantity){
    const cartDOM = document.querySelector('.cart')
    const cartNumberDOM = document.getElementById('cart-number')
    if (quantity <= 0){
        cartDOM.classList.remove('active')
        cartNumberDOM.classList.remove('cart-item-number')
        cartNumberDOM.textContent = '';
    }
    else {
        cartDOM.classList.add('active')
        cartNumberDOM.classList.add('cart-item-number')
        cartNumberDOM.textContent = quantity;
    }
}