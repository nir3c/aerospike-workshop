package com.workshop.aerospike

import com.aerospike.client.AerospikeClient
import com.aerospike.client.async.{EventPolicy, NettyEventLoops}
import com.aerospike.client.policy.ClientPolicy
import io.netty.channel.nio.NioEventLoopGroup

import scala.util.Try

object AerospikeFactory {

  private val eventLoops: Either[Throwable, NettyEventLoops] =
    Try(new NettyEventLoops(new EventPolicy(), new NioEventLoopGroup(1))).toEither

  private val aerospikeConnection: (String, Int, NettyEventLoops) => Either[Throwable, AerospikeClient] = {
    case (host, port, els) =>
      Try {
        val clp = new ClientPolicy()
        clp.eventLoops = els
        new AerospikeClient(clp, host, port)
      }.toEither
  }

  def createAerospikeConnections(host: String, port: Int): Either[Throwable, (AerospikeClient, NettyEventLoops)] = (for {
    el <- AerospikeFactory.eventLoops
    c <- AerospikeFactory.aerospikeConnection(host, port, el)
  } yield (c, el))

}
