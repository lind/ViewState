package org.nextstate.interaction

trait StopWatchInteraction {

  def startButtonText(text:String)
  def startButtonEnabled(enabled: Boolean)
  def clearButtonText(text:String)
  def clearButtonEnabled(enabled: Boolean)
  def submitButtonText(text:String)
  def submitButtonEnabled(enabled: Boolean)
  def result(text:String)
}
