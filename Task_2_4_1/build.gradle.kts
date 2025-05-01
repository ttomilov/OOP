plugins {
    kotlin("jvm") version "2.1.20"
}

group = "org.dsl"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.20")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jsr223-embeddable:1.3.72")
    implementation("org.jetbrains.kotlin:kotlin-scripting-common:2.1.20")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm:2.1.20")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host:2.1.20")
    implementation("org.jetbrains.kotlin:kotlin-main-kts:2.1.20")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}