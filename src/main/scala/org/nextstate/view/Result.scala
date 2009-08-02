package org.nextstate.view

import scala.actors.Actor
import scala.actors.Actor._
import scala.actors.remote.RemoteActor._
import util.logging._
import org.nextstate.state._

object RemoteResultApp extends ConsoleLogger {
    def main(args: Array[String]) : Unit = {
        val port = args(0).toInt
        log("starting RemoteResultActor on port: " + port)
        val result = new RemoteResultActor(port)
        result.start()
        log("RemoteResultActor started")

    }
}

class RemoteResultActor(port: Int) extends Actor with ConsoleLogger {
def act() {
    alive(port)
    register('Result, self)
 
    while (true) {
      receive {
        case signal: ResultSignal =>
          log("RemoteResultActor: result: " + signal.result + " id: " + signal.id)
        case signal: QuitSignal =>
          Console.println("RemoteResultActor: stop")
          exit()         // (10)
        case _ =>
          log("hopsan")
      }
    }
  }
}
