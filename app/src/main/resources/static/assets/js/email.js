toastr.options = {
    positionClass: 'toast-bottom-left'
};

function buildRequest() {
    const body = {
        from: document.getElementById('contact-email').value,
        message: document.getElementById('contact-message').value,
        name: document.getElementById('contact-name').value,
    };

    return {
        body: JSON.stringify(body),
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
    };
}

async function sendRequest(url, request) {
    return await fetch(url, request);
}

function notifyError() {
    toastr.error('Une erreur inattendue est survenue. Veuillez nous en excuser.');
}

function notifyBadRequest() {
    toastr.error('Il semblerait que votre adresse mail est incorrecte.');
}

function notifySuccess() {
    const form = document.getElementById('contact-form');
    form.reset();
    toastr.success('Merci pour votre message ! Nous y donnerons suite dans les plus brefs délais.');
}

function handleResponse(response) {
    if (response.ok) {
        notifySuccess();
    } else if (response.status === 400) {
        notifyBadRequest();
    } else {
        notifyError();
    }
}

async function sendEmail(event) {
    event.preventDefault();
    const request = buildRequest();
    const url = '/api/email';

    try {
        const response = await fetch(url, request);
        handleResponse(response);
    } catch (error) {
        notifyError();
    }
}
