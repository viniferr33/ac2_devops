#!/bin/sh
export PORT="8080";
export POSTGRES_PASSWORD='password';
export POSTGRES_USER='postgres';
export POSTGRES_DATABASE='ac2';
export APPLICATION_IMAGE_NAME='ac2';
export ENV='homol';

mvn clean install package -P${ENV} -DskipTests;

docker container prune -f
docker image prune -f
docker volume rm ${POSTGRES_DATABASE}_db-volume
docker build . -t $APPLICATION_IMAGE_NAME;

docker compose up