# Project-Ecommerce-api

docker-compose down -v

docker-compose up --build

Site para Swagger: http://localhost:8080/swagger-ui/index.html

https://project-ecommerce-api.onrender.com/swagger-ui/index.html

docker run -p 8081:80 -e "PGADMIN_DEFAULT_EMAIL=admin@admin.com" -e "PGADMIN_DEFAULT_PASSWORD=admin" dpage/pgadmin4

http://localhost:8081


banco temrinal

docker exec -it ecommerce-db bash
psql -U ecommerce -d ecommerce



🔥 Etapa 1: Remover os containers antigos
Execute:

bash
Copiar
Editar
docker rm -f ecommerce-db ecommerce-api ecommerce-pgadmin


✅ Próximo passo
Agora, acesse o pgAdmin no navegador:

arduino
Copiar
Editar
http://localhost:8081
E:

Faça login com:

Email: admin@admin.com

Senha: admin

Clique em Add New Server e preencha:

Aba General
Name: ecommerce-db

Aba Connection
Host name/address: db

Port: 5432

Maintenance database: ecommerce

Username: ecommerce

Password: ecommerce123