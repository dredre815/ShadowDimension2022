# ShadowDimension

A 2D fantasy role-playing game developed in Java using the Bagel game engine. Players control Fae, a character on a mission to save her father and hometown from evil creatures from another dimension.

## 🎮 Game Overview

In ShadowDimension, you play as Fae, the daughter of a scientist. Your father and other government scientists have accidentally opened a portal to a dangerous dimension called the Over Under, ruled by an evil creature named Navec. The scientists are now held captive, and Navec is creating sinkholes that threaten to destroy your world.

The game consists of two levels:
- **Level 0**: Navigate through the laboratory while avoiding walls and sinkholes
- **Level 1**: Face off against demons and their leader Navec in the Over Under dimension

## 🚀 Features

- Two distinct game levels with different challenges
- Dynamic combat system
- Health management system
- Enemy AI with different behavior patterns
- Adjustable game speed (timescale) for varying difficulty
- Collision detection system
- Status effects (invincibility frames)

## 🎯 Game Controls

- **Arrow Keys**: Move Fae around the game world
- **A**: Attack enemies (Level 1)
- **L**: Speed up enemy movement
- **K**: Slow down enemy movement
- **Space**: Start game/Start next level
- **Escape**: Exit game

## 🛠 Technical Details

### Built With
- Java
- [Bagel Game Engine](https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/)

### Architecture
The game follows object-oriented programming principles with a clear class hierarchy:

- Abstract base classes:
  - `GameEntity`: Base for all stationary objects
  - `MovingEntity`: Base for all moving characters
  - `Enemy`: Base for hostile entities

- Main game classes:
  - `ShadowDimension`: Main game controller
  - `Level0` & `Level1`: Level-specific logic
  - `Player`: Player character (Singleton pattern)
  - `Demon` & `Navec`: Enemy types

### Key Components

1. **Entity System**
   - Stationary entities (Walls, Trees, Sinkholes)
   - Moving entities (Player, Demons, Navec)
   - Collision detection system

2. **Combat System**
   - Attack mechanics
   - Damage calculation
   - Health management
   - Invincibility frames

3. **Time Control System**
   - Adjustable game speed
   - Attack cooldowns
   - Status effect durations

## 🔧 Building and Running

1. Ensure you have Java 11 or higher installed
2. Clone the repository
3. Build using Maven:
```bash
mvn clean package
```
4. Run the game:
```bash
java -jar target/bagel-0.1-SNAPSHOT.jar
```

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Feel free to fork the project and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.