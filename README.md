# Banco LCP

## Get Started with Docker

Baixe a imagem 
```bash
docker pull mysql
```

Suba um container
```bash
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=banco-lcp -e MYSQL_USER=root -e MYSQL_PASSWORD=root --name banco-lcp mysql
```

Acesse o container e crie o banco
```bash
docker exec -it banco-lcp bash
```