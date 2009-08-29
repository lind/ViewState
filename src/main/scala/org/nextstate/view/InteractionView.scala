package org.nextstate.view

import org.nextstate.state._
import swing._
  
trait InteractionView {
  var controller: StateMachine = null
  var ui: Component = null
}

