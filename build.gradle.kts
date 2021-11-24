val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project


plugins {
    application
    kotlin("jvm") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")
}

repositories {
    mavenCentral()
}

tasks{
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "com.example.ApplicationKt"))
        }
    }
}

dependencies {

    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation ("io.ktor:ktor-server-core:$ktor_version")
    implementation ("io.ktor:ktor-server-sessions:$ktor_version" )//Needed for session support
    implementation ("io.ktor:ktor-auth:$ktor_version") // Here we adding dependency for auth support, but it has to be enabled explicitly
    implementation("io.ktor:ktor-client-gson:$ktor_version")
    implementation ("io.ktor:ktor-gson:$ktor_version")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version" )
    implementation ("io.ktor:ktor-server-netty:$ktor_version")
    implementation ("ch.qos.logback:logback-classic:$logback_version")
    implementation ("io.ktor:ktor-client-core:$ktor_version")
    implementation ("io.ktor:ktor-client-core-jvm:$ktor_version")
    implementation ("io.ktor:ktor-client-apache:$ktor_version")
    implementation ("io.ktor:ktor-freemarker:$ktor_version")
    implementation("org.mongodb:mongodb-driver:3.6.3")
    implementation ("io.insert-koin:koin-core:3.1.3")
    implementation ("io.ktor:ktor-server-core:$ktor_version")
    implementation ("io.ktor:ktor-html-builder:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
