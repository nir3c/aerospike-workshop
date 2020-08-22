package com.workshop.aerospikewriter

import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.StrictLogging


/**
 * Created by Nir.
 */
object Configuration extends StrictLogging {

  private val config: Config = ConfigFactory.load()
  private val conf = config.getConfig("com.workshop.kafka-reader")


  val kafkaConfig: Config = conf.getConfig("kafka")
  val topic: String = kafkaConfig.getString("topic")

}
