# Module - Rest
This is the module for the API and REST-server in this project

## Information
This module consists of two different packages, restapi and restserver, as described below.

### Package: restapi
This package defines the REST API, wich means what kind of HTTP requests the project supports. The endpoints, payloads and responses are shown in [Schema](API-Schema.md).

Classes:
- [MmtService.java](src/main/java/mmt/restapi/MmtService.java)

Methods:

- @GET
  - getMovieList() -> Gets the stored MovieList.
- @PUT
  - putMovie(String, Movie) -> Updates an alreadey existing Movie,
- @POST
  - addMovie(Movie) -> Adds a new Movie.
- @DELETE
  - deleteMovie(String) -> Deletes a Movie.

### Package: restserver
This package defines a configuration so that the REST API and the server are able to communicate.

The package contains these classes and methods:
- [MmtConfig.java](src/main/java/mmt/restserver/MmtConfig.java)
  - MmtConfig() -> Initilizes the class
- [MmtModuleObjectMapperProvider.java](src/main/java/mmt/restserver/MmtModuleObjectMapperProvider.java)
  - MmtModuleObjectMapperProvider() -> Initilizes the class
  - getContext(Class<?> arg0) -> returns the objectmapper of given class

## Picture of flow with rest:
![REST flow](../diagrams/%20REST-call-sequence-diagram.png)












