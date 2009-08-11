package org.nextstate.state

import scala.actors.Actor
import util.logging._

@serializable @SerialVersionUID(123)
case class Signal() {
    var up = false
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
                if (null == activeState) {
                    log(signal, this + " - No active state. Initialize the State Machine.")
                } else {
                    val s = activeState.execute(signal)
                    if (null != s) activeState = s else log(signal, this + " - No transitions matched")
                    log(signal, this + " - Active state: " + activeState)
                    forwardSignal(signal)
                    log(signal, "---")
                    act()
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
            case signal:Signal if (true == signal.up) =>
                log(signal, this + " - forwardSignal: up")
                if (null != parent) parent ! signal
            case signal:Signal if (false == signal.up) =>
                log(signal, this + " - forwardSignal: down")
            if (null != children)
                for (child <- children) {
                    child ! signal
                }
        }
    }
    override def toString() = this.getClass.getSimpleName
}
