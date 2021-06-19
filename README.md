--------------------------------
Coda Global Assignment
--------------------------------

1. Pre requisites:
-----------------
* Java 8
* Maven 
* Postgresql

2. Steps:
---------------
* Clone Respository
* Checkout to master branch of this repository
* Pull Code

3. Running Code
-----------------
* create a db in ur postgresql with name ***insta***
* move to coda global folder
* Run:
  --> mvn clean install && mvn ninja:run
 * After successfully started go to browser and hit http://localhost:8080/
    It will display Hello Coda Global.
    
    
4. Note:
---------------
* If any error related to hibernate_sequence then run the following command in **insta** db of postgresql
  ***CREATE SEQUENCE hibernate_sequence START 1;***
