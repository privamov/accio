load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")
scala_import(
    name = "scalacheck",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library",
        "//3rdparty/jvm/org/scala_sbt:test_interface"
    ],
    jars = [
        "//external:jar/org/scalacheck/scalacheck_2_11"
    ],
    visibility = [
        "//visibility:public"
    ]
)


