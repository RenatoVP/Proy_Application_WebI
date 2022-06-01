-- Bd Magdalena Corp v.1.1
drop database if exists bd_magdalena;

create database bd_magdalena;
use bd_magdalena;

-- tabla que muestra el estado de alguna entidad guarda datos como "Disponible","No Disponible"
create table Estado(
	id int primary key auto_increment not null,
    descripcion varchar(45)
)engine= InnoDB;

-- tabla que almacena las categorias de  los productos
create table Categoria(
	id int primary key auto_increment not null,
    descripcion varchar(45)
)engine= InnoDB;

-- tabla que muestra el rol del usuario
create table Rol(
	id int primary key auto_increment,
    descripcion char(45) not null
)engine= InnoDB;

-- tabla que almacena los usuarios que usaran el sistema
create table Usuario(
	id int primary key auto_increment,
    nombre varchar(25) not null,
    apellidos varchar(50) not null,
    username char(45) not null,
    contrasena char(45) not null,
    fecNac date not null,
    idrol int not null,
    idestado int not null,
    foreign key(idrol) references Rol(id),
    foreign key(idestado) references Estado(id)
)engine= InnoDB;

create table Cliente(
	id int primary key auto_increment,
    razon_social varchar(35),
    dni char(8),
    telefono char(15),
    email varchar(45)
)engine= InnoDB;

-- tabla que almacena la informacion de los utiles escolares y entre otros
create table Producto(
	id char(6) primary key not null,
    descripcion varchar(90) not null,
    precio double not null,
    stock int default 0,
    idcategoria int,
    idestado int,
    foreign key(idcategoria) references Categoria(id),
    foreign key(idestado) references Estado(id)
)engine= InnoDB;

-- Tabla que almacena la venta, al vendedor y el total a pagar
create table Boleta(
	id int primary key auto_increment,
    idusuario int not null,
    idcliente int not null,
    fecha date not null,
    totalpago double not null,
    foreign key(idusuario) references Usuario(id),
    foreign key(idcliente) references Cliente(id)
)engine= InnoDB;

-- Tabla que almacena los productos solicitados por el cliente por un ticket
create table Boleta_has_Producto(
	id int primary key auto_increment,
    idboleta int not null,
    idproducto char(6) not null,
    cant int default 0,
    total double default 0.0,
    foreign key(idboleta) references Boleta(id),
    foreign key(idproducto) references Producto(id)
)engine= InnoDB;

-- insercciones
insert into Estado values
(null, 'Disponible'),
(null, 'No Disponible');

insert into Rol values
(null, 'Vendedor'),
(null, 'Cajero');

insert into Categoria(descripcion) values
('Utiles Escolares'),('Utiles de Aseo'),('Utiles de Oficina'),('Manualidades');