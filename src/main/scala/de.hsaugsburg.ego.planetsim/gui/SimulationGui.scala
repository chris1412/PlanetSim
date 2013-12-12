package de.hsaugsburg.ego.planetsim.gui

import org.slf4j.LoggerFactory
import swing._
import scala.swing.event.ButtonClicked
import vtk._
import javax.swing.{WindowConstants, JPanel}
import scala.swing.Frame
import java.awt.{BorderLayout, Toolkit}

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 12.12.13
 * Time: 01:16
 */
class SimulationGui {
  protected val log = LoggerFactory.getLogger(this.getClass)
  val controlFrame = new ControlFrame
  val simulationFrame = new SimulationFrame

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

  class SimulationFrame extends Frame{
    title = "SimulationFrame"

    minimumSize = new Dimension(1024, 800)
    //location = new Point((screenSize.width / 2 - 1024 / 2), 0)
    location = new Point(controlFrame.size.width, 0)

    var vtkPanel: vtkPanel = new vtkPanel

    //var cone = new vtkConeSource
    var sphere = new vtkSphereSource
    sphere.SetRadius(120)
    sphere.SetPhiResolution(20)
    sphere.SetThetaResolution(50)

    var sphereMapper = new vtkPolyDataMapper
    sphereMapper.SetInputConnection(cone.GetOutputPort)

    var sphereActor = new vtkActor
    sphereActor.SetMapper(coneMapper)
    sphereActor.SetPosition(10, 10, 10)

    vtkPanel.GetRenderer.AddActor(sphereActor)
    vtkPanel.GetRenderer.ResetCamera

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
