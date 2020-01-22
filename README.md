# DVS Avro Schemas

[![Build Status](https://iproject-jenkins.reactive-labs.io/buildStatus/icon?job=kafka-dvs-avro-schemas%2Fmaster)](https://iproject-jenkins.reactive-labs.io/job/kafka-dvs-avro-schemas/job/master/)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v2.0%20adopted-ff69b4.svg)](CODE_OF_CONDUCT.md)

## How to build

Generate classes running the following command:

```sh
sbt compile
```

Generated classes can be found in the `target/scala-2.12/src_managed` folder.

## How to publish artifacts

Publish artifacts locally by running the following command:

```sh
sbt publishLocal
```

## Contribution

If you'd like to contribute to the project, make sure to review our [recommendations](CONTRIBUTING.md).
