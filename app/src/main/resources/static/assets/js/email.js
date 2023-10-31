function sendEmail(event) {
    event.preventDefault();

    var name = document.getElementById('contact-name').value;
    var from = document.getElementById('contact-email').value;
    var message = document.getElementById('contact-message').value;
    var xmlhttp;

    if (window.XMLHttpRequest) {
        // code for modern browsers
        xmlhttp = new XMLHttpRequest();
    } else {
        // code for old IE browsers
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.open('POST', '/api/email', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/json')

    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            if (this.status == 204) {
                document.getElementById('contact-form').reset()
                toastr.options = {
                    positionClass: 'toast-bottom-left'
                }
                toastr.success('Merci pour votre message ! Nous y donnerons suite dans les plus brefs délais.')
            } else if (this.status == 400 ) {
                toastr.options = {
                    positionClass: 'toast-bottom-left'
                }
                toastr.error('Il semblerait que votre adresse email est incorrecte.')
            } else {
                toastr.options = {
                    positionClass: 'toast-bottom-left'
                }

                toastr.error('Une erreur inattendue est survenue. Veuillez nous en excuser.')
            }
        }
    };

    xmlhttp.send(JSON.stringify({
        name: name,
        from: from,
        message: message
    }));
}
