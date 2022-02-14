FROM node:12.13.0

COPY . /var/hobby/
WORKDIR /var/hobby
RUN npm install
EXPOSE 3001
#ENTRYPOINT tail -f /dev/null
CMD node --max-http-header-size=16384 app.js
