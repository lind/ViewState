package org.nextstate.view

import swing._
import util.logging._
import org.nextstate.interaction._
import org.nextstate.state._

object ResultView extends SimpleGUIApplication with ResultInteraction with ConsoleLogger {

  var controller: StateMachine = null
     
  // UI Elements (and events)
  var clearButton: Button = new Button(Action("ClearAction") {	
	      controller ! new ClearResultSignal
	    })
  
  val result = new TextField { text = "0"; columns = 9 }
    
  // UI interface
  def result(text: String) {
    result.text = text
  } 

  // add contnets to layout
  val ui = new FlowPanel {
    contents += result
    contents += clearButton
  }

  // For running the StopWatchView standalone
  def top = new MainFrame {
    title = "ResultView"
    
    controller = new DummyController
    controller.start
  
   	contents = ui
  }
}
