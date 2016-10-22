/*
 * Copyright LIRIS-CNRS (2016)
 * Contributors: Vincent Primault <vincent.primault@liris.cnrs.fr>
 *
 * This software is a computer program whose purpose is to study location privacy.
 *
 * This software is governed by the CeCILL-B license under French law and
 * abiding by the rules of distribution of free software. You can use,
 * modify and/ or redistribute the software under the terms of the CeCILL-B
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty and the software's author, the holder of the
 * economic rights, and the successive licensors have only limited liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading, using, modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean that it is complicated to manipulate, and that also
 * therefore means that it is reserved for developers and experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-B license and that you accept its terms.
 */

package fr.cnrs.liris.privamov.ops

import com.google.inject.Inject
import fr.cnrs.liris.accio.core.api._
import fr.cnrs.liris.common.random.SamplingUtils
import fr.cnrs.liris.privamov.core.model.Trace
import fr.cnrs.liris.privamov.core.sparkle.SparkleEnv

/**
 * Perform a uniform sampling on traces, keeping each event with a given probability.
 */
@Op(
  category = "prepare",
  help = "Uniformly sample events inside traces.")
class UniformSamplingOp @Inject()(env: SparkleEnv) extends Operator[UniformSamplingIn, UniformSamplingOut] with SparkleOperator {

  override def execute(in: UniformSamplingIn, ctx: OpContext): UniformSamplingOut = {
    val output = read(in.data, env).map(transform(_, in.probability))
    UniformSamplingOut(write(output, ctx.workDir))
  }

  private def transform(trace: Trace, probability: Double): Trace =
    trace.replace(SamplingUtils.sampleUniform(trace.events, probability))
}

case class UniformSamplingIn(
  @Arg(help = "Probability to keep each event") probability: Double,
  @Arg(help = "Input dataset") data: Dataset) {
  require(probability >= 0 && probability <= 1, s"Probability must be in [0, 1] (got $probability)")
}

case class UniformSamplingOut(
  @Arg(help = "Output dataset") data: Dataset)