package com.workshop.aerospikewriter

import java.time.Instant

import akka.NotUsed
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.stream.SystemMaterializer
import akka.stream.scaladsl.Source
import com.aerospike.client.policy.ScanPolicy
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
  private val printActor: ActorRef = system.actorOf(Props[PrintActor], "PrintActor")

  val (client, eventLoops) = AerospikeFactory.createAerospikeConnections(
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
        client.scanAll(eventLoops.next(), new PrintScanCallback(printActor, nextTimestamp), new ScanPolicy(),
          "xdr_intro_namespace", "xdr_intro_set_name")
        nextTimestamp = Instant.now().toEpochMilli
      }.recover {case t => logger.error("",t)}
    }
  }.run()(sysMaterializer.materializer)


  logger.info("Start consuming from Aerospike")

}

class PrintActor extends Actor with StrictLogging {

  def receive: Receive = {
    case msg: String => logger.info(s"received Aerospike Record bin value: $msg")
    case _      => logger.info("received unknown message")
  }
}