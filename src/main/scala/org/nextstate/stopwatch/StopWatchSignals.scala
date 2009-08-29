package org.nextstate.stopwatch

import org.nextstate.state._

// Signals need to be types -> class 
class StartSignal extends Signal
class StopSignal extends Signal
class ClearSignal extends Signal
class SubmitSignal extends Signal

class ResumeSignal extends Signal
class SuspendSignal extends Signal

class SplitSignal extends Signal
class UnsplitSignal extends Signal

class TimerSignal extends Signal

class ResultSignal(val id: String, val result: String) extends Signal
class ClearResultSignal extends Signal

class NewResultInterfaceSignal extends Signal

//class QuitSignal extends Signal
