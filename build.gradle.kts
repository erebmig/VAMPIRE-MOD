plugins {
	id 'fabric-loom' version '1.6.+'
	id 'maven-publish'
	id 'kotlin'
}

sourceCompatibility = JavaVersion.VERSION_21
targetCompatibility = JavaVersion.VERSION_21

archivesBaseName = project.archives_base_name
version = project.mod_version
group = project.maven_group

repositories {
	mavenCentral()
	maven { url = "https://api.modrinth.com/maven" }
	maven { url = "https://maven.terraformersmc.com/" }
}

dependencies {
	minecraft "com.mojang:minecraft:${project.minecraft_version}"
	mappings loom.officialMojangMappings()
	
	modImplementation "net.fabricmc:fabric-loader:${project.loader_version}"
	modImplementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_version}"
	modImplementation "net.fabricmc:fabric-language-kotlin:${project.fabric_language_kotlin_version}+kotlin.${project.kotlin_version}"
}

tasks.withType(JavaCompile).configureEach {
	it.options.release = 21
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).configureEach {
	kotlinOptions {
		jvmTarget = "21"
		freeCompilerArgs += "-Xjvm-default=all"
	}
}

java {
	withSourcesJar()
}

jar {
	from("LICENSE") {
		rename { "${it}_${project.archivesBaseName}" }
	}
}

publishing {
	publications {
		mavenJava(MavenPublication) {
			from components.java
		}
	}

	repositories {
	}
}
