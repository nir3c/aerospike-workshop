docker build -t workshop-aerospike-kafka-outbound:3.0.0 ./docker/kafka-connector
cd ./docker/all-services
docker-compose up -d
echo 'number of running containers:'
docker ps | wc -l
docker-compose down
