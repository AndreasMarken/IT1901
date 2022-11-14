# Release 3
For the last sprint of the it1901 group assignment, we have focused on creating more logic and functionality to the user, as well as improving code quality, work flow and teamwork.

During this sprint we have focused on work flow containing pair programming and code review. We have written most of the code added throughout this sprint with pair-programming in a team of two. We have also tried to use the git-tag co-authored-by: @username, to give credit to both contributors.

With the use of pair programming, our code quality has improved because there is two sets of eyes watching, writing and accepting the code before it is commited. 

To also improve the code quality of the project, we have also used code-review in gitlab to give comments to eatch other, such that all code is verified and accepted by more contributors.

The logic we have focused on implementing to this sprint, has mainly been the implementation of a REST-API and an actor class.

The actor class has given movies another dimension, where one actor can be linked to multiple movies, and one movie can have multiple actors. In the mmt-app, you can now also search for movies containing certain actors or by movie name. By giving the user this possibility, the user can now find movies containing their favorite actor, or search for sequels to movies that you enjoy watching.

The implementation of the REST-API gives the user the possibility to either run the application on locally stored files or using a server. To make this possible we have created an interface named IAccess, which we have implemented in LocalMmtAccess and RemoteMmtAcces. This gives us two different implementations of the way mmt can be stored. You can read more about our API set up and base endpoints [here](../mmt/rest/README.md)

To read more about the implementations and how it is intended to use the mmt app, read [here](../mmt/README.md).

We have also made our product shippable, as the requirements in `arbeidskrav` states. To read more about the shippable configuration, read [here](../mmt/README.md#shippable-product---export-the-project)

We have also updated the diagrams we created for deliverable 2. To read more about the diagrams, check out the [diagrams](../mmt/README.md#diagrams) section in the mmt README file.

## Modularization and architecture
The new modules added to the project is:
- [Rest](../mmt/rest/README.md)
- [Integrationtests](../mmt/integrationtests/README.md)

## Code quality 
To ensure even more code quality, we have updated our pipeline to also perform a formating check. This makes the pipeline use the `mvn prettier:check` command, and ensure that the project contains a good code formatting.