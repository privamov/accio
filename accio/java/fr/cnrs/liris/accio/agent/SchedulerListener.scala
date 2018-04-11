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

package fr.cnrs.liris.accio.agent

import com.google.common.eventbus.Subscribe
import com.google.inject.{Inject, Singleton}
import com.twitter.util.logging.Logging
import fr.cnrs.liris.accio.api.{ProcessCompletedEvent, ProcessStartedEvent}
import fr.cnrs.liris.accio.service.RunManager
import fr.cnrs.liris.accio.storage.Storage

/**
 *
 * @param storage    Storage.
 * @param runManager Run lifecycle manager.
 */
@Singleton
final class SchedulerListener @Inject()(storage: Storage, runManager: RunManager) extends Logging {
  @Subscribe
  def onTaskCompleted(event: ProcessCompletedEvent): Unit = {
    storage.write { stores =>
      stores.runs.get(event.runId) match {
        case None => logger.warn(s"Completed task is associated with unknown run ${event.runId}")
        case Some(run) =>
          val parent = run.parent.flatMap(stores.runs.get)
          val (newRun, newParent) = if (event.result.exitCode == 0) {
            runManager.onSuccess(run, event.nodeName, event.result, parent)
          } else {
            runManager.onFailed(run, event.nodeName, event.result, parent)
          }
          stores.runs.save(newRun)
          newParent.foreach(stores.runs.save)
      }
    }
  }

  @Subscribe
  def onTaskStarted(event: ProcessStartedEvent): Unit = {
    storage.write { stores =>
      stores.runs.get(event.runId) match {
        case None => logger.warn(s"Started task is associated with unknown run ${event.runId}")
        case Some(run) =>
          val newRun = runManager.onStart(run, event.nodeName)
          stores.runs.save(newRun)
      }
    }
  }
}