# Student Management System — Java 21 + MySQL + Maven

An IntelliJ IDEA console application with **persistent MySQL student records** (add, list, search, update, delete). Includes a Git repository with the previously configured GitHub origin; no database passwords are bundled.

## 1. Prerequisites on macOS

- JDK 21, Maven and IntelliJ IDEA
- MySQL Server 8.0+ running locally; on Homebrew, typically `brew install mysql` and `brew services start mysql` (only if not already installed)
- Verify: `java -version`, `mvn -version`, `mysql --version`

## 2. Initialize MySQL from the terminal

Open `sql/setup.sql` and **replace `CHANGE_THIS_PASSWORD` with a strong password** (both occurrences). Do not commit your real password: revert the file before committing, or run the SQL manually with a secure password.

From the project root:

```bash
mysql -u root -p < sql/setup.sql
```

If the MySQL root account uses socket authentication or another admin account, use the credentials appropriate for your installation.

To inspect the database:

```bash
mysql -u root -p student_management
```

Inside the MySQL prompt:

```sql
SHOW TABLES;
DESCRIBE students;
SELECT * FROM students;
EXIT;
```

## 3. Run from IntelliJ terminal

```bash
export DB_USER=student_app
export DB_PASSWORD='YOUR_STRONG_PASSWORD'
# Optional: export DB_URL='jdbc:mysql://localhost:3306/student_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'
mvn clean verify
mvn exec:java
```

`DB_PASSWORD` must be exported in **the same terminal session** used to run Maven. For the IntelliJ Run button, add it in Run Configuration > Environment variables. Do not put real passwords in Git-tracked files. Note that typing a password in a shell command can be retained in shell history; you can use `read -s DB_PASSWORD; export DB_PASSWORD` instead.

## 4. Confirm persistence

Add a student in the application, choose `0` to exit, then rerun `mvn exec:java`. Select View All Students: the record should still exist. You can also check using `mysql -u root -p student_management -e 'SELECT * FROM students;'`.

## 5. Git / GitHub

```bash
git remote -v
git status
git add pom.xml src sql README.md .gitignore
git commit -m "Add MySQL JDBC persistence and SQL terminal setup"
git push -u origin main
```

**Caution:** If your GitHub repository already has independent commits, inspect/reconcile histories before pushing. Do not force-push.

## 6. Notes

- `App.java` is the entry point (`com.studentmanagement.App`).
- `StudentManager.java` uses JDBC prepared statements, with a separate connection per operation.
- `sql/setup.sql` is idempotent for database/table creation, but resets the `student_app` password to the example value if rerun. Always change the placeholder first.
- `mvn clean verify` tests the Java model; it does not test a live MySQL connection. Actual database execution requires a running MySQL server and valid credentials.
- Git history in the ZIP is the previously packaged local repository, not a clone of GitHub.
