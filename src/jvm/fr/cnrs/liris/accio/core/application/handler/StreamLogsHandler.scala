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

package fr.cnrs.liris.accio.core.application.handler

import com.google.inject.Inject
import com.twitter.util.Future
import com.typesafe.scalalogging.LazyLogging
import fr.cnrs.liris.accio.core.domain.{RunRepository, UnknownTaskException}

class StreamLogsHandler @Inject()(runRepository: RunRepository)
  extends Handler[StreamLogsRequest, StreamLogsResponse] with LazyLogging {

  @throws[UnknownTaskException]
  override def handle(req: StreamLogsRequest): Future[StreamLogsResponse] = {
    val runIds = req.logs.map(_.runId).toSet
    val unknownRunIds = runIds.filterNot(runRepository.exists)
    unknownRunIds.foreach { runId =>
      logger.warn(s"Received logs associated with unknown run ${runId.value}")
    }
    runRepository.save(req.logs.filterNot(log => unknownRunIds.contains(log.runId)))
    Future(StreamLogsResponse())
  }
}