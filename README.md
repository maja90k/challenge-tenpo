Instrucciones para Ejecutar el Servicio y la Base de Datos Localmente

Prerrequisitos

JDK 1.8
Maven 3.X.X

Node.js y npm10
PostgreSQL(según la configuración)

Clona el repositorio: clone https://github.com/maja90k/challenge-tenpo.git


#Pasos para Ejecutar el Backend (Spring Boot)
Configuracion la base de datos POSTGRESQL:
en caso de no poder ocupar la imagen de Docker proporcionada seguir estos pasos: ,
crear una instancia de base de datos con puerto 5432 usr> postgres/pw> root para luego abrir un sql script y  correr el siguiente script en una base de datos:

CREATE TABLE public."transaction" (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	amount int4 NULL,
	bank_draft varchar NULL,
	"name" varchar NULL,
	"date" varchar NULL
);


Compila y ejecuta el proyecto: se recomienda lanzar un clean y seguido un install
con esto las dependencias quedaran ok para darle a run.

directorio del proyecto:cd api-challenge-tenpo
Limpia el proyecto y reinstala las dependencias:
./mvnw clean install

posterior puedes ejecutar la aplicación con el siguiente comando:
./mvnw spring-boot:run

##

#Pasos para Ejecutar el Frontend (Angular)
Navega al directorio del frontend: cd front
Instala las dependencias del proyecto con: npm install
Ejecuta la aplicación: ng serve o npm start
Accede a la aplicación en tu navegador en http://localhost:4200.

##

Detalles sobre Cómo Interactuar con la API

GET localhost:8080/transactions/paginated: Obtendras la lista de [transaction] completa.
GET localhost:8080/transactions/{id}: Obtendras una [transaction] específica por ID.
POST localhost:8080/transaction: Crea una nueva [transaction]. 
El cuerpo de la solicitud debe contener 
{
    "id": 0,
    "amount": 10000,
    "bankDraft": "Banco Chile",
    "name": "Byron Pastrian",
    "transactionDate": "2025-10-20"
}.

PUT localhost:8080/transaction/{id}: Actualiza una [transaction] existente.
El cuerpo de la solicitud debe contener 
{
    "amount": 10000,
    "bankDraft": "Banco Chile",
    "name": "Byron Pastrian",
}.

DELETE localhost:8080/transaction/{id}: Elimina una [transaction] específica.