function confirmDialogue(message) {
    return confirm(message)
}
const deleteBtn = document.querySelectorAll('.deleteBtn')
deleteBtn.forEach(button => button.addEventListener('click', (e) => {
    if (!confirmDialogue('Delete this item?')) e.preventDefault()
}))


// Slider
const slides = document.querySelector('.slides');
const prevButton = document.querySelector('.prev');
const nextButton = document.querySelector('.next');

let currentIndex = 1;   // first real image
const totalSlides = slides.childElementCount // Total images including duplicates

function updateSlider() {
    slides.style.transition = "transform 0.3s ease-in-out";
    // slides.style.transform = `translateX(${-currentIndex * 100}%)`;
    slides.style.transform = `translateX(${-currentIndex * 100}vw)`;
    // console.log(-currentIndex * 100) // for debugging
}

// forward slide
nextButton.addEventListener('click', () => {
    currentIndex++;
    updateSlider();
    // Reset to the first real image when reaching the duplicate
    if (currentIndex === totalSlides - 1) {
        setTimeout(() => {
            // console.log('active forward')
            slides.style.transition = "none"; // Disable transition
            currentIndex = 1; // Jump to the first real image
            slides.style.transform = `translateX(${-currentIndex * 100}vw)`;
        }, 300); // Match the duration of the CSS transition
    }
});

// backward slide
prevButton.addEventListener('click', () => {
    currentIndex--;
    updateSlider();
    // Reset to the last real image when reaching the duplicate
    if (currentIndex === 0) {
        setTimeout(() => {
            // console.log('active backward')
            slides.style.transition = "none"; // Disable transition
            currentIndex = totalSlides - 2; // Jump to the last real image
            slides.style.transform = `translateX(${-currentIndex * 100}vw)`;
        }, 300);
    }
});

// Handle window resizing
window.addEventListener('resize', updateSlider);

// load the first real image
updateSlider()

// Auto Slide
const delayTime = 1000 * 4
setInterval(() => {
    nextButton.click()
}, delayTime)


// POP-UP
const closePopUpBtn = document.getElementById('close-popupBtn')
if (closePopUpBtn != null) {
    closePopUpBtn.addEventListener('click',()=>{
        document.getElementById('popup').style.display = 'none'
    })
}


// Snackbar Message
const statusMsg = document.querySelector('.snackbarMsg')
if (statusMsg) {
    setTimeout(() => {
        statusMsg.classList.add('show');
        setTimeout(() => {
            statusMsg.classList.remove('show');
            setTimeout(() => {
                statusMsg.style.display = 'none';
            }, 400);
        }, 6000);
    }, 100);
}



// Shopping cart
function updateCart(productCode){
    const xHttp = new XMLHttpRequest();
    // Handle response
    xHttp.onload = function () {
        const cart = JSON.parse(this.responseText)
        // console.log(cart)
        if (this.status == 200){
            const { quantity } = cart;
            // Update Navbar cart
            updateCartDOM(quantity);
        }
        else if (this.status == 400) {
            window.location = '/login'
        }
        else {
            alert('something went wrong...')
        }
    }
    // Send Request
    xHttp.open('GET', `cart/add?productCode=${productCode}`);
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