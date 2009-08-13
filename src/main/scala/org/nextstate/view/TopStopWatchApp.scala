package org.nextstate.view

import swing._
import event._
import Swing._
import org.nextstate.state._

object TopStopWatchApp extends SimpleGUIApplication {

  var parentController:StateMachine = new DummyController
  parentController.start

  var resultController:StateMachine = new ResultController(ResultView)
  resultController.parent = parentController
  resultController.start    
  ResultView.controller = resultController
  
  var stopWatchController:StateMachine = new StopWatchController(StopWatchView)
  stopWatchController.parent = parentController
  stopWatchController.start    
  StopWatchView.controller = stopWatchController
  
  parentController.children = List(resultController, stopWatchController)

  val label = new Label("Status...")
  val tabs = new TabbedPane {

    import TabbedPane._
    pages += new Page("Result", ResultView.ui)
    pages += new Page("StopWatch", StopWatchView.ui)
      
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
