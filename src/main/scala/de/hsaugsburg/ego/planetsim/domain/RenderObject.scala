package de.hsaugsburg.ego.planetsim.domain

import de.hsaugsburg.ego.planetsim.master.GuiPlugin
import vtk.{vtkActor, vtkPolyDataMapper, vtkSphereSource}

/**
 * User: chris
 * Date: 13.12.13
 * Time: 03:12
 */
class RenderObject (val id: String, val objectType: RenderObjectType, val radius: Double, val mass: Double,
                    private var _position: (Double, Double, Double), private var _velocity: (Double, Double, Double)) {

  def position_= (value: (Double, Double, Double)): Unit = {
    _position = value
    sphereActor.SetPosition(scaledPosition._1, scaledPosition._2, scaledPosition._3)
  }
  def position = _position

  def velocity_= (value: (Double, Double, Double)): Unit =
  {
    _velocity = value
  }
  def velocity = _velocity

  def scaledRadius: Double = radius / objectType.radScale

  def scaledPosition: (Double, Double, Double) = (position._1 / objectType.posScale,
                                                  position._2 / objectType.posScale,
                                                  position._3 / objectType.posScale)

  val sphere = new vtkSphereSource
  sphere.SetRadius(scaledRadius)
  sphere.SetPhiResolution(GuiPlugin.phiResolution)
  sphere.SetThetaResolution(GuiPlugin.thetaResolution)

  val sphereMapper = new vtkPolyDataMapper
  sphereMapper.SetInputConnection(sphere.GetOutputPort)

  val sphereActor = new vtkActor
  sphereActor.SetMapper(sphereMapper)
  sphereActor.GetProperty.SetColor(objectType.color._1, objectType.color._2, objectType.color._3)
  sphereActor.SetPosition(scaledPosition._1, scaledPosition._2, scaledPosition._3)

  def getActor: vtkActor = sphereActor

  def getMapper: vtkPolyDataMapper = sphereMapper

  override def toString: String = {
    "RenderObject (%s, %s, %s, %s, %s, %s) ".format(id, objectType.name, radius.toString, mass.toString, position.toString, velocity.toString)
  }

}

sealed abstract class RenderObjectType(val name: String, val radScale: Double, val posScale: Double, val color: (Double, Double, Double))

case object Star extends RenderObjectType("Star", 10e6, 9e8, (1, 1, 0))

case object Planet extends RenderObjectType("Planet", 20e5, 9e8, (0, 0.8, 1))