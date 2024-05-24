USE Facturacion;

-- Insertar usuarios
INSERT INTO Usuario (nombreUsuario, contrasena, estado, rol) VALUES
('admin', 'password', 'ACTIVO', 'ADMINISTRADOR'),
('proveedor1', 'password', 'ACTIVO', 'PROVEEDOR'),
('proveedor2', 'password', 'INACTIVO', 'PROVEEDOR'),
('proveedor3', 'password', 'PENDIENTE', 'PROVEEDOR');

-- Insertar usuarios con la contraseña "password" encriptada
/*INSERT INTO Usuario (nombreUsuario, contrasena, estado, rol) VALUES
('admin', '{bcrypt}$2a$10$k2lv9mKrnclYn4OHco054eTt/iBavxIFy4iRxiIqquUwFdfix/S.K', 'ACTIVO', 'ADMINISTRADOR'),
('proveedor1', '{bcrypt}$2a$10$VKqdek0YPdCFglv1/hGLN.0ejqDLn3ALNCs2HGniFxoNJU3.MGHRO', 'ACTIVO', 'PROVEEDOR'),
('proveedor2', '{bcrypt}$2a$10$DTa4PSnNWCINIBMVS0y9/OUq26ywfDLQpeSqku.Jf9nKNdIzYiQHe', 'INACTIVO', 'PROVEEDOR'),
('proveedor3', '{bcrypt}$2a$10$GF7CXwZzabkjR0AEFECvWOK3JlTyxbDhGVnhDSbzw/sK.WGonWiiu', 'PENDIENTE', 'PROVEEDOR');*/

-- Insertar proveedores con sus respectivos usuarios
INSERT INTO Proveedor (ID, usuarioID, nombre, correoElectronico, numeroTelefono, direccion) VALUES
(123456789, 2, 'Proveedor Uno', 'proveedor1@ejemplo.com', '12345678', 'Dirección proveedor 1'),
(234567891, 3, 'Proveedor Dos', 'proveedor2@ejemplo.com', '23456789', 'Dirección proveedor 2'),
(345678912, 4, 'Proveedor Tres', 'proveedor3@ejemplo.com', '34567891', 'Dirección proveedor 3');

-- Insertar clientes
INSERT INTO Cliente (ID, nombre, correoElectronico, numeroTelefono, direccion, proveedorID) VALUES
(123123123, 'Cliente Uno', 'cliente1@ejemplo.com', '11111111', 'Dirección cliente 1', '123456789'),
(234234234, 'Cliente Dos', 'cliente2@ejemplo.com', '22222222', 'Dirección cliente 2', '123456789'),
(345345345, 'Cliente Tres', 'cliente3@ejemplo.com', '33333333', 'Dirección cliente 3', '234567891');


-- Insertar productos para el proveedor activo (ID 123456789)
INSERT INTO Producto (nombre, descripcion, precio, proveedorID) VALUES
('Producto 1', 'Descripción del Producto 1', 100.00, 123456789),
('Producto 2', 'Descripción del Producto 2', 200.00, 123456789),
('Producto 3', 'Descripción del Producto 3', 150.00, 123456789),
('Producto 4', 'Descripción del Producto 4', 250.00, 123456789);

-- Insertar factura para el proveedor activo y el cliente 1
INSERT INTO Factura (fecha, proveedorID, clienteID, total) VALUES
('2024-03-22', 123456789, 123123123, 300.00),
('2024-03-23', 123456789, 345345345, 400.00);

-- Insertar detalle de factura
INSERT INTO DetalleFactura (facturaID, productoID, cantidad, precioUnitario, subtotal) VALUES
(1, 1, 1, 100.00, 100.00),
(1, 2, 1, 200.00, 200.00),
(2, 3, 2, 150.00, 300.00),
(2, 4, 1, 250.00, 250.00);

