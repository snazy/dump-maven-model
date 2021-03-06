[![Build Status](https://github.com/snazy/dump-maven-model/actions/workflows/ci.yml/badge.svg)](https://github.com/snazy/dump-maven-model/actions/workflows/ci.yml)
[![Maven Central](https://img.shields.io/maven-central/v/org.caffinitas.dump-maven-model/dump-maven-model)](https://search.maven.org/artifact/org.caffinitas/dump-maven-model/dump-maven-model-parent)

# Dump Maven model as JSON

A Maven lifecycle extension that dumps the Maven model as JSON.

It is intended to _quickly_ retrieve the list of all artifacts. 

## Usage

Preferred way is to add a profile to your top-level pom like this:

```xml
  <profiles>
    <profile>
      <id>dump-model</id>
      <build>
        <plugins>
          <plugin>
            <groupId>org.caffinitas.dump-maven-model</groupId>
            <artifactId>dump-maven-model</artifactId>
            <version>0.1.0</version>
            <extensions>true</extensions>
          </plugin>
        </plugins>
      </build>
    </profile>
  </profiles>
```

And run the following to get the dump the whole Maven model as JSON.
```bash
mvn -Pdump-model validate -q > model.json
```

### Option: restrict to root project

Setting the property `dump-model.all-projects` to `false` restricts the output to the root project.

Example:
```bash
mvn -Ddump-model.all-projects=false -Pdump-model validate -q > model.json
```

### JSON model

The JSON model currently contains two top-level properties:
* `model` mirroring (most of) the Maven model including all modules. Property names are the same
  as in the Maven model classes (`org.apache.maven.model.Model` and related), but not all available
  properties are mapped.
* `allModelArtifacts` list containing all artifacts that the Maven model contains, including the
  artifact of the top-level module and all submodules.

### Example: Dump the IDs of all modules in your multimodule project

```bash
jq -r '.allModelArtifacts[].id' < model.json
```

### Example: Dump the artifact IDs of all modules in your multimodule project

```bash
jq -r '.allModelArtifacts[].artifactId' < model.json
```

As above, but prefixed with `:`.

```bash
jq -r '":" + .allModelArtifacts[].artifactId' < model.json
```

As above, but a single, comma-separated list.

```bash
jq -r '[":" + .allModelArtifacts[].artifactId] | join(",")' < model.json
```

## Slow Alternative

You could also the exec-maven-plugin, however this will take quite a while. 

```bash
man -q org.codehaus.mojo:exec-maven-plugin:1.6.0:exec@noid -Dexec.executable=echo -Dexec.args='${project.groupId}:${project.artifactId}:${project.version}:${project.packaging}'
```
