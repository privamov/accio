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

package fr.cnrs.liris.lumos.transport

import java.util.concurrent.atomic.AtomicInteger

import com.google.inject.{Inject, Singleton}
import com.twitter.util.{Future, Time}
import com.twitter.util.logging.Logging
import fr.cnrs.liris.lumos.domain.Event

@Singleton
final class MultiplexerEventTransport @Inject()(transports: Set[EventTransport]) extends EventTransport with Logging {
  private[this] val errors = new AtomicInteger(0)

  def hasErrors: Boolean = errors.get > 0

  override def name = "Multiplexer"

  override def sendEvent(event: Event): Future[Unit] = {
    Future.join(transports.map(sendEvent(event, _)).toSeq)
  }

  override def close(deadline: Time): Future[Unit] = {
    Future.join(transports.map(_.close(deadline)).toSeq)
  }

  private def sendEvent(event: Event, transport: EventTransport): Future[Unit] = {
    transport.sendEvent(event).onFailure { e =>
      errors.incrementAndGet()
      logger.error(s"Error while sending event to ${transport.name}", e)
    }
  }
}