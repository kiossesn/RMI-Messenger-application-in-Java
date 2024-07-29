# RMI-Messenger-application-in-Java
This is a Direct Messaging application that follows the Remote Method Invocation (RMI) protocol.

[Εργασία Εξαμήνου Δίκτυα Επικοινωνιώv.pdf](https://github.com/user-attachments/files/16419704/v.pdf)

The application consists of four main classes:
<pre>
  MessagingServer
  MessagingClient
  Account
  Message
</pre>
And one interface:
<pre>
  MessengerInt  
</pre>
With its implementation:
<pre>
  RemoteMessenger
</pre>
 
In the **MessagingServer** class, the server operation is initiated.\
The server runs continuously and waits for requests from clients.\
It can serve multiple clients simultaneously.

In the **MessagingClient** class, the client-server connection is established, and the remote object is defined, through which the functions implemented in the interface are called.\
First, functions are defined in the MessagingClient class (one for each operation) through which the interface functions are called, and messages are printed.\
Additionally, arguments are assigned to variables, and the FN_ID is encoded with numbers.

In the **RemoteMessenger** class, the application operations are performed, and certain informative messages are printed on the server.
