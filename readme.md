# 🧩 SonarCloud Maven Example

This repository demonstrates how to integrate **SonarCloud** analysis into a **Java (Maven)** project using **GitHub Actions** for continuous code quality and static analysis.

---

## 🚀 Project Overview

This project is a simple Java + Maven example configured to:
- Build and test using **Maven**.
- Automatically analyze code quality with **SonarCloud**.
- Run analysis on **push** and **pull requests** to the `main` branch.
- Report quality gate results directly on GitHub Pull Requests.

---

## ⚙️ Prerequisites

Before you start, make sure you have:

1. **A SonarCloud account:**  
   👉 [https://sonarcloud.io](https://sonarcloud.io)

2. **A SonarCloud project key and organization name.**

3. **A SonarCloud token** (used for authentication).

   Generate one under:  
   **My Account → Security → Generate Tokens**

---

## 🔐 Create GitHub Secrets

In your GitHub repository:

1. Navigate to:  
   **Settings → Secrets and variables → Actions**

2. Create a new secret:
   | Name | Value |
   |-------|--------|
   | `SONAR_TOKEN` | `2efbaf6da1369b109363e31e427b34ddb1e6fe82` *(example — use your real token)* |

GitHub will also automatically provide a `GITHUB_TOKEN` for pull request decoration.

---

## 🏗️ Maven Configuration

Your `pom.xml` should include:

```xml
<properties>
    <java.version>17</java.version>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>

<build>
    <plugins>
        <plugin>
            <groupId>org.sonarsource.scanner.maven</groupId>
            <artifactId>sonar-maven-plugin</artifactId>
            <version>3.10.0.2594</version>
        </plugin>
    </plugins>
</build>
```

Optional properties file (`src/main/resources/config/sonar.properties`):

```properties
sonar.organization=learn-and-test
sonar.projectKey=khilendra_retiejs
sonar.host.url=https://sonarcloud.io
```

---

## ⚡ GitHub Actions Workflow

Save this file as:
```
.github/workflows/sonarcloud.yml
```

```yaml
name: SonarCloud Analysis

on:
  push:
    branches:
      - main
  pull_request:
    types: [opened, synchronize, reopened]

jobs:
  build:
    name: Build and Analyze on SonarCloud
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v4
        with:
          fetch-depth: 0

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: 17
          distribution: 'zulu'

      - name: Cache SonarCloud packages
        uses: actions/cache@v4
        with:
          path: ~/.sonar/cache
          key: ${{ runner.os }}-sonar
          restore-keys: ${{ runner.os }}-sonar

      - name: Cache Maven packages
        uses: actions/cache@v4
        with:
          path: ~/.m2
          key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
          restore-keys: ${{ runner.os }}-m2

      - name: Build and analyze on SonarCloud
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        run: |
          mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar             -Dsonar.projectKey=khilendra_retiejs             -Dsonar.organization=learn-and-test             -Dsonar.host.url=https://sonarcloud.io             -Dsonar.login=${{ secrets.SONAR_TOKEN }}
```

---

## 🧪 Run Analysis Locally

You can also run SonarCloud analysis from your local machine using Maven:

```bash
mvn clean verify sonar:sonar   -Dsonar.organization=learn-and-test   -Dsonar.projectKey=khilendra_retiejs   -Dsonar.host.url=https://sonarcloud.io   -Dsonar.login=YOUR_SONAR_TOKEN
```

---

## 📊 View Your Analysis Results

After a successful analysis, results are available on your SonarCloud dashboard:

👉 [https://sonarcloud.io/dashboard?id=khilendra_retiejs](https://sonarcloud.io/dashboard?id=khilendra_retiejs)

---

## 🧰 Troubleshooting

| Issue | Cause | Solution |
|--------|--------|-----------|
| `Connection reset` | Network or firewall blocking SonarCloud | Check proxy/firewall settings, clear cache (`rm -rf ~/.sonar`) |
| `localhost:9000` errors | Using local SonarQube URL | Set `sonar.host.url=https://sonarcloud.io` |
| `Recursive property` errors | Misconfigured POM placeholders | Remove self-referencing properties |

---

## 🧠 Summary

✅ Uses Maven + SonarCloud  
✅ Analyzes automatically via GitHub Actions  
✅ Publishes PR comments and code quality reports  
✅ Easy to run locally or in CI  

---

**Maintainer:** [@khilendra](https://github.com/khilendra)  
**Powered by:** [SonarCloud](https://sonarcloud.io) • [GitHub Actions](https://github.com/features/actions)
