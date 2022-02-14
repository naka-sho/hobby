FROM node:12.13.0

COPY . /var/hobby/
WORKDIR /var/hobby
RUN yarn install
EXPOSE 3002
#ENTRYPOINT tail -f /dev/null
CMD yarn --max-http-header-size=16384 start
