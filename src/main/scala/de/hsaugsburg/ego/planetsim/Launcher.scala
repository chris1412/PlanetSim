package de.hsaugsburg.ego.planetsim

import de.hsaugsburg.smas.startup.{BasicSystemBuilder, XmlSystemBuilder}
import de.hsaugsburg.smas.util.HolonUtil

object StartGui
{
  val configFile = "/config/planetsim/gui.xml"

  def main(args: Array[String])
  {
    XmlSystemBuilder.runOverXmlFileAndBuildSystem(configFile)
  }
}

object StartWorkers
{
  val configFile = "/config/planetsim/workers.xml"

  def main(args: Array[String])
  {
    val nodes = XmlSystemBuilder.runOverXmlFileAndBuildSystem(configFile)

    val workerManager = nodes.head
    val guiManager = HolonUtil.getHolonAddressesFromXml(StartGui.configFile).head

    BasicSystemBuilder.introduceTwoHolonsToEachOther(workerManager, guiManager)
  }
}