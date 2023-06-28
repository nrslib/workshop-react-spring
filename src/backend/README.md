# Workshop Service 

---

## Application

- Swagger UI

  http://localhost:8180/swagger-ui/index.html

- Document

  http://localhost:8180/v3/api-docs

- h2

  http://localhost:8180/h2-console

## Docker

```shell
AWSPROFILE={your-aws-profile-for-xray} docker compose up
```

## Environment Variables

### Docker

```shell
SPRING_DATASOURCE_DATABASE=coredb;SPRING_DATASOURCE_HOST=localhost;SPRING_DATASOURCE_PASSWORD=mysql;SPRING_DATASOURCE_PORT=3306;SPRING_DATASOURCE_USERNAME=docker;SPRING_PROFILES_ACTIVE=local
```

