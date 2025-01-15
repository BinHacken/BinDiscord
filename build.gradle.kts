plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-json", "1.8.0")
    implementation("org.jetbrains.kotlinx", "kotlinx-datetime", "0.6.1")

    implementation("com.akuleshov7", "ktoml-core", "0.5.1")

    implementation("net.dv8tion", "JDA", "5.2.2") { exclude(module = "opus-java") }
    implementation("club.minnced", "jda-ktx", "0.12.0")
    implementation("io.github.kaktushose", "jda-commands", "4.0.0-beta.4")
    implementation("ch.qos.logback", "logback-classic", "1.5.6")

    implementation("org.jetbrains.exposed", "exposed-core", "0.58.0")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.58.0")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.58.0")
    implementation("org.jetbrains.exposed", "exposed-kotlin-datetime", "0.58.0")
    implementation("org.xerial", "sqlite-jdbc", "3.44.1.0")
}


tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "de.binhacken.discord.MainKt"
        }

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(sourceSets.main.get().output)
        dependsOn(configurations.runtimeClasspath)
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })
    }
}

application {
    mainClass = "de.binhacken.discord.Main"
}