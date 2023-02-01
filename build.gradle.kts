import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

val detektVersion = "1.21.0"
val kotestVersion = "5.5.4"

plugins {
    kotlin("jvm") version "1.8.0"
    id("io.gitlab.arturbosch.detekt").version("1.21.0")
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("MainKt")
}

repositories {
    mavenCentral()
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    testImplementation(kotlin("test"))

    // kotest
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    // detekt
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
}


kotlin {
    jvmToolchain(8)
}

tasks.register("integrationTests") {
    doLast {
        println("Do nothing. Integration tests are included into 'build' task.")
    }
}

detekt {
    autoCorrect = true
    config = files("$projectDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
        txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
        sarif.required.set(true) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
        md.required.set(true) // simple Markdown format
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}
