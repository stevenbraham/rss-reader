# Java RSS Reader
This is a simple CLI application for reading RSS news feeds. Withing the application you can add and read feeds using commands. The feeds are stored on disk using an auto generated XML file. This is not a serious project, but something I made for fun.

Use [Apache maven](https://maven.apache.org/) to run and build this app.

## Available commands
After booting the application, you can input the following commands to use the application:

| Command       | Description |
| ------------- | ------------- |
| `add-feed` | Opens the wizard for storing a new feed in the app database  |
| `read-feed [feed id]`  | Displays news item from a feed |
| `edit-feed [feed id]`  | Allows you to edit feed information |
| `delete-feed [feed id]`  | Removes a feed from the app database |
| `help`  | Lists all available commands |
| `exit`  | Exists application |

## Open source software used
This project uses the following open source software packages:
* [Apache Commons Text](https://commons.apache.org/proper/commons-text/) for formatting the terminal output
* [JDom](http://www.jdom.org/) for read and writing the XML config file
* [Jsoup](https://jsoup.org/cookbook/introduction/parsing-a-document) to convert RSS html item descriptions into plain text
* [ROME](https://rometools.github.io/rome/) for parsing the RSS feeds
* [Spring Shell](https://projects.spring.io/spring-shell/) as the base framework
