package de.hsaugsburg.ego.planetsim.gui

import java.awt.{BorderLayout, Point, Dimension}
import javax.swing.JPanel

import de.hsaugsburg.ego.planetsim.domain.RenderObject
import de.hsaugsburg.ego.planetsim.master.GuiPlugin
import vtk.{vtkRenderer, vtkPanel}

import scala.reflect.New
import scala.swing.{Component, Frame}

/**
 * User: chris
 * Date: 14.06.14
 * Time: 00:37
 */
class SimulationFrame(val gui: GuiPlugin) extends Frame {
  title = "SimulationFrame"

  minimumSize = new Dimension(1024, 800)
  //location = new Point((screenSize.width / 2 - 1024 / 2), 0)
  location = new Point(gui.controlFrame.size.width, 0)

  val panel = new vtkPanel
  panel.InteractionModeZoom()

  val renderer = panel.GetRenderer()

  val vtkComponent = new Component {
    override lazy val peer = new JPanel(new BorderLayout())
    peer.add(panel)
  }

  contents = vtkComponent

  def init {

    panel.LightFollowCameraOn()
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
    panel.Render()
  }


}