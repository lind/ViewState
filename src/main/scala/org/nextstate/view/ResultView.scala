package org.nextstate.view

import swing._
import util.logging._
import org.nextstate.interaction._
import org.nextstate.state._

trait InteractionView {
  var controller: StateMachine = null
  var ui: Component = null
}
object TheResultView extends ResultView {
  // For testing the ResultView standalone with a dummy controller
    controller = new DummyController
    controller.start

} 
class ResultView extends SimpleGUIApplication with InteractionView with ResultInteraction with ConsoleLogger {

//  var controller: StateMachine = null
     
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
  
  override def top = new MainFrame {
    title = "ResultView"
      
   	contents = ui
  }  
}
