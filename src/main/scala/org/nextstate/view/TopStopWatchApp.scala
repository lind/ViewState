package org.nextstate.view

import swing._
import event._
import Swing._
import org.nextstate.state._

object TopStopWatchApp extends SimpleGUIApplication {

  import TabbedPane._
  
  val stopWatchView = StopWatchView
    
    var controller:StateMachine = new StopWatchController(stopWatchView)
//    var controller:StateMachine = new DummyController
    controller.start
    
    stopWatchView.controller = controller
  

  val label = new Label("Status...")
  val tabs = new TabbedPane {

    pages += new Page("Result", ResultView.ui)
    pages += new Page("StopWatch", stopWatchView.ui)
      
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
