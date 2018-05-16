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

package fr.cnrs.liris.accio.domain

/**
 * Definition of an operator.
 *
 * @param name        Unique operator name.
 * @param category    Category. Only used for presentational purposes.
 * @param help        One-line help text.
 * @param description Longer description of what the operator does.
 * @param inputs      Definition of inputs the operator consumes.
 * @param outputs     Definition of outputs the operator produces.
 * @param deprecation Deprecation message, if this operator is actually deprecated.
 * @param resources   Declaration of resources this operator needs to be executed.
 * @param unstable    Whether this operator is unstable, will produce deterministic outputs given
 *                    the some inputs. Unstable operators need a random seed specified to produce
 *                    deterministic outputs.
 */
case class Operator(
  name: String,
  category: String,
  help: Option[String] = None,
  description: Option[String] = None,
  inputs: Seq[Attribute] = Seq.empty,
  outputs: Seq[Attribute] = Seq.empty,
  deprecation: Option[String] = None,
  resources: Map[String, Long] = Map.empty,
  unstable: Boolean = false)