package com.workshop.aerospikewriter

import java.time.Instant

import akka.NotUsed
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.stream.SystemMaterializer
import akka.stream.scaladsl.Source
import com.aerospike.client.{Bin, Key}
import com.aerospike.client.policy.{ScanPolicy, WritePolicy}
import com.typesafe.scalalogging.StrictLogging
import com.workshop.aerospike.{AerospikeFactory, PrintScanCallback}

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Try
/**
 * Created by Nir.
 */
object Main extends App with StrictLogging {

  val system = ActorSystem()
  val sysMaterializer: SystemMaterializer = SystemMaterializer(system)

  val (client, _) = AerospikeFactory.createAerospikeConnections(
    Configuration.aerospikeHost, Configuration.aerospikePort)
    .fold(t => {
    logger.error("Failed to create Aerospike connection")
    throw t
  }, identity)

  var nextTimestamp = 0L

  Source.tick(
    1 seconds,
    5 seconds,
    NotUsed
  ).map{
    _ => {
      Try {
        val timestamp = Instant.now().toEpochMilli
        val key = new Key("xdr_intro_namespace", "xdr_intro_set_name", timestamp)
        val bins = Seq(new Bin("bin", s"This is bin from Aerospike ${Configuration.aerospikeHost}:${Configuration.aerospikePort} value for record: $timestamp"),
          new Bin("timestamp", timestamp))
        client.put(new WritePolicy(), key, bins: _*)
        logger.info(
          s"""Put new record in Aerospike:
             |Host:Port: ${Configuration.aerospikeHost}:${Configuration.aerospikePort}
             |namespace.setname.key: ${key.namespace}.${key.setName}.${key.userKey}""".stripMargin)
      }.recover {case t => logger.error("",t)}
    }
  }.run()(sysMaterializer.materializer)

  logger.info("Start producing to Aerospike")

}