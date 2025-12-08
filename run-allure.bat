@echo off
echo Allure raporu hazirlaniyor...
cd %~dp0
allure serve target/allure-results
pause
