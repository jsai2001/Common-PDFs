# Use the official Node.js image
FROM node:14 as build

# Create and change to the app directory
WORKDIR /usr/src/app

# Copy application dependency manifests to the container image.
COPY package*.json ./

# Clear npm cache and install dependencies
# RUN npm cache clean --force && 
RUN npm install

RUN npm ci && npm audit fix

# Copy local code to the container image.
COPY . .

# Build the app
RUN npm run build

# Stage 2: Serve the built application using Nginx
FROM nginx:alpine

# Copy the build output from the previous stage to the Nginx html directory
COPY --from=build /usr/src/app/build /usr/share/nginx/html

# Expose the port Nginx will serve on
EXPOSE 80

# Run Nginx
CMD ["nginx", "-g", "daemon off;"]