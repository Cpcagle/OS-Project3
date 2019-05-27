import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @version 4/10/19
 * @author Cameron Cagle
 * This is a threaded class that executes the finite state machine.
 */
public class Markov implements Callable<Data>{
    /** determines which state the finite state machine is in. */
    private int state;
    /** determines how many interations each machine will execute. */
    private int iterations;
    /** holds the data for the finite state machine. */
    private Data info;

    /**
     * Deafault constructor for the Markov class.
     */
    public Markov(){
        this.state = 0;
        this.iterations = 0;
        this.info = new Data();
    }

    /**
     * Constructor for Markov class that uses a startState, a number of iterations, and
     * a Data object to run a machine.
     * @param startState the state in which the finite state machine will start.
     * @param iterations the number of iterations each machine will perform.
     * @param info Data object used for message passing and holding information about the FSM.
     */
    public Markov(int startState, int iterations, Data info){
        this.state = startState;
        this.iterations = iterations;
        this.info = info;
    }

    /**
     * This function is called by the thread to run the finite state machine.
     * @return a Data object with the result, id, and representation.
     */
    public Data call() {
        try {
            double[][] machine = info.getRepresentation();
            double probability;
            for (int i = 0; i < iterations; i++){
                double sum = 0.0;
                probability = ThreadLocalRandom.current().nextDouble();
                for (int j = 0; j < machine.length; j++){
                    sum = sum + machine[this.state][j];
                    if (probability <= sum ){
                        this.state = j;
                        j = machine.length;
                    }
                }
            }
            info.setResult(this.state);
        }
        catch(Exception e){
            System.out.println("somebody interrupted the party");
        }
        return info;
    }


}
