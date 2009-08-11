package org.nextstate.view

import swing._
import util.logging._
import org.nextstate.interaction._
import org.nextstate.state._

object StopWatchView extends SimpleGUIApplication with StopWatchInteraction with ConsoleLogger {

  var controller: StateMachine = null
  
  def createButton(text: String, action: Action): Button = {val b = new Button(action); b.text = text; return b}
   
  // UI Elements (and events)
  var startButton: Button = createButton("Start", Action("StartAction") {	
	      controller ! new StartSignal
	    })
  var clearBtton: Button = createButton("Clear", Action("ClearAction") {	
	      controller ! new ClearSignal
	    })
  var submitButton: Button = createButton("Submit", Action("SubmitAction") {	
	      controller ! new SubmitSignal
	    })
  
  val result = new TextField { text = "0"; columns = 9 }
    
  // UI interface
  def startButtonText(text: String) {
    startButton.text = text
  } 
  def startButtonEnabled(enabled: Boolean) {
    startButton.enabled = enabled
  }
  def clearButtonText(text: String) {
    clearBtton.text = text
  } 
  def clearButtonEnabled(enabled: Boolean) {
    startButton.enabled = enabled
  }
  def submitButtonText(text: String) {
    submitButton.text = text
  } 
  def submitButtonEnabled(enabled: Boolean) {
    startButton.enabled = enabled
  }
  def result(text: String) {
    result.text = text
  } 

  // add contnets to layout
  val ui = new FlowPanel {
    contents += result
    contents += startButton
    contents += clearBtton
    contents += submitButton
  }

  // For running the StopWatchView standalone
  def top = new MainFrame {
    title = "StopWatchView"
    
    controller = new DummyController
    controller.start
  
   	contents = ui
  }
}
