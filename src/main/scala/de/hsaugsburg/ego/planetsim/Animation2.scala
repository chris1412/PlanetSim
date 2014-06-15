package de.hsaugsburg.ego.planetsim

import vtk._

/**
 * User: chris
 * Date: 15.06.14
 * Time: 16:12
 */
object Animation2 {


  def main(args: Array[String]) {



    val renderer: vtkRenderer = new vtkRenderer
    val renderWindow: vtkRenderWindow = new vtkRenderWindow
    renderWindow.AddRenderer(renderer)

    val sphereSource: vtkSphereSource = new vtkSphereSource
    sphereSource.SetCenter(0.0, 0.0, 0.0)
    sphereSource.SetRadius(5.0)
    sphereSource.Update
    val mapper: vtkPolyDataMapper = new vtkPolyDataMapper
    mapper.SetInputConnection(sphereSource.GetOutputPort)
    val actor: vtkActor = new vtkActor
    actor.SetMapper(mapper)


    renderer.AddActor(actor)
    renderer.SetBackground(1, 1, 1)
    renderWindow.Render

  }
}
