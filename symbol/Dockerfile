FROM node:12.13.0

COPY . /var/hobby/
WORKDIR /var/hobby
RUN npm install
EXPOSE 3000
#ENTRYPOINT tail -f /dev/null
CMD node app.js
