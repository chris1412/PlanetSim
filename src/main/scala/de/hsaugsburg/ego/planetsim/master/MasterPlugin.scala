package de.hsaugsburg.ego.planetsim.master

import de.hsaugsburg.smas.plugin.base.SmasPlugin

/**
 * User: chris
 * Date: 12.12.13
 * Time: 01:05
 */
class MasterPlugin extends SmasPlugin {

  def onStart =
  {
    //gui.init

    //gui.loadSolarSystem

    //gui.render

    log.info("Simulation MasterPlugin created!")
    true
  }

  def onStop = true
}
