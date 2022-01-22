tool:
	docker-compose \
		-f docker-compose-adminer.yml \
		-f docker-compose-mysql.yml \
		up

dev:
	./gradlew quarkusDev

build:
	docker build -f src/main/docker/Dockerfile.jvm -t quarkus/hobby-jvm . --no-cache

prod:
	docker-compose \
		-f docker-compose-adminer.yml \
		-f docker-compose-mysql.yml \
		-f docker-compose.yml \
		up
