package managers

import akka.actor.{Actor, PoisonPill, Props}
import components.{InitialConstantState, Precipitation, Surface}
import messages.data.HorizontalWaterFlux
import messages.signal.{EmptyMessage, TransmitVFluxTimeseries}

class SimulationManager extends Actor {
  private val simulationTimesteps: Int = 60
  private val surfaceActor = context.actorOf(Props[Surface], name = "SurfaceActor")
  private val precipitationActor = context.actorOf(Props(new Precipitation(simulationTimesteps)), name = "PrecipitationActor")
  private var stepsComplete: Int = 0

  def receive() = {
    case EmptyMessage => precipitationActor ! TransmitVFluxTimeseries(surfaceActor)
    case HorizontalWaterFlux(computedRunoff, timestep) => {
      println(computedRunoff)
      stepsComplete += 1
      checkIfComplete()
    }
    case InitialConstantState(current, max, free, rate) =>
      println("Current storage:", current)
    case _ => println("whatev")
  }

  def checkIfComplete() = {
    if(stepsComplete == simulationTimesteps) {
      surfaceActor ! PoisonPill
      precipitationActor ! PoisonPill
      self ! PoisonPill
      context.system.terminate()
    }
  }



}
