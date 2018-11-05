# **Stack Calculator**
---
Implementation of a stack calculator via HTTP endpoints. The server end is capable of storing multiple stack calculators. Each of the calculators has its unique ID. The clients can interact with the service by making API calls which are defined in the section Functionalities. 

## Getting Started
---
In order to  use or extend the API, you need to set up a server. For our example, we will set up a server in Eclipse. This way, the project can both be edited, ran and tested. This can be done by following these steps:
1. **Set up Eclipse EE**
Download and install [Eclipse EE](http://www.eclipse.org/downloads/packages/release/kepler/sr2/eclipse-ide-java-ee-developer);

2. **Set up Tomcat**
Download and install [Tomcat](https://tomcat.apache.org/download-80.cgi). When prompted, set the following: username: Admin, password: Admin;

3. **Obtain project**
Pull the project from GitHub and import it in Eclipse as an existing Maven project;

4. **Add dependencies to the build path**
In the project explorer navigate to StackCalculator -> WebContent -> WEB-INF -> lib -> select all of the .jar files in the folder -> right click on them -> Build Path -> Add to Build Path;
5. **Configure the Runtime Environment**
Window -> Preferences -> Server -> Runtime Environment -> Add -> Select Apache Tomcat 8.5 -> Next -> Select the path to your Tomcat installation -> Finish;
6. **Set up the Tomcat Server**
File -> New -> Other -> Select server from the list -> Next -> Select Apache -> Apache 8.5 -> Select your Tomcat installation directory -> Finish;
7. **Run the project**
Run the project as a server and select the Tomcat 8.5 server;
8. **Set up Postman**
In order to make the API requests, you can download and install [Postman](https://www.getpostman.com/);
9. **Make a request using Postman**
Open postman, and type in the appropriate HTTP, select the corresponding HTTP method and click send!

If set up to the default settings, the Server for the RESTful Service will have the following base path:
&emsp;&emsp;&emsp;`http://localhost:8080/StackCalculator`

**__All the paths specified below should be appended to this base URL.__**

In case that you have selected a different port, make adequate changes to the base URL, while keeping the general structure intact. This would basically mean changing the `8080` to your server's port number.

## Functionalities
---
The possible actions to perform on this stack calculator are defined here. They are executed by appending the path stated for each of the actions to the base URL of the server.  

The `:id` argument is an integer, and represents the ID of the stack calculator in question. Furthermore, each action will only be executed on the stack calculator with the corresponding ID, labeled in the examples as _stack[]_.

* **Peek**
_HTTP Method:_ **GET**
The peek operation will return the stack[top] of the calculator. In case that the stack is empty, this request will produce an error.
&emsp;&emsp; `/calc/:id/peek`

* **Push**
_HTTP Method:_ **PUT**
The push operation pushes the integer number <n> onto the top of the stack of the calculator. If the calculator does not exist, it will be created and <n> pushed on top of it.
&emsp;&emsp; `/calc/:id/push/<n>`

* **Pop**
_HTTP Method:_ **PUT**
The pop operation returns the top stack[top] of the calculator, and removes it from the calculator. In case that the stack is empty, this request will produce an error.
&emsp;&emsp; `/calc/:id/pop`

* **Add**
_HTTP Method:_ **PUT**
The add operation will remove the stack[top] and stack[top-1] elements of the calculator, and will replace them with stack[top-1] + stack[top]. In the case that there are less than two elements on the stack, this request will produce an error.
&emsp;&emsp; `/calc/:id/add`

* **Subtract**
_HTTP Method:_ **PUT**
The subtract operation will remove the stack[top] and stack[top-1] elements of the calculator, and will replace them with stack[top-1] - stack[top]. In the case that there are less than two elements on the stack, this request will produce an error.
&emsp;&emsp; `/calc/:id/subtract`

* **Multiply**
_HTTP Method:_ **PUT**
The multiply operation will remove the stack[top] and stack[top-1] elements of the calculator, and will replace them with stack[top-1] * stack[top]. In the case that there are less than two elements on the stack, this request will produce an error.
&emsp;&emsp; `/calc/:id/multiply`

* **Divide**
_HTTP Method:_ **PUT**
The divide operation will remove the stack[top] and stack[top-1] elements of the calculator, and will replace them with stack[top-1] / stack[top]. Please note that this is **integer division**. In the case that there are less than two elements on the stack, or that the stack[top] is zero (division by zero), this request will produce an error.
&emsp;&emsp; `/calc/:id/divide`

* **Clear**
_HTTP Method:_ **DELETE**
The clear operation will remove **all** of the calculators from the server. It functions as a reset button.
&emsp;&emsp; `/calc/:id/clear`

## Response
---
In case that the right path is entered, and that it correctly contains the structure up to, and including `/calc`, the server will provide relevant feedback. Otherwise, the adequate generic HttpURLConnection response will be given.

##### Response Format
---
All of the stated API requests return a Response structured in a same way. It is a JSON object with the following structure:

&emsp;&emsp;{
&emsp;&emsp;&emsp;&emsp;"responseMessage": message,
&emsp;&emsp;&emsp;&emsp;"statusCode": code
&emsp;&emsp;}

It is worth mentioning that the value of `message` can be parsed as a **String**, while the value of `code` can be parsed as an **Integer**.

This approach was adapted since it captured the most important information. The message addresses the user, and informs him sufficiently about the outcome, while the status code provides the status of the API request. Moreover, it is easy to communicate the already written message to the user, and although the status code of the API call is included in the generic response in which the JSON is wrapped, having it in the JSON proved useful (e.g. for assertions or other tests).

##### Status Codes
---
The RESTful service will encapsulate the following response codes in the Response, in the given situations:
* **200** - If everything is well and the action was performed successfully;
* **201** - If the creation of the calculator with the new id finished successfully.
* **403** - If the action defined in the call was is allowed (e.g. division by 0);
* **404** - If there are any errors in the path of the request, or if the request defined the action to make use of a nonexistent resource (e.g. peek on an empty stack calculator, divide with less than 2 elements on the stack);
* **405** - If the path of the request is correct, but the HTTP method used for the call is wrong (e.g. making a call to use the push function, but using a DELETE HTTP method).
* **500** - If there is an error which was unforeseen, the service returns the internal server error status code.

## Future development
---
The stack calculator, although a simple service, has a lot of room for expansion. The basic functionalities are there, but implementing additional features is always possible. Some of the ideas for the future expansion are as follows:

* **More Complex Operations** - Adding an expansion towards implementing more complex arithmetic operations (e.g. square root, exponentiation, percentages, modulo, etc.) would make the service more appealing and would make it more useful.
* **Floats Support** - Would provide more precision, and on top of that would stop the service being bound to integer division.
* **Undo / Redo Option** - Useful in case of a wrong API call.
* **Deleting a single Stack Calculator** - The possibility to delete a single calculator and not everything. This was not implemented since no use cases needed it, but might be useful in the future.
* **Adding Response Descriptiveness** - Depending on the use cases in the future, it might be the case that the response needs to be adjusted. For example, for easier parsing. In this case, there is an option to add another key-value pair to the response. It would bear only the result in the form of an integer or a float. This would make the parsing completely easy.
* **A Simple GUI** - For the demonstration purposes, or for the sake of saving time needed to set up a server, an adequate user interface could be set up as a front end. It would then be connected to this service, which it would serve a back end.
* **Moving Calculators to a new ID** - Option which might prove useful for some use cases. For example in the case that the ID of each calculator corresponds to an ID of some entity in a real-world system. In that case, if the entity is assigned a new ID in the real-world system, its calculator (for e.g. calculating the accomplished tasks or productiveness) can be moved to that ID as well.
* **Loops** - Powerful and useful tool in the case when something needs to be done multiple times. Although most of the users will have the ability to make multiple API calls from within a loop, that would create a massive amount of requests coming to the server. This way, the task of repeating the action is delegated to the server, and only one API request is needed.

## Additional Information
---
The project was done in Java 8 (1.8.0_144).

All of the code has been covered with the appropriate unit and integration JUnit tests. JUnit was picked since it seems like the most natural option for testing Java code. Moreover, since the test code is pretty straight-forward, there is no need for full on javadoc style comments.