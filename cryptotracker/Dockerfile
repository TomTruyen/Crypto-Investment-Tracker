FROM arm32v7/openjdk:11

# Set current directory to root
WORKDIR /

# Install maven (mvn)
RUN apt-get update
RUN apt-get upgrade -y --allow-unauthenticated
RUN apt-get install maven -y --allow-unauthenticated

# Get GitHub repository
RUN git clone https://github.com/TomTruyen/Crypto-Investment-Tracker.git

# Set GitHub repository (API) directory
WORKDIR /Crypto-Investment-Tracker/cryptotracker

# On startup ==> Get newest version from git
# Create new package
# Start API
ENTRYPOINT git pull && mvn clean package && java -jar target/*.jar

EXPOSE 8888
