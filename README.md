# Cat Database

A JavaFX cat app to help pet owners connect using a form to store a cat's details using login to only allow changes to own cats. Everything stored in database. Currently AWS Relational Database System being used is shut down.


## Table of contents
* [General info](#general-info)
* [Screenshots](#screenshots)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)
* [Status](#status)
* [Inspiration](#inspiration)
* [Contact](#contact)

## General info
I became a cat owner a few years ago and wanted to apply a way for people to meet with other similar cats. Just an idea and still a work in progress. Logging into the application allows access to the database to only make changes to your own cats. Uses some password hashing and database storage to fullfill some of my interests. Many more updates and features on the way. 

## Screenshots
![login screen screenshot](https://github.com/SteveT90/Cat-Database/blob/master/src/main/resources/Login%20Screen.jpg?raw=true)
![main database view](https://github.com/SteveT90/Cat-Database/blob/master/src/main/resources/Main%20Screeen.jpg)
## Technologies
* openjdk-15 - java version "15.0.2"
* JavaFX-sdk-15
* MySQL mysql-connector-java:8.0.15 
* Gradle

## Setup
Gradle runs on all major operating systems and requires only a Java JDK version 8 or higher to be installed. To check, run java -version \
Download gradle here at: https://gradle.org/releases.\
After installation is complete navigate into the project folder in terminal or command prompt and use command: gradlew run
Source code can be found here https://github.com/SteveT90/Cat-Dating-DB.git and can also run using Intellij or Eclipse as Gradle project

<!--## Code Examples
Show examples of usage:
`put-your-code-here` -->

## Features
List of features ready and TODOs for future development
* User login information stored and passwords are hashed using java.security hashing algorithm "PBKDF2WithHmacSHA512".
* Cat details are able to be added, updated, and deleted with a click of a button.

To-do list:
* Database is currently on local machine and must have mySQL on local machine port 3036, within the week the database will be server based.
* More cat details will be able to be stored along with user contact information and pictures.
* Currently anyone can change the details of the cats, soon they will only be able to change thier own details entered by themselves.

## Status
Project is: _in progress_

## Inspiration
Project inspired by wanting to learn more about using Java and mySQL together along with JavaFX GUI. Also my first attempt at working with password hashing that has always been an interest to me. As you may be able to tell I also enjoy pets.
## Contact
Created by SteveT90 - feel free to contact me!
