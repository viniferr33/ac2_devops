docker container prune -f
docker image prune -f

"${MAVEN_HOME}"/bin/mvn clean install package -Pprod -DskipTests

docker build . -t "${APPLICATION_IMAGE_NAME}"

docker login -u "$DOCKER_USER" -p "$DOCKER_TOKEN"
docker push "${APPLICATION_IMAGE_NAME}"
