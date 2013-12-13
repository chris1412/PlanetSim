package de.hsaugsburg.ego.planetsim.domain

import de.hsaugsburg.ego.planetsim.gui.PlanetarySimulation
import vtk.{vtkActor, vtkPolyDataMapper, vtkSphereSource}

/**
 * User: chris
 * Date: 13.12.13
 * Time: 03:12
 */
class RenderObject(val ao: AstronomicObject) {

  val scaledRadius = PlanetarySimulation.calcScaledRadius(ao)

  val scaledPosition = PlanetarySimulation.calcScaledPosition(ao)

  val sphere = new vtkSphereSource
  sphere.SetRadius(scaledRadius)
  sphere.SetPhiResolution(PlanetarySimulation.getPhiResolution)
  sphere.SetThetaResolution(PlanetarySimulation.getThetaResolution)

  val sphereMapper = new vtkPolyDataMapper
  sphereMapper.SetInputConnection(sphere.GetOutputPort)

  val sphereActor = new vtkActor
  sphereActor.SetMapper(sphereMapper)
  sphereActor.GetProperty.SetColor(ao.color._1, ao.color._2, ao.color._3)
  sphereActor.SetPosition(scaledPosition._1, scaledPosition._2, scaledPosition._3)

  def getActor: vtkActor = sphereActor

  def getMapper: vtkPolyDataMapper = sphereMapper
}