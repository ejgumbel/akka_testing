package components

import akka.actor.Actor
import computers.{InitialConstant, SurfaceComputer}
import messages.{EmptyMessage, GetInitialConstantState, InitialConstantInitialized, InitialConstantInitializer}

class Surface extends Actor {
  private var ic: InitialConstant = new InitialConstant(InitialConstantDefaults.initial,
    InitialConstantDefaults.max,
    InitialConstantDefaults.rate)

  def receive = {
    case InitialConstantInitializer(initial, max, rate) =>
      reinitialize(initial, max, rate)
    case GetInitialConstantState => sender() ! getState()
    case EmptyMessage => sender() ! EmptyMessage
    case _ => sender() ! "Nothing for this case."
  }

  def reinitialize(initial: Double, max: Double, rate: Double) = {
    ic = new InitialConstant(initial, max, rate)
    sender() ! InitialConstantInitialized
  }

  def getState(): InitialConstantState = {
    InitialConstantState(ic.getCurrentStorage(), ic.getMaxStorage(), ic.freeStorage(), ic.getConstantRate())
  }
}
