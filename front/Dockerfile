FROM node:20 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
FROM nginx:alpine
COPY --from=build /app/dist/mi-proyecto /usr/share/nginx/html
EXPOSE 4200
CMD ["nginx", "-g", "daemon off;"]