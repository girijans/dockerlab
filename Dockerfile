FROM node:12-alpine
RUN apk add --no-cache python2 g++ make
WORKDIR /app
COPY ./app/. .
RUN yarn install --production
CMD ["node", "src/index.js"]
EXPOSE 3000
