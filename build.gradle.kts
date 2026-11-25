plugins {
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.ploceus)
    alias(libs.plugins.maven.publish)
}

version = "${property("mod_version")}+${property("target_version")}"
group = property("maven_group") as String

base {
    archivesName = property("archives_name") as String
}

loom {
    decompilers {
        named("vineflower") {
            options.putAll(mapOf("mark-corresponding-synthetics" to "1", "indent-string" to "    "))
        }
    }
    runConfigs.configureEach {
        jvmArguments.add("-Dorg.lwjgl.glfw.libname=/home/justin/.local/lib/libglfw.so")
    }
}

ploceus {
    setIntermediaryGeneration(2)
}

dependencies {
    minecraft(libs.minecraft)
    signatures(ploceus.sparrow(libs.versions.sparrow.build.get()))
    mappings(ploceus.featherMappings(libs.versions.feather.build.get()))
    modImplementation(libs.fabric.loader)
}

tasks.named<JavaCompile>("compileJava") {
    options.encoding = "UTF-8"
    options.release = 8
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
}

tasks.jar {
    from("LICENSE") {
        into("META-INF/LICENSES/MIT.txt")
    }
}

tasks.processResources {
    val fmjProperties = mapOf("version" to version)
    inputs.properties(fmjProperties)
    filesMatching("fabric.mod.json") {
        expand(fmjProperties)
    }
}

publishing {
    publications.create<MavenPublication>("mavenJava") {
        from(components["java"])
    }
    repositories {}
}
