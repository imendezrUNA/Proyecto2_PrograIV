var backend = 'http://localhost:8080/api';
var api = backend + '/productos';

var state = {
    list: [],
    item: {id: "", nombre: "", descripcion: "", precio: ""},
    mode: "" // ADD, EDIT
};

document.addEventListener("DOMContentLoaded", loaded);

async function loaded(event) {
    try {
        await menu();
    } catch (error) {
        return;
    }
    document.getElementById("btnBuscar").addEventListener("click", search);
    document.getElementById("btnCrear").addEventListener("click", ask);

    document.querySelector("#modalProducto .btn-primary").addEventListener("click", save);

    fetchAndList();
}

function fetchAndList() {
    const request = new Request(api, {method: 'GET', headers: {}});
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            errorMessage(response.status);
            return;
        }
        state.list = await response.json();
        render_list();
    })();
}

function render_list() {
    var listado = document.getElementById("TablaProductos").querySelector("tbody");
    listado.innerHTML = "";
    state.list.forEach(item => render_list_item(listado, item));
}

function render_list_item(listado, item) {
    var tr = document.createElement("tr");
    tr.innerHTML = `<td>${item.id}</td>
                    <td>${item.nombre}</td>
                    <td>${item.descripcion}</td>
                    <td>${item.precio}</td>
                    <td class='text-center'>
                        <button class='btn btn-warning btn-sm edit' data-id='${item.id}' data-bs-toggle="modal" data-bs-target="#modalProducto">Editar</button>
                    </td>`;
    tr.querySelector(".edit").addEventListener("click", () => {
        edit(item.id);
    });
    listado.append(tr);
}

function ask() {
    empty_item();
    state.mode = "ADD";
    render_item();
}

function empty_item() {
    state.item = {id: "", nombre: "", descripcion: "", precio: ""};
}

function render_item() {
    const idField = document.getElementById("id");
    idField.value = state.item.id;
    idField.readOnly = state.mode === "EDIT";

    document.getElementById("nombre").value = state.item.nombre;
    document.getElementById("descripcion").value = state.item.descripcion;
    document.getElementById("precio").value = state.item.precio;
}

function save() {
    if (!loginstate.logged) {
        return;
    }

    load_item();

    if (!validate_item()) return;

    let method = state.mode === "EDIT" ? 'PUT' : 'POST';
    let url = state.mode === "EDIT" ? `${api}/${state.item.id}` : api;

    let request = new Request(url, {
        method: method,
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(state.item)
    });

    (async () => {
        try {
            const response = await fetch(request);
            if (!response.ok) {
                let errorMessage = `Error: ${response.status} ${response.statusText}`;
                console.error('There has been a problem with your fetch operation:', errorMessage);
                const errorText = await response.text();
                console.error('Error details:', errorText);
                throw new Error(errorMessage);
            }
            var modal = bootstrap.Modal.getInstance(document.getElementById('modalProducto'));
            modal.hide();
            fetchAndList();
        } catch (error) {
            console.error('Fetch error:', error);
        }
    })();
}

function load_item() {
    state.item = {
        id: document.getElementById("id").value,
        nombre: document.getElementById("nombre").value,
        descripcion: document.getElementById("descripcion").value,
        precio: document.getElementById("precio").value,
        proveedorId: loginstate.user.id
    };
}

function validate_item() {
    var error = false;
    document.querySelectorAll('input').forEach((i) => {
        i.classList.remove("invalid");
    });

    if (state.item.nombre.length == 0) {
        document.getElementById("nombre").classList.add("invalid");
        error = true;
    }
    if (state.item.descripcion.length == 0) {
        document.getElementById("descripcion").classList.add("invalid");
        error = true;
    }
    if (state.item.precio.length == 0 || isNaN(state.item.precio)) {
        document.getElementById("precio").classList.add("invalid");
        error = true;
    }

    return !error;
}

function edit(id) {
    let request = new Request(`${api}/${id}`, {method: 'GET', headers: {}});
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            errorMessage(response.status);
            return;
        }
        state.item = await response.json();
        state.mode = "EDIT";
        render_item();
    })();
}

async function search() {
    const nombreBusqueda = document.getElementById("nombreBusqueda").value.trim();
    let request;

    if (nombreBusqueda === "") {
        // Si el campo de búsqueda está vacío, devuelve la lista completa
        request = new Request(api, {method: 'GET', headers: {}});
    } else {
        // Si hay un valor de búsqueda, busca por nombre
        request = new Request(api + `/search?nombre=${encodeURIComponent(nombreBusqueda)}`, {
            method: 'GET',
            headers: {}
        });
    }

    try {
        const response = await fetch(request);
        if (!response.ok) {
            errorMessage(response.status);
            return;
        }
        state.list = await response.json();
        render_list();
    } catch (error) {
        console.error('There was an error during the search operation:', error);
    }
}

function errorMessage(status) {
    console.error(`Error: ${status}`);
}
