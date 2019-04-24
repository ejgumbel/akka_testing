import akka.actor.{ActorSystem, Props}
import components.Surface
import messages.EmptyMessage

object Main extends App {
  val system = ActorSystem("RunoffComputer")
  val surfaceActor = system.actorOf(Props[Surface], name = "SurfaceActor")

//  surfaceActor ! EmptyMessage

  system.terminate()

}
