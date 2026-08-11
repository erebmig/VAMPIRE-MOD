# 🧛 Vampire Mod for Minecraft 1.21.4

A comprehensive Fabric mod that transforms players into powerful vampires with unique abilities, mechanics, and features!

## Features

### 🧛 Vampire Transformation
- Transform into a vampire with special abilities
- Bite villagers to drink their blood
- Convert villagers into vampire minions
- Unique vampire-only powers and weaknesses

### 🩸 Blood System
- Blood meter that decreases over time (every 3 minutes)
- Drink blood from villagers to restore health
- Starvation effects when blood is low
- Visual blood drain indicator on HUD

### 💪 Vampire Abilities
- **Bite Attack**: Attack villagers to drink blood
- **Minion Control**: Converted villagers will attack other villagers on your command
- **Enhanced Senses**: Better vision in darkness
- **Strength Boost**: Increased damage when fed

### 👹 Starvation Effects
- Screen blur and vignette when blood is low
- Weakness status effect when starving
- Dizziness and disorientation
- Visual warnings when critically low on blood

### 🎮 Custom HUD
- Real-time blood meter display
- Vampire level indicator
- Minion count tracker
- Low blood warning system

### ⌨️ Keybinds
- **B**: Special bite attack (when vampire)
- Commands for admin control

## Commands

### `/vampire make <player>`
Convert a player into a vampire.

### `/vampire blood <player> <amount>`
Set a vampire's blood level (0-200).

### `/vampire status <player>`
Check a vampire's current status including blood level, level, and minion count.

## Installation

1. Download the latest JAR from GitHub Releases
2. Place it in your `.minecraft/mods` folder
3. Requires Fabric Loader and Fabric API
4. Requires Java 21+

## Building from Source

```bash
# Clone the repository
git clone https://github.com/erebmig/VAMPIRE-MOD.git
cd VAMPIRE-MOD

# Build the mod
./gradlew build

# JAR will be at: build/libs/vampire-mod-1.0.0.jar
```

## Version Info
- **Minecraft Version**: 1.21.4
- **Mod Loader**: Fabric
- **Language**: Kotlin + Java
- **Java Version**: 21+

## Features Roadmap

- [ ] Custom vampire teeth model rendering
- [ ] Blood effects and particle system
- [ ] Vampire-exclusive items and tools
- [ ] Night vision enhancement
- [ ] Weakness to garlic and sunlight
- [ ] Vampire clans and territories
- [ ] Boss vampire encounters
- [ ] Config file support
- [ ] Custom sounds and music

## License

MIT License - See LICENSE file for details

## Support & Issues

Found a bug? Have a feature request? Open an issue on [GitHub Issues](https://github.com/erebmig/VAMPIRE-MOD/issues)

---

**Made with ❤️ and 🧛 by erebmig**
