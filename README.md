**This project is my course project from the third year of university on "programming of network applications" subject.**

The application is a system for managing the workflow of a cinema, or a system build for selling tickets specifically. It is very similar to the one I made in my second year, but it was highly improved to have a more user-friendly interface, and also some new updated features. The software features include:
- management of movie billboard, through which it is possible to purchase tickets;
  - purchasing tickets envolves generation of a receipt in the specific folder;
- storage of information about cinemas, movies, movie shows as well as tickets for them, and also reviews for movies in the database;
  - _user_ with normal role can view the billboard and buy tickets for the showingsm as well as viewing reviews of other users on movies and writing their own reviews that would affect the rating of the movie;
  - _content manager_ could do everything an usual user could, as well as managing data about movies stored in the database;
  - _showing manger_ could do everything an usual user could, as well as managing data about movie showings in the database and tickets for them;
  - _administrator_ could do everything listed above, as well as managing list of users (confirming users, changing their roles, blocking and unblocking) and list of reviews, deleting them and blocking the users if needed, the basic administrator login and password is admin - admin;
- stored passwords are hashed using the SHA-256 algorithm.


The software is developed in Java as javaFX application. The data is stored in the database using the MySQL language, it is created as 'cinemacourseproject'

The developed software application works according to the principle of "client-server" with usage of the TCP/IP protocol. That is, there are two applications: client and server, which differ from each other in their functionality. The server connects to the database and maintains business logic algorithms, while the user interacts with the server through a client program via a graphical shell.

The program implemented its own class hierarchy and implemented several design patterns (singletone and factory method). Also the data concealment was used, as well as overload and definition methods, serialization, abstract data types, static methods and processing of exceptional situations. Generally speaking, the tools and potential of the Java programming language were used as much as possible. Maven utility was used as project collector.

In addition, the program has been tested not only by manual testing but also by automatic testing using the junit.jupiter and mockito libraries.

In the long run, this program can be expanded by adding new functionality and expanding the old one. For example: adding the ability for content managers to download covers for movies and trailers, or adding more parameters for movies (such as genres).

<br/>

Some screenshots of work:

- Main menu of the application
  <img width="595" height="423" alt="Image" src="https://github.com/user-attachments/assets/3daf973e-a7f8-4283-9049-9aa1ca5987bd" />

- Registration window
  <img width="597" height="442" alt="Image" src="https://github.com/user-attachments/assets/dde70670-73ac-4715-ade8-d2a0fe774d97" />
