package eif209.facturacioncsr.presentation;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import eif209.facturacioncsr.data.FacturaRepository;
import eif209.facturacioncsr.data.ProveedorRepository;
import eif209.facturacioncsr.logic.Detallefactura;
import eif209.facturacioncsr.logic.Factura;
import eif209.facturacioncsr.logic.Proveedor;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class Facturas {
    @Autowired
    FacturaRepository facturaRepository;
    @Autowired
    ProveedorRepository proveedorRepository;
    @GetMapping
    public List<Factura> read(@RequestParam String id){
        return (List<Factura>) facturaRepository.findByProveedorByProveedorIdId(Long.valueOf(id));
    }

    @GetMapping("/searchProveedor")
    public Proveedor findProveedor(@RequestParam String id){
        System.out.println(id);
        return proveedorRepository.findProveedorByUsuarioByUsuarioId_Id(Integer.parseInt(id)).get();
    }

    @GetMapping("/{id}/pdf")
    public void pdf(@PathVariable int id, HttpServletResponse response){
        try {
            response.addHeader("Content-type", "application/pdf");
            Factura factura = facturaRepository.findById(id).get();

            PdfWriter writer = new PdfWriter(response.getOutputStream());

            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);

            Paragraph paragraph = new Paragraph();
            paragraph.add("Detalles de la Factura\n");

            Table table = new Table(5);

            table.addCell("ID de la Factura");
            table.addCell("Fecha");
            table.addCell("Cliente del ID");
            table.addCell("Nombre del Cliente");
            table.addCell("Total");

            table.addCell(String.valueOf(factura.getId()));
            table.addCell(factura.getFecha().toString());
            table.addCell(String.valueOf(factura.getClienteByClienteId().getId()));
            table.addCell(factura.getClienteByClienteId().getNombre());
            table.addCell(String.valueOf(factura.getTotal()));

            Table table2 = new Table(5);


            table2.addCell("ID del Producto");
            table2.addCell("Descripción del Producto");
            table2.addCell("Cantidad");
            table2.addCell("Precio");
            table2.addCell("Subtotal");

            for (Detallefactura d : factura.getDetallefacturasById()) {

                table2.addCell(String.valueOf(d.getProductoByProductoId().getId()));
                table2.addCell(d.getProductoByProductoId().getNombre());
                table2.addCell(String.valueOf(d.getCantidad()));
                table2.addCell(String.valueOf(d.getPrecioUnitario()));
                table2.addCell(String.valueOf(d.getSubtotal()));

            }
            Paragraph paragraph2 = new Paragraph();
            paragraph2.add("\nDetalles de los Productos");
            document.add(paragraph);
            document.add(table);
            document.add(paragraph2);
            document.add(table2);
            document.close();
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


    }


}
