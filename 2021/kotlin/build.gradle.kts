plugins {
    application
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src/main/kotlin")
        }
        test {
            java.srcDirs("src/test/kotlin")
        }
    }

    test {
        useJUnitPlatform()
    }

    wrapper {
        gradleVersion = "7.3"
    }
}

application {
    mainClass.set("com.pietromaggi.aoc2021.day24.Day24Kt")
}