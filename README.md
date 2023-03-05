
Para desenvolvimento
```bash
docker run --name postgresql \
-p 5432:5432 \
-e POSTGRESQL_DATABASE=users \
-e POSTGRESQL_USERNAME=my_user \
-e POSTGRESQL_PASSWORD=password123 \
 bitnami/postgresql:15.2.0
```