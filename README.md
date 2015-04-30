fiebdc3-parser 	![alt text](https://travis-ci.org/DISID/fiebdc3-parser.svg?branch=master "Build status")
==============

Java parser for the FIEBDC 3/2004 file format (www.fiebdc.org) 

It provides support to load files in the format set in 2004. Take into account this is an old format, which has been updated many times (1995, 2002, 2004, 2012, ...), and each time new fields or subfields have been added.

Also only the main register types have been implemented:

* V
* K (ignored)
* C
* D
* T
* M

Also some fields into each register are ignored, as our main initial objetive is to import the .bc3 files generated with the current version of the Arquimedes application, and only the minimal needed data.

Installation
---------------

The project is divided in two modules: *fiebdc3.api* and *fiebdc3.impl*. The first one is the API, and the second one is the ANTLR4 based implementation.

To compile from source just checkout the root folder project and perform a *mvn install*.

To use in your own project, just add the following dependencies to your *pom.xml* *TODO: upload to gvNIX maven repo and include here*:

```xml
		<dependency>
			<groupId>com.disid</groupId>
			<artifactId>fiebdc3.api</artifactId>
			<version>1.0.0-SNAPSHOT</version>
			<scope>compile</scope>
		</dependency>

		<dependency>
			<groupId>com.disid</groupId>
			<artifactId>fiebdc3.impl</artifactId>
			<version>1.0.0-SNAPSHOT</version>
			<scope>compile</scope>
		</dependency>
```

You can change the scope of the implementation to *runtime* if you don't instantiate the implementation in your code.


Usage
------

The API entry point is the *Fiebdc3Service*. Ex:

```java
File testFile = new File("test.bc3");
Reader testReader = new FileReader(testFile);
Fiebdc3Service service = new AntlrFiebdc3Service();
Database database = service.parse(testReader);
```

If you want to be independent from the implementation in your code, you can use an IOC library like SpringFramework, and use the configuration files to instantiate the service implementation (*AntlrFiebdc3Service*).

The Database object will contain all the information loaded from the .bc3 file, with the following structure:

```
database
'-> root concept
    |-> concept 1
    |   '-> measurement
    |       |-> line 1
    |       |-> line 2
    |       ...
    |       '-> line n 
    |-> concept 2
    ...
    '-> concept n
```
    
Take a look at the Javadoc of the parser API for more information. 

Javadoc and other technical reports are available in the project's maven site: http://disid.github.io/fiebdc3-parser/sites/0.1.0-SNAPSHOT/

