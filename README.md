# JavaFX Starter Template

A starter project for **CST 338 – Software Design**, a 300-level course at CSU Monterey Bay. Use this template as your starting point for JavaFX assignments.

---

## What's Included

- **JavaFX 21** with `javafx.controls` and `javafx.fxml` modules
- **Java 17** source/target compatibility
- **Gradle** build system with the OpenJFX plugin
- **JUnit 5** for unit testing
- A minimal `Main.java` that launches a working JavaFX window

---

## Prerequisites

| Tool | Version |
|------|---------|
| JDK  | 17+     |
| Gradle | 8+ (or use the included wrapper) |
| IntelliJ IDEA | Recommended IDE |

> No manual JavaFX SDK download needed — Gradle pulls it automatically via Maven Central.

---

## Getting Started

### 1. Clone or download this repo

```bash
git clone <repo-url>
cd JavaFX_Starter
```

### 2. Open in IntelliJ IDEA

**File → Open** and select the project folder. IntelliJ will detect the Gradle build automatically.

### 3. Run the app

**Option A — IntelliJ:** Click the green Run button next to `main()` in `Main.java`.

**Option B — Terminal:**
```bash
./gradlew run        # macOS / Linux
gradlew.bat run      # Windows
```

---

## Project Structure

```
JavaFX_Starter/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── Main.java          # Entry point — start here
│   │   └── resources/
│   └── test/
│       ├── java/                  # Your JUnit tests go here
│       └── resources/
├── build.gradle                   # Dependencies and JavaFX config
├── settings.gradle
└── gradlew / gradlew.bat          # Gradle wrapper — no install needed
```

---

## Assignment Workflow

1. **Rename / extend `Main.java`** or add new classes in `src/main/java/`.
2. **Update the class Javadoc** — fill in the description, keep your name, and update the version.
3. **Add tests** in `src/test/java/` for any business logic.
4. Run `./gradlew test` to verify tests pass before submitting.

---

## Common Gradle Tasks

| Command | Description |
|---------|-------------|
| `./gradlew run` | Build and launch the JavaFX app |
| `./gradlew test` | Run all JUnit tests |
| `./gradlew build` | Compile and package |
| `./gradlew clean` | Remove build outputs |

---

## Resources

- [JavaFX Documentation](https://openjfx.io/)
- [JavaFX API Reference](https://openjfx.io/javadoc/21/)
- [OpenJFX Gradle Plugin](https://github.com/openjfx/javafx-gradle-plugin)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
