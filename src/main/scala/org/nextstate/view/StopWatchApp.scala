package org.nextstate.view

import swing._
  
object StopWatchApp extends SimpleGUIApplication {
  def top = new MainFrame {
    title = "StopWatch Application"
    
    val stopWatchView = StopWatchView
    
    import org.nextstate.state._
//    var controller:StateMachine = new OnOffController(stopWatchView)
    var controller:StateMachine = new StopWatchController(stopWatchView)
//    var controller:StateMachine = new DummyController
    controller.start
    
    stopWatchView.controller = controller
    
    contents = new BorderPanel {
      
      import BorderPanel.Position._
      layout(stopWatchView.ui) = South
    }
  }

}
