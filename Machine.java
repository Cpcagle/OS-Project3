import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @version: 3/26/19
 * @author: Cameron Cagle
 * This class starts and collects results from all threads. All in put and output is done here.
 */
public class Machine {
    /** Is the total number of finite state machines to create. */
    private int fsm;
    /** Is the number of iterations each machine should execute. */
    private int iterations;
    /** Is the max number of threads to be run at any one time. */
    private int threadAmt;
    /** Holds the results of each finite state machine. */
    private int[] computation;
    /** Is the input file given from the user. */
    private File fileName;

    /**
     * This class will create finite state machines that run on 100 threads concurrently.
     */
    public void go(){
        double[][] matrix = readFile(fileName);
        int startState = ThreadLocalRandom.current().nextInt(matrix.length);
        this.computation = new int[matrix.length];
        ExecutorService executor = Executors.newFixedThreadPool(100);

        Markov[] machines = new Markov[fsm];
        ExecutorCompletionService<Data> exCompServ = new ExecutorCompletionService<Data>(executor);
        for (int i = 0; i < this.fsm; i++) {
            Data info = new Data(i, matrix);
            machines[i] = new Markov(startState, iterations, info);
        }
        for (int i = 0; i < this.fsm; i++){
            exCompServ.submit(machines[i]);
        }
        try {
            for (int i = 0; i < this.fsm; i++) {
                Data result = exCompServ.take().get();
                // this will increment that number at the end state of the array
                this.computation[result.getResult()]++;
                //This will test the output of the machines and determine whether
                //they are running concurrently.
                //**********UNCOMMENT PRINT STATEMENT BELOW************
                //System.out.println(result.getID());
            }
        }
        catch (InterruptedException | ExecutionException exception)  {
            System.out.println("This was interrupted");
        }
        executor.shutdown();
    }

    /**
     * This function will display the steady state results.
     */
    public void display(){
        System.out.println("\nSteady state results: ");
        for (int i = 0; i < this.computation.length; i++) {
            double answer = ((double)this.computation[i]/(double)this.fsm);
            System.out.println("State " + i + ": " +  answer + "%");
        }
    }

    /**
     * Constructor for a machine with a start state passed in as a parameter.
     * @param startState is the state in which the finite state machine will start.
     */
    public Machine(String startState){
        Scanner kb = new Scanner(System.in);
        String inputFile = "";

        this.fsm = checkInt(kb, "How many Finite State Machines to create? > ");
        this.iterations = checkInt(kb, "How many iterations for each machine? > ");
        this.threadAmt = checkInt(kb, "How many threads? > ");
        System.out.print("Please enter input fileName > ");
        while(inputFile == ""){ // file is empty
            inputFile = kb.next();
        }
        fileName = new File(inputFile);
        if (!(fileName.exists())){ // file doesnt exist
            System.out.println("This file does not exist");
            System.exit(1);
        }
    }

    /**
     * Constructor for a machine with a random start state.
     */
    public Machine(){
        Scanner kb = new Scanner(System.in);
        String inputFile = "";
        this.fsm = checkInt(kb, "How many Finite State Machines to create? > ");
        this.iterations = checkInt(kb, "How many iterations for each machine? > ");
        this.threadAmt = checkInt(kb, "How many threads? > ");
        System.out.print("Please enter input fileName > ");
        while(inputFile == ""){ // file is empty
            inputFile = kb.next();
        }
        fileName = new File(inputFile);
        if (!(fileName.exists())){ // file doesnt exist
            System.out.println("This file does not exist");
            System.exit(1);
        }
    }

    /**
     * This function reads in a matrix from a file. The matrix passed in has the rows and columns
     * switched.
     * @param inFile is the input file the user passed in.
     * @return a matrix with transition probabilities from one state to another.
     */
    private double[][] readFile(File inFile){
        int lineCount = 0;
        double[][] matrix = null;
        int matrixLength = 0;
        try{
            Scanner scan = new Scanner(inFile);
            matrixLength = scan.nextInt();
            matrix = new double[matrixLength][matrixLength];
            while (scan.hasNextDouble()){
                for (int i = 0; i < matrixLength; i++){
                    matrix[i][lineCount] = scan.nextDouble();
                }
                lineCount++;
            }
            return matrix;
        }
        catch(FileNotFoundException fnfe){
            System.out.println("fuckckckck");
        }
        return matrix;
    }

    /**
     * This function checks if the input given to the scanner is an integer.
     * @param kb is the scanner being used for input.
     * @param message is what the user is being asked to input to the program.
     * @return the integer the user entered.
     */
    private int checkInt(Scanner kb, String message){
        int num = 0;
        boolean isNumber = false;
        do {
            System.out.print(message);
            if(kb.hasNextInt()){
                num = kb.nextInt();
                isNumber = true;
            }
            else{
                System.out.println("Please enter an integer");
                isNumber = false;
                kb.next();
            }
        } while(!(isNumber));

        return num;
    }

    /**
     * This function runs a given amount of finite state machines over a given amount of iterations
     * on a given amount of threads.
     * @param args if args is null then the start state of the finite state machines are random.
     *             if args.length = 1 then a start state is given.
     */
    public static void main(String[] args){

        if (args.length > 1){
            System.out.println("Program accepts at most one command line argument.");
            System.exit(1);
        }
        try{
            Machine driver;
            if (args.length == 0){ // random start state.
                driver = new Machine();
            }
            else{ // given start state.
                driver = new Machine(args[0]);
            }
            driver.go(); // runs the finite state machines.
            driver.display();// displays the results of the machines.
        }
        catch(NumberFormatException nfe){
            System.out.println("what");
        }
    }











}
