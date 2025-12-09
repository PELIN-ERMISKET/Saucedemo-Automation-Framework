# Saucedemo Automation Framework

Selenium + TestNG automation framework for [Saucedemo](https://www.saucedemo.com),  
built with Page Object Model (POM), Allure reporting, video recording and parallel execution.

---

## 🏷 Badges

![Java](https://img.shields.io/badge/Language-Java%2017-orange)
![TestNG](https://img.shields.io/badge/Test%20Framework-TestNG-9b59b6)
![Selenium](https://img.shields.io/badge/Automation-Selenium%20WebDriver-43B02A)
![Maven](https://img.shields.io/badge/Build-Maven-brightgreen)
![Allure](https://img.shields.io/badge/Reporting-Allure%20Report-ff69b4)
![Status](https://img.shields.io/badge/Status-Active-success)

---

## 🚀 Overview

This repository contains an end-to-end UI automation framework for **Saucedemo**.

Main goals:

- Clean & scalable architecture (**Page Object Model – POM**)
- Highly readable test scenarios with Allure `@Epic / @Feature / @Story`
- Parallel execution support (TestNG)
- Rich Allure HTML reports
- Video recording for each test run
- Reusable utilities & listeners

---

## ✨ Features

- ✔️ **Selenium WebDriver 4**
- ✔️ **TestNG** test runner
- ✔️ **POM (Page Object Model)** structure
- ✔️ **Allure reporting integration**
- ✔️ **Video recording** (Monte Screen Recorder)
- ✔️ **Parallel execution support**
- ✔️ **Reusable utilities, base classes and listeners**

---

## 📁 Project Structure

```text
Saucedemo-Automation-Framework
│
├─ saucedemo-automation-framework
│  └─ src
│     └─ test
│        └─ java
│           ├─ base
│           ├─ pages
│           ├─ tests
│           └─ utilities
│
├─ test-output/
├─ test-recordings/
├─ target/
├─ allure-report/
├─ allure-report-overview.png
└─ run-allure.bat
```

## 🧪 Example Allure Report
Screenshot from a sample test run:
![Allure Report Overview](allure-report-overview.png)

To view the live report locally: http://127.0.0.1:62089/#

bash
Kodu kopyala
allure serve target/allure-results
🛠 Installation


1️⃣ Clone the project
bash
Kodu kopyala
git clone https://github.com/PELIN-ERMISKET/Saucedemo-Automation-Framework.git
cd Saucedemo-Automation-Framework/saucedemo-automation-framework


2️⃣ Install dependencies
Maven will automatically download all required dependencies on the first run:

bash
Kodu kopyala
mvn clean test -DskipTests
▶ Running Tests
Default suite
Runs the standard TestNG suite:

bash
Kodu kopyala
mvn clean test
Parallel execution (if you add a parallel suite)
bash
Kodu kopyala
mvn clean test -DsuiteXmlFile=testng-parallel.xml

## 📊 Allure Reporting
Generate & open Allure report (CLI)
bash
Kodu kopyala
allure serve target/allure-results
Or via helper .bat file
From the project root:


bash
Kodu kopyala
run-allure.bat
This will generate and open the latest Allure report in your default browser.

## 🎥 Video Recording
Each test run can be recorded automatically using Monte Screen Recorder.

Sample recordings are stored under:
### 🎥 Checkout Success GIF
![Checkout GIF](TestRecording_checkout-GIF.gif)


bash
Kodu kopyala
test-recordings/
You can attach these videos to defects or share them with the team for easier debugging.
