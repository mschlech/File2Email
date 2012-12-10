package com.entscheidungsbaum.file2email

import akka.actor.Actor
import akka.camel.{Producer, CamelMessage}
import akka.actor.IO.Failure
import reflect.internal.Required

case class EmailerConfig(gmailPassword:String)
case class Email(from:String , to:String , subject:String , body:String )

class Emailer(cfg:EmailerConfig) extends Actor with Producer {


  def endpointUri = "smtps:///smtp.gmail.com:587?username=<userName>@gmail.com&password=<password>&host=smtp.gmail.com&debugMode=false" //format cfg.gmailPassword.toString


  override protected def transformOutgoingMessage(msg: Any) = msg match {

    case Email(from, to, subject, body) =>
      println("message in producer actor " + msg.toString)
      new CamelMessage(body, Map("from" -> from,  "to" -> to,  "subject"-> subject, "body" -> body))
  }

  override protected def transformResponse(msg : Any) = msg match {
    case resp: Failure => akka.actor.Status.Failure(resp.cause)
    case _ => msg
  }
}
