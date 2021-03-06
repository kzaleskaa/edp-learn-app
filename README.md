<div align="center">
<h1>Event-driven programming - final project</h1>
<img src="https://user-images.githubusercontent.com/62251989/174919552-336212b8-25dd-4842-bbeb-6cad50a2d6dc.png" alt="app logo"/>
</div>


## Introduction
Application developed during the lab activities of "Event-Driven programming" module. The task made it possible to learn about aspects such as event-bus, design patterns, custom events and components and much more.

## Used technologies
The application was written in Java SE programming language. JavaFX was used to create a graphical user interface and PostgreSQL as a database relational management system. All additional libraries were added using maven dependencies. Some data such as the database password has been added to the app.properties file to avoid harcoding this data in code. The PropertiesManager class allows you to read data from this file. 

## Requirements
- Java SE technology
- GUI application (Swing, Java FX, SWT)
- multithreading
- asynchronous programming
- events (custom events)
- using data taken from external web services
- databases (JDBC, JPA)
- config properties file
- custom graphical components 
- use of Apache Commons libraries and event buses
- design patterns

## Conslusions
The application is multi-threaded and uses aspects of asynchronous programming. Custom events allow you to log the user in or display an appropriate message. The data available from the two external web services is used when checking a word or displaying an advice. The words added to the user account are stored in the database. Config properties prevented the use of passwords or other sensitive information in code. Apache Commons made it possible to send welcome emails while the event bus was used to display alerts during login/registration. The design patterns used made it possible, for example, to save information about the logged-in user.

## Credits - used icons and images
- [Frepik](https://pl.freepik.com/)
- [Flaticon](https://www.flaticon.com/) 