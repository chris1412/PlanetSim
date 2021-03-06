package de.hsaugsburg.ego.planetsim.master

import de.hsaugsburg.smas.node.SmasNode
import de.hsaugsburg.smas.services.messages.RegisterService

/**
 * User: chris
 * Date: 12.12.13
 * Time: 01:00
 */
class MasterGuiNode extends SmasNode {
  def onStart
  {
    log.info("manager: " + manager)

    if(manager != null)
    {
      manager ! RegisterService("SimulationMasterService", me)
      log.info("SimulationMasterService registered!")
    }
  }

  def onStop {}
}
