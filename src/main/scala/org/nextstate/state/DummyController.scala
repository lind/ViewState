package org.nextstate.state

import util.logging._

class DummyController extends StateMachine with ConsoleLogger {

  object DummyState extends State {
    override def execute(signal: Signal): State = {
      log("Signal received:" + signal)
      return DummyState
    }
  }
  activeState = DummyState

}
