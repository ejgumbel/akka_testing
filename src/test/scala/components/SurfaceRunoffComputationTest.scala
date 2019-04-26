package components

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import messages.data.{HorizontalWaterFlux, VerticalWaterFlux}

import scala.concurrent.duration._
import scala.concurrent.Await
import org.scalactic.TolerantNumerics
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class SurfaceRunoffComputationTest extends FunSuite with BeforeAndAfterAll {
  implicit val doubleEquality = TolerantNumerics.tolerantDoubleEquality(0.001)
  implicit val timeout = Timeout(5 seconds)
  val system = ActorSystem("TestingSystem")
  val surface = system.actorOf(Props[Surface])


  test("DefaultICSurfaceRunoffTimestep1") {
    val future = surface ? VerticalWaterFlux(0.254, 60d)
    val result = Await.result(future, timeout.duration).asInstanceOf[HorizontalWaterFlux]
    assert(result.depth === 0d)
  }
  test("DefaultICSurfaceRunoffTimestep1TS") {
    val future = surface ? VerticalWaterFlux(0.254, 60d)
    val result = Await.result(future, timeout.duration).asInstanceOf[HorizontalWaterFlux]
    assert(result.timestep === 60d)
  }


}
