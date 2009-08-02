package org.nextstate.view

import swing._
import GridBagPanel._
import java.awt.Insets

object StopWatchViewGrid extends SimpleGUIApplication {

  val ui = new GridBagPanel {
    val c = new Constraints
    val shouldFill = true
    if (shouldFill) {
      c.fill = Fill.Horizontal
    }

    val time = new TextField("0:00:00.0")
    c.fill = Fill.Horizontal
    c.anchor = Anchor.North
    c.ipady = 40;      //make this component tall
    c.weightx = 0.0;
    c.gridwidth = 3;
    c.insets = new Insets(10, 0, 0, 0)
    c.gridx = 0;
    c.gridy = 0;
    layout(time) = c
    
    val button1 = new Button("Start")     
    c.ipady = 0;      //make this component small
    c.anchor = Anchor.CENTER
    c.weightx = 0.5
    c.gridwidth = 1;
    c.fill = Fill.Horizontal
    c.gridx = 0;
    c.gridy = 1;
    layout(button1) = c

        val button2 = new Button("Clear")
    c.fill = Fill.Horizontal
    c.weightx = 0.5;
    c.gridx = 1;
    c.gridy = 1;
    layout(button2) = c

    val button3 = new Button("Submit")
    c.fill = Fill.Horizontal
    c.weightx = 0.5;
    c.gridx = 2;
    c.gridy = 1;
    layout(button3) = c

//    listenTo(button1)
//    
//    reactions += {
//      case 
//    }
  }
          
  def top = new MainFrame {
    title = "StopWatchGrid"
   	contents = ui
  }

}
