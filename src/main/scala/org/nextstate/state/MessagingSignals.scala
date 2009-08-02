package org.nextstate.state

class ClearMessageSignal extends Signal
class SendMessageSignal extends Signal
class MessageSignal(val message: String, user: String) extends Signal
