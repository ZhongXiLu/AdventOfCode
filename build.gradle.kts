plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.1.0")
    implementation("org.reflections", "reflections", "0.10.2")
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("com.beust:klaxon:5.6")
    implementation("org.mariuszgromada.math:MathParser.org-mXparser:6.1.0") // https://mathparser.org/mxparser-tutorial/

    testImplementation("org.jetbrains.kotlin:kotlin-test:2.1.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.1.0")
}

application {
    mainClass.set("DayRunner")
}
