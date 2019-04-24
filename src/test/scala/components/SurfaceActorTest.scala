package components

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import messages.EmptyMessage
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class SurfaceActorTest()
  extends TestKit(ActorSystem("System"))
    with ImplicitSender
    with WordSpecLike
    with Matchers
    with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "The Surface actor" must {
    "return an EmptyMessage when contacted" in {
      val surface = system.actorOf(Props[Surface])
      surface ! EmptyMessage
      expectMsg(EmptyMessage)
    }
  }

}
