plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10")
    implementation("org.reflections", "reflections", "0.10.2")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("com.beust:klaxon:5.6")
    implementation("org.mariuszgromada.math:MathParser.org-mXparser:5.2.1") // https://mathparser.org/mxparser-tutorial/

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.10")
}

application {
    mainClass.set("DayRunner")
}
