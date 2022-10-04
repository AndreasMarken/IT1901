# Userstories

- As a user, I want to add movies to a list which contains movies that I find interesting, so that when i want to watch a movie I know which ones i like.

- As a user, I want to be able to add movies that I like to a watchlist that contains movies that I am due to watch, so that I know which movie I should watch when the weekend comes

- As a user, I want to remove movies from a list which contains movies that I like, in case i do not like them anymore

- As a user, I want to sort my list of movies that I like based on duration, so that if a friend asks me for a good movie that lasts less than an hour

- As a user, I want to sort my list of movies that I like based on the title, so that when I have a lot of movies in my list, it is easy to find the one I am looking for

- As a user, I want to sort my list of movies that I like based on rating, in case someone asks me what my all-time favorite movie is, it is easy for me to figure out which.

- As a user, I want to have a list of movies that I find interesting available, so that I have suggestions when being asked for a recommendation

- As a user, I want to write a short summary of each movie that i like, so that at a later time I would remind myself of what the movie was about when considering to watch it

- As a user, i want to give each movie that a like a rating on a 1/10 scale (1.1, 1.2, ...,10), so that i know which movie is the best of the best

# Conceptual model

## Concepts

### Design metaphor
- A Movie-object has a rectangular shape, just like a movie poster, with its attributes displayed on the front of this "poster" similar to a typical movie poster layout. 


### Data-objects 
- Movie-objects that can be initiated and deleted
- A "database" list-object which contains all Movie-objects that the user initiates
- A "watchlist" list-object which is a sublist of the "database" list-object, in which the user can add and remove Movie-objects

### Data-object attributes and operations

#### Movie-object attributes
- Title
- Release date
- Duration
- Rating (1, 1.1, 1.2, ..., 10)

#### Movie-object operations
- set and edit the title
- set and edit the release date
- set and edit the duration
- set and edit the rating
 
#### List-object attributes
- A list consisting of Movie-objects, initially zero

#### List-object operations
- Sort the Movie-objects contained in the list based on rating, title and duration
- Add Movie-objects
- Remove Movie-objects

### Relations
- The "database" list-object contains all the Movie-objects initiated by the user
- The "watchlist" list-object is a sublist of the "database" list-object
- The Movie-object has a title, release date, duration and rating

### Mapping
- The list-objects corresponds to a library of movies
- A Movie-object corresponds to an existing movie that the user has added some details to

# Personas

## Live Langåsen
![Image was not shown](images-userstories/Live.jpeg)

### Description

Live works as a doctor at the St. Olavs hospital. Her days are usually quite busy. When she is not working, she loves spending quality time with her husband and two young children. They often watch a movie together. If it is a good movie, Live would like to recommend it for her friends, her colleagues and her patients. In Lives opinion, movies are always a great subject to talk about to get the conversation going. When talking about movies with others, she also often recive recommendations. Sadly, due to busy days of work, she often forgets the movie she was recommended. 

### Familiarity with software applications

:large_blue_circle: :large_blue_circle: :large_blue_circle: :white_circle: :white_circle:

(3/5 - avrage knowledge)

### Goals
- Keep track of movies by removing and adding movies
- Able to categorize movies
- For each movie, add her own review of it
- Able to distinguish the movies she has and has not seen (in case reciving a recommendation, and want to watch it later) 
- An app that is easy and fast to use- as few clicks as possible!

### Frustrations
- Having to retain knowledge of each movie she watches
- When reciving a movie recommendation, it is easy to forget
- software apps that are to complex and has unnecessary functions
 

 ### Interests

 :large_blue_circle: :large_blue_circle: :white_circle: :white_circle: :white_circle:  &nbsp;  **Tech**

 :large_blue_circle: :large_blue_circle: :large_blue_circle: :large_blue_circle: :white_circle:  &nbsp;  **Science**

 :large_blue_circle: :large_blue_circle: :large_blue_circle: :white_circle: :white_circle:  &nbsp;  **Politics**

 :large_blue_circle: :large_blue_circle: :white_circle: :white_circle: :white_circle:  &nbsp;  **Business**


### Usability priorities


&nbsp; &nbsp; &nbsp; <img src="https://raw.githubusercontent.com/FortAwesome/Font-Awesome/6.x/svgs/regular/hourglass.svg" width="50" height="50">
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src="https://raw.githubusercontent.com/FortAwesome/Font-Awesome/6.x/svgs/solid/mobile-screen-button.svg" width="50" height="50">
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src="https://raw.githubusercontent.com/FortAwesome/Font-Awesome/6.x/svgs/solid/map-location-dot.svg" width="50" height="50">


**Time efficient** &nbsp; &nbsp; **Accessible** &nbsp; &nbsp; **Easy to navigate**


### Data priorities


&nbsp; &nbsp; &nbsp; <img src="https://raw.githubusercontent.com/FortAwesome/Font-Awesome/6.x/svgs/solid/database.svg" width="50" height="50">
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src="https://raw.githubusercontent.com/FortAwesome/Font-Awesome/6.x/svgs/solid/download.svg" width="50" height="50">
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src="https://raw.githubusercontent.com/FortAwesome/Font-Awesome/6.x/svgs/solid/circle-info.svg" width="50" height="50">




**Good metadata** &nbsp; &nbsp; **Save and load data** &nbsp; &nbsp; **Human readable stored data (JSON)**

### Motivations

![50%](https://progress-bar.dev/70?title=Incentive)

![50%](https://progress-bar.dev/40?title=Fear)

![50%](https://progress-bar.dev/90?title=Achievement )

![50%](https://progress-bar.dev/80?title=Power)

![50%](https://progress-bar.dev/90?title=Social)

![50%](https://progress-bar.dev/80?title=Goal)







