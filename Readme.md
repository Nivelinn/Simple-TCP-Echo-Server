## TCP Echo Server

Implements a TCP Echo server: an application which accepts TCP connections, receives newline-terminated text messages and sends them back to clients.

The application keeps track of the number of connected clients and the number of messages exchanged with each.

Clients are able to request these stats by sending a specific command message, 'list' to the server.

TCP Echo server is listening on port 7773

Solution implements simple TCP client for testing.

## Command line build

### Server:

Being in projects source files directory issue:

javac TcpEchoServer.java

javac ConnectionHandler.java

### Test client:

Being in projects source files directory issue:

javac TcpEchoClient.java

## IntelliJ IDEA build

Create new project by cloning solution repository and build (both modules will be built)

## Run & Tests

### Server:

In the command line being in projects source files directory issue:
Remark: if build is done in IntelliJ IDEA launch directory is:  ..\out\production\Java-TCP-Echo-Server

java -cp . TcpEchoServer
(This will start TCP Echo server)

### Test client:

In the command line being in projects source files directory issue:
Remark: if build is done in IntelliJ IDEA launch directory is:  ..\out\production\Java-TCP-Echo-Server

java -cp . TcpEchoClient <Host name / IP address> 

Where: <Host name / IP address> are Server host IP or host name

Remark: To test multiple connected clients step can be repeated

## Unit Tests

Provide functional and load / stress test of the server

Remark: It needs IntelliJ IDEA project, done as mentioned

1) Launch server (form IDE or command line. The lates way to be used if server is deployed on a remote host)

2) Configure the following test unit parameters in class TcpEchoServerTest.java

SERVER_NAME – to set server host name or IP address, for example:  localhost

MESSAGE – to set message to be sent, for example: list

CLIENT_NUMBER – to set number of clients started simultaneously, for example 100

CREATE_TIMEOUT- to set timeout in milliseconds to create each of the clients, for example 1000 (1 second)

3) Run TcpEchoServerTest

4) After test done - terminate


## Environment tests are done

java 15.0.1 on Windows 10

