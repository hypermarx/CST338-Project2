# Fahrenheit ↔ Celsius Converter — JavaFX Assignment

## Overview

Build a two-scene JavaFX application that converts temperatures between Fahrenheit and Celsius. The primary scene converts Fahrenheit to Celsius. A secondary scene converts Celsius to Fahrenheit. A long press on the Convert button switches between scenes and carries the converted value forward into the next scene.

---

## Final App Requirements

Each scene must contain the following four components:

| Component | Description |
|-----------|-------------|
| Conversion label | Identifies the current conversion direction (e.g., `"Fahrenheit → Celsius"`) |
| TextField | Accepts the temperature value to convert |
| Convert button | Performs the conversion on short click; switches scenes on long press |
| Result label | Displays the converted value |

---

## Part 1: Text Input

Create the primary scene layout. Add a `TextField` where the user will enter a Fahrenheit temperature.

- Place a `Label` at the top of the scene that reads `"Fahrenheit → Celsius"`
- Add a `TextField` below it with prompt text (e.g., `"Enter °F"`)
- Use a layout container such as `VBox` to organize the components vertically
- Set a reasonable preferred width on the `TextField` and add padding to the layout so the UI is not cramped
- Display this scene in the primary `Stage`

**Goal:** Running the app shows a labeled text field with prompt text.

---

## Part 2: The Button

Add a `Button` labeled `"Convert"` to the scene.

- Add the button to the layout below the `TextField`
- Attach a `setOnAction` handler that reads the current text from the `TextField` and prints it to the console
- Leave the conversion logic out for now — just confirm the value is being read

**Goal:** Clicking the button prints the contents of the text field to the console.

---

## Part 3: Conversion Logic

Write a `private` method `fahrenheitToCelsius` that accepts a `double` and returns a `double`.

- Formula: `(fahrenheit - 32) * 5.0 / 9.0`
- The method should contain only the calculation and the return statement — no UI code
- Do not connect this to the button yet; verify it compiles and the formula is correct

**Goal:** The method exists, compiles, and produces the correct result when called directly.

---

## Part 4: Update the Display

Wire the Convert button to call `fahrenheitToCelsius` and show the result on screen.

- Add a result `Label` to the layout below the button, initially empty
- Update the `setOnAction` handler on the Convert button to:
  1. Read the text from the `TextField` and parse it to a `double`
  2. Pass the parsed value to `fahrenheitToCelsius`
  3. Format the result to two decimal places and set it on the result `Label`
- If the text cannot be parsed to a number, set the result `Label` to `"Invalid input"` instead of crashing

**Goal:** Entering a number and clicking Convert displays the Celsius result. Entering non-numeric text shows `"Invalid input"`.

---

## Part 5: Switch Scenes with Long Press

Add a long press gesture to the Convert button that switches to a second scene.

Define a constant for the long press threshold at the top of your class:

```
private static final int LONG_PRESS_MS = 500;
```

Use a `PauseTransition` to detect the long press:

- On `setOnMousePressed`: start a `PauseTransition` with a duration of `LONG_PRESS_MS` milliseconds
- On `setOnMouseReleased`: stop and reset the transition if it has not yet finished (this is the short-click path — `setOnAction` handles the conversion as normal)
- In the `PauseTransition`'s `setOnFinished` handler: switch to Scene 2 by calling `primaryStage.setScene(scene2)`

Because `setOnAction` fires on mouse release, a long press will still trigger it after the mouse is lifted. Use a `boolean` flag (e.g., `longPressHandled`) to track whether the long press already fired. Set the flag to `true` in `setOnFinished`, and check it at the start of `setOnAction` to skip the conversion if a long press was already handled. Reset the flag to `false` in `setOnMousePressed`.

Create a minimal placeholder `scene2` (a single `Label` reading `"Scene 2"`) to verify scene switching works before building it out fully.

**Goal:** Short click converts; holding the button for the defined duration switches to Scene 2.

---

## Part 6: Celsius to Fahrenheit Scene

Replace the placeholder Scene 2 with a fully functional Celsius-to-Fahrenheit converter. Mirror the structure from Parts 1–4.

- Add a `Label` reading `"Celsius → Fahrenheit"`
- Add a `TextField` with prompt text (e.g., `"Enter °C"`)
- Write a `private` method `celsiusToFahrenheit` that accepts a `double` and returns a `double`
  - Formula: `(celsius * 9.0 / 5.0) + 32`
- Add a Convert button and result `Label` that function the same way as in Part 4
- Apply the same long press logic from Part 5 to Scene 2's button, but have it switch **back** to Scene 1: `primaryStage.setScene(scene1)`
- Use the same `LONG_PRESS_MS` constant and the same `boolean` flag approach

**Goal:** Scene 2 is fully functional. Long pressing its button returns to Scene 1.

---

## Part 7: Transfer Value Between Scenes

When long pressing to switch scenes, carry the result forward into the destination scene's `TextField`.

- **Scene 1 → Scene 2:** Before switching, read the text currently displayed in Scene 1's result `Label`. If it is not empty and not `"Invalid input"`, set that text as the content of Scene 2's `TextField`.
- **Scene 2 → Scene 1:** Before switching, read the text currently displayed in Scene 2's result `Label`. If it is not empty and not `"Invalid input"`, set that text as the content of Scene 1's `TextField`.

This pre-fills the input for the next conversion so the user can continue working with the same value in the opposite direction.

**Goal:** Long pressing with a valid result displayed pre-fills the other scene's input field. If no result has been calculated yet, the destination `TextField` is left unchanged.

---

## Unit Tests

Write JUnit 5 tests in `src/test/java/` covering the two conversion methods. The conversion methods should be accessible to tests — either package-private or extracted into a separate utility class.

### `fahrenheitToCelsius` test cases

| Input (°F) | Expected Output (°C) | Notes |
|------------|----------------------|-------|
| 32.0 | 0.0 | Freezing point of water |
| 212.0 | 100.0 | Boiling point of water |
| -40.0 | -40.0 | Crossover point |
| 98.6 | 37.0 | Average body temperature |

### `celsiusToFahrenheit` test cases

| Input (°C) | Expected Output (°F) | Notes |
|------------|----------------------|-------|
| 0.0 | 32.0 | Freezing point of water |
| 100.0 | 212.0 | Boiling point of water |
| -40.0 | -40.0 | Crossover point |
| 37.0 | 98.6 | Average body temperature |

### Round-trip test

Convert a value from Fahrenheit to Celsius, then back to Fahrenheit, and assert the final result equals the original input. Repeat in the other direction. Use `assertEquals(expected, actual, delta)` with a delta of `0.001` for all floating-point comparisons.

---

## Rubric

| Part | Description | Points |
|------|-------------|--------|
| Part 1 | Scene 1 layout: conversion label, styled TextField with prompt text | 10 |
| Part 2 | Convert button present; click reads and prints TextField value to console | 10 |
| Part 3 | `fahrenheitToCelsius` method implemented with correct formula | 10 |
| Part 4 | Button calls conversion method, updates result label, handles invalid input | 15 |
| Part 5 | Short click converts; long press switches to Scene 2; `longPressHandled` flag prevents double-fire | 15 |
| Part 6 | Scene 2 fully functional with `celsiusToFahrenheit`; long press returns to Scene 1 | 15 |
| Part 7 | Long press transfers result to destination TextField; empty/invalid results leave TextField unchanged | 15 |
| Unit Tests | All required test cases pass using `assertEquals` with delta | 10 |
| **Total** | | **100** |
