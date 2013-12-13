package de.hsaugsburg.ego.planetsim.gui

import org.slf4j.LoggerFactory
import scala.swing._
import vtk._
import javax.swing.{WindowConstants, JPanel}
import scala.swing.Frame
import java.awt.BorderLayout
import de.hsaugsburg.ego.planetsim.domain._
import scala.collection.mutable.ListBuffer
import scala._
import scala.swing.event.ButtonClicked
import de.hsaugsburg.ego.planetsim.domain.Planet
import de.hsaugsburg.ego.planetsim.domain.Star

/**
 * User: chris
 * Date: 12.12.13
 * Time: 01:16
 */

object PlanetarySimulation {
  protected val log = LoggerFactory.getLogger(this.getClass)

  private var PhiResolution = 20
  private var ThetaResolution = 50

  val camPos: (Double, Double, Double) = (0.0, 200.0, 0.0)
  val camAngel: Double = 40

  def getPhiResolution = PhiResolution
  def getThetaResolution = ThetaResolution

  def calcScaledRadius(ao: AstronomicObject): Double = {
    ao match {
      case ao: Star => ao.radius / 10e6
      case ao: Planet => ao.radius / 20e5
    }
  }

  def calcScaledPosition(ao: AstronomicObject): (Double, Double, Double) = {
    val scale = 9e8
    (ao.position._1 / scale, ao.position._2 / scale, ao.position._3 / scale)
  }

}

class PlanetarySimulation {
  private val controlFrame = new ControlFrame
  private val simulationFrame = new SimulationFrame

  private var aoList: List[AstronomicObject] = List()

  def start {
    displayCtrlFrame
    displaySimFrame
  }

  def displayCtrlFrame {
    controlFrame.visible = true
  }

  def displaySimFrame {
    simulationFrame.visible = true
  }

  def loadSolarSystem {
    val aoLB = new ListBuffer[AstronomicObject]()
    aoLB += Star("Sonne",     696e6,    2.054e30, (0.0,       0.0,  0.0), (0.0, 0.0,      0.0))
    aoLB += Planet("Merkur",  244e4,    3.30e23,  (57.91e9,   0.0,  0.0), (0.0, 47.9e3,   0.0))
    aoLB += Planet("Venus",   6052e3,   4.87e24,  (108.94e9,  0.0,  0.0), (0.0, 35.05e3,  0.0))
    aoLB += Planet("Erde",    6371e3,   5.97e24,  (149.6e9,   0.0,  0.0), (0.0, 29.8e3,   0.0))
    aoLB += Planet("Mars",    3390e3,   6.42e23,  (227.94e9,  0.0,  0.0), (0.0, 24.24e3,  0.0))
    aoLB += Planet("Jupiter", 69911e3,  1.90e27,  (778.57e9,  0.0,  0.0), (0.0, 13.01e3,  0.0))
    aoLB += Planet("Saturn",  58232e3,  5.68e26,  (1.434e12,  0.0,  0.0), (0.0, 9.6e3,    0.0))
    aoLB += Planet("Uranus",  25362e3,  8.68e25,  (2.872e12,  0.0,  0.0), (0.0, 6.8e3,    0.0))
    aoLB += Planet("Neptun",  24624e3,  1.02e26,  (4.495e12,  0.0,  0.0), (0.0, 5.43e3,   0.0))

    aoList = aoLB.toList
  }

  class SimulationFrame extends Frame{
    title = "SimulationFrame"

    minimumSize = new Dimension(1024, 800)
    //location = new Point((screenSize.width / 2 - 1024 / 2), 0)
    location = new Point(controlFrame.size.width, 0)

    var vtkPanel: vtkPanel = new vtkPanel

    vtkPanel.InteractionModeZoom()

    loadSolarSystem

    aoList foreach {
      ao: AstronomicObject => {
        var ro = new RenderObject(ao)
        vtkPanel.GetRenderer.AddActor(ro.getActor)
      }
    }

    vtkPanel.LightFollowCameraOn()
    val camera = vtkPanel.GetRenderer.GetActiveCamera

    camera.SetPosition(PlanetarySimulation.camPos._1, PlanetarySimulation.camPos._2, PlanetarySimulation.camPos._3)
    camera.SetFocalPoint(0, 0, 0)
    camera.SetViewUp(0, 0, 1)
    camera.SetViewAngle(PlanetarySimulation.camAngel)

    vtkPanel.GetRenderer.SetActiveCamera(camera)

    //vtkPanel.GetRenderer.ResetCamera

    val vtkComponent = new Component {
      override lazy val peer = new JPanel(new BorderLayout())
      peer.add(vtkPanel)
    }

    contents = vtkComponent

  }

  class ControlFrame extends Frame {
    title = "ControlFrame"

    //minimumSize=new Dimension(350, 400)
    location = new Point(0, 0)

    peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    val startButton = new Button("Start")
    val stopButton = new Button("Stop")


    contents = new GridBagPanel {
      val gbc = new Constraints()
      gbc.fill = GridBagPanel.Fill.Both
      gbc.insets = new Insets(5,5,5,5);

      gbc.gridwidth = 2
      gbc.gridx = 0
      gbc.gridy = 0
      add(new Label("Simulation"), gbc)

      gbc.gridwidth = 1
      gbc.gridx = 0
      gbc.gridy = 1
      add(startButton, gbc)
      gbc.gridx = 1
      gbc.gridy = 1
      add(stopButton, gbc)
    }

    listenTo(startButton,stopButton)

    reactions += {
      case ButtonClicked(`startButton`) => {
        startSimulation
      }
      case ButtonClicked(`stopButton`) => {
        stopSimulation
      }
    }


    def startSimulation {
      displaySimFrame
    }

    def stopSimulation {
      simulationFrame.close()
      //sys.exit(0)
    }

  }
}