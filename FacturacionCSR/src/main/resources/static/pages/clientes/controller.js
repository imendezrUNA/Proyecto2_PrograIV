var backend = 'http://localhost:8080/api';
var api = backend + '/clientes';
var cliente;

var state = {
    list: [],
    item: { id: "", nombre: "", correoElectronico: "", numeroTelefono: "", direccion: "" },
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

    document.querySelector("#modalCliente .btn-primary").addEventListener("click", save);


    fetchAndList();
}

function fetchAndList() {
    const request = new Request(api, {method: 'GET', headers: { }});
    (async ()=>{
        const response = await fetch(request);
        if (!response.ok) {errorMessage(response.status);return;}
        state.list = await response.json();
        render_list();
    })();
}

function render_list() {
    var listado = document.getElementById("TablaClientes").querySelector("tbody");
    listado.innerHTML = "";
    state.list.forEach(item => render_list_item(listado, item));
}

function render_list_item(listado, item) {
    var tr = document.createElement("tr");
    tr.innerHTML = `<td>${item.id}</td>
                    <td>${item.nombre}</td>
                    <td>${item.correoElectronico}</td>
                    <td>${item.numeroTelefono}</td>
                    <td>${item.direccion}</td>
                    <td class='text-center'>
                        <button id="editar" class='btn btn-warning btn-sm edit' data-id='${item.id}'>Editar</button>
                    </td>`;
    tr.querySelector(".edit").addEventListener("click", () => { edit(item.id); });
    listado.append(tr);
}

function ask(){
    empty_item();
    toggle_itemview();
    state.mode="ADD";
    render_item()
}

function toggle_itemview() {
    document.getElementById("modalCliente").classList.toggle("active");
}

function empty_item() {
    state.item = { id: "", nombre: "", correoElectronico: "", numeroTelefono: "", direccion: "" };
}

function render_item() {
    document.getElementById("id").value = state.item.id;
    document.getElementById("nombre").value = state.item.nombre;
    document.getElementById("correo").value = state.item.correoElectronico;
    document.getElementById("numTelefono").value = state.item.numeroTelefono;
    document.getElementById("direccion").value = state.item.direccion;
}

function save() {
    load_item();
    if (!validate_item()) return;

    let method = state.mode === "EDIT" ? 'PUT' : 'POST';
    let url = state.mode === "EDIT" ? `${api}/${state.item.id}` : api;

    console.log('Saving item:', state.item); // Depuración: Ver los datos antes de enviarlos

    let request = new Request(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
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
            toggle_itemview();
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
        correoElectronico: document.getElementById("correo").value,
        numeroTelefono: document.getElementById("numTelefono").value,
        direccion: document.getElementById("direccion").value
    };
}

function validate_item() {
    var error = false;
    document.querySelectorAll('input').forEach((i) => { i.classList.remove("invalid"); });

    if (state.item.nombre.length == 0) {
        document.getElementById("nombre").classList.add("invalid");
        error = true;
    }
    if (state.item.correoElectronico.length == 0) {
        document.getElementById("correo").classList.add("invalid");
        error = true;
    }
    if (state.item.numeroTelefono.length == 0) {
        document.getElementById("numTelefono").classList.add("invalid");
        error = true;
    }
    if (state.item.direccion.length == 0) {
        document.getElementById("direccion").classList.add("invalid");
        error = true;
    }

    return !error;
}
function edit(id) {
    let request = new Request(`${api}/${id}`, { method: 'GET', headers: {} });
    (async () => {
        const response = await fetch(request);
        if (!response.ok) { errorMessage(response.status); return; }
        state.item = await response.json();
        toggle_itemview();
        state.mode = "EDIT";
        render_item();
    })();

}
async function search() {
    const nombreBusqueda = document.getElementById("nombreBusqueda").value.trim();
    let request;

    if (nombreBusqueda === "") {
        // Si el campo de búsqueda está vacío, devuelve la lista completa
        request = new Request(api, { method: 'GET', headers: {} });
    } else {
        // Si hay un valor de búsqueda, busca por nombre
        request = new Request(api + `/search?nombre=${encodeURIComponent(nombreBusqueda)}`, { method: 'GET', headers: {} });
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
