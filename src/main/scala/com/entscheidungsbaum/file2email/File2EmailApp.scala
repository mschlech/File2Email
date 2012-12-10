package com.entscheidungsbaum.file2email

import akka.actor.{Props, ActorSystem}


object File2EmailApp extends App{

  println("Starting File2EmailApp")

  val sys = ActorSystem("File2Email")
  sys.actorOf(Props[HttpConsumerFile2Email],"httpConsumer")
  sys.awaitTermination()

}
