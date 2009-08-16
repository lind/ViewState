package org.nextstate.view

import swing._

object RemoteApp extends SimpleGUIApplication {
  def top = new MainFrame {
    title = "Result Application"
    
    val resultView = new ResultView
    
    import org.nextstate.state._
    var resultController = new ResultController(resultView)
    resultController.start

    import org.nextstate.state._ 
    val remoteController = new TopRemoteController
    remoteController.children = List(resultController)
    remoteController.start

    resultView.controller = resultController
    
    contents = new BorderPanel {
      
      import BorderPanel.Position._
      layout(resultView.ui) = South
    }
  }

}

//import org.nextstate.interaction._
//import org.nextstate.state._
//import util.logging._
//
//class ResultController(interaction: ResultInteraction) extends StateMachine(9010) with ConsoleLogger {
//
//  object ResultState extends State
//
////  object AddResultAction {
////	def execute(signal: ResultSignal) {
////      interaction.result( signal.result)
////    }   
//// 
////  }
//
//  object ClearResultAction {
//	def execute(signal: Signal) {
//      interaction.result("")
//    }   
//  }
//
//    // Transitions
//    object ResultTransitions extends Transitions {
//        def execute(signal: Signal): State = {
//            signal match {
//                case signal: ResultSignal => interaction.result( signal.result); return ResultState
//                case signal: ClearResultSignal => ClearResultAction.execute(signal); return ResultState
//                case _ => return null
//            }
//        }
//    }
//
//    // State configurations
//    ResultState.transitions = ResultTransitions
//    activeState = ResultState
//
//}