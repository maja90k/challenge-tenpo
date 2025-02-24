Instrucciones para Ejecutar el Servicio y la Base de Datos Localmente

Prerrequisitos

JDK 1.8
Maven 3.X.X

Node.js y npm10
PostgreSQL(según la configuración)

Clona el repositorio: clone https://github.com/maja90k/challenge-tenpo.git


#Pasos para Ejecutar el Backend (Spring Boot)
Configuracion la base de datos POSTGRESQL:

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