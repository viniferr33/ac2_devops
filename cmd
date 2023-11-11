#!/bin/sh
export PORT="8080";
export POSTGRES_PASSWORD='password';
export POSTGRES_USER='postgres';
export POSTGRES_DATABASE='ac2';
export APPLICATION_IMAGE_NAME='ac2';

mvn clean install package -Pprod -DskipTests;

docker build . -t $APPLICATION_IMAGE_NAME;

docker compose up