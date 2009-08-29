package org.nextstate.stopwatch

import swing._
import util.logging._
import org.nextstate.state._
import org.nextstate.view._

// For testing the view with a dummy controller
object TheResultView extends ResultView {
    controller = new DummyController
    controller.start

} 
class ResultView extends SimpleGUIApplication with InteractionView with ResultInteraction with ConsoleLogger {

  // UI Elements (and events)
  var clearButton: Button = new Button(Action("Clear Result") {	
	      controller ! new ClearResultSignal
	    })
  
  val result = new TextField { text = "0"; columns = 9 }
    
  // UI interface
  def result(text: String) {
    result.text = text
  } 

  // add contnets to layout
  ui = new FlowPanel {
    contents += result
    contents += clearButton
  }
  
  def top = new MainFrame {
    title = "ResultView"
      
   	contents = ui
  }  
}
