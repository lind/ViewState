package org.nextstate.state

import util.logging._

class DummyController extends StateMachine with ConsoleLogger {

  object DummyState extends State with ConsoleLogger {
    override def execute(signal: Signal): State = {
      log(this + " - Signal received: " + signal.toString)
      return DummyState
    }
  }
  activeState = DummyState

}
