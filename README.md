# Fix-Me  

A simulation tool for the financial market that exchanges a simplified version of FIX (The **Financial Information eXchange** (**FIX**) protocol is an electronic [communications protocol](https://en.wikipedia.org/wiki/Communications_protocol "Communications protocol") initiated in 1992 for international real-time exchange of information related to the [securities](https://en.wikipedia.org/wiki/Security_(finance) "Security (finance)") transactions and markets.) messages. The focus isn't on the trading algos but the implementation of a robust and performant messaging. Future updates will be made to improve the trading algorithms.

## Compile
To compile in cmd  
* Run:  
   -> mvn clean package 
From the root directory   

##  Run
To run modules in cmd  
* Run:  
   ->  
From the root directory 

## References

### [Oracle's Java SE 7 Documentation](https://docs.oracle.com/javase/7/docs/api/)  
e.g. https://docs.oracle.com/javase/7/docs/api/java/io/BufferedReader.html

### [Oracle's Java SE 7 Tutorials](https://docs.oracle.com/javase/tutorial/essential/)  
e.g. https://docs.oracle.com/javase/tutorial/essential/exceptions/finally.html

### [A pretty good programming resource](http://www.java2s.com/)
e.g. http://www.java2s.com/Code/JavaAPI/java.io/BufferedInputStream.htm

### [Intellij's Swing Gui Documetation](https://www.jetbrains.com/help/idea/creating-form-initialization-code.html)
e.g. https://www.jetbrains.com/help/idea/creating-form-initialization-code.html

### [Making a swing project using IntelliJ IDEA GUI builder with maven, Including executable jar](http://glxn.net/2010/08/17/making-a-swing-project-using-intellij-idea-and-gui-builder-with-maven-including-executable-jar)
e.g. http://glxn.net/2010/08/17/making-a-swing-project-using-intellij-idea-and-gui-builder-with-maven-including-executable-jar

### [Java 9 Modules (Part 2): IntelliJ and Maven](https://dzone.com/articles/java-9-modules-part-2-intellij-and-maven)
e.g. https://dzone.com/articles/java-9-modules-part-2-intellij-and-maven

### [Financial Information eXchange (FIX)](https://en.wikipedia.org/wiki/Financial_Information_eXchange)
e.g.https://en.wikipedia.org/wiki/Financial_Information_eXchange 

### Links  
*  https://docs.oracle.com/javase/7/docs/api/java/io/BufferedReader.html  
*   https://docs.oracle.com/javase/tutorial/essential/exceptions/finally.html  
*   https://docs.oracle.com/javase/tutorial/java/data/manipstrings.html  
*   http://www.java2s.com/Code/JavaAPI/java.util/foreachloopforArrayList.htm  
*   https://www.geeksforgeeks.org/arrays-in-java/  
*   https://www.tutorialspoint.com/java/java_strings.htm  
*   https://www.tutorialspoint.com/java/java_inheritance.htm  
*   https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html
*   https://en.wikipedia.org/wiki/Class_diagram  
*   https://www.youtube.com/watch?v=wiQdrH2YpT4 Observer Design Pattern  
*   https://www.youtube.com/watch?v=ohL2HIBK1pg Observer Design Pattern  
*   https://www.youtube.com/watch?v=ub0DXaeV6hA&t=4s Factory Design Pattern  
*   https://www.youtube.com/watch?v=xbjAsdAK4xQ Abstract Factory Design Pattern  
*   https://www.jetbrains.com/help/idea/creating-form-initialization-code.html
*   http://glxn.net/2010/08/17/making-a-swing-project-using-intellij-idea-and-gui-builder-with-maven-including-executable-jar  
*    https://en.wikipedia.org/wiki/Financial_Information_eXchange  
* https://dzone.com/articles/java-9-modules-part-2-intellij-and-maven 

## Mandatory part  
You need to implement a simulation tools for the financial markets that exchange a simplified version of FIX messages. The tools will be able to communicate over a network using the TCP protocol. The focus in this project are not the trading algos. 
It has 3 independent components that will communicate over the network:
* A market component.
* A broker component.
* A message router.
Some key points need to be met
* Use non blocking sockets.
* Use the java executor framework for message handling.
* Multi-module Maven build.

## V.1 Router  
The router is the central component of your applications. All other components connect to it in order to send messages to other components. The router will perform no business logic, it will just dispatch messages to the destination component(s). We call the router a market connectivity provider, because it allows brokers to send . messages (in FIX format) to markets, without depending in specific implementation of the market.
 The Router will listen on 2 ports: 
 * Port 5000 for messages from the Broker components. When a Broker establishes the connection the router assigns it a unique 6 digit ID and communicates the ID to the broker. 
 * Port 5001 for messages from Market components. When a Market establish the connection the Router assigns it a unique 6 digit ID and communicates the ID to the Market. 
Brokers and Markets will include the assigned ID in all messages for identification the and the Router will use the ID to create the routing table. 
Once the router receives a message based on the checksum.
* Validate the message based on the checksum.
* Identify the destination in the routing table.
* Forward the message.
 
## V.2 Broker 
The Broker will send two types of messages: 
* Buy - An order where the broker want to sell an instrument.
* Sell - An order where the broker want to sell an instrument 
and will receive from the market messages of the following types: 
* Exeuted - when the order was accepted by the market and the action succeeded.
* Rejected - when the order could not be met

## V.3 Market  
A market has a list of instruments that can be traded. When orders are received from the brokers the market tries to execute it. If the execution is successful, it updates the internal instrumental list and sends the broker an Executed message. If the order can't be met, the market sends a Rejected message.
The rules by which a market executes orders can be complex and you can play with them. This is why you build the simulator. Some simple rules that you need to respect is that an order can't executed if the instrument is traded on the market or if the demanded quantity is available (in case Buy orders). 

## V.4 FIX Messages
All messages will respect the FIX notation. 
 
### Notation
Header+Body+Trailer : FIX Content

Example of a FIX message : Execution Report (Pipe character is used to represent  [SOH](https://en.wikipedia.org/wiki/C0_and_C1_control_codes#SOH "C0 and C1 control codes")  character)

```
8=FIX.4.2 | 9=176 | 35=8 | 49=PHLX | 56=PERS | 52=20071123-05:30:00.000 | 11=ATOMNOCCC9990900 | 20=3 | 150=E | 39=E | 55=MSFT | 167=CS | 54=1 | 38=15 | 40=2 | 44=15 | 58=PHLX EQUITY TESTING | 59=0 | 47=C | 32=0 | 31=0 | 151=15 | 14=0 | 6=0 | 10=128 | 
```
Buys and Sell messages will have the following mandatory fields
• Instrument  
• Quantity   
• Market  
• Price

## Bonus Part
* You store all transactions in a database
* You conceive a fail-over mechanism so that ongoing transactions are restored in case one component goes down.
