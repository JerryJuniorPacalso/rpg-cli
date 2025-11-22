# Copilot Instructions for `rpg-cli`

This document is for AI coding assistants working in this repository. Follow these rules by default unless the user explicitly overrides them.

## 1. Purpose & Scope

- Repository: Java/Gradle CLI RPG game (`rpg-cli`).
- Primary goals:
  - Keep the project building and tests passing.
  - Improve code quality, safety, and maintainability with **small, incremental** changes.
  - Respect existing architecture, naming, and style unless explicitly asked to refactor.

## 2. General Behavior

- **Tone & Communication**
  - Be concise, direct, and friendly.
  - Prefer short paragraphs and bullet lists.
  - Avoid over-explaining obvious Java/Gradle concepts unless asked.

- **Edits & Scope**
  - Prefer **minimal, targeted edits** over large refactors.
  - Do **not** reformat entire files or reorder imports unless requested or required by the change.
  - Preserve existing public APIs and behavior unless the task explicitly says otherwise.
  - If a task is ambiguous, state 1–2 reasonable assumptions and proceed; ask for clarification only when truly blocked.

- **Tests-First Mindset**
  - When implementing new behavior or fixing a bug:
    - Prefer to **add or update tests first**, then modify production code until they pass.
    - Keep tests focused, small, and deterministic (no randomness or time-dependence without control).

- **Dependencies**
  - Avoid adding new dependencies unless absolutely necessary.
  - If adding a dependency, explain why; update relevant Gradle files and keep versions explicit.

## 3. Project Conventions

- **Language & Tooling**
  - Java version: follow the version configured in Gradle (do not assume a higher/lower JDK).
  - Build tool: Gradle with Kotlin DSL.
  - Source layout (typical):
    - Main code: `app/src/main/java/...`
    - Tests: `app/src/test/java/...`

- **Testing**
  - Likely uses JUnit 5 (and possibly Mockito); follow existing patterns in `app/src/test/java`.
  - Put new unit tests in the appropriate package mirroring the main source.

- **Logging**
  - Use existing logging utilities (e.g., `LogConfig` or similar) instead of introducing new logging frameworks.
  - Do not change log levels, appenders, or formats unless explicitly requested.

- **CLI / Game Loop**
  - If working in game logic or scenes, respect the current scene management and game loop design.
  - Avoid introducing blocking or long-running operations that could freeze the CLI.

## 4. Build & Test Workflow

Use these commands from the repository root unless the user indicates otherwise.

- **Run unit tests**
  - Unix/macOS:
    - `./gradlew test`
  - Windows:
    - `gradlew.bat test`

- **Run full checks (if configured)**
  - `./gradlew check` (Unix/macOS)
  - `gradlew.bat check` (Windows)

- **Run the app (if defined)**
  - `./gradlew :app:run` (Unix/macOS)
  - `gradlew.bat :app:run` (Windows)

- **Jacoco / Coverage**
  - If Jacoco tasks are present, keep them green (e.g., `jacocoTestCoverageVerification`).
  - When adding features, add tests to avoid reducing coverage.

## 5. CI & GitHub Actions Guidance

- CI workflow file: `.github/workflows/gradle-ci.yml`.
- Default behavior in this workflow:
  - Uses `ubuntu-latest` runner.
  - Ensures `gradlew` is executable.
  - Sets up JDK 17 (Temurin) via `actions/setup-java@v4`.
  - Caches Gradle dependencies via `actions/cache@v4`.
  - Runs: `./gradlew test --no-daemon`.
  - Uploads test report from `app/build/reports/tests/test` as artifact.

- When **editing CI workflows**:
  - Keep steps idempotent and compatible with Linux runners.
  - Prefer official actions (`actions/*`) and pin by major version (e.g., `@v4`).
  - Preserve or improve caching keys; dont remove caches without reason.
  - If changing the Gradle command (e.g., `test` → `check`), ensure the project passes locally with the new command.
  - Validate any new paths (e.g., artifact paths) actually exist in the Gradle build outputs.

- Triggers:
  - Current workflow triggers on pull requests to `develop` and `feature/**` branches.
  - When changing triggers, ensure they match the intended branching model; do not broaden them unnecessarily.

## 6. Coding Guidelines

- **Style & Structure**
  - Match existing code style (indentation, braces, naming conventions).
  - Keep methods small and focused; avoid deeply nested logic when possible.
  - Prefer clear, descriptive names for methods and variables.

- **Error Handling**
  - Fail fast with clear exceptions in internal code unless user-facing behavior requires graceful handling.
  - For user-visible errors in the CLI, prefer clear messages printed via the existing console/printing utilities.

- **Testing Guidelines**
  - For new functionality, add at least:
    - One happy-path test.
    - One edge-case test (e.g., null/empty input, boundary values, or invalid state).
  - Use existing helper methods or test utilities if present.

## 7. Assistant Checklist

Use this checklist for **every change** you make.

### Before Editing

1. Understand the user request and restate the concrete goals in your own words.
2. Identify all relevant files (use search if needed: source, tests, build scripts, CI configs).
3. Skim related code to understand current behavior and conventions.
4. Decide on the smallest reasonable change set that satisfies the request.

### During Editing

1. Prefer updating/adding tests first for new behavior or bug fixes.
2. Apply minimal edits to the relevant files only.
3. Maintain existing style, structure, and patterns.
4. Avoid large-scale refactoring unless explicitly requested.

### After Editing

1. Ensure the code compiles conceptually and imports are correct.
2. Run appropriate Gradle commands (at least `test`; or `check` if you changed build logic):
   - Unix/macOS: `./gradlew test`
   - Windows: `gradlew.bat test`
3. If tests conceptually fail, iterate until they would pass; adjust tests or code if expectations were wrong.
4. Summarize:
   - Files changed and why.
   - New behavior and its impact.
   - How to run tests / verify the change.

## 8. When to Ask or Flag

- Ask for clarification (or clearly state assumptions) when:
  - The requested behavior contradicts existing documented requirements.
  - The change would require large refactors or architectural changes.
  - Security, data loss, or compatibility could be affected.

- If you must leave something incomplete (e.g., missing external information), clearly mark it with a TODO comment and explain it in your response.

