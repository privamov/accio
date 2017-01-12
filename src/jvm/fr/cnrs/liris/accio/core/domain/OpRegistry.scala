/*
 * Accio is a program whose purpose is to study location privacy.
 * Copyright (C) 2016 Vincent Primault <vincent.primault@liris.cnrs.fr>
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

package fr.cnrs.liris.accio.core.domain

import com.google.inject.{Inject, Singleton}
import fr.cnrs.liris.accio.core.api.Operator

/**
 * Registry providing definitions of all operators known to Accio.
 */
trait OpRegistry {
  /**
   * Return all operator definitions.
   */
  def ops: Set[OpDef]

  /**
   * Check whether the registry contains an operator with given name.
   *
   * @param name Operator name.
   */
  def contains(name: String): Boolean

  /**
   * Return the definition of a given operator, if it exists.
   *
   * @param name Operator name.
   */
  def get(name: String): Option[OpDef]

  /**
   * Return the definition of a given operator.
   *
   * @param name Operator name.
   * @throws NoSuchElementException If there is no operator with the given name.
   */
  @throws[NoSuchElementException]
  def apply(name: String): OpDef
}

/**
 * Operator registry embedding static definitions (i.e., known when instantiating the object).
 */
class StaticOpRegistry(defns: Set[OpDef]) extends OpRegistry {
  private[this] val index = defns.map(opDef => opDef.name -> opDef).toMap

  override def ops: Set[OpDef] = index.values.toSet

  override def contains(name: String): Boolean = index.contains(name)

  override def get(name: String): Option[OpDef] = index.get(name)

  override def apply(name: String): OpDef = index(name)
}

/**
 * Registry extracting operator definitions from implementation classes. It also provides runtime information such
 * as classes implementing these operators.
 *
 * @param reader  Operator metadata reader.
 * @param classes Classes containing operator implementations.
 */
@Singleton
class RuntimeOpRegistry @Inject()(reader: OpMetaReader, classes: Set[Class[_ <: Operator[_, _]]]) extends OpRegistry {
  // This field is declared as lazy because it can throw errors and we do not want them to be thrown while
  // Guice is injecting dependencies.
  private[this] lazy val index = classes.map { clazz =>
    val meta = reader.read(clazz)
    meta.defn.name -> meta
  }.toMap

  def getMeta(name: String): Option[OpMeta] = index.get(name)

  def meta(name: String): OpMeta = index(name)

  override def ops: Set[OpDef] = index.values.map(_.defn).toSet

  override def contains(name: String): Boolean = index.contains(name)

  override def get(name: String): Option[OpDef] = index.get(name).map(_.defn)

  override def apply(name: String): OpDef = index(name).defn
}