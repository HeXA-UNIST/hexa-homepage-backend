### Docker file build & run

build : docker build -t (new image name) .  
run : docker run --name (container name) -d -p 8080:8080 (new image name)  