# Weather application

### Prerequisites for getting started:
Writing the correct port, address, login and passwords to connect to the database in the application.properties file (PostgreSQL was used as the database)


### The technology, library and unique features used in the development of the program

I used JWT filter for spring security;

For taking informations about weather, I used [openweathermap](http://api.openweathermap.org/data/2.5/weather?q=Tashkent,uz&appid=2300d429630058871993058db910a620).org

Firstly we should add the regions or cities to Database. We will add it by ourselves. If this city is available in openweather, we can take informations about them.

Roles and permissions are given for users (default is entered as a normal user)

If anyone wants to use our application, he firstly should register himself with username and password. Then, he has to take a token. Then he can subscribe to cities for taking informations about them.

Steps for using urls in this link [link](https://api.postman.com/collections/25489668-dbfcd6a2-f239-4703-a422-080e73508100?access_key=PMAT-01GRV3K8CJT90X09AGWXEEZ6DE)

