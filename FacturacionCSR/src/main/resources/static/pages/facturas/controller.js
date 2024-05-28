var api_Facturas = 'http://localhost:8080/api/facturas';

document.addEventListener("DOMContentLoaded",loaded);
var state ={
    list: new Array(),
    factura: "",
    proveedor: ""
}


async function loaded(event) {
    try{ await menu();} catch(error){}
    buscar();

}

function  buscar(){
    const request = new Request(api_Facturas + `/searchProveedor?id=${loginstate.user.id}`,
        {method: 'GET', headers:{}});
    (async () =>{
        const response = await fetch(request);
        if(!response.ok) {errorMessage(response.status);return;}
        state.proveedor = await response.json();
        prov();
    })();
}

function prov(){
    texto = document.createTextNode(state.proveedor.nombre);
    parrafo = document.getElementById('fact');
    parrafo.appendChild(texto);
    fetchAndList()
}

function fetchAndList(){
    const request = new Request(api_Facturas + `?id=${state.proveedor.id}`, {method: 'GET', headers: { }});
    (async ()=>{
        const response = await fetch(request);
        if (!response.ok) {errorMessage(response.status);return;}
        state.list = await response.json();
        render_list();
    })();
}

function render_list(){
    var listado=document.getElementById("tablaFacturas");

    state.list.forEach( item => render_list_item(listado, item));
}

function render_list_item(listado,item){

    var tr =document.createElement("tr");
    tr.innerHTML= `<td> ${item.id} </td>
                <td>  ${item.fecha} </td>
                <td> ${item.total}  </td>
                <td> ${item.clienteByClienteId.nombre} </td>
                <td> <a href="/api/facturas/${item.id}/pdf" target="_blank"><img src="/images/pdf.png" style="width: 30px"> </a>  </td>
                 <td> <img id="xml" src="/images/xml.png" style="width: 30px"> </td>`;

    tr.querySelector("#xml").addEventListener("click",()=>{render_xml(item);});
    listado.append(tr);
}


function render_xml(factura){
    contenido = `<?xml version="1.0" encoding="utf-8"?>
    <FacturaElectronica>
    <Clave>50614081900011121314500100001010000000027101002449</Clave>
    <CodigoActividad>851201</CodigoActividad>
    <NumeroConsecutivo>00100001010000000027</NumeroConsecutivo>
    <FechaEmision>${factura.fecha} </FechaEmision>
    <Emisor>
        <Nombre> ${factura.proveedorByProveedorId.nombre}</Nombre>
        <Identificacion>
            <Tipo>01</Tipo>
            <Numero>  ${factura.proveedorByProveedorId.id}</Numero>
        </Identificacion>

        <NombreComercial> ${factura.proveedorByProveedorId.nombre}</NombreComercial>
    </Emisor>
    <Receptor>
        <Nombre>${factura.clienteByClienteId.nombre} </Nombre>
        <Identificacion>
            <Tipo>01</Tipo>
            <Numero>${factura.clienteByClienteId.id} </Numero>
        </Identificacion>
        <CorreoElectronico> ${factura.clienteByClienteId.correoElectronico}</CorreoElectronico>
        <NombreComercial> ${factura.proveedorByProveedorId.nombre}</NombreComercial>
    </Receptor>
    <CondicionVenta>01</CondicionVenta>
    <MedioPago>01</MedioPago>
    <ResumenFactura>
        <CodigoTipoMoneda>
            <CodigoMoneda>CRC</CodigoMoneda>
            <TipoCambio>1.0</TipoCambio>
        </CodigoTipoMoneda>
        <TotalServGravados>0.0000</TotalServGravados>
        <TotalServExentos>0</TotalServExentos>
        <TotalMercanciasGravadas>0</TotalMercanciasGravadas>
        <TotalMercanciasExentas>0.0000</TotalMercanciasExentas>
        <TotalGravado>0</TotalGravado>
        <TotalExento>0</TotalExento>
        <TotalVenta>${factura.total} </TotalVenta>
        <TotalDescuentos>0</TotalDescuentos>
        <TotalVentaNeta> ${factura.total}</TotalVentaNeta>
        <TotalImpuesto>0</TotalImpuesto>
        <TotalComprobante> ${factura.total}</TotalComprobante>
    </ResumenFactura>
    </FacturaElectronica>
    `;

    var blob = new Blob([contenido], {type: 'text/xml'});
    window.open(URL.createObjectURL(blob));
}