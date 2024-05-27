document.addEventListener("DOMContentLoaded", loaded);

async function loaded(event) {
    try {
        await menu();
    } catch (error) {
        return;
    }

    document.getElementById("registrolink").addEventListener('click', function (e) {
        e.preventDefault();
        document.location = "/pages/registro/view.html";
    });
}
