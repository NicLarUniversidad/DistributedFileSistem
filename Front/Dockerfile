FROM node:22-alpine as builder
WORKDIR /app

ARG REACT_APP_GATEWAY_URL
ENV REACT_APP_GATEWAY_URL=$REACT_APP_GATEWAY_URL
ARG REACT_APP_CHUNK_SIZE
ENV REACT_APP_CHUNK_SIZE=$REACT_APP_CHUNK_SIZE

ARG private_key
ENV private_key=$private_key

RUN echo ${private_key}

COPY package*.json .
COPY yarn*.lock .
RUN yarn install
COPY . .
RUN yarn build

#Stage 2
FROM nginx

WORKDIR /usr/share/nginx/html

RUN rm -rf ./*
COPY --from=builder /app/build .
COPY nginx.conf /etc/nginx/nginx.conf
RUN echo ${private_key} > front.key
COPY front.key /etc/ssl/front.key
COPY front.crt /etc/ssl/front.crt

ENTRYPOINT ["nginx", "-g", "daemon off;"]

