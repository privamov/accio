# Accio is a platform to launch computer science experiments.
# Copyright (C) 2016-2018 Vincent Primault <v.primault@ucl.ac.uk>
#
# Accio is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# Accio is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with Accio.  If not, see <http://www.gnu.org/licenses/>.

scala_library(
  name = 'ops',
  srcs = glob(["*.scala"]),
  deps = [
    '//accio/java/fr/cnrs/liris/accio/sdk',
    '//accio/java/fr/cnrs/liris/accio/sdk:annotations',
    '//accio/java/fr/cnrs/liris/locapriv/sparkle',
    '//accio/java/fr/cnrs/liris/locapriv/model',
    '//accio/java/fr/cnrs/liris/locapriv/lppm',
    '//accio/java/fr/cnrs/liris/locapriv/clustering',
    '//accio/java/fr/cnrs/liris/locapriv/io',
    '//accio/java/fr/cnrs/liris/common/geo',
    '//accio/java/fr/cnrs/liris/common/util',
    '//accio/java/fr/cnrs/liris/common/random',
    '//accio/java/com/google/common/geometry',
    '//3rdparty/jvm/com/github/nscala_time',
    '//3rdparty/jvm/com/google/guava',
    '//3rdparty/jvm/org/scalanlp:breeze',
    '//3rdparty/jvm/com/twitter:util_jvm',
    '//3rdparty/jvm/com/twitter:inject_slf4j',
  ],
  resources = [
    'linux_w4m_LST',
    'linux_w4m_LST_chunk',
    'mac_w4m_LST',
    'mac_w4m_LST_chunk',
  ],
  resource_strip_prefix = "accio/java",
  visibility = ["//visibility:public"],
)