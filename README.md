[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2238/gr2238/-/tree/master/)
# Welcome to My Movie Tracker :movie_camera:

My Movie Tracker is an application whose main purpose is to help you track the movies you have seen, so that you, at a later time, know which movie to watch again or have a broad selection of movies to recommend for your friends.

## The codeproject

The codeproject is located in the mmt folder, which can be accessed using `cd mmt` in a terminal from the root folder. The application is built using standard Maven directory layout. To run the project, you can either run it directly from the class or use the maven commands listed in the [README in mmt folder](mmt/README.md).

The application has now two modules, both located in the mmt folder.
- core, which is the backend of the project.
- fxui, which consist of the user-interface.

The application also has a json folder where saving objects are handled. It was considered to make this folder the third module, but since it has a close connection to the core, it was decided not to make it a third module. 


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


## Running in Gitpod


1. Click on gitpod tag at the top of the page.
2. Navigate to the correct folder: `cd /workspace/gr2238/mmt` when in the workspace. This should automaticly be done when you open gitpod from the project.
3. You can now either run: `mvn javafx:run -f fxui/pom.xml` or you can firstly `cd fxui` and then use the command `mvn javafx:run`. 
4. click "remote explorer" in the toolbar to the left.
5. click "GITPOD WORKSPACE" -> "Ports".
6. click on the port in "Ports" 





