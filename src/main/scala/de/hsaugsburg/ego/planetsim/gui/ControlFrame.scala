package de.hsaugsburg.ego.planetsim.gui

import java.awt.{Insets, Point}
import javax.swing.WindowConstants

import de.hsaugsburg.ego.planetsim.master.GuiPlugin
import de.hsaugsburg.smas.plugin.base.SmasPlugin

import scala.swing.event.ButtonClicked
import scala.swing.{Button, Frame, GridBagPanel, Label}

/**
 * User: chris
 * Date: 14.06.14
 * Time: 00:29
 */
class ControlFrame(val guiPlugin: GuiPlugin) extends Frame {
  title = "ControlFrame"

  //minimumSize=new Dimension(350, 400)
  location = new Point(0, 0)

  peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  val startButton = new Button("Start")
  val stopButton = new Button("Stop")


  contents = new GridBagPanel {
    val gbc = new Constraints()
    gbc.fill = GridBagPanel.Fill.Both
    gbc.insets = new Insets(5, 5, 5, 5);

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

  listenTo(startButton, stopButton)

  reactions += {
    case ButtonClicked(`startButton`) => {
      startSimulation
    }
    case ButtonClicked(`stopButton`) => {
      stopSimulation
    }
  }


  def startSimulation {
    guiPlugin.displaySimFrame
  }

  def stopSimulation {
    guiPlugin.simulationFrame.close()
    //sys.exit(0)
  }
}