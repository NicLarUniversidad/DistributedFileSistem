FROM node:22.9.0 as builder
WORKDIR /app

ARG REACT_APP_GATEWAY_URL
ARG REACT_APP_CHUNK_SIZE

COPY package*.json .
COPY yarn*.lock .
RUN yarn install
COPY . .

RUN REACT_APP_GATEWAY_URL=${REACT_APP_GATEWAY_URL} REACT_APP_CHUNK_SIZE=${REACT_APP_CHUNK_SIZE} npm run build
#RUN yarn build

#Stage 2
FROM nginx

WORKDIR /usr/share/nginx/html

RUN rm -rf ./*
COPY --from=builder /app/build .
COPY nginx.conf /etc/nginx/nginx.conf
COPY front.key /etc/ssl/front.key
COPY front.crt /etc/ssl/front.crt
COPY password /etc/keys/password
RUN echo -e ${private_key} >> /etc/ssl/front.key

ENTRYPOINT ["nginx", "-g", "daemon off;"]

