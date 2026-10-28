# Student Management System — Java 21 / Maven / IntelliJ IDEA

A console application for adding, viewing, searching, updating, and deleting students. Data is **in memory** and resets when the application exits. No MySQL server is required.

## Open in IntelliJ IDEA (Mac)
1. Extract the ZIP.
2. IntelliJ IDEA → File → Open → choose the extracted `StudentManagementSystem_Ready_IntelliJ` folder.
3. Trust the project, select Java SDK 21, and reload Maven.
4. Open `src/main/java/com/studentmanagement/App.java` and click the green Run arrow next to `main`.

## Run from Terminal
```bash
mvn clean verify
mvn exec:java
```
If you prefer a precompiled app without Maven, run `java -jar StudentManagementSystem.jar` from the extracted project folder (Java 21 required).

## GitHub
Git is already initialized with `origin` pointing to the repository URL you previously supplied:
`https://github.com/aaravchoudharyxyl1-ops/StudentManagementSystem.git`

Inspect with `git remote -v` and `git status`. To upload after confirming the remote is yours, run:
```bash
git push -u origin main
```
GitHub login/authentication is required. **The ZIP does not contain any passwords, tokens, or SSH keys.** If the remote has existing commits, fetch and reconcile histories before pushing; do not force-push blindly.

## Project files
- `pom.xml` Maven build (Java 21, JUnit 5, exec plugin)
- `src/main/java/com/studentmanagement/App.java` entry point
- `Student.java` validated student model
- `StudentManager.java` in-memory CRUD
- `src/test/java/com/studentmanagement/AppTest.java` JUnit tests
- `.gitignore` IntelliJ/Maven/macOS ignores
- `StudentManagementSystem.jar` standalone Java application

## Notes
MySQL connectivity and permanent storage are **not implemented** in this starter. Do not add real credentials to version control.
