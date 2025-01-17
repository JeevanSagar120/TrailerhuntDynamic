/**
 * 
 */
document.getElementById('switch').addEventListener('change', function() {
    if (this.value === 'User') {
        window.location.href = 'login.html';
    }
});

var userpassword = document.getElementById('password');
var chkbtn = document.getElementById('chkbtn');
var errorDetails = document.getElementById('error-details');
var inpMail = document.getElementById("email");
var inpPassword = document.getElementById("password");

chkbtn.addEventListener('change', function () {
    userpassword.type = userpassword.type === "password" ? "text" : "password";
});

// Admin validation
let adminMail = "sagarjeevan120@gmail.com"; 
let adminPassword = "sagar@120"; 

let loginForm = document.getElementById("loginForm");
loginForm.addEventListener('submit', function(event) {
    event.preventDefault();

    if (adminMail === inpMail.value && adminPassword === inpPassword.value) {
        window.location.href = "adminDashboard.html"; 
    } else {
        errorDetails.innerText = "Invalid email or password."; 
    }
});
