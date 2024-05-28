document.addEventListener("DOMContentLoaded", loaded);

async function loaded(event) {
    try {
        await menu();
    } catch (error) {
        return;
    }
    await listarProveedores();
}

async function listarProveedores() {
    const response = await fetch("/api/proveedores/listar");
    if (response.ok) {
        const proveedores = await response.json();
        renderProveedores(proveedores);
    } else {
        document.getElementById("mensaje").textContent = "Error al cargar los proveedores";
    }
}

function renderProveedores(proveedores) {
    const tbody = document.getElementById("proveedores-lista");
    tbody.innerHTML = "";
    proveedores.forEach(proveedor => {
        const estado = proveedor.estadoUsuario ? proveedor.estadoUsuario : 'No disponible';
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${proveedor.id}</td>
            <td>${proveedor.nombre}</td>
            <td>${proveedor.correoElectronico}</td>
            <td>${estado}</td>
            <td>
                <button onclick="activarProveedor(${proveedor.id})">Activar</button>
                <button onclick="desactivarProveedor(${proveedor.id})">Desactivar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

async function activarProveedor(id) {
    const response = await fetch(`/api/proveedores/activar/${id}`, {method: 'POST'});
    if (response.ok) {
        await listarProveedores();
        document.getElementById("mensaje").textContent = "Proveedor activado exitosamente.";
    } else {
        document.getElementById("mensaje").textContent = "Error al activar el proveedor.";
    }
}

async function desactivarProveedor(id) {
    const response = await fetch(`/api/proveedores/desactivar/${id}`, {method: 'POST'});
    if (response.ok) {
        await listarProveedores();
        document.getElementById("mensaje").textContent = "Proveedor desactivado exitosamente.";
    } else {
        document.getElementById("mensaje").textContent = "Error al desactivar el proveedor.";
    }
}
