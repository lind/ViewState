package org.nextstate.state

class SignalOne extends Signal 
class SignalTwo extends Signal 
class SignalThree extends Signal

// StopWatch signals
class StartSignal extends Signal // Stop signal in state Running
class ClearSignal extends Signal
class SubmitSignal extends Signal

// TODO: to implement
//case class ResumeSignal extends Signal
//case class SuspendSignal extends Signal
//
//case class SplitSignal extends Signal
//case class UnSplitSignal extends Signal

class TimerSignal() extends Signal

class ResultSignal(val id: String, val result: String) extends Signal
class ClearResultSignal extends Signal
class QuitSignal extends Signal

class NewResultInterfaceSignal extends Signal
