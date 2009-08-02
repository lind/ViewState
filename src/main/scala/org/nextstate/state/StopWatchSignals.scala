package org.nextstate.state

class SignalOne extends Signal 
class SignalTwo extends Signal 
class SignalThree extends Signal

// StopWatch signals
class StartSignal extends Signal
class StopSignal extends Signal
class ClearSignal extends Signal

class SubmitSignal extends Signal

class ResumeSignal extends Signal
class SuspendSignal extends Signal

class SplitSignal extends Signal
class UnSplitSignal extends Signal

class TimerSignal extends Signal

class ResultSignal(val id: String, val result: String) extends Signal
class QuitSignal extends Signal
