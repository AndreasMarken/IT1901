[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2238/gr2238/-/tree/master/)
# Welcome to My Movie Tracker :movie_camera:

My Movie Tracker is an application whose main purpose is to help you track the movies you have seen, so that you, at a later time, know which movie to watch again or have a broad selection of movies to recommend for your friends.

## The codeproject

The codeproject is located in the mmt folder, which can be accessed using `cd mmt` in a terminal from the root folder. The application is built using standard Maven directory layout. To run the project, you can either run it directly from the class or use the maven commands listed in the [Building and running the project localy](#building-and-running-the-project-loacaly) section.

The application currently consists of 4 modules.
- core, which is the backend of the project.
- fxui, which consist of the user-interface.
- rest, which consist of the api set up of the project. This is the module that creates the server set up.
- integrationtests, which is the module that starts the server, and tests that everything is set up correctly.

The application also has a json folder where serializing and deserializing objects are handled. It was considered to make this folder the fifth module, but since it has a close connection to the core, it was decided not to make it another module.
To read more about each module, you can do it in their own README:
- [core](mmt/core/README.md)
- [fxui](mmt/fxui/README.md)
- [rest](mmt/rest/README.md)
- [integrationtests](mmt/integrationtests/README.md)

## Building and running the project loacaly
The project is built using maven, and therefore, maven must be used, to run the project.

If you are in the root folder **gr2238**, you will have to change directory to **mmt** before continuing. Perform:

```
cd mmt
```

You should now be in the correct folder.
### Build the project
To build the project, perform:

```
mvn clean install -DskipTests
```
This will clean the maven project, and build it from scratch. If you want to run the tests when building the project, you can skip the last configuration `-DskipTests`.

### Starting the server
This is an optional step. If you want, you can run the project and save the movielist localy, however, if you want the complete experience of the app, you should run it with the server.

To start the server you can use the following command:
```
mvn -pl integrationtests jetty:run -D"jetty.port=8080"
```
This has got to be done in a seperate tab.

### Running the project
If you have started the server, you can open a new terminal tab, and continue. If you choose to run the project on localy stored files, you can continue in the same tab.

To run the javafx part of the project you have two opportunities, either perform:

```
mvn javafx:run -f fxui/pom.xml
```

Or perform this:
```
cd fxui
mvn javafx:run
```

This should open a new window with the application. From here you can add movies to the database.

### Testing the project
To perform all of the test, you can use:

```
mvn test
```

This will run all of the test, and give you a feedback on how many that succeded. If you only want to perform tests in one module, you can `cd` into that specific module and then perform `mvn test`.

After you have run the test, there will open a new folder in each of the modules target folder. The folders name is site/jacoco. In this folder, there will be an index.html. This can be opened in a web browser, to look at the test coverage.

## Running in Gitpod
1. Click on gitpod tag at the top of the page.
2. Navigate to the correct folder: `cd /workspace/gr2238/mmt` when in the workspace. This should automaticly be done when you open gitpod from the project.
3. You can now either run: `mvn javafx:run -f fxui/pom.xml` or you can firstly `cd fxui` and then use the command `mvn javafx:run`. 
4. click "remote explorer" in the toolbar to the left.
5. click "GITPOD WORKSPACE" -> "Ports".
6. click on the port in "Ports" 

If the steps above does not work, we recommend following the steps above, telling how to build and run the project on your own computer.

# Contributing
## Workflow

For us to deliver the highest standard of quality in a fast-paced project we have chosen the Scrum Workflow for our project.

In our case, "the heartbeat of Scrum" a.k.a. the sprints has the duration equivalent to the time between IT1901 submission deadlines.

**Our goal is to:**
- Have a "daily standup" every other day
- A Sprint Review close to the submission deadline
- Sprint Retrospective the day after the submisson deadline. 
- Sprint Planning starts within two days of the deadline.

We strive to achive 80% code coverage on all modules (from jacoco reports)

### Creating a new branch

For simplicity and effectiveness, we create a new branch for each issue. The name should be on the form: 
```git
<type of work>_<optional scope>_<tiny description of issue>
```

### Commits
We strive to follow Conventional Commits 1.0.0, to keep a consistency in our commit messages and automatically generate human readable changelogs. 

Example (short version):

```git
docs: update userstories

feat: add sorting functionality to movieList
```

Below you can find a table of suggestions, inspired NTNUI Sprint.

| type | description |
| --- | --- |
| build | Changes that affect the build system or external dependencies |
| ci | Changes to CI configuration files and scripts |
| docs | Documentation only changes |
|feature | A new feature |
| fix | A bug fix |
| perf | A code change that improves performance |
| refactor | A code change that neither fixes a bug nor adds a feature |
| style | Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc) |
| test | Adding missing tests or correcting existing tests |

### MRs - Merge requests

All MRs should target the development branch `develop`

Our development and master branch are protected to ensure good code quality, by having the work by a group member reviewed by another group member.

We have created our own MR template for what our MRs should contain.