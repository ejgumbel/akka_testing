package components

import akka.actor.{Actor, ActorRef}
import messages.data.VerticalWaterFlux
import messages.signal.{EmptyMessage, GetInitialConstantState, TransmitVFluxTimeseries}

class Precipitation(timesteps: Int) extends Actor {
  private val fixedRate: Double = 1.5 * 25.4 / 60 / 60 //Goes from inches/hour to mm/s
  private val fixedTimeStep: Double = 60d
  private val pr = List.fill(timesteps)(fixedRate)

  def receive = {
    case TransmitVFluxTimeseries(surface) => transmitVFluxTimeseries(surface)
    case _ => println("whoop whoop")
  }

  def transmitVFluxTimeseries(target: ActorRef): Unit = {
    for(prts <- pr) target ! VerticalWaterFlux(prts * fixedTimeStep, fixedTimeStep)
//    target ! GetInitialConstantState
  }

}
