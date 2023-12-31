# Project Description

This project is a music streaming application that allows users to listen to their favorite music anywhere, anytime. It provides a wide range of music genres and artists, allowing users to explore and discover new music.

# Benefits

The project is useful for music lovers who want access to a vast library of songs without the need to purchase or download them. It provides seamless music listening experience.

# Getting Started

To get started with the project, users need to download and install the music streaming application on their preferred device. After that, they can sign up for an account or log in if they already have one. Once logged in, they can start exploring the music library, searching for their favorite songs, artists, or genres. They can create and manage playlists, follow their favorite artists, and discover new music through personalized recommendations.

# Getting Help

If users encounter any issues or need help with the project, they can refer to the documentation included with the application. Additionally, there is a help section within the application itself, where users can find answers to frequently asked questions and get assistance with common problems. They can also reach out to the support team through a contact form available on the application's website.

# Maintenance and Contributions

This project is actively maintained and regularly updated by a team of dedicated developers. Bug fixes, feature enhancements, and security updates are part of the ongoing commitment to providing the best possible music streaming experience. Users can also contribute to the project by reporting any bugs they encounter or suggesting new features through the project's official GitHub repository. The development team welcomes community feedback and contributions to make the project even better.

# Model in application
User, Artist, Song

# Controllers
### UserController "/user"
has CRUD operations:     
GET: getUsers,    
POST: createUser,     
PUT: updateUser,    
DELETE(id): deleteUser     
and some others: GET: getUserByLastName /last/{lastName}; getUserByFirstName /first/{firstname}; getUser /{id}   

### SongController "/song"
has CRUD operations:   
GET: getSongs,    
POST: createSong, (you should add filename of the MP3 file)    
PUT: updateSong,    DELETE(id): deleteSong    
and some others: GET: getSongByName /song_name/{songName}

### ArtistController
has CRUD operations: 
GET: getArtist,   
POST: createArtist,    
PUT: updateArtist,   
DELETE(id): deleteArtist /id 

# Registration with postman
1. /registration
2. {   
   "firstName": "string",   
   "lastName": "string",  
   "userLogin": "string",  
   "userPass": "string"  
   }
3. registration

# To authorize with postman

1. /authorisation
2. {   
   "login": "string",   
   "password": "string"   
   }
3. get token
4. do a request with token

