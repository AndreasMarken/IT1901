# Release 3
For the last sprint of the it1901 group assignment, we have focused on creating more logic and functionality to the user, as well as improving code quality, work flow and teamwork.

During this sprint we have focused on work flow containing pair programming and code review. We have written most of the code added throughout this sprint with pair-programming in a team of two. We have also tried to use the git-tag co-authored-by: @username, to give credit to both contributors.

With the use of pair programming, our code quality has improved because there is two sets of eyes watching, writing and accepting the code before it is commited. 

To also improve the code quality of the project, we have also used code-review in gitlab to give comments to eatch other, such that all code is verified and accepted by more contributors.

The logic we have focused on implementing to this sprint, has mainly been the implementation of a REST-API and an actor class.

The actor class has given movies another dimension, where one actor can be linked to multiple movies, and one movie can have multiple actors. In the mmt-app, you can now also search for movies containing certain actors or by movie name. By giving the user this possibility, the user can now find movies containing their favorite actor, or search for sequels to movies that you enjoy watching.

The implementation of the REST-API gives the user the possibility to either run the application on locally stored files or using a server. To make this possible we have created an interface named IAccess, which we have implemented in LocalMmtAccess and RemoteMmtAcces. This gives us two different implementations of the way mmt can be stored. You can read more about our API set up and base endpoints [here](../mmt/rest/README.md), or continue reading the [API-setup](#api-setup) section in this file.

We have also made our product shippable, as the requirements in `arbeidskrav` states. To read more about the shippable configuration, read [here](../mmt/README.md#shippable-product---export-the-project).

We have also updated the diagrams we created for deliverable 2. To read more about the diagrams, check out the [diagrams section](../mmt/README.md#diagrams) in the mmt README file. We have also created a diagram that displays the endpoint usage, you can view it [here](../mmt/rest/README.md).

As our focus have been in prior sprints, our focus has been on having descriptive branch names, as well as branches that links to each userstorie/issue. Our branches are ment to have the same start as the names in the table in the root [readme file](../README.md#commits). Followed by a descriptive name of the issue it is linked with. We have also decided to create a table showing witch issue is linked to which userstory. Under the last table row, you can see the issues we feel are essential for the project, but not necessarily linked to a userstory.

| Userstory | Issue |
| --- | --- |
| As a user, I want to add movies to a list which contains movies that I find interesting, so that when i want to watch a movie I know which ones i like. | #10, #19, #14, #42|
| As a user, I want to be able to add movies that I like to a watchlist that contains movies that I am due to watch, so that I know which movie I should watch when the weekend comes | #27, #46 |
| As a user, I want to remove movies from a list which contains movies that I like, in case i do not like them anymore | #27 |
| As a user, I want to sort my list of movies that I like based on duration | #44 |
| As a user, I want to sort my list of movies that I like based on the title, so that when I have a lot of movies in my list, it is easy to find the one I am looking for | #44 |
| As a user, I want to sort my list of movies that I like based on rating, in case someone asks me what my all-time favorite movie is, it is easy for me to figure out which. | #44 |
| As a user, I want to have a list of movies that I find interesting available, so that I have suggestions when being asked for a recommendation | #15, #16, #18, #47, #27, #46 |
| As a user, I want to write a short summary of each movie that i like, so that at a later time I would remind myself of what the movie was about when considering to watch it | #15, #16, #18, #47 |
| As a user, I want to give each movie that a like a rating on a 1/10 scale (1.1, 1.2, ...,10), so that i know which movie is the best of the best | #15, #16, #18, #47 |
| As a user, I want to add a cast to a Movie, so that I can see which actors play in which movies | #22 |
| As a user, I want to be able to search among my movies, so that I can see if my list contains a specific movie | #22 |
| As a user, I want to search for movies that contain a specific actor, so that it is easy to find a movie to watch that a specific actor plays in | #22 |
| As a user, I want to see statistics like the mean rating of the movies, mean duration, and the actor that plays in most movies, so that I can get an overview of the characteristics of my list of movies | #55, #53 |

Issues based on requirements in `arbeidskrav` or project related issues:

| Requirement/description | Issue |
| --- | --- |
| Rest API/Local storage | #13, #31, #38, #52, #57 |
| Shippable Product | #58 |
| Spotbugs | #35, #56, #62 |
| Checkstyle | #35, #48, #54 |
| Documentation | #5, #6, #21, #23, #24, #29, #32, #33, #34, #40, #41, #49, #51, #55, #59, #60, #61, #63 |
| Project set up | #3, #8, #9, #20, #28, #30, #36, #62, #68 |
| User interface | #11, #17, #45, #43, #65, #66 |
| Ci | #12 |
| Testing | #37, #39, #67, #69 |

To read more about the implementations and how it is intended to use the mmt app, read [here](../mmt/README.md#my-movie-tracker).

## Modularization and architecture
The new modules added to the project is:
- [Rest](../mmt/rest/README.md)
- [Integrationtests](../mmt/integrationtests/README.md)

Which adds to the existing modules:
- [Core](../mmt/core/README.md#module---core)
- [Fxui](../mmt/fxui/README.md#module---fxui)

## Code quality 
To ensure even more code quality, we have updated our pipeline to also perform a formating check. This makes the pipeline use the `mvn prettier:check` command, and ensure that the project contains a good code formatting.

We have also continued with making the pipeline ensure that the project compiles, as well as that the core-tests runs without any failures.

## Documentation
As we have continued developing the project, some of the commands to run the project needs to be altered, as well as new ones are added. Therefore we have updated the readme files that describes how to use the application. We have also moved all of the documentation that describes how to run the project into one readme file, insted of having some in the root file and some in the mmt file. If you want to read more about how to run the project, read [here](../README.md#build-the-project). In the root readme file you can also read about our workflow, which we have written to inform future contributors and readers about how we work on our project.

For release 3, we have also decided to write documentation about the different modules, which can be read in the different modules README-files that are linked [above](#modularization-and-architecture).

## Testing
As we have done throughout the whole project, we have focused on maintaining a good degree of testing. For us, we have said that we want at least 80% testing degree in our jacoco reports. By obtaining this, we can ensure that the system works as intended, and not lead to any errors. 

We have added new tests to the new parts of the system. We have written tests that ensure that the statistics view performs correct calucation. We have also written tests for the rest api, endpoints and to ensure that the server can run.

If you want to view our jacoco reports, you can run the maven test command, described in the [root README](../README.md#testing-the-project), and follow the steps listed in there to view the report.

As we previously have said, making sure that our program is tested, is important for us. Therefore, we have made sure that all classes have a good line testing degree. Under, you can see a table displaying the line testing degree of the different classes. To view a picture of the jacoco report, check out the [images](../mmt/images/) folder. (core.png, fxui.png and rest.png)

| Class | Line testing degree |
| --- | --- |
| Movie | 92% |
| MovieList | 73% |
| Rating | 100% |
| Actor | 94% |
| MovieDeserializer | 96% |
| MovieListDeserializer | 100% |
| MovieListSerializer | 100% |
| MovieModule | 100% |
| MovieSerializer | 100% |
| RatingDeserializer | 100% |
| RatingSerializer | 100% |
| ActorSerializer | 100% |
| ActorDeserializer | 100% |
| MyMovieConfig | 83% |
| DisplayMovieController | 100% |
| EditMovieController | 95% |
| GiveRatingController | 100% |
| LocalMmtAccess | 79% |
| MyMovieTrackerController | 94% |
| RemoteMmtAccess | 88% |
| StatisticController | 99% |
| MmtService | 85% |
| MmtConfig | 100% |
| MmtModuleObjectMapperProvider | 100% |

## JSON-Storage Format
To get the full display on how each class is serialized, check out the [core module README file](../mmt/core/README.md#storing-format).

Underneath you can see the storage format for a movielist. The newly added component to milestone release 3, is the including of a cast list and a movie ID. The reason we have added a movie ID is to always be able to get the correct movie from the server when using endpoints.

MovieList:
```json
    {
        "movies" : [
            {
            "title" : "No Time to Die",
            "releaseDate" : "2021-10-01",
            "duration" : "02:43:00",
            "rating" : {
                "rating" : 10,
                "comment" : "This was a great movie, glad i got around to watch it!"
            },
            "watchlist" : true,
            "cast" : [
                {
                "name" : "Daniel Craig"
                },
                {
                "name" : "Rami Malek"
                },
                {
                "name" : "Lashana Lynch"
                },
                {
                "name" : "Léa Seydoux"
                }
            ],
            "id" : "e65b957e-6415-11ed-81ce-0242ac120002"
            }, 
            ...//And the list continues
        ]
    }
```
As you probably can see, as the size of the movielist grows, the data flow increases. As we first had implemented in our system, the complete movielist was always serialized and deserialized and displayed to the user. This leads to a huge amount of data flow, which can slow the system down. Therefore we have made som adjustments in the MMT controller, to better the data flow.

## API-Setup
In our API-Setup, we have created 4 base endpoints. 
- Get
    - Returns the complete movielist stored in the file.
- Put
    - Updates a movie in the movielist that is stored.
- Post
    - Adds a new movie to the file.
- Delete
    - Deletes a movie in the file.

As we first set up the api, we only had Get movielist, and put movielist, which ment that we had to always send huge amounts of data. This lead to a bad dataflow, as described earlier. We then removed the endpoint, put movielist, and instead created the three more shown above.

This is the reason we also included an ID to a movie. With this ID, we can search for movies in the server, and get the correct one. From there we can easily delete or update that movie.

You can read more about the [rest module](../mmt/rest/README.md) and the description of the [endpoints](../mmt/rest/API-Schema.md) should you find this interesting.