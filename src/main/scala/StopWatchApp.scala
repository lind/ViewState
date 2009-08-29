import swing._
import org.nextstate.stopwatch._
  
/*
//val stop = org.nextstate.view.StopWatchApp
StopWatchApp.top.visible = true
StopWatchApp.controller ! StartSignal
StopWatchApp.controller ! StopSignal
StopWatchApp.parentController ! new org.nextstate.state.NewResultInterfaceSignal

*/
object StopWatchApp extends SimpleGUIApplication {
    
    val stopWatchView = new StopWatchView
    
    import org.nextstate.state._
    var controller:StateMachine = new StopWatchController(stopWatchView)
    controller.start
    
    stopWatchView.controller = controller
      
  def top = new MainFrame {
    title = "StopWatch Application"

    contents = new BorderPanel {
      
      import BorderPanel.Position._
      layout(stopWatchView.ui) = South
    }
  }
}