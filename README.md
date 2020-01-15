# DVS Avro Schemas


## Dependencies

### Repositories

Generated artifacts are published to a private Nexus repository. Make sure to provide a `~/.sbt/.credentials.dvs` file containing valid credentials:

```properties
realm=Sonatype Nexus Repository Manager
host=nexus.reactive-labs.io
user=<your-username>
password=<your-password>
```

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
