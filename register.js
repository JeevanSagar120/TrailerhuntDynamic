/**
 * 
 */
    var userpassword = document.getElementById('password');
    var chkbtn = document.getElementById('chkbtn');
    var errorDetails = document.getElementById('error-details');

    // Password Visibility Toggle
    chkbtn.addEventListener('change', function () {
        if (userpassword.type === "password") {
            userpassword.type = "text";
        } else {
            userpassword.type = "password";
        }
    });