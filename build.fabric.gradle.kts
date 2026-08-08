plugins {
    id("mod-platform")
    id("dev.kikugie.loom-back-compat")
}

stonecutter {
    properties.tags(current.version, "fabric")

    replacements.string(current.parsed >= "1.21.11") {
        replace("ResourceLocation", "Identifier")
        replace("location()", "identifier()")
    }
    replacements.string(current.parsed >= "26.1.2") {
        replace("FabricDataOutput", "FabricPackOutput")
    }
}

platform {
    loader = "fabric"
    dependencies {
        required("minecraft") {
            fabricLikeVersionRange = prop("deps.minecraft").replace(
                Regex("""^(\d+\.\d+)-snapshot-(\d+)$"""), "$1-alpha.$2"
            )
        }
        required("fabric-api") {
            slug("fabric-api")
            fabricLikeVersionRange = ">=${prop("deps.fabric-api")}"
        }
        required("fabricloader") {
            fabricLikeVersionRange = ">=${prop("deps.fabric-loader")}"
        }
        optional("modmenu") {}
    }
}

loom {
    accessWidenerPath = rootProject.file("src/main/resources/aw/${sc.current.version}.accesswidener")
    runs.named("client") {
        client()
        ideConfigGenerated(true)
        runDir = "run/"
        environment = "client"
        configName = "Fabric Client"
    }
    runs.named("server") {
        server()
        ideConfigGenerated(true)
        runDir = "run/"
        environment = "server"
        configName = "Fabric Server"
    }
}

fabricApi {
    configureDataGeneration {
        outputDirectory = file("${rootDir}/versions/datagen/${sc.current.version.split("-")[0]}/src/main/generated")
        client = true
    }
}

repositories {
    mavenCentral()
    strictMaven("https://maven.terraformersmc.com/", "com.terraformersmc") { name = "TerraformersMC" }
    strictMaven("https://api.modrinth.com/maven", "maven.modrinth") { name = "Modrinth" }
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
}

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:${prop("deps.fabric-loader")}")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${prop("deps.minecraft")}")
    if (sc.current.parsed < "26") {
        mappings(loom.layered {
            officialMojangMappings()
            if (hasProperty("deps.parchment")) parchment("org.parchmentmc.data:parchment-${prop("deps.parchment")}@zip")
        })
    }
    modImplementation("net.fabricmc:fabric-loader:${prop("deps.fabric-loader")}")
    // implementation(libs.moulberry.mixinconstraints)
    // include(libs.moulberry.mixinconstraints)
    modImplementation("net.fabricmc.fabric-api:fabric-api:${prop("deps.fabric-api")}")
    if (hasProperty("deps.modmenu")) {
        modLocalRuntime("com.terraformersmc:modmenu:${prop("deps.modmenu")}")
    }
    modLocalRuntime(libs.devauth.fabric)
}
