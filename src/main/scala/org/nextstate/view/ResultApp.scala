package org.nextstate.view

import swing._

object ResultApp extends SimpleGUIApplication {
  def top = new MainFrame {
    title = "Result Application"
    
    val resultView = ResultView
    
    import org.nextstate.state._
    var controller:StateMachine = new ResultController(resultView)
//    var controller:StateMachine = new DummyController
    controller.start
    
    resultView.controller = controller
    
    contents = new BorderPanel {
      
      import BorderPanel.Position._
      layout(resultView.ui) = South
    }
  }

}

import org.nextstate.interaction._
import org.nextstate.state._
import util.logging._

class ResultController(interaction: ResultInteraction) extends StateMachine with ConsoleLogger {

  object ResultState extends State

//  object AddResultAction {
//	def execute(signal: ResultSignal) {
//      interaction.result( signal.result)
//    }   
// 
//  }

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
                case _ => return null
            }
        }
    }

    // State configurations
    ResultState.transitions = ResultTransitions
    activeState = ResultState

}
