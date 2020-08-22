FROM ubuntu:18.04

RUN apt-get update
RUN apt-get install -y openjdk-8-jdk 
RUN apt-get install -y  openjdk-8-jre 
RUN apt-get install -y  wget
RUN wget "https://www.aerospike.com/enterprise/download/connectors/aerospike-kafka-outbound/3.0.0/artifact/legacy-deb" -O aerospike-kafka-outbound-3.0.0.deb
RUN dpkg -i aerospike-kafka-outbound-3.0.0.deb
RUN rm -rf aerospike-kafka-outbound-3.0.0.deb
#RUN echo "/opt/aerospike-kafka-outbound/bin/aerospike-kafka-outbound --http -f /etc/aerospike-kafka-outbound/aerospike-kafka-outbound.yml > /dev/null 2>&1 &"  > /opt/aerospike-kafkaoutbound/start.sh
RUN echo "/opt/aerospike-kafka-outbound/bin/aerospike-kafka-outbound --http -f /etc/aerospike-kafka-outbound/aerospike-kafka-outbound.yml"  > /opt/aerospike-kafka-outbound/start.sh
RUN chmod 777 /opt/aerospike-kafka-outbound/start.sh
CMD ["/bin/bash", "/opt/aerospike-kafka-outbound/bin/aerospike-kafka-outbound", "--http" , "-f", "/etc/aerospike-kafka-outbound/aerospike-kafka-outbound.yml"]
