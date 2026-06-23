create database if not exists FootStore
	character set utf8mb4
	collate utf8mb4_unicode_ci;
use FootStore;

create table usuario(
	id BIGINT auto_increment primary key,
	nombre VARCHAR(30) not null,
	apellido VARCHAR(30) not null,
	mail VARCHAR(50) not null unique,
	celular VARCHAR(20) not null,
	rol VARCHAR(20) not null,
	eliminado BOOLEAN not null default FALSE
);

create table categoria (
	id BIGINT auto_increment primary key,
	nombre VARCHAR(30) not null unique,
	descripcion VARCHAR(255),
	eliminado BOOLEAN not null default FALSE
);

create table producto (
	id BIGINT auto_increment primary key,
	nombre VARCHAR(50) not null,
	descripcion VARCHAR(255),
	precio DECIMAL(10,2) not null,
	stock INT not null,
	imagen VARCHAR(255),
	disponible BOOLEAN not null default true,
	id_categoria BIGINT null,
	eliminado BOOLEAN not null default false,
	constraint fk_producto_categoria foreign key (id_categoria)
		references categoria(id)
		on update cascade 
		on delete set null
);

CREATE TABLE pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(50) NOT NULL,
    forma_pago VARCHAR(50) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_pedido_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE detalle_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detalle_pedido FOREIGN KEY (id_pedido)
        REFERENCES pedido(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,  
    CONSTRAINT fk_detalle_producto FOREIGN KEY (id_producto)
        REFERENCES producto(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT   
);










