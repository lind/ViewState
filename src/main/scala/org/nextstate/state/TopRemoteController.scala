package org.nextstate.state

import util.logging._

class TopRemoteController extends RemoteStateMachine(9010) with ConsoleLogger {

    object ResultState extends State {
        override def execute(signal: Signal): State = {
            signal match {
                case _ => signal.direction = Direction.Down; return ResultState // Always return this state. The state machine forwards the signal to the children.
            }
        }
    }

    // State configuration
    activeState = ResultState
}
