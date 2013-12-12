package de.hsaugsburg.ego.planetsim.master

import de.hsaugsburg.smas.plugin.base.SmasPlugin
import de.hsaugsburg.ego.planetsim.gui.SimulationGui

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 12.12.13
 * Time: 01:05
 */
class MasterPlugin extends SmasPlugin {

  val gui = new SimulationGui()

  def onStart =
  {
    gui.start

    log.info("Simulation MasterPlugin created!")
    true
  }

  def onStop = true
}
