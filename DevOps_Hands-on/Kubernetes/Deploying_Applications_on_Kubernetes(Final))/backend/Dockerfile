# Use the official Node.js image
FROM node:14

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

# Expose the port the app runs on
EXPOSE 3000

# Run the web service on container startup.
CMD [ "node", "src/server.js" ]