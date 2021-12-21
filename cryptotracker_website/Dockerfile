FROM nginx

# Set directory
WORKDIR /etc/nginx/conf.d

# Clear default nginx conf
RUN rm *
# Set nginx conf
ADD nginx_configuration.conf /etc/nginx/conf.d/default.conf

# Set directory
WORKDIR /usr/share/nginx/html

# Delete the default Nginx Template Files
RUN rm *

# Get required packages
RUN apt update
RUN apt upgrade -y
RUN apt install npm nodejs -y

# Clone repository
RUN git clone https://github.com/TomTruyen/Crypto-Investment-Tracker.git

WORKDIR /usr/share/nginx/html/Crypto-Investment-Tracker/cryptotracker_website

ENTRYPOINT rm -rf node_modules && git pull && npm i && npm run build && nginx && tail

EXPOSE 80