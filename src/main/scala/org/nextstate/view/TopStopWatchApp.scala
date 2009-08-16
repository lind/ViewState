package org.nextstate.view

import swing._
import event._
import Swing._
import org.nextstate.state._

trait AddableInterface {
  def addPage(view: InteractionView)
}

// val app = org.nextstate.view.TopStopWatchApp
// app.top.visible = true
// app.stopWatchController ! new org.nextstate.state.StartSignal
// app.parentController ! new org.nextstate.state.NewResultInterfaceSignal
object TopStopWatchApp extends SimpleGUIApplication with AddableInterface {

  var parentController:StateMachine = new TopController(this)
  parentController.start

  val resultView = new ResultView
  var resultController:StateMachine = new ResultController(resultView)
  resultController.parent = parentController
  resultController.start
  resultView.controller = resultController
  
  var stopWatchController:StateMachine = new StopWatchController(StopWatchView)
  stopWatchController.parent = parentController
  stopWatchController.start    
  StopWatchView.controller = stopWatchController
  
  parentController.children = List(resultController, stopWatchController)

  import TabbedPane._
  def addPage(view: InteractionView) {
    tabs.pages += new Page("addPage", view.ui)
    view.controller.parent = parentController
    parentController.children =  parentController.children ::: List(view.controller)
  }
  
  val label = new Label("Status...")

  val tabs = new TabbedPane {
    pages += new Page("StopWatch", StopWatchView.ui)      
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