package components

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import messages.signal.GetInitialConstantState

import scala.concurrent.duration._
import scala.concurrent.Await
import org.scalactic.TolerantNumerics
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class SurfaceStatusTest extends FunSuite with BeforeAndAfterAll {
  implicit val doubleEquality = TolerantNumerics.tolerantDoubleEquality(0.001)
  implicit val timeout = Timeout(5 seconds)
  val system = ActorSystem("TestingSystem")
  val surface = system.actorOf(Props[Surface])

  override def afterAll(): Unit = {
    surface ! PoisonPill
    system.terminate()
  }

  test("InitialConstantStatusMessageForRate") {
    val future = surface ? GetInitialConstantState
    val result = Await.result(future, timeout.duration).asInstanceOf[InitialConstantState]
    assert(result.rate === 5.076)
  }
  test("InitialConstantStatusMessageForMax") {
    val future = surface ? GetInitialConstantState
    val result = Await.result(future, timeout.duration).asInstanceOf[InitialConstantState]
    assert(result.max === 25.4)
  }
  test("InitialConstantStatusMessageForCurrent") {
    val future = surface ? GetInitialConstantState
    val result = Await.result(future, timeout.duration).asInstanceOf[InitialConstantState]
    assert(result.current === 0d)
  }
  test("InitialConstantStatusMessageForFree") {
    val future = surface ? GetInitialConstantState
    val result = Await.result(future, timeout.duration).asInstanceOf[InitialConstantState]
    assert(result.free === 25.4)
  }
}
