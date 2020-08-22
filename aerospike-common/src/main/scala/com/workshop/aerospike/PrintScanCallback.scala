package com.workshop.aerospike

import akka.actor.ActorRef
import com.aerospike.client.{AerospikeException, Key, Record}
import com.aerospike.client.listener.RecordSequenceListener
import com.typesafe.scalalogging.StrictLogging

import scala.util.Try

class PrintScanCallback(printActor: ActorRef, timestampToPrintFrom: Long) extends RecordSequenceListener with StrictLogging {

  override def onRecord(key: Key, record: Record): Unit =
    Try {
      val maybeRecord = Option(record)
      if(maybeRecord.isEmpty)
        logger.warn("Got empty record")
      maybeRecord.map(_.getLong("timestamp"))
        .filter(timestamp => timestamp != 0 && timestampToPrintFrom <= timestamp )
        .flatMap(_ => maybeRecord.map(_.getString("bin")))
        .foreach(msg => printActor ! msg)
    }.recover {case t => logger.error("",t)}

  override def onSuccess(): Unit = {}

  override def onFailure(exception: AerospikeException): Unit =
    logger.error("Failed To scan all", exception)

}