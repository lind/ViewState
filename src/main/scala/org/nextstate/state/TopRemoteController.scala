package org.nextstate.state

import util.logging._

class TopRemoteController extends RemoteStateMachine(9010) with ConsoleLogger {

  object ResultState extends State

//  object AddResultAction {
//	def execute(signal: ResultSignal) {
//      interaction.result( signal.result)
//    }   
// 
//  }

//  object ReceiveAction {
//	def execute(signal: Signal) {
//		// do nothing 
//    }   
//  }

    // Transitions
    object ResultTransitions extends Transitions {
        def execute(signal: Signal): State = {
            signal match {
                case _ => signal.direction = Direction.Down; return ResultState // Always return this state. The state machine forwards the signal to the children.
            }
        }
    }

    // State configurations
    ResultState.transitions = ResultTransitions
    activeState = ResultState
}
