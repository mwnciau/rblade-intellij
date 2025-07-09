import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.21"
    id("org.jetbrains.intellij.platform") version "2.5.0"
}

group = "com.mwnciau"
version = "2.0.3"

repositories {
  mavenCentral()

  intellijPlatform {
    defaultRepositories()
  }
}

dependencies {
  intellijPlatform {
    rubymine("2025.1")

    bundledPlugin("org.jetbrains.plugins.ruby")

    pluginVerifier()
    zipSigner()

    testFramework(TestFrameworkType.Platform)
    testFramework(TestFrameworkType.Plugin.Ruby)
  }

  testImplementation(kotlin("test"))
}

// Include the generated files in the source set
sourceSets {
  main {
    java {
      srcDirs("src/main/gen")
    }
  }
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
      sourceCompatibility = "17"
      targetCompatibility = "17"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
      compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
      }
  }

  patchPluginXml {
      sinceBuild.set("250")
      untilBuild.set(provider { null })
  }

  signPlugin {
      certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
      privateKey.set(System.getenv("PRIVATE_KEY"))
      password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
      token.set(System.getenv("PUBLISH_TOKEN"))
  }

  setupDependencies {
  }
}
