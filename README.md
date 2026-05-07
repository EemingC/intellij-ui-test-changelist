# IntelliJ UI Test Project

This project demonstrates automated UI testing for IntelliJ IDEA using JetBrains IntelliJ Driver and IDE Starter.

## Technologies

- Kotlin
- JUnit 5
- IntelliJ Driver SDK
- IntelliJ IDE Starter
- IntelliJ IDEA Ultimate

## Based On

This project was developed using the official JetBrains examples from:

```kotlin
com.intellij.ide.starter.examples.driver
```

and the IntelliJ IDE Starter framework.

## JDK Version

The original examples may use JDK 25.

This project was changed to use:

```kotlin
kotlin {jvmToolchain(21)}
```

## Run Tests

Windows:

```bash
gradlew.bat test
```

Linux / macOS:

```bash
./gradlew test
```

## Test Workflow

The test automatically:

1. Opens IntelliJ IDEA
2. Opens Settings
3. Navigates to Version Control → Changelists
4. Enables "Create changelists automatically"
5. Verifies the checkbox is selected
```kotlin
> Task :test
UiTestWithDriverCheckbox > testChangelistCheckbox() PASSED
```
6. Clicks OK

Sample display:
<p align="center">
  <img src="screenshot/screenshot20260507.gif" width="700"/>
</p>

## Key APIs Used

```kotlin
driver.invokeAction("ShowSettings", now = true)
settingsDialog.settingsTree.clickPath("Version Control", ...)
settingsDialog.checkBox(...)
assertTrue(checkbox.isSelected())
```
