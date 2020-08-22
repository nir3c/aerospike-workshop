 <pre>
prerequisites for this workshop:
    -1. basic Kafka knowledge.
    0. basic Aerospike XDR knowledge.
    1. ability to run docker images on your machine.
    2. basic docker-compose file knowledge.
    3. aerospike feature key file(so you can run Aerospike Enterprise Edition).

To make sure all is set for the workshop please put your feature key file (features.conf) under
<your-clone-project-path>/docker/all-services/aerospike/features/
<your-clone-project-path>/docker/aerospike-receiver/aerospike/features/ 
<your-clone-project-path>/docker/kafka-only/aerospike/features/ 
 
 for windows please replace $PWD with the full cloned project folder path
 
Then run setup-workshop.sh(mac + linux) 
Then run setup-workshop.bat(windows) 

if all good you should get log as:

number of running containers:
      7

 
 Modules in this repository:
    1. aerospike-common - library module for aerospike-writer and aerospike-reader.
    2. aerospike-writer - write new data to aerospike.
        write 2 bins:
            a. "bin" - String value indicate the value of the record.
            b. "timestamp" -  long value indicate the timestamp the record created.
        environment variable configurations you can change:
            a.  AEROSPIKE_HOST - host of aerospike to connect to.
            b.  AEROSPIKE_PORT - port of aerospike to connect to.
    3. aerospike-reader - read data from aerospike based on "timeout" bin - starting to read from beginning.
        environment variable configurations you can change:
            a.  AEROSPIKE_HOST - host of aerospike to connect to.
            b.  AEROSPIKE_PORT - port of aerospike to connect to.
    4. kafka-reader - read data from kafka.
        environment variable configurations you can change:
            a.  KAFKA_SERVERS - kafka server ip and port like "127.0.0.1:9092".
            b.  KAFKA_TOPIC - which topic to consume records from.
            
            
On workshop we will work on:
    1. setup Aerospike without xdr feature(under ./docker/aerospike-receiver).
    2. setup Aerospike with xdr feature(under ./docker/aerospike-receiver).
    3. setup Aerospike Kafka Connector(under ./docker/kafka-only).
    
</pre>
