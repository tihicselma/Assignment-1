# Assignment-1
# The Great Potato Space Program

A Java project that reads CSV files with launch records and generates five output reports.

## How I Built It

I modeled the project around `Potato` class (with separate subclasses for each type), `Launcher`, and `LaunchAttempt`, where each attempt keeps direct references to its potato and launcher objects. While reading the CSV files, I used two maps (by potato ID and launcher ID) to quickly match each attempt to the right potato and launcher, and stored all attempts in an `ArrayList`. I put the score formulas in a `Scorable` interface so each potato subclass calculates points its own way, and implemented `Comparable` on `LaunchAttempt` to handle sorting for the last report. I split file reading, logic, and report writing into separate classes so the code stays organized and easy to follow.

## Project Structure

```text
src/
├── Main.java
├── CsvReader.java
├── ReportGenerator.java
├── Potato.java
├── FingerlingPotato.java
├── PurplePotato.java
├── RussetPotato.java
├── YukonGoldPotato.java
├── Launcher.java
├── LaunchAttempt.java
└── Scorable.java
input/
├── potatoes.csv
├── launchers.csv
└── launch_attempts.csv
output/
├── 01_potato_flight_log.csv
├── 02_launcher_activity.csv
├── 03_launch_results_by_pair.csv
├── 04_payload_violations.csv
└── 05_best_test_flights.csv
