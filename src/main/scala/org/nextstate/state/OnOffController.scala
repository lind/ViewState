package org.nextstate.state

import org.nextstate.interaction._
import util.logging._

class OnOffController(interaction: StopWatchInteraction) extends StateMachine with ConsoleLogger {
    object OnState extends State
    object OffState extends State

    object OffAction extends StateAction {
      def execute(signal: Signal) {
//        log("OffAction running") 
        interaction.startButtonText("Aa")
      }
    }
    object OnAction extends StateAction {
      def execute(signal: Signal) {
//        log("OnAction running")
        interaction.clearButtonText("OnOn")
        interaction.submitButtonText("OOOo")
        interaction.result("13,43")
      }
    }

    object FromOnTransitions extends Transitions {
        def execute(signal: Signal): State = {
//            log("In FromOnTransitions signal:" + signal.getClass.getName)
            signal match {
                case signal: SignalOne => OffAction.execute(signal); return OffState
                case _ => return null
            }
        }
    }
    object FromOffTransitions extends Transitions {
        def execute(signal: Signal): State = {
//            log("In FromOffTransitions signal:" + signal.getClass.getName)
            signal match {
                case signal: SignalTwo => OnAction.execute(signal); return OnState
                case _ => return null
            }
        }
    }
    
    OnState.transitions = FromOnTransitions
    OffState.transitions = FromOffTransitions
    activeState = OnState
}
