package components

import akka.actor.Actor
import computers.InitialConstant
import messages.data.{HorizontalWaterFlux, InitialConstantInitializer, VerticalWaterFlux}
import messages.signal.{EmptyMessage, GetInitialConstantState, InitialConstantInitialized}

class Surface extends Actor {
  private var ic: InitialConstant = new InitialConstant(InitialConstantDefaults.initial,
    InitialConstantDefaults.max,
    InitialConstantDefaults.rate)

  def receive = {
    case InitialConstantInitializer(initial, max, rate) =>
      reinitialize(initial, max, rate)
    case GetInitialConstantState => context.parent ! getState()
    case VerticalWaterFlux(depth, timestep) => {
      context.parent ! computeRunoff(depth, timestep)
    }

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

  def computeRunoff(depth: Double, timestep: Double): HorizontalWaterFlux = {
    val computedRunoff: Double = ic.computeRunoffTimestep(depth, timestep)
    return HorizontalWaterFlux(computedRunoff, timestep)
  }
}
