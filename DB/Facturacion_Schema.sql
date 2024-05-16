CREATE DATABASE IF NOT EXISTS Facturacion;
USE Facturacion;

CREATE TABLE Usuario (
    ID INT AUTO_INCREMENT NOT NULL,
    nombreUsuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL, -- Hash de contraseña (si se logra implementar xd)
    estado ENUM('ACTIVO', 'INACTIVO', 'PENDIENTE') NOT NULL DEFAULT 'PENDIENTE',
    rol ENUM('ADMINISTRADOR', 'PROVEEDOR') NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE Proveedor (
    ID BIGINT NOT NULL, -- Ced. identidad física o jurídica sin guiones
    usuarioID INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    correoElectronico VARCHAR(255) NOT NULL UNIQUE,
    numeroTelefono VARCHAR(8) NOT NULL UNIQUE,
    direccion VARCHAR(255),
    PRIMARY KEY (ID),
    FOREIGN KEY (usuarioID) REFERENCES Usuario(ID)
);

CREATE TABLE Cliente (
    ID BIGINT NOT NULL, -- Ced. identidad física o jurídica sin guiones
    nombre VARCHAR(100) NOT NULL,
    correoElectronico VARCHAR(255) NOT NULL UNIQUE,
    numeroTelefono VARCHAR(8) NOT NULL UNIQUE,
    direccion VARCHAR(255),
    proveedorID BIGINT NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (proveedorID) REFERENCES Proveedor(ID)
);

CREATE TABLE Producto (
    ID INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    proveedorID BIGINT NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (proveedorID) REFERENCES Proveedor(ID)
);

CREATE TABLE Factura (
    ID INT AUTO_INCREMENT NOT NULL,
    fecha DATE NOT NULL,
    proveedorID BIGINT NOT NULL,
    clienteID BIGINT NOT NULL,
    total DECIMAL(10, 2),
    PRIMARY KEY (ID),
    FOREIGN KEY (proveedorID) REFERENCES Proveedor(ID),
    FOREIGN KEY (clienteID) REFERENCES Cliente(ID)
);

CREATE TABLE DetalleFactura (
    ID INT AUTO_INCREMENT NOT NULL,
    facturaID INT NOT NULL,
    productoID INT NOT NULL,
    cantidad INT NOT NULL,
    precioUnitario DECIMAL(10, 2),
    subtotal DECIMAL(10, 2),
    PRIMARY KEY (ID),
    FOREIGN KEY (facturaID) REFERENCES Factura(ID),
    FOREIGN KEY (productoID) REFERENCES Producto(ID)
);

