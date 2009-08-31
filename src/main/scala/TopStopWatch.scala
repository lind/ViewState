import swing._
import event._
import Swing._
import org.nextstate.state._
import org.nextstate.view._
import org.nextstate.stopwatch._

/*
val app = org.nextstate.view.TopStopWatchApp

TopStopWatch.top.visible = true
TopStopWatch.stopWatchController ! StartSignal
TopStopWatch.parentController ! NewResultInterfaceSignal
TopStopWatch.parentController ! StateConfigurationSignal
TopStopWatch.stopWatchController ! new org.nextstate.state.StateConfigurationSignal
*/
object TopStopWatch extends SimpleGUIApplication with AddableInterface {

  var parentController:StateMachine = new TopController(this, "127.0.0.1", 9010)
  parentController.start

  val resultView = new ResultView
  var resultController:StateMachine = new ResultController(resultView)
  resultController.parent = parentController
  resultController.start
  resultView.controller = resultController
  
  val stopWatchView = new StopWatchView
  var stopWatchController:StateMachine = new StopWatchController(stopWatchView)
  stopWatchController.parent = parentController
  stopWatchController.start    
  stopWatchView.controller = stopWatchController
  
  parentController.children = List(resultController, stopWatchController)

  import TabbedPane._
  def addPage(view: InteractionView) {
    tabs.pages += new Page(view.getClass.getSimpleName, view.ui)
    view.controller.parent = parentController
    parentController.children =  parentController.children ::: List(view.controller)
  }
  
  val label = new Label("Status...")

  val tabs = new TabbedPane {
    pages += new Page("StopWatch", stopWatchView.ui)      
    pages += new Page("Result", resultView.ui)
  }

  val ui = new BorderPanel {
    layout(tabs) = BorderPanel.Position.Center
    layout(label) = BorderPanel.Position.South
  }

  def top = new MainFrame { 
    title = "TopStopWatch Application"
    contents = ui
  }
}  

