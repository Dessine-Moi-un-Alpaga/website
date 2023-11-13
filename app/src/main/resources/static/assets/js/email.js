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
    let error;

    try {
        const response = await fetch(url, request);

        if (!response.ok) {
            error = response.statusText;
        }
    } catch (networkError) {
        error = networkError;
    }

    if (error) {
        throw error;
    }
}

function notifyError() {
    toastr.error('Une erreur inattendue est survenue. Veuillez nous en excuser.');
}

function notifySuccess() {
    const form = document.getElementById('contact-form');
    form.reset();
    toastr.success('Merci pour votre message ! Nous y donnerons suite dans les plus brefs délais.');
}

async function sendEmail(event) {
    event.preventDefault();
    const request = buildRequest();
    const url = '/api/email';

    try {
        await sendRequest(url, request);
        notifySuccess();
    } catch (error) {
        notifyError();
    }
}
