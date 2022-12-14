# Blockchain
## What is blockchain? 
> Blockchain is the modern and secure way to store data in the ledger, and every segment of data it’s called a block. Also,  Blockchain is a series of data blocks, where the validity of each succeeding block is dependent upon the previous block (and thus on all previous blocks).

## How the program work ? 
This application will ask the user to use three processes, and the program should not start processing the blocks and ledgers unless process number three is initialized. For instance, you should determine which processes you want to use after you run the program using the terminal.


## Using JSON
I used JSON ' JavaScript Object Notation' to store and interchange the data between processes [ 0, 1,2] to contribute to saving or updating the ledger and verifying and adding data. Thus, the Blockchain.java program will create a JSON file to store all verified blocks on it. 

## 1. Name / Date:
Name : Fahad Al Summan.
Date: 25 / 10 / 2022
## 2. Java version used :
18.0.2


## 3. Precise command-line compilation examples / instructions:
e.g.:
> javac -cp "gson-2.8.2.jar" BlockChain.java
> javac -cp "gson-2.8.2.jar" BlockChain.java


## 4. Precise examples / instructions to run this program:

You should run the programs in different terminal(MacOS) or CMD(Windows).

Note: I use Linux OS
> java -cp ".;gson-2.8.2.jar" BlockChain 0
<br>
> java -cp ".;gson-2.8.2.jar" BlockChain 1
</br>
> java -cp ".;gson-2.8.2.jar" BlockChain 2

## 5. List of files needed for running the program.

e.g.:

 a. checklist.html
 b. BlockChain.java
 c. BlockInput0.txt
 d. BlockInput1.txt
 e. BlockInput2.txt
 f. gson-2.8.2.jar
 g. BlockchainLedgerSample.json

## 5. Notes:
    When BlockChain 0, BlockChain 1, BlockChain 2 start, the program will start from many servers [Main server]-> that responsilbe for lanshing all other server.
    Then, the program will send the Public keys which were generated to all nodes -> 4710, 4711, and 4712. <br>
    Next, the program store the genius block to the ledger with fake data => NOTE: the data that I used my name and my information.
    Next, the program will read all three files BlockInput0,BlockInput1, and BlockInput2, and the program will extract all information.
    Then, the program will multicast unverified blocks -> that extracted recently from txt files |> 4810, 4811,4812.


## 6. Reference that help me in this assignment:
    - Professor Elliot's utilities codes. Thank you, prof, for your helpful utility code.
    - https://www.youtube.com/watch?v=igedHOpSqu4&t=756s [Professor Sluiter]
    - https://www.youtube.com/watch?v=ea3miXs_P6Y
    - https://www.javatpoint.com/blockchain-java.
    - https://www.quickprogrammingtips.com/java/how-to-generate-sha256-hash-in-java.html [SHA-256]


