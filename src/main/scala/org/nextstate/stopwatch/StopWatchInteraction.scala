package org.nextstate.stopwatch

import org.nextstate.state._
trait StopWatchInteraction {

  def startButtonText(text:String)
  def startButtonSignal(signal: Signal)
  def startButtonEnabled(enabled: Boolean)
  def clearButtonText(text:String)
  def clearButtonSignal(signal: Signal)
  def clearButtonEnabled(enabled: Boolean)
  def submitButtonText(text:String)
  def submitButtonSignal(signal: Signal)
  def submitButtonEnabled(enabled: Boolean)
  def result(text:String)
}
