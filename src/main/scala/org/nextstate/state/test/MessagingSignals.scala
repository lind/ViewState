package org.nextstate.state.test

import org.nextstate.state._

case class ClearMessageSignal() extends Signal
case class SendMessageSignal() extends Signal
case class MessageSignal(val message: String, user: String) extends Signal
