# :mega: ChatUnitest Gradle Plugin

## Running Steps

### 1. Build

First, you need to build the plugin itself using the following command:

```bash
./gradlew build
```

After successfully building the plugin code, publish it to your local maven repository:

```bash
./gradlew publishToMavenLocal
```

### 2. Configure `gradle.build`

Add the chatunitest-gradle-plugin configuration to the `gradle.build` file in the project for which you want to generate unit tests, and add parameters as needed:
```gradle
plugins {
    id 'org.ifpb.ideia.chatunitest' version '2.0.0'
}

chatunitest {
    apiKeys = "xxx"
    model = "deepseek-coder-v2:latest"
    url = "http://localhost:11434/v1/chat/completions"
}
```

If you are building the plugin locally, you need to configure your `settings.gradle`:
```gradle
pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
    }
}
rootProject.name = 'project_name'
```

If you are also building `chatunitest-core` locally, put this in your `build.gradle` before the `dependencies` section to use local package files:
```gradle
repositories {
    mavenLocal()
    mavenCentral()
}
```

### 3. Add the following dependency to your `gradle.build`

Similarly, add the dependency in the `gradle.build` of the project for which you want to generate unit tests:
```gradle
dependencies {
    implementation 'io.github.ZJU-ACES-ISE:chatunitest-starter:1.5.0'
}
```

### 3. Run

After you correctly configure the project and compile it using `./gradlew build`, you can run the plugin with following commands:

**PS: For now we only support some commands and configurations, but the ideia is to replicate the same functionality as the `chatunitest-maven-plugin`**

- Generate unit tests for a target method:
```bash
./gradlew chatunitest.method -PselectMethod=Calculator#add --info
```

- Generate unit tests for a target class:
```bash
./gradlew chatunitest.class -PselectClass=Calculator --info
```

- Generate unit tests for the entire project:
```bash
./gradlew chatunitest.project --info
```

