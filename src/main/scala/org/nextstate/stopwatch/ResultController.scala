package org.nextstate.stopwatch

import org.nextstate.state._
import util.logging._

class ResultController(interaction: ResultInteraction) extends StateMachine with ConsoleLogger {

    object ResultState extends State {
        override def execute(signal: Signal): State = {
            signal match {
                case signal: ResultSignal => interaction.result( signal.result); return ResultState
                case signal: ClearResultSignal => interaction.result(""); return ResultState
                case _ => return ResultState
            }
        }
    }

    // State configurations
    activeState = ResultState
}