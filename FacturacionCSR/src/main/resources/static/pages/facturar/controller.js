var api_facturar = 'http://localhost:8080/api/facturar';

document.addEventListener("DOMContentLoaded",loaded);

var cliente = {
    total: 0.0,
    detallefacturasById: new Array(),
    proveedorByProveedorId: "",
    clienteByClienteId: ""
}
async function loaded(event) {
    try {
        await menu();
    } catch (error) {
        return;
    }
    buscarProovedor();

    document.getElementById("buscarCliente").addEventListener("click", buscarCliente);
    document.getElementById("codigoProducto").addEventListener("click", buscarProducto);
    document.getElementById("guardar").addEventListener("click", add)
}

function  buscarProovedor(){
    const request = new Request(api_facturar + `/searchProveedor?id=${loginstate.user.id}`,
        {method: 'GET', headers:{}});
    (async () =>{
        const response = await fetch(request);
        if(!response.ok) {errorMessage(response.status);return;}
        cliente.proveedorByProveedorId = await response.json();
        proveedor();
    })();
}

function proveedor(){
    texto = document.createTextNode(cliente.proveedorByProveedorId.nombre);
    parrafo = document.getElementById('proovedor');
    parrafo.appendChild(texto);
}
function mostrar_Cliente(){
    texto = document.createTextNode(cliente.clienteByClienteId.nombre);
    parrafo = document.getElementById('cliente');
    parrafo.appendChild(texto);
}

function  buscarCliente(){
    nombreBusqueda = document.getElementById("idCliente").value;
    const request = new Request(api_facturar + `/search?id=${nombreBusqueda}`,
        {method: 'GET', headers:{}});
    (async () =>{
        const response = await fetch(request);
        if(!response.ok) {errorMessage(response.status);return;}
        cliente.clienteByClienteId = await response.json();
        mostrar_Cliente();
    })();
}

function  buscarProducto(){
    nombreBusqueda = document.getElementById("producto").value;
    const request = new Request(api_facturar + `/searchProducto?id=${nombreBusqueda}`,
        {method: 'GET', headers:{}});
    (async () =>{
        const response = await fetch(request);
        if(!response.ok) {errorMessage(response.status);return;}
        cliente.detallefacturasById.push(await response.json());
        mostrar_Productos();
    })();
}

function mostrar_Productos(){
    var listado = document.getElementById("productos");
    listado.innerHTML="";
    cliente.total = 0.0;
    cliente.detallefacturasById.forEach( clienteByClienteId => renderProducto(listado,clienteByClienteId));
    mostrar_Total();
}

function renderProducto(listado,clienteByClienteId){
    var div = document.createElement("div");
    cliente.total = cliente.total + (clienteByClienteId.cantidad * clienteByClienteId.subtotal);
    div.className+=" productos";
    div.innerHTML=`
                  <div style="display: inline-block"><button id='menos'>-</button></div>
                        <p>${clienteByClienteId.cantidad}</p>
                        <p>${clienteByClienteId.productoByProductoId.nombre} </p>
                        <p>${clienteByClienteId.productoByProductoId.precio}</p>
                        <p> ${clienteByClienteId.subtotal * clienteByClienteId.cantidad} </p>
                        <button id='mas'>+</button>`;
    div.querySelector("#menos").addEventListener("click",()=>{menos(clienteByClienteId.productoByProductoId.id);});
    div.querySelector("#mas").addEventListener("click",()=>{mas(clienteByClienteId.productoByProductoId.id);});
    listado.append(div);
}

function menos(idProducto){
    let indice = cliente.detallefacturasById.findIndex(obj => obj.productoByProductoId.id === idProducto);
    cliente.detallefacturasById[indice].cantidad--;
    if(cliente.detallefacturasById[indice].cantidad === 0){
        cliente.detallefacturasById.splice(indice,1);
    }
    mostrar_Productos();
}

function mas(idProducto){
    let indice = cliente.detallefacturasById.findIndex(obj => obj.productoByProductoId.id === idProducto);
    cliente.detallefacturasById[indice].cantidad++;
    mostrar_Productos();
}

function mostrar_Total(){
    parrafo = document.getElementById('total');
    parrafo.innerText = cliente.total;
}

function add(){
    let request = new Request(api_facturar, {method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify(cliente)});
    (async ()=>{
        const response = await fetch(request);
        if (!response.ok) {errorMessage(response.status);return;}
        location.reload();
        alert("La factura se ha guardado correctamente.");
    })();
}
