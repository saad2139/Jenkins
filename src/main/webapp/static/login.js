function login() {
    let username = document.getElementById('username').value;
    let password = document.getElementById('key').value;
    let xhttp = new XMLHttpRequest();
    xhttp.onload = (resp) => {
        if (xhttp.status === 200) {
            window.location = './home';
        } else {
            alert('Invalid Credentials')
        }
    }
    let user = {
        "username": username,
        "password": password
    }
    xhttp.open('POST', './');
    xhttp.send(JSON.stringify(user));

}


