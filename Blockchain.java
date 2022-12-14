
/*--------------------------------------------------------
1. Name / Date:
Student Name : Fahad Al Summan.
Date: 25 / 10 / 2022
2. Java version used :
18.0.2


3. Precise command-line compilation examples / instructions:
e.g.:
> javac -cp "gson-2.8.2.jar" BlockChain.java
> javac -cp "gson-2.8.2.jar" BlockChain.java


4. Precise examples / instructions to run this program:

You should run the programs in different terminal(MacOS) or CMD(Windows).

Note: I use Linux OS
> java -cp ".;gson-2.8.2.jar" BlockChain 0
> java -cp ".;gson-2.8.2.jar" BlockChain 1
> java -cp ".;gson-2.8.2.jar" BlockChain 2

5. List of files needed for running the program.

e.g.:

 a. checklist.html
 b. BlockChain.java
 c. BlockInput0.txt
 d. BlockInput1.txt
 e. BlockInput2.txt
 f. gson-2.8.2.jar
 g. BlockchainLedgerSample.json

5. Notes:
    When BlockChain 0, BlockChain 1, BlockChain 2 start, the program will start from many servers [Main server]-> that responsilbe for lanshing all other server.
    Then, the program will send the Public keys which were generated to all nodes -> 4710, 4711, and 4712.
    Next, the program store the genius block to the ledger with fake data => NOTE: the data that I used my name and my information.
    Next, the program will read all three files BlockInput0,BlockInput1, and BlockInput2, and the program will extract all information.
    Then, the program will multicast unverified blocks -> that extracted recently from txt files |> 4810, 4811,4812.


6. Reference that help me in this assignment:
    - Professor Elliot's utilities codes. Thank you prof for helpful code.
    - https://www.youtube.com/watch?v=igedHOpSqu4&t=756s [Professor Sluiter]
    - https://www.youtube.com/watch?v=ea3miXs_P6Y
    - https://www.javatpoint.com/blockchain-java.
    - https://www.quickprogrammingtips.com/java/how-to-generate-sha256-hash-in-java.html [SHA-256]


* IMPORTANT Note : Sometimes the ledger has duplicate values, I tried to solve this issue, but I cannot figure out what the issue.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Blockchain {
    // this boolean should change to true when the process number 2 is started in order to start all procedures.
    public static boolean allProcessesStart;

    // main function for Blockchain class
    public static void main(String[] args) {
        // defined process_id and send it to the determine process method.
        int process_id = determineProcess(args);
        // check if the process is assigned.
        if (process_id != -1 && process_id >= 0) {
            // if assign correct process id correctly run the main
            System.out.println("----> Here is main <------ \n");
            // create a new instance of Ports class.
            Ports newPort = new Ports();
            // set the process id to the appropriate server
            newPort.setPort(process_id);
            // new instance of blockchainWorker class and pass process id then run it.
            new blockChainWorker(process_id).run(); // here start the with first process that used [0]
        } else {
            // error message when the user forget to write the process id
            System.out.println("X   You're trying to exceed the processes numbers [0,1,2].");
        }
    }

    //this method will pass the argument array and extract the process ID.
    public static int determineProcess(String[] arguments) {
        // initialize value of process
        int processNumber = 0;
        try {
            // get the first argument.
            processNumber = Integer.parseInt(arguments[0]);
            // if the process number more than 2 -> return -1 to let the main server know about this issue
            if (processNumber > 2) {
                return -1;
            }
            // catch the null pointer
        } catch (NullPointerException n) {
            System.out.println("X   You must enter the process ID in order to spread works between three process. ");
        }
        // return the process number.
        return processNumber;
    }
}

// Blockchain record
class CreateBlockRecord {
    // variable that will store the BlockID
    String Block_ID;
    // variable that will store the timeStamp
    String timeStamp_currentTime;
    // variable that will store the SignBlock
    String Sign_Block_;
    // variable that will store the universally unique identifier
    UUID uuid;
    // variable that will store the First name
    String Fname;
    // variable that will store the Last name
    String LastNamePatient;
    // variable that will store the Social Security Number
    String SSNPatient;
    // variable that will store the Date Of Brith
    String DateOfBrith;
    // variable that will store the medical Diagnoses
    String medicalـDiag;
    // variable that will store the medical treatment
    String medical_Treat;
    // variable that will store the medical prescriptions
    String medical_Rx;
    // variable that will store the previous hash value of previous block.
    String PreviousHash;
    // variable that will store the random value that help to guess the wining hash.
    String RandomSeed;
    // variable that will store the wining hash value.
    String WinningHashBlock;
    // variable that will store the process number that store it in the ledger.
    String process_number;
    // variable that will store the process number that verify the block.
    String verification_process;
    // variable that will store the signature of winning value.
    String WinningSignedHashV;

    public String getBlockID() {
        return Block_ID;
    }

    public void setBlockID(String BID) {
        this.Block_ID = BID;
    }

    // TimeStamp in blockRecord ..
    public void setTimeStamp(String timeStamp) {
        this.timeStamp_currentTime = timeStamp;
    }

    public String getTimeStamp() {
        return timeStamp_currentTime;
    }

    public void setSignBlock(String signBlock) {
        Sign_Block_ = signBlock;
    }

    public String getSignBlock() {
        return Sign_Block_;
    }

    /*          public String getVerificationProcessID() {return VerificationProcessID;}
                public void setVerificationProcessID(String VID){this.VerificationProcessID = VID;}
            */
    public UUID getUUID() {
        return uuid;
    } // Later will show how JSON marshals as a string. Compare to BlockID.

    public void setUUID(UUID ud) {
        this.uuid = ud;
    }

    //  the getblockLastName
    public String getBlockLastName() {
        return LastNamePatient;
    }

    public void setBlockLastName(String LastNamePatient) {
        this.LastNamePatient = LastNamePatient;
    }

    public String getBlockFirstName() {
        return Fname;
    }

    public void setBlockFirstName(String FirstNamePatient) {
        this.Fname = FirstNamePatient;
    }

    public String getSSNumPatient() {
        return SSNPatient;
    }

    public void setSSNumPatient(String SSNPatient) {
        this.SSNPatient = SSNPatient;
    }

    public String getDOB() {
        return DateOfBrith;
    }

    public void setDOB(String RS) {
        this.DateOfBrith = RS;
    }

    public String getPatientDiag() {
        return medicalـDiag;
    }

    public void setPatientDiag(String D) {
        this.medicalـDiag = D;
    }

    public String getTreat() {
        return medical_Treat;
    }

    public void setTreat(String Tr) {
        this.medical_Treat = Tr;
    }

    public String getRx() {
        return medical_Rx;
    }

    public void setRx(String Rx) {
        this.medical_Rx = Rx;
    }

    // this method to get the previous hash from previous block
    public String getPreviousHashBlock() {
        return this.PreviousHash;
    }

    //this method to set the previous hash to determine the previous hash for a block
    public void setPreviousHashBlock(String previousHash) {
        this.PreviousHash = previousHash;
    }

    //setter & getter for process number
    public void setBlockProcess_number(String process_number) {
        this.process_number = process_number;
    }

    //setter & getter for process number
    public String getBlockProcess_number() {
        return process_number;
    }

    //setter & getter for Winning Hash
    public String getWinningHashBlock() {
        return WinningHashBlock;
    }

    //setter & getter for Winning Hash
    public void setWinningHashBlock(String WinningHa) {
        this.WinningHashBlock = WinningHa;
    }

    //setter & getter for setVerification_process
    public void setVerification_process(String verification_process) {
        this.verification_process = verification_process;
    }

    //setter & getter for setVerification_process
    public String getVerification_process() {
        return verification_process;
    }

    //setter & getter for Random seed guessing the winning value
    public String getRandomSeed() {
        return RandomSeed;
    }

    //setter & getter for Random seed guessing the winning value
    public void setRandomSeed(String randomSeed) {
        this.RandomSeed = randomSeed;
    }

    //setter & getter for Winning Signed HashValue
    public void setWinningSignHashVal(String winningSignedHashValue) {
        WinningSignedHashV = winningSignedHashValue;
    }

    //setter & getter for Winning Signed HashValue
    public String getWinningSignHashVal() {
        return WinningSignedHashV;
    }

    // method that will build up all values from setter and getter and return it as hash map
    public HashMap<String, String> buildBlockRecord() {
        HashMap<String, String> buildUp = new HashMap<>();
        buildUp.put("Block_ID", getBlockID());
        buildUp.put("Time_stamp", getTimeStamp());
        buildUp.put("Sign_Block", getSignBlock());
        buildUp.put("First_Name", getBlockFirstName());
        buildUp.put("Last_Name", getBlockLastName());
        buildUp.put("Data_of_Brith", getDOB());
        buildUp.put("SSN_Number", getSSNumPatient());
        buildUp.put("Diagnoses", getPatientDiag());
        buildUp.put("Rx", getRx());
        buildUp.put("Treat", getTreat());
        buildUp.put("UUID", getUUID().toString());
        buildUp.put("PreviousHash", getPreviousHashBlock());
        buildUp.put("Winning", getWinningHashBlock());
        buildUp.put("Random_Seed", getRandomSeed());
        buildUp.put("verification_process", getVerification_process());
        buildUp.put("WinningSignedHashValue", getWinningSignHashVal());
        System.out.println("√   The blockchain record has built up..");
        return buildUp;
    }
}

