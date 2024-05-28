var backend = "http://localhost:8080/api";
var api_login = backend + '/login';

var loginstate = {
    logged: false,
    user: { id: "", rol: "" }
};

async function checkuser() {
    let request = new Request(api_login + '/current-user', { method: 'GET' });
    const response = await fetch(request);
    if (response.ok) {
        loginstate.logged = true;
        loginstate.user = await response.json();
    } else {
        loginstate.logged = false;
        loginstate.user = { id: "", rol: "" };
    }
}

async function login() {
    let user = {
        nombreUsuario: document.getElementById("id").value,
        contrasena: document.getElementById("password").value
    };
    let request = new Request(api_login + '/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user)
    });
    const response = await fetch(request);
    if (!response.ok) {
        errorMessage(response.status);
        return;
    }
    loginstate.user = await response.json();
    loginstate.logged = true; // Establecer estado logged a true después del login exitoso
    if (loginstate.user.rol === "PROVEEDOR") {
        document.location = "/pages/facturar/view.html";
    } else if (loginstate.user.rol === "ADMINISTRADOR") {
        document.location = "/pages/proveedores/view.html";
    }
}

function logout(event) {
    event.preventDefault();
    let request = new Request(api_login + '/logout', { method: 'POST' });
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            errorMessage(response.status);
            return;
        }
        loginstate.logged = false;
        loginstate.user = { id: "", rol: "" }; // Restablecer el estado del usuario en el logout
        document.location = "/pages/bienvenida/view.html";
    })();
}

function errorMessage(status) {
    let error;
    switch (status) {
        case 404:
            error = "Registro no encontrado";
            break;
        case 409:
            error = "Registro duplicado";
            break;
        case 401:
            error = "Usuario no autorizado";
            break;
        case 403:
            error = "Usuario no tiene derechos";
            break;
        default:
            error = "Error desconocido";
    }
    window.alert(error);
}

// Expose functions to the global scope
window.login = login;
window.logout = logout;
window.checkuser = checkuser;
window.loginstate = loginstate;
