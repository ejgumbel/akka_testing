package components

import akka.actor.Actor
import computers.{InitialConstant, SurfaceComputer}
import messages.EmptyMessage

class Surface extends Actor {
  private var sc: SurfaceComputer = new InitialConstant(0d, 50.8, 11.4)

  def receive = {
    case _ => sender() ! EmptyMessage
  }
}
