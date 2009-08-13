package org.nextstate.state

import scala.actors.Actor
import util.logging._

object Direction extends Enumeration {
  val Up, Down, None = Value
}

@serializable @SerialVersionUID(123)
case class Signal() {
  // TODO remove and use direction
//    var up = true
    var direction = Direction.None // Default is to not forward the signal
    var loggable = true
    override def toString = this.getClass.getSimpleName
}

// not needed..?
trait StateAction {
    def execute(signal: Signal)
    override def toString = this.getClass.getSimpleName
}

//trait State {
//    def execute(signal: Signal): State
//}

// Transitions contains all transitions from one state.
abstract class Transitions {
    def execute(signal: Signal): State
    override def toString = this.getClass.getName
}

class State extends Logged { // extends State
    var transitions: Transitions = null

    def execute(signal: Signal): State = {
        if (null == transitions) {
            log(this + " - No transitions in state")
            return null
        }
        transitions.execute(signal)
    }
    override def toString() = this.getClass.getSimpleName
}

import scala.actors.remote.RemoteActor._
import scala.actors.Actor._

abstract class StateMachine extends Actor with Logged {

    var activeState: State = null
    var parent: StateMachine = null
    var children: List[StateMachine] = null
    def log(signal: Signal, msg: String) {
      if (signal.loggable) log(msg)
    }
    
    def act() {
        react {
            case signal: Signal  =>
                log(signal, this + " - Signal:" + signal)
                if (null != activeState) {
                    val s = activeState.execute(signal)
                    if (null != s) activeState = s else log(signal, this + " - No transitions matched")
                    log(signal, this + " - Active state: " + activeState)
                    forwardSignal(signal)
                    log(signal, "---")
                    act()
                } else {
                    log(signal, this + " - No active state. Initialize the State Machine.")
                }
            case other =>
                log(this + " - message not a Signal: " + other)
                act()
        }
        log(this + " - closing")
    }
    
    /**
     * Passing the signal to the parent or children.
     */
    def forwardSignal(signal: Signal) {
        signal match {
            case signal:Signal if (Direction.Up == signal.direction) =>
                log(signal, this + " - forwardSignal: up")
                if (null != parent) parent ! signal
            case signal:Signal if (Direction.Down == signal.direction) =>
                log(signal, this + " - forwardSignal: down")
                if (null != children) {
                	for (child <- children) {
                		child ! signal
                	}
                }
//            case signal:Signal if (Direction.None == signal.direction) => // Do nothing
            case _ => log(signal, this + " - forwardSignal: None")
        }
    }
    override def toString() = this.getClass.getSimpleName
}

abstract class RemoteStateMachine(port: Int) extends StateMachine {
override def act() {
    log(this + " - starting listening on port:" + port)
    alive(port)
    register('Result, self)
 
    while (true) {
      receive {
        case signal: QuitSignal =>
          Console.println("RemoteResultActor: stop")
          exit()         // (10)
        case signal: Signal =>
          log(this + " - Signal received: " + signal)
          signal.direction = Direction.Down
          forwardSignal(signal)
        case _ =>
          log("hopsan")
      }
    }
  }
}
