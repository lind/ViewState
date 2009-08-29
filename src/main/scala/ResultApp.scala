import swing._
import org.nextstate.stopwatch._
 
/*
ResultApp.top.visible = true
ResultApp.resultView.result("Hei")
ResultApp.controller ! ClearResultSignal
*/
object ResultApp extends SimpleGUIApplication {
  val resultView = new ResultView

  import org.nextstate.state._
  var controller:StateMachine = new ResultController(resultView)
  controller.start
    
  resultView.controller = controller
    
  def top = new MainFrame {
    title = "Result Application"
    
    contents = new BorderPanel {
      import BorderPanel.Position._
      layout(resultView.ui) = South
    }
  }
 
}