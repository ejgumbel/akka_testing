package messages.signal

import akka.actor.ActorRef

case class TransmitVFluxTimeseries(surface: ActorRef)
