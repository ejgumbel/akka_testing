package computers

import org.scalactic.TolerantNumerics
import org.scalatest.FunSuite

class InitialConstantTest extends FunSuite {
  implicit val doubleEquality = TolerantNumerics.tolerantDoubleEquality(0.001)
  test("InitialConstant.freeStorage1") {
    val ic: InitialConstant = new InitialConstant(0d, 50.8, 11.4)
    assert(ic.freeStorage() === 50.8)
  }
  test("InitialConstant.computeRunoffTimestep1") {
    val sc: SurfaceComputer = new InitialConstant(0d, 50.8, 11.4)
    assert(sc.computeRunoffTimestep(0.1, 3600) === 0d)
  }
  test("InitialConstant.computeRunoffTimestep2") {
    val sc: SurfaceComputer = new InitialConstant(0d, 50.8, 11.4)
    assert(sc.computeRunoffTimestep(51, 3600) === 0d)
  }
  test("InitialConstant.computeRunoffTimestep3") {
    val sc: SurfaceComputer = new InitialConstant(0d, 50.8, 11.4)
    assert(sc.computeRunoffTimestep(63, 3600) === 0.8)
  }
}
