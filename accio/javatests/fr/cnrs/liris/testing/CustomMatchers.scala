/*
 * Accio is a platform to launch computer science experiments.
 * Copyright (C) 2016-2018 Vincent Primault <v.primault@ucl.ac.uk>
 *
 * Accio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Accio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Accio.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.cnrs.liris.testing

import org.scalatest.matchers.{BeMatcher, MatchResult}

trait CustomMatchers {

  class CloseToMatcher(expectedValue: Double, tolerance: Double) extends BeMatcher[Double] {

    override def apply(left: Double) = MatchResult(
      left >= expectedValue - tolerance && left <= expectedValue + tolerance,
      s"Value $left was not close to $expectedValue (+/- $tolerance).",
      s"Value $left was close to $expectedValue (+/- $tolerance)."
    )
  }

  def closeTo(expectedValue: Double, tolerance: Double) = new CloseToMatcher(expectedValue, tolerance)
}
