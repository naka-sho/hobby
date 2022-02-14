tool:
	docker-compose \
		-f docker-compose-adminer.yml \
		-f docker-compose-mysql.yml \
		up

dev:
	./gradlew quarkusDev

router:
	docker-compose \
		-f docker-compose-router.yml \
		up

symbol-server:
	cd symbol && npm install && node app.js

user-client:
	cd user/chat-client && yarn install && yarn start

socket:
	cd user/chat-server && npm install && node app.js

build:
	./gradlew build && docker build -f src/main/docker/Dockerfile.jvm -t quarkus/hobby-jvm . --no-cache

prod:
	./gradlew build && docker build -f src/main/docker/Dockerfile.jvm -t quarkus/hobby-jvm . --no-cache && \
	docker-compose \
		-f docker-compose-adminer.yml \
		-f docker-compose-mysql.yml \
		-f docker-compose.yml \
		-f docker-compose-symbol.yml \
		-f docker-compose-user.yml \
		-f docker-compose-socket.yml \
		-f docker-compose-router-prod.yml \
		up
