package com.knoldus.bootstrap

import com.knoldus.request.{AsynchronousFutureRequest, AsynchronousStreamRequest, SynchronousRequest}

object Application extends App {

  val synchronousRequest = new SynchronousRequest
  val asynchronousFutureRequest = new AsynchronousFutureRequest
  val asynchronousStreamRequest = new AsynchronousStreamRequest

  synchronousRequest.runSynchronousRequest
  asynchronousFutureRequest.asynchronousRequest
  asynchronousStreamRequest.asynchronousRequest
}
