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
class PlanetSimOptionFrame(val gui: GuiPlugin) extends Frame {

  title = "ControlFrame"

  //minimumSize=new Dimension(350, 400)
  location = new Point(0, 0)

  contents = new GridBagPanel {
  }


}