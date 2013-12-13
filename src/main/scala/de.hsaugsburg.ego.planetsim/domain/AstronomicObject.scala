package de.hsaugsburg.ego.planetsim.domain


/**
 * User: chris
 * Date: 12.12.13
 * Time: 20:42
 */
abstract class AstronomicObject(val id: String, val radius: Double, val mass: Double,
                                val position: (Double, Double, Double), val velocity: (Double, Double, Double),
                                val color: (Double, Double, Double))

case class Star(override val id: String, override val radius: Double, override val mass: Double,
                override val position: (Double, Double, Double), override val velocity: (Double, Double, Double))
                extends AstronomicObject(id, radius, mass, position, velocity, (1, 1, 0))

case class Planet(override val id: String, override val radius: Double, override val mass: Double,
                  override val position: (Double, Double, Double), override val velocity: (Double, Double, Double))
                  extends AstronomicObject(id, radius, mass, position, velocity, (0, 0.8, 1))



