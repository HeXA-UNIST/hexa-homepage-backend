### Docker file build & run

build : 
```
docker build -t homepage_backend .
```  
run :
```
docker run --name homepage_backend --network host \
-e DATABASE_NAME=${HOMEPAGE_BACKEND_DATABASE_NAME} \
-e MYSQL_PASSWORD=${HOMEPAGE_BACKEND_MYSQL_PASSWORD} \
-e MYSQL_USERNAME=${HOMEPAGE_BACKEND_MYSQL_USERNAME} \
-e SUPER_DUPER_SECURE_VALUE=${HOMEPAGE_BACKEND_SUPER_DUPER_SECURE_VALUE} \
-e SUPER_SECURE_KEY=${HOMEPAGE_BACKEND_SUPER_SECURE_KEY} \
homepage_backend
```