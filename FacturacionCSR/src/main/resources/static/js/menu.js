var backend = "http://localhost:8080/api";
var api_login = backend + '/login';

var loginstate = {
    logged: false,
    user: {id: "", rol: ""}
};

async function checkuser() {
    let request = new Request(api_login + '/current-user', {method: 'GET'});
    const response = await fetch(request);
    if (response.ok) {
        loginstate.logged = true;
        loginstate.user = await response.json();
    } else {
        loginstate.logged = false;
    }
}

async function menu() {
    await checkuser();
    if (!loginstate.logged && document.location.pathname !== "/pages/bienvenida/view.html") {
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
                    <li id="loginlink"><a href="#"> Login</a></li>
                    <li id="bienvenidalink"><a href="#"> Bienvenida</a></li>
                </ul>
            </div>
        `;
        document.querySelector('#menu').innerHTML = html;
        document.querySelector("#menu #loginlink").addEventListener('click', ask);
        render_loginoverlay();
        render_loginview();
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
                        <li id="perfillink"><a href="#"> Perfil</a></li>
                        <li id="logoutlink"><a href="#"> Logout</a></li>
                    </ul>
                </div>
                <div class="user">&nbsp &nbsp ${loginstate.user.id}</div>
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
                <div class="user">&nbsp &nbsp ${loginstate.user.id}</div>
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

function login() {
    let user = {
        nombreUsuario: document.getElementById("id").value,
        contrasena: document.getElementById("password").value
    };
    let request = new Request(api_login + '/login', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(user)
    });
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            errorMessage(response.status);
            return;
        }
        loginstate.user = await response.json();
        if (loginstate.user.rol === "PROVEEDOR") {
            document.location = "/pages/facturar/view.html";
        } else if (loginstate.user.rol === "ADMINISTRADOR") {
            document.location = "/pages/proveedores/view.html";
        }
    })();
}

function logout(event) {
    event.preventDefault();
    let request = new Request(api_login + '/logout', {method: 'POST'});
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            errorMessage(response.status);
            return;
        }
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