class blockChainWorker { // you can extend Thread here
    public static int process; // process number that currently used
    Socket socket; // socket to open a new endpoint.
    static KeyPair keyPair; // keyPair to create public key or private key
    // to check if all process is running
    public static boolean threeProcess = false; // this variable will change to true when the all process starting at the same time.
    public static int totalProcessNumber = 3; // this to declare the all process is 3 process I will use.
    public static boolean process0; // is process 0 run => to store the first block and store the json file in the disk.
    public static boolean allKeysSet = false; // if recieve 3 keys from different processes I will use them later and change this to true
    static String serverName = "localhost"; // here I define the server name => localhost as default.
    //store the hashmap in our class as static variable so we can access it from anywhere in our class without creating an instance of it.
    // I will use this public key hash map in Sign class.
    public static HashMap<String, PublicKey> publicKeysMap = new HashMap<>(); // store the public key here in class and keep it static
    // this LinkedList will store Unverified blocks for our process before verify them later. => will not store in the ledger.
    public static LinkedList<CreateBlockRecord> Unverified_blocks = new LinkedList<CreateBlockRecord>();
    //this is the linkedlist will store all verification blocks then apply the PriorityBlock.
    /*
    1. We create a new PriorityBlockingQueue object.
    2. The queue has a maximum size of 100 elements.
    3. The queue uses the BRComparator class to compare the elements in the queue and sort them accordingly*/
    public static LinkedList<CreateBlockRecord> blockChainLedger = new LinkedList<CreateBlockRecord>();
    // PriorityBlock queue
    public static PriorityBlockingQueue<CreateBlockRecord> blockPriorityQueue = new PriorityBlockingQueue<>(100, new ComparatorQueue());
    // ask the professor about this variable and prevliges
    MulticastToAllPeers sendToPeers = new MulticastToAllPeers(); // send public key
    MulticastToAllPeers ubToPeers = new MulticastToAllPeers(); // send the Unverified blocks to all nodes.

    // constructor fro worker class and pass the process number in the parameter.
    public blockChainWorker(int process_number) {
        this.process = process_number;
    }

