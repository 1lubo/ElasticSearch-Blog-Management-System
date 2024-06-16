docker from https://github.com/deviantony/docker-elk

# Project Name

Simple Blog Management System
“Spring Boot 2.0 Projects
by Mohamed Shazin Sadakath
Copyright © 2018 Packt Publishing”


## Table of Contents
- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Building and Running](#building-and-running)
- [Configuration](#configuration)
- [Elastic Stack on Docker Documentation](#elastic-stack-on-docker-documentation)
- [License](#license)

## Introduction
This Spring Boot application provides a simple blog management system. 
The main purpose is to demonstrate the use of Spring Boot, Spring Data Elasticsearch for persistence, 
Apache Freemarker for view.



## Prerequisites
To build and run this project, you need:
1. Java Development Kit (JDK) 8 or later
2. Gradle 8
3. An IDE like IntelliJ IDEA, Eclipse, or VSCode with the necessary plugins for Spring Boot development
4. Docker

## Getting Started
To get started with this project, follow these steps:
1. Clone the repository to your local machine.
2. Open the project in your favorite IDE.
3. Configure the database settings in `src/main/resources/application.properties`.
4. Build the project using Gradle: `gradle clean build`

## Building and Running
To build and run the application, use the following commands:
1. Build the project: `gradle clean build`
2. Elastic stack (ELK) on Docker `docker compose up`
3. Run the application: `gradle bootRun`

The application will start on port 8080 by default. You can change this in the `src/main/resources/application.properties` file.

## Configuration
This project uses Spring Boot's auto-configuration feature to set up the database connection and other settings. You can customize these settings in the `src/main/resources/application.properties` file.

## Elastic Stack on Docker Documentation
https://github.com/deviantony/docker-elk

## License
This project is licensed under the MIT License. See the `LICENSE` file for more information.
