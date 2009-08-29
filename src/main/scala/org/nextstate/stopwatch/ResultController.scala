package org.nextstate.stopwatch

import org.nextstate.state._
import util.logging._

class ResultController(interaction: ResultInteraction) extends StateMachine with ConsoleLogger {

  object ResultState extends State

  object ClearResultAction {
	def execute(signal: Signal) {
      interaction.result("")
    }   
  }

    // Transitions
    object ResultTransitions extends Transitions {
        def execute(signal: Signal): State = {
            signal match {
                case signal: ResultSignal => interaction.result( signal.result); return ResultState
                case signal: ClearResultSignal => ClearResultAction.execute(signal); return ResultState
                case _ => return ResultState
            }
        }
    }

    // State configurations
    ResultState.transitions = ResultTransitions
    activeState = ResultState
}