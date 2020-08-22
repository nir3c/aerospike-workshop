package com.workshop.aerospikewriter

import akka.actor.ActorSystem
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.SystemMaterializer
import com.typesafe.scalalogging.StrictLogging

import scala.language.postfixOps
/**
 * Created by Nir.
 */
object Main extends App with StrictLogging {

  val system = ActorSystem()
  val sysMaterializer: SystemMaterializer = SystemMaterializer(system)

  val consumerSettings = ConsumerSettings[String, String](Configuration.kafkaConfig, None, None)

  Consumer
    .plainSource(consumerSettings, Subscriptions.topics(Configuration.topic))
    .runForeach {
      record =>
        logger.info(
          s"""New record from ${Configuration.topic}:
             |key: ${record.key()}
             |value: ${record.value()}""".stripMargin)
    }(sysMaterializer.materializer)






    logger.info("Start consuming from Kafka")

}