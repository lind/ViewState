package org.nextstate.stopwatch

import swing._
import util.logging._
import org.nextstate.state._
import org.nextstate.view._

// For testing the view with a dummy controller
object TheStopWatchView extends StopWatchView {
    controller = new DummyController
    controller.start
}
  
class StopWatchView extends SimpleGUIApplication with InteractionView with StopWatchInteraction with ConsoleLogger {

  def createButton(text: String, action: Action): Button = {val b = new Button(action); b.text = text; return b}
   
  // UI Elements
  val startButton: Button = createButton("Start", Action("StartAction") {	
	      controller ! startButtonSignal
	    })
  val clearButton: Button = createButton("Clear", Action("ClearAction") {	
	      controller ! clearButtonSignal
	    })
  val submitButton: Button = createButton("Submit", Action("SubmitAction") {	
	      controller ! submitButtonSignal
	    })
  val result = new TextField { text = "0"; columns = 9 }

  // UI events with default signals
  var startButtonSignal: Signal = new StartSignal;
  var clearButtonSignal: Signal = new ClearSignal;
  var submitButtonSignal: Signal = new SubmitSignal;
      
  // UI interface API
  def startButtonText(text: String) {
    startButton.text = text
  } 
  def startButtonSignal(signal: Signal) {
    startButtonSignal = signal
  }
  def startButtonEnabled(enabled: Boolean) {
    startButton.enabled = enabled
  }

  def clearButtonText(text: String) {
    clearButton.text = text
  } 
  def clearButtonSignal(signal: Signal) {
    clearButtonSignal = signal
  }
  def clearButtonEnabled(enabled: Boolean) {
    clearButton.enabled = enabled
  }
  
  def submitButtonText(text: String) {
    submitButton.text = text
  } 
  def submitButtonSignal(signal: Signal) {
    submitButtonSignal = signal
  }
  def submitButtonEnabled(enabled: Boolean) {
    submitButton.enabled = enabled
  }
  def result(text: String) {
    result.text = text
  } 

  // add contnets to layout
  ui = new FlowPanel {
    contents += result
    contents += startButton
    contents += clearButton
    contents += submitButton
  }

  def top = new MainFrame {
    title = "StopWatchView"    
   	contents = ui
  }
}