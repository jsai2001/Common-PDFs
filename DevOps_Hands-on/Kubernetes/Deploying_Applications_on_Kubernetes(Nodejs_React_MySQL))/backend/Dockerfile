# Use the official Node.js image
FROM node:14

# Create and change to the app directory
WORKDIR /usr/src/app

# Copy application dependency manifests to the container image.
# This includes package.json and package-lock.json
COPY package*.json ./

# Clear npm cache and install dependencies
# This step installs the dependencies listed in package.json and package-lock.json
RUN npm cache clean --force && npm install

# Copy local code to the container image.
# This step copies the rest of the application code to the Docker image
COPY . .

# Expose the port the app runs on
# This step exposes port 3000 for the application
EXPOSE 3000

# Run the web service on container startup.
# This step starts the Node.js application
CMD [ "node", "src/server.js" ]