    // run method
    public void run() { // runnable method that inherited from Thread class.
        // Versions are the simi projects that I had done.
        System.out.println("Fahad Al Summan's BlockChain version 1.8 the process[" + process + "] is starting now... \r");
        new Thread(new BlockchainMainServer(process, threeProcess)).start(); // start new spwan
        // Start the Public key Thread -> this  will help to multicast the public keys between processes.
        new Thread(new PublicKeyServer()).start();
        // start the Unverified_blocks server
        new Thread(new unverifiedBServer()).start();
        // sleep 2000 millisecond to arrange the procedures between all servers.
        try {
            Thread.sleep(2100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // start the Update blockchain server
        new Thread(new UpdateBlockChainServer()).start();
        // this to create a progress bar in the program
        // sleep again to arrange the procedures between all servers.
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // generate key Pairs seed 999 to use it in the publicKey server and share them between all peers.
        try {
            keyPair = GenerateKeyPairs.generatePublicKey(999);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // trigger all server to start when  process 2 is sarting.
        if (process == 2) {
            runAllProcess();
        }
        // if all process start to work start to doing the blockchain duty..
        while (!threeProcess) { // sleep again until the all three processes run.
            // this will help when the user start two nodes even if the process 2 start. You Must start all three processes.
            try {
                // sleep again 2000 millsecond
                Thread.sleep(2100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // send all key to all process that I have here
        sendToPeers.setProcess(process);
        // set the server name
        sendToPeers.setServerName("localhost");
        // send the public key to all peers => nodes.
        sendToPeers.sendPublicKey();
        // if all keys not received and store in the hash map public keys
        if (!allKeysSet) {
            // sleep
            while (!allKeysSet) {
                if (allKeysSet == true) {
                    break;
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        String fileName = new String(); // file name for txt files.
        // chose the file by using the process number.
        switch (process) {
            case 0:
                // in case 0 => i will load blockinput0.txt
                fileName = "./src/BlockInput0.txt";
                System.out.println("√   File that will deal in this process is " + fileName.split("/")[1]);

                break;
            case 1:
                // in case 1 => I will load BlockInput1.txt
                fileName = "./src/BlockInput1.txt";
                System.out.println("√   File that will deal in this process is " + fileName.split("/")[1]);
                break;
            case 2:
                // in case 1 => I will load BlockInput2.txt
                fileName = "./src/BlockInput2.txt";
                System.out.println("√   File that will deal in this process is " + fileName.split("/")[1]);
                break;
            default:
                // if not found showed the error messages.
                System.out.println("X   Error the file is not found !!");
        }
        // read function to read file name and extract the values from the txt files and store
        try {
            readFromTextFile(fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // send the Unverified blocks to all nodes between processes [0 -> 1 -> 2]
        ubToPeers.setPort_service(Ports.portBaseUPServer); // BasedPort
        // server name
        ubToPeers.setServerName("localhost");
        //send the linked list
        ubToPeers.sendUBtoAllPeers(Unverified_blocks);
        // new Thread(new WorkClass(qqBlock)).run();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
        }
        // start the Work class that will work as Work=> blockchain
        new Thread(new WorkClass(blockPriorityQueue)).start();
        try {
            Thread.sleep(19000);
        } catch (Exception e) {

        }
        System.out.println("======== The Ledger content is =============");
        try {
            printAllLedger();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // option server => you should create a new server for this properties

    }

    // check if the allProcesses have been running or not.
    // this method is inspired from Professor Elliot utilities code.
    public static boolean runAllProcess() {
        // socket to connect to all nodes 4610
        Socket socket;
        // if all run return true
        boolean isRun = false;
        // send message to main server to notify them about the current situation.
        PrintStream toServer;
        try {
            int process_item = 3;
            // for loop throw all process that I have => 3 processes
            for (int i = 0; i < blockChainWorker.totalProcessNumber; i++) {
                // new socket that connect to the based Server + process id.
                socket = new Socket(blockChainWorker.serverName, (Ports.startServerPort - i));
                // to server -> send a message to the servers
                toServer = new PrintStream(socket.getOutputStream());
                // notified all server we are run now.
                toServer.append("run");
                process_item -= i;
                // check if three processes have been working.
                if (process_item == 0) {
                    // let the blockchainWorker know that all processes are running
                    toServer.append(" AllProcessesRun");
                    blockChainWorker.threeProcess = true;// true
                    isRun = true;// true is run
                }
                toServer.flush(); // flush the message
                socket.close();// close the endpoint.
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return isRun; // return the boolean
    }

    public void printAllLedger() throws Exception { // this help to print out  ledger file
/*
        System.out.println("[ID]   [FirstName]-    [LastName]-     [TimeStamp]-    [medical prescriptions] - [medical Diagnoses ]");
*/
        Gson gsonPrintLed = new Gson(); // create a new instance of the Gson file
        ArrayList<CreateBlockRecord> allLedgerList; // ArrayList of create Block Record
        Type typeMyFile = new TypeToken<ArrayList<CreateBlockRecord>>() {
        }.getType(); // dataType
        // read my current ledger
        FileReader readJosnFile = new FileReader("./src/BlockchainLedger.json");
        // update the Arraylist with BlockchainLedger.json and store all information in arraylist
        allLedgerList = gsonPrintLed.fromJson(readJosnFile, typeMyFile);
        // this part of professor's  Elliot code to iterate all ledger content in ArrayList
        Iterator<CreateBlockRecord> iterator = allLedgerList.iterator();
        // this variable to determine the number of ids that the ldeger have.

        var ref = new Object() {
            int item_num = allLedgerList.size();
        };

        // for loop through the iterator
     /*   TableList tl = null;
        for (; iterator.hasNext(); ) { // while has next value
            CreateBlockRecord temp = iterator.next(); // assign the next value of iterator tp block record
            //System.out.print("["+ item_num + "]");
            tl = new TableList(6,"FirstName", "LastName","TimeStamp","medical prescriptions","medical Diagnoses","medical Treatment").sortBy(0).withUnicode(true);
            tl.addRow(temp.getBlockFirstName());
            tl.addRow(temp.getBlockLastName());
            tl.addRow(temp.getTimeStamp());
            tl.addRow(temp.getRx());
            tl.addRow(temp.getPatientDiag());
            tl.addRow(temp.getTreat());
           *//* ref.item_num -=1;*//*
            //yourListOrWhatever.forEach(element -> tl.addRow(element.getID(), element.getS1(), element.getS2()));

        }*/

        /*Iterator<CreateBlockRecord> iteratorNew = allLedgerList.iterator();
        CreateBlockRecord temp;
        temp = iteratorNew != null?iteratorNew.next(): null;
        allLedgerList.forEach(element -> tl.addRow(temp.getBlockFirstName(),temp.getBlockLastName(),
                temp.getTimeStamp(),temp.getRx(),temp.getPatientDiag(),temp.getTreat()));*/

       // iterator.forEachRemaining();



    }

    // method that will help to create the first block genuis block
    public static CreateBlockRecord initialBlock(int process) {
        // open file and store them in the blockChain
        CreateBlockRecord blockInit = new CreateBlockRecord(); // create a new instance of block record to create the initial block.
        UUID binaryUUID = UUID.randomUUID();
        String uid = binaryUUID.toString();
        Date date = new Date();
        String t = String.format("%1$s %2$tF.%2$tT", "", date);
        String timeStamp = t + "." + process;/*new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss").format(new java.util.Date()) + "." + process;*/
        String signBlock = "";
        SignBlock sign_block = new SignBlock();
        sign_block.setBytesDate(uid.getBytes());
        sign_block.setPrivateKeys(keyPair.getPrivate());
        StringBuffer hexString;
        String hash256 = "";
        String SHA256Data;
        try {
            byte[] dSignBlock = sign_block.signItemBlock(sign_block.getBytesDate(), sign_block.getPrivateKeys());
            signBlock = Base64.getEncoder().encodeToString(dSignBlock);
            // todo modify
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            StringBuilder buildBlockRecord = new StringBuilder();
            buildBlockRecord = WorkClass.buildRecord(blockInit);
            md.update(buildBlockRecord.toString().getBytes());
            byte[] bytesVal = md.digest();
            hexString = new StringBuffer();
            for (int i = 0; i < bytesVal.length; i++) {
                hexString.append(Integer.toString((bytesVal[i] & 0xff) + 0x100, 16).substring(1));
            }
            hash256 = hexString.toString();
            SHA256Data = hash256;

            // todo end-modify
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        blockInit.setBlockID(uid);
        blockInit.setUUID(binaryUUID);
        blockInit.setTimeStamp(timeStamp);
        blockInit.setSignBlock(signBlock);
        blockInit.setBlockFirstName("Fahad");
        blockInit.setBlockLastName("AlSumman");
        blockInit.setDOB("1993.09.01");
        blockInit.setSSNumPatient("123-45-67890");
        blockInit.setPatientDiag("sinus");
        blockInit.setTreat("sinus_treatment");
        blockInit.setRx("blo");
        blockInit.setPreviousHashBlock("0");
        blockInit.setBlockProcess_number(String.valueOf(process));
        blockInit.setWinningHashBlock(SHA256Data);
        blockInit.setWinningSignHashVal(SHA256Data);
        return blockInit;
    }

    public static void writeJsonFile(String fileName, LinkedList<CreateBlockRecord> blockRecord) {
        System.out.println("    √  The current JSON file has been written correctly.. ");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // here to write a json file | or Create json file if that file is not found.
        try (FileWriter writeOnFile = new FileWriter(fileName)) { // todo if you grade this file using IDE on MAC OR Windows you should write down the correct paths
            gson.toJson(blockChainWorker.blockChainLedger, writeOnFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFromTextFile(String fileName) throws Exception {
        String input;
        String[] temp;
        Date current_Time = new Date(); // help to store the date current_Time
        String signBlock; // sing the block that will store in the hash map
        SignBlock sign_block = new SignBlock(); // create a new instance of the singBlock to sing my block
        try {
            FileReader readFile = new FileReader(fileName);
            BufferedReader inputData = new BufferedReader(readFile);
            try {
                while ((input = inputData.readLine()) != null) {

                    // here I will create new variables that help me to set the block record values

                    String current_time_format = String.format("%1$s %2$tF.%2$tT", "", current_Time);
                    String timeStamp = current_time_format + "." + process;/*new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss").format(new java.util.Date()) + "." + process;*/
                    // split the input tempraroy readline from server in order to store each value to the block record
                    temp = input.split(" +");// use regex "+" =>The delimiter is one or more spaces
                    CreateBlockRecord blockRecord = new CreateBlockRecord(); // => create a new instance of CreateblockRecord that help to store  values  in the hashmap.
                    UUID binaryUUID = UUID.randomUUID(); // => generate random UUID
                    String uid = binaryUUID.toString(); // convert uuid to string.
                    // set theByteData get it from uuid
                    sign_block.setBytesDate(uid.getBytes());
                    // set the private key from KeyPair class.
                    sign_block.setPrivateKeys(keyPair.getPrivate());
                    try { // try and catch to catch any exceptions might occur
                        byte[] dSignBlock = sign_block.signItemBlock(sign_block.getBytesDate(), sign_block.getPrivateKeys());
                        signBlock = Base64.getEncoder().encodeToString(dSignBlock);
                    } catch (SignatureException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeyException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                    blockRecord.setBlockID(uid);
                    blockRecord.setUUID(binaryUUID);
                    blockRecord.setTimeStamp(timeStamp);
                    blockRecord.setSignBlock(signBlock);
                    blockRecord.setBlockFirstName(temp[0]);
                    blockRecord.setBlockLastName(temp[1]);
                    blockRecord.setDOB(temp[2]);
                    blockRecord.setSSNumPatient(temp[3]);
                    blockRecord.setPatientDiag(temp[4]);
                    blockRecord.setTreat(temp[5]);
                    blockRecord.setRx(temp[6]);
                    blockRecord.setBlockProcess_number(String.valueOf(process));
                    Unverified_blocks.add(blockRecord); // store them in the Unverified block
                    Thread.sleep(1000); // sleep 1000 millsec to arrange the procedures.
                }
            } catch (IOException r) {
                r.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // generate first block
    public static void generateFirstBlock(int process) {
        CreateBlockRecord genusBlock = initialBlock(process);
        genusBlock.buildBlockRecord();
        //unvBlock.add(0, genusBlock);
        blockChainWorker.blockChainLedger.add(0, genusBlock);
        if (process == 0) { // when the process 0 running  store the first block in the disk -> json
            System.out.println("√   Dummy block has created with Process ZERO");
            System.out.println("√   The ledger has been updated.");
            MulticastToAllPeers sendUpdateLedger = new MulticastToAllPeers(); // multicast my new ledger btw all nodes
            // update this ledger.
            sendUpdateLedger.sendUpdateToProcesses(genusBlock);
            // write them in the disk
            // todo -> NOTE if you're using IDE change the src
            writeJsonFile("./src/BlockchainLedger.json", blockChainLedger);
        }
    }
}


class Ports {
    //todo change the variables names
    public static int portBaseServer = 4610; // this port for start up main server and other server
    public static int portBaseUPServer = 4820; // Un_verifiedBlock server
    public static int portUpdateBlockRecordBasedServer = 4930; // UB
    public static int portBasePublicKeyServer = 4710; // public key
    public static int portBaseOption = 5510; // this for option part

    public static int startServerPort; // this start server port
    // process ID = 0 | 1 | 2
    public static int portPublicKeys;
    public static int portUnverified;
    public static int portUpdateBlock;
    public static int portOption; // this port will start when the all program end because the List credit and List verivication processes.

    // set ports by using the process numbers
    public void setPort(int process_number) {
        startServerPort = portBaseServer + process_number;
        portPublicKeys = portBasePublicKeyServer + process_number;
        portUnverified = portBaseUPServer + process_number;
        portUpdateBlock = portUpdateBlockRecordBasedServer + process_number;
        portOption = portBaseOption + process_number;
    }


}

// This class responsible for the main server run, and is the most important server in the Blockchain program.
/*todo this is process 0  => running here */
class BlockchainMainServer implements Runnable {
    public int process_id;
    public boolean threeProcess; // flag for all process

    //constructor takes two parameters: process and threeProcess
    public BlockchainMainServer(int process, boolean threeProcess) {
        this.process_id = process;
        this.threeProcess = threeProcess;
    }

    @Override
    public void run() { // Runnable method
        int len = 6; // the length of the Open system queue
        // message to all console about wach port and process you've used.
        System.out.println("√   The Blockchain main Server has been starting, and its port = " + Ports.startServerPort);
        Socket socket; // socket to endpoint
        try { // try and catch any errors in socket connection
            // start server socket and accept that connection.
            ServerSocket serverSocket = new ServerSocket(Ports.startServerPort, len);
            while (true) { // while is ture

                socket = serverSocket.accept();// accept any connection from that server
                new BlockchainMainServerWorker(socket, process_id).start(); // start the Runnable -> workerClass
            }
        } catch (IOException e) {
            // print out the message of that errors
            System.out.println("Address that are used => " + Ports.portBaseServer + process_id);
            e.printStackTrace();
        }
    }
}

// class for main base server 4610
class BlockchainMainServerWorker extends Thread {
    Socket socket;
    int process;

    // constructor that receive two parameters socket end point and process id
    public BlockchainMainServerWorker(Socket soc, int pros) {
        this.socket = soc;
        this.process = pros;
    }

    @Override
    public void run() {
        try {
            // read the input from servers
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String readInput = in.readLine();// read Line
            // printOut to the server
            PrintStream toServer = new PrintStream(socket.getOutputStream());
            // if the main server receive Process ID -> 0, then I will create first dummy block
            // here will notfiy main server about all processes are running _
            if (readInput != null) {
                // if you read the input is run
                if (readInput.equals("run")) {
                    // chainge all blockchainWorker -> true.
                    blockChainWorker.process0 = true;
                    blockChainWorker.threeProcess = true;
                }
                // split the input
                String[] readLineMoreAttr = readInput.split(" ");
                // here if all process start to work, I will create first Block in my blockchain...
                if (readLineMoreAttr.length != 0 && readLineMoreAttr.length == 2) { // if I recieve
                    if (readLineMoreAttr[1].equals("AllProcessesRun")) { // if all processes running
                        blockChainWorker.process0 = true; // change the process 0 is true run
                        blockChainWorker.threeProcess = true; // three processes running
                        blockChainWorker.generateFirstBlock(0);// generate genius block
                    }
                }
            }
            // flush all message
            toServer.flush();
            socket.close(); // close the end point
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Here I will establish the Public Key servers that contain processes [0 -> 1 -> 2]
class PublicKeyServer implements Runnable {
    @Override
    public void run() {
        int len = 6; // len of SysOps that I will deal with in this server.
        Socket socket; // I will initialize a new socket -> enable another server to communicate with this server.
        System.out.println("√   Public key Server has been starting .. " + Ports.portPublicKeys);
        try {

            // server Side implement ~> to accept the connection from server.
            ServerSocket serverSocket = new ServerSocket(Ports.portPublicKeys, len);
            while (true) { // while true
                // accept connection from server
                socket = serverSocket.accept();
                // start the public key
                new PublicKeyWorker(socket).start(); // pass the current socket to the worker class
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// worker class for public keys
class PublicKeyWorker extends Thread {
    Socket socket; // socket for this worker

    public PublicKeyWorker(Socket soc) { // receive the socket from the Server
        this.socket = soc;// this socket is the same socket I defiend before
    }

    @Override
    public void run() { // Runnable method
        try {
            // input from the Server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String readInput = in.readLine(); // read that input in new string
            String[] splitInput = readInput.split(" "); // split the input and assign it to array
            PublicKey reGeneratePublicKeys = reGeneratePK(splitInput);// send the input to the method to extract the public key from the array
            // here I will store all public keys for all process in hash map => enhance the performance.
            blockChainWorker.publicKeysMap.put(splitInput[0], reGeneratePublicKeys);
            socket.close(); // close the end point
            if (blockChainWorker.publicKeysMap.size() == 3) { // if the server receive all three keys
                blockChainWorker.allKeysSet = true; // change the boolean variable to true
                // print out these messages into the  console
                System.out.println("√   All Public Keys are sent to all processes IDs");
                // print out these messages into the  console
                System.out.println("√   ==> Mulitcasting public key to all nodes <==");
                // print out these messages into the  console
                System.out.println("√   -> Send Public Key to All Peers <- ");
                // print out these messages into the  console
                System.out.println(splitInput[1] + "\r\n"); // print the keys
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // this method will regenerate each
    public PublicKey reGeneratePK(String[] publicKeyFromNodes) {
        // recieve the key from the input array and parse the key
        byte[] pkNode = Base64.getDecoder().decode(publicKeyFromNodes[1]);// decodes the public key from base64 to bytes array.
        PublicKey re = null; // return value of this method
        /*`X509EncodedKeySpec` is a class that represents the X.509 encoding of a public key. 2. `pubSpec` is an object of type `X509EncodedKeySpec`. 3. The constructor takes one argument, which is the byte array containing the encoded public key (the value of variable `pkNode`). 4. The constructor returns an object of type `X509EncodedKeySpec`, which we store in variable `pubSpec`.*/
        //`X509EncodedKeySpec` is a class that represents the X.509 encoding
        X509EncodedKeySpec publicKeySpe = new X509EncodedKeySpec(pkNode);
        try {
            // create new instance of RSA algorithm and
            KeyFactory publicKeyFactoryInstance = KeyFactory.getInstance("RSA");
            re = publicKeyFactoryInstance.generatePublic(publicKeySpe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("√   The public Key received from ID -> " + publicKeyFromNodes[0]);
        // if the publicKeysMap is full send message to the main server this is ready complete
        return re;
    }
}


// code prof Elliot => I should try to modify it
/*
 *       The `compare` method compares two objects of type `CreateBlockRecord`.
 *       3. It returns 0 if the dates are equal, -1 if date1 is null, 1 if date2 is null and otherwise it returns the result of comparing the two
 *       dates using String's compareTo method. 4. If we want to use this comparator
 *         in a priority queue we need to create an instance of it and pass it as an argument when creating a new priority que
 * */
class ComparatorQueue implements Comparator<CreateBlockRecord> {
    @Override
    public int compare(CreateBlockRecord firstBlock, CreateBlockRecord secondBlock) {
        // create block 1 data and assign to them the timeStamp.
        String block1Data = firstBlock.getTimeStamp();
        // create block 2 data and assign to them the timeStamp.
        String block2Data = secondBlock.getTimeStamp();
        // if equal return 0;
        if (block2Data.equals(block2Data))
            return 0;
        // if null return -1
        if (block1Data == null)
            return -1;
        // if block2 data is null return 1;
        if (block2Data == null)
            return 1;
        return block1Data.compareTo(block2Data);
    }
}

/* Thank you, Professor Elliot this is part of his utilities code CSC453*/
// This class responsible for generate keyPairs [Public Keys - Private Key] @
class GenerateKeyPairs {
    //public static KeyPair keyPairs;
    // method that will return  KeyPair -> Cryptography system |> RSA
    public static KeyPair generatePublicKey(long seed) throws NoSuchAlgorithmException, NoSuchProviderException {
        /*
         * We create a KeyPairGenerator object.
         * 2. We create a SecureRandom object with the seed we want to use.
         * 3. We initialize the KeyPairGenerator with our SecureRandom object and the size of the key (1024 bits).
         * 4. Finally, we generate our public key pair using our initialized KeyPairGenerator object and return it as a result of this function call.
         * */
        // defiend the algorithm that I'll to generate public keys |> RSA Cryptography system
        KeyPairGenerator keyPairsGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom rng = SecureRandom.getInstance("SHA1PRNG", "SUN");
        // SecureRandom object with the seed we want to use. For instance, in the utilities code the prof used 999
        rng.setSeed(seed);
        // This responsible for initialize key Pair with SecureRandom object, and its size is 1024 bits. <|
        keyPairsGenerator.initialize(1024, rng);
        // Finally, I generate a public key pair using the generateKeyPair methods
        return (keyPairsGenerator.generateKeyPair());
    }
}

// this class will deal with unverified blocks and store them in array or linkedList
//unverified Block Server
class unverifiedBServer implements Runnable {
    BlockingQueue<CreateBlockRecord> unVerifiedBlock;
    Socket unVSocket; // socket for the server

    @Override
    public void run() { // Runnable methods
        try { // try and catch to catch any error in the Ports or server connection
            // Server Socket
            ServerSocket unVeriServer = new ServerSocket(Ports.portUnverified, 6);
            // print out the message of un verified block server
            System.out.println("√   Un_verifiedBlock Server has been starting .. " + Ports.portUnverified);
            // while the connection accept
            while (true) {
                unVSocket = unVeriServer.accept(); // accept the connection from server
                new unverifiedBServerWorker(unVSocket).start();// start the client
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

// client worker for unverified Block
class unverifiedBServerWorker extends Thread {
    Socket socketUBWorker;

    //constructor that pass Socket
    public unverifiedBServerWorker(Socket soc) {
        this.socketUBWorker = soc;
    }

    @Override
    public void run() { // runnable method
        try {
            // BufferReader to read the input from the server

            /*
            1. We create a BufferedReader object to read the input stream from the socket.
            2. We create a StringBuilder object to store the data we read from the socket.
            3. We create a Gson object to convert JSON strings into Java objects and vice versa.
            4. We loop through all of the lines in our input stream, appending them to our StringBuilder object until we reach an empty line (null).
            5. Once we have
            */
            //BufferedReader object to read the input stream from the socket.
            BufferedReader inputUnvBlock = new BufferedReader(new InputStreamReader(socketUBWorker.getInputStream()));
            //create a StringBuilder object to store the data we read from the socket.
            StringBuilder readLine = new StringBuilder();
            String inp;
            //create a Gson object to convert JSON strings into Java objects and vice versa.
            Gson gson = new Gson();
            //loop through all of the lines in our input stream.
            while ((inp = inputUnvBlock.readLine()) != null) {
                readLine.append(inp);//appending them to our StringBuilder object until we reach an empty line (null)
            }

            //finished reading all of our lines, we use Gson's `fromJson` method with two parameters: The first is our JSON string that was stored in `readLine`, and the second is a
            // TypeToken that tells Gson what type of Java class it should convert this JSON string into (in this case, it's CreateBlockRecord).
            Type type = new TypeToken<CreateBlockRecord>() {
            }.getType();
            // marshal to json
            CreateBlockRecord br = gson.fromJson(readLine.toString(), type);
            // add the value to blockPriorityQueue
            blockChainWorker.blockPriorityQueue.add(br);
            socketUBWorker.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
}

// here I will make the all processes update the blockchain and verify them
class UpdateBlockChainServer implements Runnable {
    @Override
    public void run() {
        int len = 6; // length of Sys ops
        Socket socket;// socket for Update server
        // print out to all consoles notify about the server that established
        System.out.println("√   Update Blockchain Server has been starting .." + Ports.portUpdateBlock);
        try {
            // try to connect to the server and accept the connection from them.
            ServerSocket serverSocket = new ServerSocket(Ports.portUpdateBlock, len);
            while (true) {
                // accept the connection while the socket pass the correct Port
                socket = serverSocket.accept();
                // start the client Worker
                new UpdateBlockChainWorker(socket).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

// update Blockchain worker
class UpdateBlockChainWorker extends Thread {
    Socket socket;

    //constructor passed the Socket from server
    public UpdateBlockChainWorker(Socket socketServer) {
        this.socket = socketServer; // the same local socet is the same socket
    }

    // create a new instance of Similarity class to check the Similarity and duplicate
    Similarity checkMySimilarity = new Similarity();

    @Override
    public void run() {
        try {
            //blockChainLedger read all input from nodes
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // String builder to assign the read line to it
            StringBuilder readLine = new StringBuilder();
            String inp;
            // create a new gson instance for marshal data.
            Gson gson = new Gson();
            // while the input is not null
            while ((inp = in.readLine()) != null) {
                readLine.append(inp); // append the line to the String builder
            }
            // type of the CreateBlockRecrod
            Type type = new TypeToken<CreateBlockRecord>() {
            }.getType();
            // marshal data here
            CreateBlockRecord br = gson.fromJson(readLine.toString(), type);// pass the type to the fromJson => from the professor Elliot codes
            if (!checkMySimilarity.similarity(br)) { // check similirit if there no similarity
                // print out into  console these important information
                // print out  which person added to the ledger
                System.out.printf("     => Insert %s to Ledger  process ID %s \n", br.getBlockFirstName(), br.getBlockProcess_number());
                System.out.println("    ¶ Verified Block size now is ->: " + blockChainWorker.blockChainLedger.size());
                blockChainWorker.blockChainLedger.add(0, br);
            }
            // write in the disk in the process zero
            if (blockChainWorker.process == 0) {
                blockChainWorker.writeJsonFile("./src/BlockchainLedger.json", blockChainWorker.blockChainLedger);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// class that responsible for multicast to all nodes and I can use it when I need to share any information between all nodes
class MulticastToAllPeers {
    public static int processes = 3; // processes || nodes => that I will share my information among them.
    private static int port_service;// port of that services I will share data with them such as |> Update Blockchain - multicast keys and so on.
    private String serverName; // server name => localhost as default.
    private int process; // setter and getter for process number

    // this method help to set the port that I will multicast items to it
    // that will determine by basePort. For example, if you multicast PublicKey to all peers you will set the based port => 4710
    public void setPort_service(int port_service) {
        this.port_service = port_service;
    }

    public int getPort_service() {
        return port_service;
    }

    // this method will help to set the server name to use it later.
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    // setter process number
    public void setProcess(int process) {
        this.process = process;
    }

    // send public key to all peers
    public void sendPublicKey() {
        // socket to share pk
        Socket sendNodesSocket;
        PrintStream toNodes; // Print to all nodes
        KeyPair keyPair1; // keyPair
        try {
            // generate key Pairs with 999  seed
            keyPair1 = GenerateKeyPairs.generatePublicKey(999);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
        // get the public key and encode the public key, and store them in the byte array
        byte[] kPairs = keyPair1.getPublic().getEncoded();
        // convert to String with Base64
        String pkConvertToString = Base64.getEncoder().encodeToString(kPairs);

        try {
            for (int i = 0; i < processes; i++) { // for loop through all process that I have
                // new socket and connection to end point with based public key and process number
                sendNodesSocket = new Socket(serverName, Ports.portBasePublicKeyServer + i);
                // send the public keys to all nodes
                toNodes = new PrintStream(sendNodesSocket.getOutputStream());
                // Compain public key and process number in String and send them.
                String combinePK_processID = process + " " + pkConvertToString + " " + i;
                // send the key and process number.
                toNodes.println(combinePK_processID);
                // flush the message
                toNodes.flush();
                // close connection.
                sendNodesSocket.close();
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
        }
    }

    // this method will help to send unverified block to all nodes
    public void sendUBtoAllPeers(LinkedList unvBlock) {
        Socket socketUB; // new socket for unverified blocks
        PrintStream toServer; // print message to unverifiedServer
        CreateBlockRecord tempBlock; //new temporary BlockRecord
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // prepare the block to write in appropriate JSON way.
        Iterator<CreateBlockRecord> iterUnverifiedB = unvBlock.iterator(); // this inspires from the professor Elliot code bc->
        try {
            // infinite loop
            for (; iterUnverifiedB.hasNext(); ) { // while the iteration has next
                tempBlock = iterUnverifiedB.next(); // assign the next Unverified block to the temporary.
                String blockre = gson.toJson(tempBlock); // marshal data in json format as String.
                for (int i = 0; i < processes; i++) { // for loop through all processes that I have
                    // new socket that will connect to all three servers
                    socketUB = new Socket(serverName, Ports.portBaseUPServer + i);
                    toServer = new PrintStream(socketUB.getOutputStream()); // getting the output from the servers
                    toServer.println(blockre); // print the block to all nodes
                    // print message show all nodes that send
                    System.out.println("    ~> Unverified block is sent to [" + i + "] process");
                    toServer.flush(); // flush the message
                    socketUB.close();// close endpoint
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    // this method will send all verivied block through all processes.
    public void sendUpdateToProcesses(CreateBlockRecord updateBlock) {
        Socket socketUpdateB; // socket for update the block recrod
        PrintStream toServer;// print the new block recrod in all nodes
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // create an appropriate structure -> json
        String UpdateJsonFile = gson.toJson(updateBlock); // marshal data in json
        for (int i = 0; i < processes; i++) { // for loop through all process 3.
            try {
                // socket that will connect to the server
                socketUpdateB = new Socket(serverName, Ports.portUpdateBlockRecordBasedServer + i);
                // message that will send to all servers
                toServer = new PrintStream(socketUpdateB.getOutputStream());
                // send the json file to all nodes
                toServer.println(UpdateJsonFile);
                // print out the message that help the user know that we multicasr this blockID to all Processes that we have.
                System.out.printf("    √ verified block [%s]  multicast to process [%d]\n", updateBlock.getBlockID(), i);
                toServer.flush(); // flush messages
                socketUpdateB.close();// close the endpoint.
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    // this method is one of the multicasts to nodes class.
    // if the block is not verified it will send again between all nodes processes
    public void reVerifyBlock(CreateBlockRecord reVerifyBlock_r) {
        Socket socketReVerifyBlock; // new socket
        PrintStream toServer; // printStream to server
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // create json that compatible with json file
        String json = gson.toJson(reVerifyBlock_r); // convert the block record that we passed to json file.
        for (int i = 0; i < processes; i++) { // for loop that will iterate => 3 processes that I have
            try {// try and catch to trace all error that might occur when connect to the server
                socketReVerifyBlock = new Socket(serverName, Ports.portUpdateBlockRecordBasedServer + i);
                // gather all out put from server streamer.
                toServer = new PrintStream(socketReVerifyBlock.getOutputStream());
                // print the json to the server, and catch the input in the client to save the json file
                toServer.println(json);
                // close socket
                socketReVerifyBlock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

// this class will deal with similarity that will occur when I insert verification block.
// Note: because of some time the Work puzzle don't sleep method working well there are  duplicate records that will affect on the final result.
class Similarity {
    // this variable will store the BlockRecord
    private CreateBlockRecord checkSimilarity;

    // setter and getter for Block Record.
    public CreateBlockRecord setCheckSimilarity(CreateBlockRecord checkSimilarity) {
        this.checkSimilarity = checkSimilarity;
        return checkSimilarity;
    }

    // setter and getter for Block Record.
    public CreateBlockRecord getCheckSimilarity() {
        return checkSimilarity;
    }

    // in this method I will check if the Blockchain record has duplicate values.
    public boolean similarity(CreateBlockRecord block) {
        //here I initialize a new CreateBlockRecord variable that will store the
        CreateBlockRecord currentBlockTemp = block;
        // create variable to check if the method will return true => if there is any similarity
        boolean isDuplicated = false; // is not douplicate initial variable.
        for (int i = 0; i < blockChainWorker.blockChainLedger.size(); i++) {
            // loop through all blockledgers that we have in the linked list.
            //then determine if the block ledger has duplicate..
            if (currentBlockTemp.getBlockID().equals(blockChainWorker.blockChainLedger.get(i).getBlockID())) {
                // if catch any similarity  change the variable to true
                isDuplicated = true;
                // return it.
                return isDuplicated;
            }
        }
        return isDuplicated;
    }
}

// Part of professor Elliot's utilities code => I've tried to simplify it.
class SignBlock {
    // define  private variable that will store the bytesData
    private byte[] bytesDate;

    // define  private variable that will store the Private Key
    private PrivateKey privateKeys;

    // setter and getter for the private Keys
    public void setPrivateKeys(PrivateKey privateKeys) {
        this.privateKeys = privateKeys;
    }

    // setter and getter for the private Keys
    public PrivateKey getPrivateKeys() {
        return privateKeys;
    }

    // setter and getter for the BytesDate
    public void setBytesDate(byte[] bytesDate) {
        this.bytesDate = bytesDate;
    }

    // setter and getter for the BytesDate
    public byte[] getBytesDate() {
        return bytesDate;
    }

    // method
    /*We create a signature object using the SHA1withRSA algorithm.
    2. We initialize the signature object with our private key.
    3. We update the signature object with our data (the bytesDate variable).
    4. Finally, we sign and return the result of this operation (the signed data).
     */
    public byte[] signItemBlock(byte[] bytesDate, PrivateKey privateK) throws SignatureException, InvalidKeyException, NoSuchAlgorithmException {
        // create a signature Object that will use SHA with RSA algorithm to sign the block
        Signature signature = Signature.getInstance("SHA1withRSA");
        // Then, initialize value of the signutre and use the private key that we used
        signature.initSign(privateK);
        //Update the signature that was created with bytesData
        signature.update(bytesDate);
        // Return the results signed data.
        return (signature.sign());
    }
}

// Work method that will help to solve the puzzle
// here I used many codes from WorkA and WorkB classes that were given by Professor Elliot.
class WorkClass implements Runnable {
    // create a variable that will store the block queue => from CreateBlockRecord class that contain of block data
    public static BlockingQueue<CreateBlockRecord> blockQ;
    //Final alphaNumericStr that will not change never, and we will use it
    /*. It creates a StringBuilder object.
    2. It loops over the count variable and appends a random character to the string builder.
    3. The loop stops when count is 0, which means that it will append characters until count is 0 (count-- != 0).
    4. The method returns the string builder as a string (stringBuilder.toString()).
    5. We call this method in our test: ```String randomStr = UtilsForTestsService.randomAlphaNumeric(10);```*/
    private static final String alphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // constructor for work class that will call when I want to do work on the   => Work.
    public WorkClass(PriorityBlockingQueue<CreateBlockRecord> receiveBlockQueue) {// pass the Block queue
        this.blockQ = receiveBlockQueue;
    }

    // create a new instance of the MulticastToAllPeers, then use it to share the block between all peers.
    MulticastToAllPeers multicast = new MulticastToAllPeers();
    // create a new instance of the similarity
    Similarity checkSimilarity = new Similarity();
    // create a new instance of the SignBlock to sign hash value inside the block.
    SignBlock sign_block = new SignBlock();
    // store the current key that  asked by using all keys that stored.
    PublicKey currentKey;
    // boolean is this block is verified or not.
    boolean isBlockVerified;
    // this boolean variable will check similarity.
    boolean isSimilar;
    // String will assign later tha hash value
    String signBlock = "";
    //define the random alphabetic => A->Z 0->9
    String random;
    //Digital signature
    byte[] Digital_signature;
    // hash value of the BlockChain value..
    byte[] bytesValHash;
    // Block data with random seed.
    String concatenated;
    // will create hash with algorithm SHA-256
    String stringSHA256Hash;

    @Override
    public void run() {
        System.out.println("    |> Solve Puzzle here  <|    ");
        try {
            boolean isWorkNotCompleted = true;
            for (; isWorkNotCompleted; ) {
                /*  while (true) {*/
                //CreateBlockRecord tempBlockRecord = blockChainWorker.blockPriorityQueue.take();
                CreateBlockRecord checkMyRecord = checkSimilarity.setCheckSimilarity(blockChainWorker.blockPriorityQueue.take());
                // this variable will assist to get the process number that store in the Block chain record
                int process_number_block = Integer.parseInt(checkMyRecord.getBlockProcess_number());
                // hashmap that contain all public keys that stored when the program established public key Server.
                HashMap<String, PublicKey> publicKeyHashMap = blockChainWorker.publicKeysMap;
                // String builder to build the record
                StringBuilder buildBlockRecord;
                // called build record in order to build blockId and  sign block
                buildBlockRecord = buildRecord(checkMyRecord);
                // this boolean variable to check if my block records has similarities or not.
                isSimilar = checkSimilarity.similarity(checkMyRecord);
                // update the current public key
                currentKey = getBlockKey(process_number_block, publicKeyHashMap);
                // check is this block is verified or not
                isBlockVerified = checkBlockSign(checkMyRecord, currentKey);
                // get the previous the block ID.
                String prevBlockID = blockChainWorker.blockChainLedger.get(0).getBlockID();
                // assign concatenated to the string of build block record.
                String updateBlockConc = buildBlockRecord.toString();
                // update the value with winning hash value.
                updateBlockConc += blockChainWorker.blockChainLedger.get(0).getWinningHashBlock();
                int WorkNumber; // work number
                if (!isSimilar) { // if the ledger has no similarity values
                    for (int i = 0; i < 20; i++) { // in order to solve the puzzle fast will iterate 20 times
                        random = randomAlphaNumeric(8); // assign the random alpha => count 8
                        concatenated = updateBlockConc + random; // merge block that I should update with random.
                        // create  a new instance of MessageDigest with SHA-256 algorithm.
                        MessageDigest msgDig = MessageDigest.getInstance("SHA-256");

                        // update the bytesValue
                        bytesValHash = msgDig.digest(concatenated.getBytes("UTF-8"));
                        // convert the from byte to string
                        stringSHA256Hash = byteArrayToString(bytesValHash);
                        // WorkNumber takes the first 4 characters of the hash string
                        WorkNumber = Integer.parseInt(stringSHA256Hash.substring(0, 4), 16);
                        // get the private key from KeyPair
                        PrivateKey prK = blockChainWorker.keyPair.getPrivate();
                        // sign block that
                        sign_block.setBytesDate(stringSHA256Hash.getBytes());
                        // set the private key in order to send it to the sing_block -> sign item() method
                        sign_block.setPrivateKeys(prK);
                        // send the digital signature to the signItemBlock to add the digital signature.
                        Digital_signature = sign_block.signItemBlock(stringSHA256Hash.getBytes(), sign_block.getPrivateKeys());
                        //signBlock is assign to function that converts the encoded digital signature to string.
                        signBlock = Base64.getEncoder().encodeToString(Digital_signature); // get Base64 encoder then change it to the String.
                        System.out.printf("     √ The Digital signature of the block is [%s] \n", signBlock);
                        if (!(WorkNumber < 20000)) { // if the work number not less than 2000 -> we should solve it again.
                            System.out.format("     X [%d] is not less than 20,000. Solving Puzzle Again!\n\n", WorkNumber);
                        }
                        if (WorkNumber < 20000) {
                            if (!prevBlockID.equals(blockChainWorker.blockChainLedger.get(0).getBlockID())) {
                                System.out.printf("    -> Send block [%s] to all nodes in order to re-verify it. \n", checkMyRecord.getBlockID());
                                // send again to all nodes in order to verify it again.
                                multicast.reVerifyBlock(checkMyRecord); // reVerify block because cannot solve it.
                            }
                            // set the winning has it solved
                            checkMyRecord.setWinningHashBlock(stringSHA256Hash);
                            //set the random seed for the block whihc solved
                            checkMyRecord.setRandomSeed(random);
                            // here is the lese if the work number more that 20000, so that mean the puzzle solved.
                            System.out.format("     √ [%d] is less than 20,000. ** Puzzle has Solved! ** \n", WorkNumber);
                            System.out.println("    √ Puzzle answer for this block is => : " + random);
                            // set the winning hash becase we solved the problem.
                            checkMyRecord.setPreviousHashBlock(blockChainWorker.blockChainLedger.get(0).getWinningHashBlock());
                            System.out.printf(" ~> Block Id => [%s] , the Winning random is [%s]\n", checkMyRecord.getBlockID(), checkMyRecord.getRandomSeed());
                            // set the verification process that solve the puzzle.
                            checkMyRecord.setVerification_process(Integer.toString(blockChainWorker.process));
                            // hash the winning value.
                            checkMyRecord.setWinningSignHashVal(signBlock);
                            // add a new item to the linked list

                            blockChainWorker.blockChainLedger.add(0, checkMyRecord);
                            // send the block to all processes to update ledger
                            multicast.sendUpdateToProcesses(checkMyRecord);
                            // sleep 2000 millisecond to rearrange all procedures.
                            Thread.sleep(2000);
                            break;
                        }
                        // if find any similarity break the loop
                        if (isSimilar) {
                            // change the isWorkNotCompleted to false and break the loop
                            isWorkNotCompleted = false;
                            if (isWorkNotCompleted == false) {
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // this method will extract the public key from the hashmap that store in the blockchainWorker
    public static PublicKey getBlockKey(int process_num, HashMap<String, PublicKey> keys) { // pass two parameters [process number and hash map keys].
        PublicKey getKeyByProcessNumber; // new variable that will store the public key, then return it
        getKeyByProcessNumber = keys.get(Integer.toString(process_num)); // get the key from the hash map
        return getKeyByProcessNumber;// return the key to solve the puzzle
    }

    // this will build record by using StringBuilder and help to simplified complex code.
    public static StringBuilder buildRecord(CreateBlockRecord blockchainRecord) {// pass blockRecord to the method.
        StringBuilder newString = new StringBuilder();// new StringBuilder that will build by using BlockchainRecord
        newString.append(blockchainRecord.getBlockID()); // get the Block id and add it to the new String
        newString.append(blockchainRecord.getSignBlock()); // get sing block and add it to the new string.
        return newString; // return the string that I built.
    }


    public static String randomAlphaNumeric(int count) {
        //create a new StringBuilder object.
        StringBuilder stringBuilder = new StringBuilder();
        // loop through the count variable and append a random character from the alphaNumericStr string to our StringBuilder object.
        while (count-- != 0) {
            int character = (int) (Math.random() * alphaNumericStr.length());
            //append a random character from the alphaNumericStr string to our StringBuilder object
            stringBuilder.append(alphaNumericStr.charAt(character));
        }
        //return the string representation of our StringBuilder object as a result of this method call
        return stringBuilder.toString();
    }

    // this method will check if the block is signed or not
    public boolean checkBlockSign(CreateBlockRecord block, PublicKey pk) throws Exception {
        //takes the blockID of the block and converts it to bytes.
        String blockID = block.getBlockID(); // The bytes of the blockID. - The public key that was used to sign this block (the miner's public key).
        //the signature of this block (that we got from CreateBlockRecord).
        return (isVerify(blockID.getBytes(), pk, Base64.getDecoder().decode(block.getSignBlock())));//If isVerify returns true, then checkBlockSign returns true as well, otherwise false will be returned.
    }

    // This code from professor Elliot utilities, and this code will help to check if the block is verify or not.
    public boolean isVerify(byte[] bytesData, PublicKey publicKey, byte[] decode) throws Exception {
        //create a signature object with the algorithm "SHA1withRSA".
        Signature signature = Signature.getInstance("SHA1withRSA");
        //initialize the signature object with our public key.
        signature.initVerify(publicKey);
        //update the signature object with our data (bytesData).
        signature.update(bytesData);
        //verify that the data is signed by our private key using decode as a parameter (decode is actually the encoded version of bytesData).
        return (signature.verify(decode));
    }

    // this method is part of professor Elliot's utility code, and the code help to convert from byte array to String
    public static String byteArrayToString(byte[] ba2s) { // pass the byte[] to convert from the byte to string.
        StringBuilder hexString = new StringBuilder(ba2s.length * 2);
        for (int i = 0; i < ba2s.length; i++) {
            //result is a string of hexadecimal characters that represent the original bytes in the array.
            hexString.append(String.format("%02X", ba2s[i])); //  loops through the byte array and converts each byte to its hexadecimal representation.
        }
        return hexString.toString(); // string of hexadecimal characters as its output value (the return type is String)
    }
}



