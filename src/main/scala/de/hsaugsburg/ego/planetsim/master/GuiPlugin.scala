package de.hsaugsburg.ego.planetsim.master

import de.hsaugsburg.ego.planetsim.domain.{RenderObject, Planet, Star}
import de.hsaugsburg.ego.planetsim.gui.{PlanetSimMainFrame, PlanetSimOptionFrame}
import de.hsaugsburg.smas.plugin.base.SmasPlugin

import scala.collection.mutable.ListBuffer

/**
 * User: chris
 * Date: 12.12.13
 * Time: 01:05
 */
class GuiPlugin extends SmasPlugin {

  val controlFrame = new PlanetSimOptionFrame(this)
  val simulationFrame = new PlanetSimMainFrame(this)

  var roMap: Map[String, RenderObject] = Map()

  def displayCtrlFrame {
    controlFrame.visible = true
  }

  def displaySimFrame {
    simulationFrame.visible = true
  }

  def onStart =
  {
    displayCtrlFrame
    displaySimFrame

    GuiPlugin.solarSystemList foreach {
      ro: RenderObject => {
        roMap += (ro.id -> ro)
      }
    }

    simulationFrame.init

    /*while(true) {

      roMap.values foreach {
        ro: RenderObject => {


          ro.objectType match {
            case Planet => {
              var oldPos = ro.position
              ro.position = (oldPos._1 + ro.objectType.posScale, oldPos._2 + ro.objectType.posScale, oldPos._3 + ro.objectType.posScale)
            }
            case _ => { }
          }
        }
      }

      simulationFrame.update

      Thread.sleep(500)

    }*/

    log.info("Simulation MasterPlugin created!")
    true
  }

  def onStop = true
}

object GuiPlugin {

  var phiResolution = 20
  var thetaResolution = 50

  val camPos: (Double, Double, Double) = (0.0, 1000.0, 00.0)
  val camAngel: Double = 90

  def solarSystemList: List[RenderObject] = {
    val roLB = new ListBuffer[RenderObject]()
    roLB += new RenderObject("Sonne",   Star,     696e6,    2.054e30, (0.0,       0.0,  0.0), (0.0, 0.0,      0.0))
    roLB += new RenderObject("Merkur",  Planet,   244e4,    3.30e23,  (57.91e9,   0.0,  0.0), (0.0, 47.9e3,   0.0))
    roLB += new RenderObject("Venus",   Planet,   6052e3,   4.87e24,  (108.94e9,  0.0,  0.0), (0.0, 35.05e3,  0.0))
    roLB += new RenderObject("Erde",    Planet,   6371e3,   5.97e24,  (149.6e9,   0.0,  0.0), (0.0, 29.8e3,   0.0))
    roLB += new RenderObject("Mars",    Planet,   3390e3,   6.42e23,  (227.94e9,  0.0,  0.0), (0.0, 24.24e3,  0.0))
    roLB += new RenderObject("Jupiter", Planet,   69911e3,  1.90e27,  (778.57e9,  0.0,  0.0), (0.0, 13.01e3,  0.0))
    roLB += new RenderObject("Saturn",  Planet,   58232e3,  5.68e26,  (1.434e12,  0.0,  0.0), (0.0, 9.6e3,    0.0))
    roLB += new RenderObject("Uranus",  Planet,   25362e3,  8.68e25,  (2.872e12,  0.0,  0.0), (0.0, 6.8e3,    0.0))
    roLB += new RenderObject("Neptun",  Planet,   24624e3,  1.02e26,  (4.495e12,  0.0,  0.0), (0.0, 5.43e3,   0.0))

    roLB.toList
  }

  def randomSystem {

  }
}