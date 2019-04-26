import akka.actor.{ActorSystem, Props}
import managers.SimulationManager
import messages.signal.{EmptyMessage}

object Main extends App {
  val system = ActorSystem("RunoffComputer")
  val simulationActor = system.actorOf(Props[SimulationManager], name = "SimulationManager")
  simulationActor ! EmptyMessage
}
