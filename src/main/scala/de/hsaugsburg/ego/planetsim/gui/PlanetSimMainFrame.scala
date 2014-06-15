package de.hsaugsburg.ego.planetsim.gui

import java.awt.{Insets, BorderLayout, Point, Dimension}
import javax.swing.{WindowConstants, JPanel}

import de.hsaugsburg.ego.planetsim.domain.RenderObject
import de.hsaugsburg.ego.planetsim.master.GuiPlugin
import vtk.{vtkRenderer, vtkPanel}

import scala.reflect.New
import scala.swing._
import scala.swing.event.ButtonClicked

/**
 * User: chris
 * Date: 14.06.14
 * Time: 00:37
 */
class PlanetSimMainFrame(val gui: GuiPlugin) extends Frame {

  title = "Planetary Simulation"

  minimumSize = new Dimension(1024, 800)
  //location = new Point((screenSize.width / 2 - 1024 / 2), 0)
  //location = new Point(gui.controlFrame.size.width, 0)
  location = new Point(0, 0)

  val startButton = new Button("Start")
  val pauseButton = new Button("Pause")
  val stopButton = new Button("Stop")
  val configButton = new Button("Einstellungen")
  val separator = new Separator()
  separator.preferredSize = new Dimension(800, 0)

  val simulationPanel = new vtkPanel
  val renderer = simulationPanel.GetRenderer()

  val controlPanel = new FlowPanel
  controlPanel.contents += startButton
  controlPanel.contents += pauseButton
  controlPanel.contents += stopButton
  controlPanel.contents += separator
  controlPanel.contents += configButton

  val simulationComponent = new Component {
    override lazy val peer = new JPanel(new BorderLayout)
    peer.add(simulationPanel)
  }

  peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  //panel.InteractionModeRotate()

  contents = new BoxPanel(Orientation.Vertical) {
    contents += controlPanel
    contents += simulationComponent
  }


  //contents = vtkComponent

  listenTo(startButton, stopButton, pauseButton, configButton)

  reactions += {
    case ButtonClicked(`startButton`) => {
      startSimulation
    }
    case ButtonClicked(`stopButton`) => {
      stopSimulation
    }
    case ButtonClicked(`pauseButton`) => {

    }
    case ButtonClicked(`configButton`) => {

    }
  }

  def startSimulation {
    gui.displaySimFrame
  }

  def stopSimulation {
    gui.simulationFrame.close()
    //sys.exit(0)
  }

  def init {

    simulationPanel.LightFollowCameraOn()
    val camera = renderer.GetActiveCamera

    camera.SetPosition(GuiPlugin.camPos._1, GuiPlugin.camPos._2, GuiPlugin.camPos._3)
    camera.SetFocalPoint(0, 0, 0)
    camera.SetViewUp(0, 0, 1)
    camera.SetViewAngle(GuiPlugin.camAngel)

    renderer.SetActiveCamera(camera)

    //renderer.ResetCamera

    gui.roMap.values.toList foreach {
      ro: RenderObject => {
        renderer.AddActor(ro.getActor)
      }
    }
  }

  def update {
    //renderer.Render()
    simulationPanel.Render()
  }


}