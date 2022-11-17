# API Schema

This is a document to show how the REST API works toghether with the server. 

## Get MovieList

Type: <span style="color:#28b463">GET</span>

Endpoint: `mmt`

Response: 

```json
{
    "movies": [
        {
            "title": "Test Title",
            "releaseDate": "2022-10-06",
            "duration": "02:32:00",
            "rating": 9,
            "watchlist": true,
            "cast": [
                {
                    "name": "Ryan Gosling"
                }
            ],
            "ID": "280c0322-d8e2-49eb-8ab0-a8a86f54787e"
        },
        {
            "title": "Test Movie 2",
            "releaseDate": "2022-10-06",
            "duration": "02:30:00",
            "rating": null,
            "watchlist": false,
            "cast": [
                {
                    "name": "Dwayne Johnson"
                },
                {
                    "name": "Mark Clerk"
                }
            ],
            "ID": "e2698381-98f0-4c1d-ba6d-a9aa0d774cca"
        }
    ]
}
```

## Update Movie

Type: <span style="color:#28b463">PUT</span>

Endpoint: `mmt/{oldMovieID}`

Payload:

```json
{
    "title": "Test Title",
    "releaseDate": "2022-10-06",
    "duration": "03:32:00",
    "rating": null,
    "watchlist": true,
    "cast": [
      {
        "name": "Ryan Gosling"
      }
    ],
    "ID": "280c0322-d8e2-49eb-8ab0-a8a86f54787e"
}
```

Response: `boolean`


## Add new Movie

Type: <span style="color:#28b463">POST</span>

Endpoint: `mmt`

Payload:

```json
{
    "title": "Test Title",
    "releaseDate": "2022-10-06",
    "duration": "03:32:00",
    "rating": null,
    "watchlist": true,
    "cast": [
      {
        "name": "Ryan Gosling"
      }
    ],
    "ID": "280c0322-d8e2-49eb-8ab0-a8a86f54787e"
}
```

Response: `boolean`


## Delete Movie

Type: <span style="color:#28b463">DELETE</span>

Endpoint: `mmt/{movieID}`

Payload: 

```json
    {
    "title": "Test Title",
    "releaseDate": "2022-10-06",
    "duration": "03:32:00",
    "rating": null,
    "watchlist": true,
    "cast": [
      {
        "name": "Ryan Gosling"
      }
    ],
    "ID": "280c0322-d8e2-49eb-8ab0-a8a86f54787e"
}
```

Response: `boolean`