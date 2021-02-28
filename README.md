# Cat Dating App

A JavaFX cat app to help pet owners connect using a form to store a cat's details using login to only allow changes to own cats. Photos and messaging will be added soon. Everything stored in database


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
![login screen screenshot](https://github.com/SteveT90/Cat-Dating-DB/blob/master/src/Images/Login%20Screen.jpg)
![main database view](https://github.com/SteveT90/Cat-Dating-DB/blob/master/src/Images/Main%20Screeen.jpg)
## Technologies
* openjdk-15 - java version "15.0.2"
* JavaFX-sdk-15
* MySQL Connector/J 5.1.49 

## Setup
Install JDK 15 to run / setup your local environement <br /> Jar file found here at https://github.com/SteveT90/Cat-Dating-DB/raw/master/out/artifacts/CatDatabaseApp_jar/CatDatabaseApp.jar  This can be run using java -jar [location of previously downloaded jar file] <br /> <br />
Source code can be found here https://github.com/SteveT90/Cat-Dating-DB.git and can also run using Intellij or Eclipse

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
