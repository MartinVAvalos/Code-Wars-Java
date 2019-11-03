import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
@SuppressWarnings("unchecked")
public class main
{
    public static void main(String[] args)
    {
        // Table.hello();   
        Scanner input = new Scanner(System.in);

        /*** RETRIEVE Q ***/
        //Ask for number of states
        int qCardinality = FTuple.askForQLength();
        //Generate State set of the NFA
        int[] nfaQ = FTuple.createQ(qCardinality);
        //Print State set of NFA
        FTuple.printQ(nfaQ);

        /*** RETRIEVE E ***/
        //Ask for Alphabet of NFA
        int eCardinality = FTuple.askForELength();
        //Generate Alphabet set of NFA
        String[] nfaE = FTuple.createE(eCardinality);
        //Print Alphabet set of NFA
        FTuple.printE(nfaE);

        /*** RETRIEVE NFA***/
        //Ask for transition info on NFA
        FTuple.askForN();
        //Create NFA
        List<Integer[]> nfa = FTuple.createN(nfaQ, nfaE);
        // FTuple.printN(nfa);

        /*** RETRIEVE Start State***/
        //Ask for start State and create startState Variable
        int startState = FTuple.findStartState();

        /*** RETRIEVE Start Final States***/
        //Ask for the final states
        List<Integer[]> finalStates = FTuple.findFinalStates();

        /*** RETRIEVE dfa***/
        // List<List<Integer>> dfa = 
        FTuple.nfa2dfa(nfaQ, nfaE, nfa, startState, finalStates);
    }
}