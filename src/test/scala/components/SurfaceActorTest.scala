package components

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import messages.{EmptyMessage, GetInitialConstantState, InitialConstantInitialized, InitialConstantInitializer}
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
    "return an EmptyMessage when contacted with an EmptyMessage" in {
      val surface = system.actorOf(Props[Surface])
      surface ! EmptyMessage
      expectMsg(EmptyMessage)
    }
  }

  "The Surface actor" must {
    "return the string \"Nothing for this case.\" when contacted with an odd message case" in {
      val surface = system.actorOf(Props[Surface])
      surface ! "TestString"
      expectMsg("Nothing for this case.")
    }
  }

  "The Surface actor" must {
    "return an InitialConstantInitialized when contacted with an InitialConstantInitializer" in {
      val surface = system.actorOf(Props[Surface])
      surface ! InitialConstantInitializer(0, 50.8, 11.4)
      expectMsg(InitialConstantInitialized)
    }
  }

}
