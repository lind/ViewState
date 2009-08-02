package state

import scala.actors.Actor

class Signal {
    var up: Boolean = false
    override def toString = this.getClass.getName
}

trait StateAction {
    def execute(signal: Signal)
    override def toString = this.getClass.getName
}

trait State {
    def execute(signal: Signal): State
}

// Transitions contains all transitions from one state.
abstract class Transitions {
    def execute(signal: Signal): State
    override def toString = this.getClass.getName
}

class StateImpl(val name: String) extends State {
    var transitions: Transitions = null

    def execute(signal: Signal): State = {
        if (null == transitions) {
            println("No transitions in state " + name)
            return null
        }
        transitions.execute(signal)
    }
    override def toString = name
}

import util.logging._
abstract class StateMachine extends Actor with Logged {
    var activeState: State = null
    var parent: StateMachine = null
    var children: List[StateMachine] = null
    def act() {
        react {
            case signal: Signal  =>
                println("SM: Signal:" + signal)
                if (null == activeState) {
                    println("No active state. Initialize the State Machine.")
//                    act()
                } else {
                    val s = activeState.execute(signal)
                    if (null != s) activeState = s else println("No transitions matched")
                    log("Active state:" + activeState)
                    forwardSignal(signal)
                    act()
                }
            case _ =>
                println("SM: message not a Signal.")
                act()
        }
        println("etter react")
    }
    
    /**
     * Passing the signal to the parent or children.
     */
    def forwardSignal(signal: Signal) {
        signal match {
            case signal:Signal if (true == signal.up) =>
                println(this + "forwardSignal: up")
                if (null != parent) parent ! signal
            case signal:Signal if (false == signal.up) =>
                println(this + "forwardSignal: down")
            if (null != children)
                for (child <- children) {
                    child ! signal
                }
        }
    }
    override def toString() = {
        this.getClass.getName
    }
}
