package org.nextstate.interaction

trait MessagingInteraction {

  def addMessage(text: String, user: String)
  def clear()
}
