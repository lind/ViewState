package org.nextstate.state

import org.nextstate.interaction._
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

  val resultActor = select(Node("127.0.0.1", 9010), 'Result)
  
  log("resultActor:" + resultActor)
  
  object StoppedState extends State
  object RunningState extends State
//  TODO: to implement
//  object SplitState extends State
//  object SuspendedState extends State
  
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
  
  object StartAction {
    def execute(signal: Signal) {
      	createTimerTask

		time.reset();
		time.start();
	    interaction.startButtonText("Stop")
	    interaction.clearButtonText("Suspend")
	    interaction.clearButtonEnabled(true)
	    interaction.submitButtonText("Split")
	    interaction.submitButtonEnabled(true)	 
	  }
  }
	object StopAction extends StateAction {
	  def execute(signal: Signal) {	
	    callbackTimer.cancel
		time.stop();
	    interaction.startButtonText("Start")	 
	    interaction.clearButtonText("Clear")
	    interaction.clearButtonEnabled(true)
	    interaction.submitButtonText("Submit")
	    interaction.submitButtonEnabled(true)	 
        interaction.result(time.toString)
	  }
	}
 object ClearAction extends StateAction {
      def execute(signal: Signal) {
        time.reset
        interaction.result(time.toString)
      }   
 }

 object SubmitAction extends StateAction {
   def execute(signal: Signal) {
     log("Sending result to Remote Actor")
     val signal = new ResultSignal(this.toString, time.toString)
     signal.direction = Direction.Up
     send(signal)
     resultActor ! signal

     log("Result sent")     
   }
 }
    object TimerAction extends StateAction {
      def execute(signal: Signal) {
        interaction.result(time.toString)
      }
    }
    
    // Transitions
    object StoppedTransitions extends Transitions {
        def execute(signal: Signal): State = {
            signal match {
                case signal: StartSignal => StartAction.execute(signal); return RunningState
                case signal: ClearSignal => ClearAction.execute(signal); return StoppedState
                case signal: SubmitSignal => SubmitAction.execute(signal); return StoppedState
                case _ => return null
            }
        }
    }
    object RunningTransitions extends Transitions {
        def execute(signal: Signal): State = {
            signal match {
                case signal: StartSignal => StopAction.execute(signal); return StoppedState
                case signal: TimerSignal => TimerAction.execute(signal); return RunningState
                case _ => return null
            }
        }
    }

    // State configurations
    StoppedState.transitions = StoppedTransitions
    RunningState.transitions = RunningTransitions
    activeState = StoppedState
}
