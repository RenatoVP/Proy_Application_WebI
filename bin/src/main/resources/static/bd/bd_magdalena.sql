-- Bd Magdalena Corp v.1.0
drop database if exists bd_magdalena;

create database bd_magdalena;
use bd_magdalena;

-- tabla que almacena las categorias de  los productos
create table Categoria(
	id int primary key auto_increment not null,
    descripcion varchar(45)
)engine= InnoDB;

-- tabla que muestra el estado de alguna entidad guarda datos como "Disponible","No Disponible"
create table Estado(
	id int primary key auto_increment not null,
    descripcion varchar(45)
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

-- insercciones
insert into Estado values
(null, 'Disponible'),
(null, 'No Disponible');

insert into Categoria(descripcion) values
('Utiles Escolares'),('Utiles de Aseo'),('Utiles de Oficina'),('Manualidades');
