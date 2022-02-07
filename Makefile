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
	cd user/chat-client && yarn install && yarn start --port=3002

socket:
	cd user/chat-server && npm install && node app.js

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
