docker container prune -f
docker image prune -f

"${MAVEN_HOME}"/bin/mvn clean install package -P"${ENV}" -DskipTests

docker build . -t "${APPLICATION_IMAGE_NAME}"
