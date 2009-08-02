package org.nextstate.state

import util.logging._
import org.nextstate.state._

class MessagingController extends StateMachine with ConsoleLogger {

  object ActiveState extends State
  
  object SendMessageAction extends StateAction {
    def execute(signal: Signal) {
      log("SendMessageAction")
//      resultActor ! new ResultSignal(this.toString, time.toString)
    }
  }

  object ClearAction extends StateAction {
    def execute(signal: Signal) {
      log("ClearAction")
//      resultActor ! new ResultSignal(this.toString, time.toString)
    }
  }

  
  object ActiveTransitions extends Transitions {
    def execute(signal: Signal): State = {
      signal match {
        case signal: SendMessageSignal => SendMessageAction.execute(signal); return ActiveState
        case signal: ClearMessageSignal => ClearAction.execute(signal); return ActiveState
        case _ => return null
      }
    }
  }
      
  ActiveState.transitions = ActiveTransitions
}
