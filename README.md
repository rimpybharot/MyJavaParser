# 202-kanban
This is the readme for the personal project for cmpe 202 sjsu , msse spring 2017.
The idea is to create a uml parser which will take in the java code and outputs the uml diagrams for it.


We will be using Maven project in eclipse so that we can get the maven dependencies.
For uml diagrams we wil be using plantuml, and the idea is to extract the notation from the files , put it in a text and then call plantuml method on it to generate the diagrams. We need an external software for the dot.exe for generating diagrams other than sequence diagrams in plantuml.

The following steps are for ubuntu machine.

PREREQS:
1. java should be installed on your machine
2. JAVA_HOME should be set in environment variables
3. GRAPHVIZ to be installed prior, check for graphviz installation using graphViz -V command
4. maven to be preinstalled


Steps:
1. Download zip file from this repository to your local machine
2. Extract the contents and 'cd' to the folder, at the location where POM.xml file is present
3. run "mvn clean compile assembly:single install"
4. build should be successful, otherwise check the errors to correct settings in your environment (system ), jar is created at target\umparser.jar
5. run command "java -jar target\umlparser.jar <input> folder", output file name will be displayed on terminal
6. run "eog <output filename>" to open the output png file.
