package org.nextstate.stopwatch

import org.nextstate.state._
import util.logging._
import org.apache.commons.lang.time.StopWatch;
import java.util.Timer;
import java.util.TimerTask;
import scala.actors.remote.Node
import scala.actors.remote.RemoteActor._

class StopWatchController(interaction: StopWatchInteraction) extends StateMachine with ConsoleLogger {

  val time = new StopWatch
  var timer = new Timer;
  var callbackTimer: TimerTask = null;

  // Signals used by the controller
  val clearSignal = new ClearSignal
  val startSignal = new StartSignal
  val stopSignal = new StopSignal
  val submitSignal = new SubmitSignal
  val suspendSignal = new SuspendSignal
  val resumeSignal = new ResumeSignal
  val splitSignal = new SplitSignal
  val unsplitSignal = new UnsplitSignal

  // intialize the buttons eventhandlers
  interaction.startButtonSignal(startSignal)	 
  interaction.clearButtonSignal(clearSignal)	 
  interaction.submitButtonSignal(submitSignal)	 

  // Controller actions
  def createTimerTask {
    callbackTimer = new TimerTask() {
	  override def run() {
	    val signal = new TimerSignal 
        signal.loggable = false
	    send(signal)
      }};
    timer.schedule(callbackTimer, 0, 10);
  }

  def send(signal: Signal): Unit = {
    this ! signal
  }
  
  def startAction() {
	createTimerTask
	
	time.reset();
	time.start();
	interaction.startButtonText("Stop")
	interaction.startButtonSignal(stopSignal)	 
	interaction.startButtonEnabled(true)
	interaction.clearButtonText("Suspend")
	interaction.clearButtonSignal(suspendSignal)	 
	interaction.clearButtonEnabled(true)
	interaction.submitButtonText("Split")
	interaction.submitButtonSignal(splitSignal)	 
	interaction.submitButtonEnabled(true)	 
  }
  
  def stopAction() {
    callbackTimer.cancel
	time.stop();
    interaction.startButtonText("Start")	 
    interaction.startButtonSignal(startSignal)	 
    interaction.clearButtonText("Clear")
    interaction.clearButtonSignal(clearSignal)	 
    interaction.clearButtonEnabled(true)
    interaction.submitButtonText("Submit")
    interaction.submitButtonSignal(submitSignal)	 
    interaction.submitButtonEnabled(true)	 
    interaction.result(time.toString)
  }

  def clearAction {
    time.reset
    interaction.result(time.toString)
 }

  def submitAction() {
    // create a resultsignal which should be forwarded to the parent controller
    val signal = new ResultSignal(this.toString, time.toString)
    signal.direction = Direction.Up
    send(signal)
  }
 
  def suspend() {
	interaction.clearButtonText("Resume");
	interaction.clearButtonSignal(resumeSignal);
	interaction.submitButtonEnabled(false)
    time.suspend
  }

  def resume() {
	interaction.clearButtonText("Suspend");
	interaction.clearButtonSignal(suspendSignal);
	interaction.submitButtonEnabled(true)
    time.resume    
  }
  
  def split() {
	interaction.submitButtonText("Unsplit");
	interaction.submitButtonSignal(unsplitSignal);
//	interaction.clearButtonEnabled(true)
    time.split
  }

  def unsplit() {
	interaction.submitButtonText("Split");
	interaction.submitButtonSignal(splitSignal);
	interaction.clearButtonEnabled(true)
    time.unsplit    
  }

  def timerAction() {
    interaction.result(time.toString)
  }

  // State configurations
  object StoppedState extends State {
	override def execute(signal: Signal): State = {
	  signal match {
	    case signal: StartSignal => startAction; return RunningState
	    case signal: ClearSignal => clearAction; return StoppedState
	    case signal: SubmitSignal => submitAction; return StoppedState
	    case _ => return null
	  }
	}
  }

  object RunningState extends State {
	  override def execute(signal: Signal): State = {
        signal match {
            case signal: StopSignal => stopAction; return StoppedState
            case signal: TimerSignal => timerAction; return RunningState
            case signal: SuspendSignal => suspend; return SuspendedState
            case signal: SplitSignal => split; return SplitState
            case _ => return null
        }
	  }
  }

  object SuspendedState extends State {
       override def execute(signal: Signal): State = {
            signal match {
                case signal: StopSignal => stopAction; return StoppedState
                case signal: ResumeSignal => resume; return RunningState
                case _ => return null
            }
       }    
  }

  object SplitState extends State {
       override def execute(signal: Signal): State = {
            signal match {
                case signal: StopSignal => stopAction; return StoppedState
                case signal: UnsplitSignal => unsplit; return RunningState
                case _ => return null
            }
       }    
  }

  // Set the active state for the state machine
  activeState = StoppedState
}


// Testing the controller
/*
org.nextstate.state.TheStopWatchController ! signal.StopSignal
*/
object TheStopWatchController extends StopWatchController(TestStopWatchInteraction) {
  start
}

import util.logging._
// A simple implementation for testing the controller 
object TestStopWatchInteraction extends StopWatchInteraction with ConsoleLogger {

  def startButtonText(text:String) {
    log("startButtonText: " + text)
  }
  def startButtonSignal(signal: Signal) {
    log("startButtonSignal: " + signal)
  }
  def startButtonEnabled(enabled: Boolean) {
    log("startButtonEnabled: " + enabled)
  }
  def clearButtonText(text:String) {
    log("clearButtonText: " + text)    
  }
  def clearButtonSignal(signal: Signal) {
    log("clearButtonSignal: " + signal)    
  }
  def clearButtonEnabled(enabled: Boolean) {
    log("clearButtonEnabled: " + enabled)    
  }
  def submitButtonText(text:String) {
    log("submitButtonText: " + text)    
  }
  def submitButtonSignal(signal: Signal) {
    log("submitButtonSignal: " + signal)    
  }
  def submitButtonEnabled(enabled: Boolean) {
    log("submitButtonEnabled: " + enabled)    
  }
  def result(text:String) {
//    log("result: " + text)
  }
}