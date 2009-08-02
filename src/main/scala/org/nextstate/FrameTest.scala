package org.nextstate

object FrameTest {
  def main(args : Array[String]) : Unit = {
    val frame = new MyFrame
    frame.pack()
    frame.visible = true
  }
}

import scala.swing._
import scala.swing.event._

class MyFrame extends Frame {

    val button = new Button {
      
       text = "Click "
    }
      
    contents = new BoxPanel(Orientation.Vertical) {
        contents += button
    }

    listenTo(button)
      reactions += {
          case ButtonClicked(b) =>  println("ButtonClicked 23456")
      }    
}
