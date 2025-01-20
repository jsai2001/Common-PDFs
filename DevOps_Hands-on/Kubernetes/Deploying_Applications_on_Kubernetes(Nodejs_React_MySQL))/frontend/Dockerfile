# Use the official Node.js image
FROM node:14

# Create and change to the app directory
WORKDIR /usr/src/app

# Copy application dependency manifests to the container image.
COPY package*.json ./

# Clear npm cache and install dependencies
RUN npm cache clean --force && npm install

RUN npm ci && npm audit fix

# Copy local code to the container image.
COPY . .

# Build the app
RUN npm run build

# Install serve to serve the build
RUN npm install -g serve

# Expose the port the app runs on
EXPOSE 5000

# Run the web service on container startup.
CMD [ "serve", "-s", "build" ]