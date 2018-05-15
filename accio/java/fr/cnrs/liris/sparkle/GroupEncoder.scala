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

package fr.cnrs.liris.sparkle

import fr.cnrs.liris.sparkle.format.{InternalRow, StructType}

import scala.reflect.ClassTag

private[sparkle] class GroupEncoder[V](inner: Encoder[V]) extends Encoder[(String, Seq[V])] {
  override def structType: StructType = inner.structType

  override def classTag: ClassTag[(String, Seq[V])] = ClassTag(classOf[(String, Seq[V])])

  override def serialize(obj: (String, Seq[V])): Seq[InternalRow] = obj._2.flatMap(inner.serialize)

  override def deserialize(row: InternalRow): (String, Seq[V]) = throw new UnsupportedOperationException
}