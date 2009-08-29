package org.nextstate.state

import util.logging._
import scala.actors.remote.Node
import scala.actors.remote.RemoteActor._
import org.nextstate.view._
import org.nextstate.stopwatch._

/*
NewResultInterfaceSignal
 */
class TopController(val topApp: AddableInterface) extends StateMachine with ConsoleLogger {

  val resultActor = select(Node("127.0.0.1", 9010), 'Result)
  
  log("resultActor:" + resultActor)

  object TopState extends State

  object BounceAction {
	def execute(signal: Signal) {
      log(this + " - Signal received: " + signal.toString)
      log("Sending result to Remote Actor")
      resultActor ! signal
      signal.direction = Direction.Down
    }   
  }

    // Transitions
    object TopTransitions extends Transitions {
        def execute(signal: Signal): State = {
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
    TopState.transitions = TopTransitions
    activeState = TopState

}