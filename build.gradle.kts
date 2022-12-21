plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.21"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.reflections", "reflections", "0.10.2")
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("org.slf4j:slf4j-simple:2.0.5")
    implementation("com.beust:klaxon:5.6")
    implementation("org.mariuszgromada.math:MathParser.org-mXparser:5.1.0") // https://mathparser.org/mxparser-tutorial/

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClass.set("DayRunner")
}
