document.addEventListener("DOMContentLoaded", loaded);

async function loaded(event) {
    try {
        await menu();
    } catch (error) {
        return;
    }
    await loadPerfil();
    document.getElementById("guardar").addEventListener("click", updatePerfil);
}

async function loadPerfil() {
    const response = await fetch("/api/perfil/get", {method: 'GET'});
    if (response.ok) {
        const perfil = await response.json();
        document.getElementById("id").value = perfil.id;
        document.getElementById("nombre").value = perfil.nombre;
        document.getElementById("correoElectronico").value = perfil.correoElectronico;
        document.getElementById("numeroTelefono").value = perfil.numeroTelefono;
        document.getElementById("direccion").value = perfil.direccion;
    } else {
        document.getElementById("error").textContent = "Error al cargar el perfil";
    }
}

async function updatePerfil() {
    const perfil = {
        id: document.getElementById("id").value,
        nombre: document.getElementById("nombre").value,
        correoElectronico: document.getElementById("correoElectronico").value,
        numeroTelefono: document.getElementById("numeroTelefono").value,
        direccion: document.getElementById("direccion").value,
        usuarioByUsuarioId: null
    };

    const response = await fetch("/api/perfil/update", {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(perfil)
    });

    if (response.ok) {
        document.getElementById("mensaje").textContent = "Perfil actualizado correctamente.";
    } else {
        document.getElementById("error").textContent = "Error al actualizar el perfil.";
    }
}
