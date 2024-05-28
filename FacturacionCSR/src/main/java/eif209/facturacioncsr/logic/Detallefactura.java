package eif209.facturacioncsr.logic;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Objects;

@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
@Entity
public class Detallefactura {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int id;
    @NotNull(message = "La cantidad no puede ser nula")
    @Positive(message = "La cantidad debe ser un número positivo")
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    @NotNull(message = "El precio unitario no puede ser nulo")
    @Positive(message = "El precio unitario debe ser mayor que cero")
    @Column(name = "precioUnitario", nullable = false)
    private BigDecimal precioUnitario;
    @Transient //Se calcula automáticamente a partir de la cantidad y el precio unitario con getSubtotal()
    //@Column(name = "subtotal")
    private BigDecimal subtotal;
    @ManyToOne
    @JoinColumn(name = "facturaID", referencedColumnName = "ID", nullable = false)
    private Factura facturaByFacturaId;
    @ManyToOne
    @JoinColumn(name = "productoID", referencedColumnName = "ID", nullable = false)
    private Producto productoByProductoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /*public BigDecimal getSubtotal() {
        return subtotal;
    }*/

    public BigDecimal getSubtotal() {
        if (precioUnitario != null) {
            return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
        return BigDecimal.ZERO;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Detallefactura that = (Detallefactura) o;
        return id == that.id && cantidad == that.cantidad && Objects.equals(precioUnitario, that.precioUnitario) && Objects.equals(subtotal, that.subtotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cantidad, precioUnitario, subtotal);
    }

    public Factura getFacturaByFacturaId() {
        return facturaByFacturaId;
    }

    public void setFacturaByFacturaId(Factura facturaByFacturaId) {
        this.facturaByFacturaId = facturaByFacturaId;
    }

    public Producto getProductoByProductoId() {
        return productoByProductoId;
    }

    public void setProductoByProductoId(Producto productoByProductoId) {
        this.productoByProductoId = productoByProductoId;
    }
}
