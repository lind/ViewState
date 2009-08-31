package org.nextstate.state

import util.logging._
import scala.actors.remote.Node
import scala.actors.remote.RemoteActor._
import org.nextstate.view._
import org.nextstate.stopwatch._

class TopController(val topApp: AddableInterface, val ip: String, val port: Int) extends StateMachine with ConsoleLogger {

  val resultActor = select(Node(ip, port), 'Result)
  
  log("resultActor:" + resultActor)

  object BounceAction {
	def execute(signal: Signal) {
      log(this + " - Signal received: " + signal.toString)
      resultActor ! signal
      signal.direction = Direction.Down
    }   
  }

    object TopState extends State {
       override def execute(signal: Signal): State = {
            signal match {
              case signal: NewResultInterfaceSignal => {
                val resultView = new ResultView
                var resultController:StateMachine = new ResultController(resultView)
              	resultController.start    
              	resultView.controller = resultController
              	topApp.addPage(resultView)
                return TopState}
              case _ => BounceAction.execute(signal); return TopState                                                        
            }
       }          
    }


    // State configurations
    activeState = TopState

}