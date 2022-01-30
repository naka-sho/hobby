tool:
	docker-compose \
		-f docker-compose-adminer.yml \
		-f docker-compose-mysql.yml \
		up

dev:
	./gradlew quarkusDev

symbol:
	cd symbol && node app.js

user-client:
	cd user/chat-client && yarn start

socket:
	cd user/chat-server && node app.js

build:
	docker build -f src/main/docker/Dockerfile.jvm -t quarkus/hobby-jvm . --no-cache

prod:
	docker build -f src/main/docker/Dockerfile.jvm -t quarkus/hobby-jvm . --no-cache && \
	docker-compose \
		-f docker-compose-adminer.yml \
		-f docker-compose-mysql.yml \
		-f docker-compose.yml \
		-f docker-compose-symbol.yml \
		-f docker-compose-user.yml \
		-f docker-compose-socket.yml \
		up

#prod:
#	docker-compose \
#		-f docker-compose-adminer.yml \
#		-f docker-compose-mysql.yml \
#		-f docker-compose.yml \
#		up
