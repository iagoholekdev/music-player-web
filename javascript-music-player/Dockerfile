FROM nginx

# Remove the default nginx index.html
RUN rm -rf /usr/share/nginx/html/*

# Copy the entire project to the nginx html directory
COPY . /usr/share/nginx/html/

# Expose port 4200
EXPOSE 4200