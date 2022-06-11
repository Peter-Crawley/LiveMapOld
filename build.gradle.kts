import net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder

plugins {
	id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
	id("com.github.johnrengelman.shadow") version "7.1.2"
	id("xyz.jpenilla.run-paper") version "1.0.6"
	kotlin("jvm") version "1.6.21"
}

version = "0.1.0-SNAPSHOT"

repositories {
	maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
	// Provided by Server
	compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")

	// Provided by LiveMap
	implementation("org.bstats:bstats-bukkit:3.0.0")

	// Loaded by Server
	compileOnly(kotlin("stdlib"))
	compileOnly("jakarta.websocket:jakarta.websocket-api:2.1.0")
	compileOnly("org.eclipse.jetty.websocket:websocket-jakarta-server:11.0.9")
}

tasks {
	compileKotlin {
		targetCompatibility = "17"
	}

	shadowJar {
		relocate("org.bstats", "io.github.petercrawley.livemap.libraries.org.bstats")

		archiveFileName.set("../../build/${project.name}-${project.version}.jar")

		minimize()
	}

	runServer {
		minecraftVersion("1.18.2")
	}

	bukkit {
		main = "io.github.petercrawley.livemap.LiveMap"
		apiVersion = "1.18"
		version = project.version.toString()
		author = "PeterCrawley"
		load = PluginLoadOrder.STARTUP

		libraries = listOf(
			"org.eclipse.jetty.websocket:websocket-jakarta-server:11.0.9",
			"jakarta.websocket:jakarta.websocket-api:2.1.0",
			"org.jetbrains.kotlin:kotlin-stdlib:1.6.21"
		)
	}

	build {
		dependsOn(":shadowJar")
	}
}