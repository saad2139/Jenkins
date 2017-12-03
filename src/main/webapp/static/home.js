
function nextPage(button) {
    let xhttp = new XMLHttpRequest();
    switch (button.id) {
        case 'history':
            xhttp.open('GET', './home');
            xhttp.send();
            window.location = './history';
            break;
        case 'manageReimbursements':
            xhttp.open('GET', './home');
            xhttp.send();
            window.location = './manageReimbursements';
            break;
        case 'logout':
            window.location = './';
            break;
        default:
            break;
    }

}
