import swing._
import org.nextstate.stopwatch._

/*
RemoteApp.top.visible = true

*/
object RemoteApp extends SimpleGUIApplication {
  def top = new MainFrame {
    title = "Result Application"
    
    val resultView = new ResultView
    
    import org.nextstate.state._
    var resultController = new ResultController(resultView)
    resultController.start

    import org.nextstate.state._ 
    val remoteController = new TopRemoteController
    remoteController.children = List(resultController)
    remoteController.start

    resultView.controller = resultController
    
    contents = new BorderPanel {
      
      import BorderPanel.Position._
      layout(resultView.ui) = South
    }
  }

}