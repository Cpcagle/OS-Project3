/**
 * @version: 4/9/19
 * @author: Cameron Cagle
 * This class holds all the information of each machine. It is used for message passing between
 * the machines and threads.
 */
public class Data {
    /** The id of each machine. */
    public int id;
    /** The state a finite state machine ended in. */
    public int result;
    /** A double matrix representation of a finite state machine. */
    public double[][] representation;

    /**
     * Default constructor for the data class.
     */
    public Data(){
        this.id = 0;
        this.result = 0;
        this.representation = new double[0][0];
    }

    /**
     * Constructor for the data class which is defined by the unique id of each machine and its
     * representation.
     * @param id the unique id of the machine.
     * @param representation a double matrix representing a finite state machine.
     */
    public Data(int id, double[][] representation){
        this.id = id;
        this.result = 0;
        this.representation = representation;
    }

    /**
     * This function sets the id of the Data.
     * @param id unique id of a machine.
     */
    public void setID(int id){
        this.id = id;
    }

    /**
     * This function gets the id of Data.
     * @return
     */
    public int getID(){
        return this.id;
    }

    /**
     * This function sets the result of the finite state machine/ Data.
     * @param result the state a finite state machine ended in.
     */
    public void setResult(int result){
        this.result = result;
    }

    /**
     * This function gets the result of the finite state machine/ Data.
     * @return an integer of the state a finite state machine ended in.
     */
    public int getResult(){
        return result;
    }

    /**
     * This function sets the representation of a finite state machine.
     * @param representation a finite state machine.
     */
    public void setRepresentation(double[][] representation){
        representation = representation;
    }

    /**
     * This function gets the representation of a finite state machine.
     * @return representation of a finite state machine.
     */
    public double[][] getRepresentation(){
        return representation;
    }
}
