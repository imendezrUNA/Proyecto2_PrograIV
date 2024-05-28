// menu.js

async function menu() {
    await checkuser();
    if (!loginstate.logged && document.location.pathname !== "/pages/bienvenida/view.html" && document.location.pathname !== "/pages/registro/view.html") {
        document.location = "/pages/bienvenida/view.html";
        throw new Error("Usuario no autorizado");
    }
    render_menu();
}

function render_menu() {
    let html;
    if (!loginstate.logged) {
        html = `
            <div class="logo">
                <span>Facturacion</span>
                <img src="/images/logo.png">
            </div>
            <div>
                <ul class="Menu">
        `;

        if (document.location.pathname !== "/pages/registro/view.html") {
            html += `<li id="loginlink"><a href="#"> Login</a></li>`;
        }

        html += `
                    <li id="registerlink"><a href="#"> Registro</a></li>
                    <li id="bienvenidalink"><a href="#"> Bienvenida</a></li>
                </ul>
            </div>
        `;
        document.querySelector('#menu').innerHTML = html;

        if (document.location.pathname !== "/pages/registro/view.html") {
            document.querySelector("#menu #loginlink").addEventListener('click', ask);
            render_loginoverlay();
            render_loginview();
        }

        document.querySelector("#menu #registerlink").addEventListener('click', function (event) {
            event.preventDefault();
            document.location = "/pages/registro/view.html";
        });
        document.querySelector("#menu #bienvenidalink").addEventListener('click', function (event) {
            event.preventDefault();
            document.location = "/pages/bienvenida/view.html";
        });
    } else {
        if (loginstate.user.rol === "PROVEEDOR") {
            html = `
                <div class="logo">
                    <span>Facturacion</span>
                    <img src="/images/logo.png">
                </div>
                <div>
                    <ul class="Menu">
                        <li id="facturarlink"><a href="#"> Facturar</a></li>
                        <li id="facturaslink"><a href="#"> Facturas</a></li>
                        <li id="clienteslink"><a href="#"> Clientes</a></li>
                        <li id="productoslink"><a href="#"> Productos</a></li>
                        <li id="perfillink"><a href="#"><div class="usuario">
                            <img id="usuario" src="/images/user.png" alt="Logo Usuario"> &nbsp &nbsp ${loginstate.user.nombreUsuario}
                        </div></a></li>
                        <li id="logoutlink"><a href="#"> Logout</a></li>
                    </ul>
                </div>
            `;
        } else if (loginstate.user.rol === "ADMINISTRADOR") {
            html = `
                <div class="logo">
                    <span>Facturacion</span>
                    <img src="/images/logo.png">
                </div>
                <div>
                    <ul class="Menu">
                        <li id="proveedoreslink"><a href="#"> Proveedores</a></li>
                        <li id="logoutlink"><a href="#"> Logout</a></li>
                    </ul>
                </div>
            `;
        }
        document.querySelector('#menu').innerHTML = html;
        document.querySelector("#menu #logoutlink").addEventListener('click', logout);
        if (loginstate.user.rol === "PROVEEDOR") {
            document.querySelector("#menu #facturarlink").addEventListener('click', e => {
                document.location = "/pages/facturar/view.html";
            });
            document.querySelector("#menu #facturaslink").addEventListener('click', e => {
                document.location = "/pages/facturas/view.html";
            });
            document.querySelector("#menu #clienteslink").addEventListener('click', e => {
                document.location = "/pages/clientes/view.html";
            });
            document.querySelector("#menu #productoslink").addEventListener('click', e => {
                document.location = "/pages/productos/view.html";
            });
            document.querySelector("#menu #perfillink").addEventListener('click', e => {
                document.location = "/pages/perfil/view.html";
            });
        } else if (loginstate.user.rol === "ADMINISTRADOR") {
            document.querySelector("#menu #proveedoreslink").addEventListener('click', e => {
                document.location = "/pages/proveedores/view.html";
            });
        }
    }
}

function render_loginoverlay() {
    html = `<div id="loginoverlay" class="loginoverlay"></div>`;
    let overlay = document.createElement('div');
    overlay.innerHTML = html;
    document.body.appendChild(overlay);
    document.querySelector("#loginoverlay").addEventListener("click", toggle_loginview);
}

function render_loginview() {
    html = `
        <div id="loginview" class='loginview'>
            <div class='col-12'>
                <div>
                    <form name="formulario">
                        <div class='container'>
                            <div class='row'><div class='col-12 text-centered cooper'>Login</div></div>
                            <div class='row'><div class='col-3 text-right'>Id</div><div class='col-6'><input type="text" name="id" id="id" value=""></div></div>
                            <div class='row'><div class='col-3 text-right'>Clave</div><div class='col-6'><input type="password" name="password" id="password" value=""></div></div>
                            <div class='row'>
                                <div class='col-6 text-centered cooper'>
                                    <input id="login" class="boton" type="button" value="Login">
                                    &nbsp
                                    <input id="cancelar" class="boton" type="button" value="Cancelar">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>    
    `;
    let view = document.createElement('div');
    view.innerHTML = html;
    document.body.appendChild(view);
    document.querySelector("#loginview #login").addEventListener("click", login);
    document.querySelector("#loginview #cancelar").addEventListener("click", toggle_loginview);
}

function ask(event) {
    event.preventDefault();
    toggle_loginview();
    document.querySelectorAll('#loginview input').forEach((i) => {
        i.classList.remove("invalid");
    });
    document.querySelector("#loginview #id").value = "";
    document.querySelector("#loginview #password").value = "";
}

function toggle_loginview() {
    document.getElementById("loginoverlay").classList.toggle("active");
    document.getElementById("loginview").classList.toggle("active");
}

// Expose functions to the global scope
window.menu = menu;
window.render_menu = render_menu;
window.render_loginoverlay = render_loginoverlay;
window.render_loginview = render_loginview;
window.ask = ask;
window.toggle_loginview = toggle_loginview;
