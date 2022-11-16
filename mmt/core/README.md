# Module - Core
This is the core module of the mmt project. This is the module where all backend configuration and calculation takes place. In this module you can create movies, actors, movielists and store everything using json serializers and deserializers.

## The different packages
In this part, you can read about the different packages in the core module. The modules consists of the two following packages: core & json.

### Core - core
The core package consists of the main part of the project files. In here you can create the movielist that contains all the different movies. The movies that you add to the movielist will also be created from this package. The different ratings that the users give to a movie, will also be created here and linked to a movie.

This package consist of three different interfaces: IActor, IMovie and IRating. All of these are implemented in classes in this package.

There is also a comparator class that will be used when the user wants to sort movies based on different purposes.

### Core - json
The json package consists of the different serializers and deserializers for the core-package classes. In here you can serialiaze and deserialize Actors, Movies, Ratings and Movielists. This package is used as the main part of storage for this project, and it is these json serializers and deserializers that will be used.

The different serializers and deserializers is combined into the MovieModule which extends SimpleModule. Something that comes with the jackson-databind dependecy that is used for registration of serializers and deserializers.

### Storing format
Underneath is a description on how we serialize our different objects. This is the way we store our objects both localy and to the server.

Actor:
```json
 {
    "name" : "Daniel Craig"
 }
```

Rating:
```json
 {
    "rating" : 10,
    "comment" : "This was a great movie, glad i got around to watch it!"
 }
```

Movie:
```json
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
 }
```

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

## Why is core and json combined
In the architecture of the project we have choosen to keep core and json combined in the same module. There are plenty of reasons to why we have felt that this is the correct architecture of the project. Here we will give a list of the main reasons:

- The Core- and Json-package are linked together in the way that json uses the classes in the core package.
- By splitting the packages into different modules would lead to more dependencies than needed for something that is very linked together.
- The json package creates core objects.
- The json package writes core objects to string, and only core objects. It does not use any other modules classes.
- If the json folder was to write e.g the mmt-controller to a string, it would be convenient to have the json folder as its own module. However since we do not use this, we felt that keeping the json-package in the core module was the correct thing to do.
