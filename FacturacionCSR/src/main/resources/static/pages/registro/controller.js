var api = backend + '/registro';

var state = {
    item: {
        idProveedor: "",
        nombreProveedor: "",
        correoElectronico: "",
        numeroTelefono: "",
        direccion: "",
        nombreUsuario: "",
        contrasena: ""
    },
    mode: "" // ADD
};

document.addEventListener("DOMContentLoaded", loaded);

async function loaded(event) {
    try {
        await menu();
    } catch (error) {
        return;
    }

    document.getElementById("registrar").addEventListener("click", add);
    document.getElementById("cancelar").addEventListener("click", () => {
        document.location = "/pages/bienvenida/view.html";
    });
}

function load_item() {
    state.item = {
        idProveedor: document.getElementById("idProveedor").value,
        nombreProveedor: document.getElementById("nombreProveedor").value,
        correoElectronico: document.getElementById("correoElectronico").value,
        numeroTelefono: document.getElementById("numeroTelefono").value,
        direccion: document.getElementById("direccion").value,
        nombreUsuario: document.getElementById("nombreUsuario").value,
        contrasena: document.getElementById("contrasena").value
    };
}

function validate_item() {
    var error = false;

    document.querySelectorAll('input').forEach((i) => {
        i.classList.remove("invalid");
    });

    if (state.item.idProveedor.length === 0) {
        document.querySelector("#idProveedor").classList.add("invalid");
        error = true;
    }

    if (state.item.nombreProveedor.length === 0) {
        document.querySelector("#nombreProveedor").classList.add("invalid");
        error = true;
    }

    if (state.item.correoElectronico.length === 0) {
        document.querySelector("#correoElectronico").classList.add("invalid");
        error = true;
    }

    if (state.item.numeroTelefono.length === 0) {
        document.querySelector("#numeroTelefono").classList.add("invalid");
        error = true;
    }

    if (state.item.direccion.length === 0) {
        document.querySelector("#direccion").classList.add("invalid");
        error = true;
    }

    if (state.item.nombreUsuario.length === 0) {
        document.querySelector("#nombreUsuario").classList.add("invalid");
        error = true;
    }

    if (state.item.contrasena.length === 0) {
        document.querySelector("#contrasena").classList.add("invalid");
        error = true;
    }

    return !error;
}

async function add() {
    load_item();
    if (!validate_item()) return;

    let request = new Request(api + '/register', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(state.item)
    });

    try {
        const response = await fetch(request);
        if (response.status === 201) {
            alert('Registro exitoso');
            window.location.href = "/pages/bienvenida/view.html";
        } else if (response.status === 409) {
            document.getElementById('error').textContent = 'Ya existe un proveedor registrado con la misma cédula o nombre de usuario.';
        } else {
            document.getElementById('error').textContent = 'Error al registrar el proveedor.';
        }
    } catch (error) {
        document.getElementById('error').textContent = 'Error al registrar el proveedor. Inténtelo de nuevo más tarde.';
        console.error('Error:', error);
    }
}
