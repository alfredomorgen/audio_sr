## Quick Start

To quickly start this project, you can utilize the Docker image stored here: https://hub.docker.com/repository/docker/alfredomorgen/audio_sr

After cloning the repository, open `compose.yaml` file and edit `build: .` line under `app` field, change it to `image: alfredomorgen/audio_sr:latest`. Then you would be able to run it by using `docker compose up`

```
- build: .
+ image: alfredomorgen/audio_sr:latest
```

## Tech Stack

* Spring Boot 3.3.3
* Java 22 (Amazon Corretto)
* Gradle 8.10.1

## Dependencies

To convert audio files, I'm using JAVE2 (https://github.com/a-schild/jave2). In this project, I have included the binaries for Windows x64 and Linux x64. In case you need to run it manually (not via Docker) and you're using Mac, you will need to add the following dependencies in Gradle (in file `build.gradle`)

```
implementation 'ws.schild:jave-nativebin-osx64:3.5.0' // for Mac OS X with Intel
implementation 'ws.schild:jave-nativebin-osxm1:3.5.0' // for Mac OS X with Apple Silicon
```
