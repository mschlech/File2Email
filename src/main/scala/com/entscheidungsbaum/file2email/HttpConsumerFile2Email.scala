package com.entscheidungsbaum.file2email

import akka.camel.{CamelMessage, Consumer}
import akka.util.Timeout
import akka.actor.Props
import akka.pattern.ask

import reflect.internal.Required

case class tempObject(a:Any , b: Any , c: Any)

class HttpConsumerFile2Email extends Consumer{

     def endpointUri = "jetty://http://localhost:11111/file2Email"

  implicit def timout = Timeout(30000)
  val emailer =context.actorOf(Props(new Emailer(EmailerConfig(System.getProperty("gmail.password")))))


   def receive = {
    case msg : CamelMessage => {


      println("CamelMessage in HttpConsumerFile2Email " + msg.getHeaders.get("name"))

      val from =  msg.getHeaders.get("from")
      val to = msg.getHeaders.get("to")
      val name = msg.getHeaders.get("name") //.getOrElse("Stranger")

      val a = tempObject(from, to , name)
      /**
       * back to the initial caller
       * asynchronous fire and forget semantic
       */
      emailer !   (Email(from.toString, to.toString,"hello", msg.getHeaders.get("name").toString) )// ignoring the response


      /**
       * send the Email to the emailer actor
       * asynchronous fire and forget semantic
       */
      println("case class " + a)
      val message = "Hello %s from  " format a.a

      sender ! message

    }

  }
}
