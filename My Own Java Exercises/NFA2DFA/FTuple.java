import java.util.*;
@SuppressWarnings("unchecked")
public class FTuple {
    
    private static Scanner input = new Scanner(System.in);

    /*** Methods for Q ***/
    public static int askForQLength() {
        System.out.println("Please enter number of the states:");
        int arrayLength = input.nextInt();
        if(arrayLength > 0) 
            return arrayLength;
        while(arrayLength <= 0) {
            System.out.println("Enter number greater than 0");
            arrayLength = input.nextInt();
        }
        return arrayLength;
    }

    public static int[] createQ(int arrayLength) {
        int[] array = new int[arrayLength];
        for(int i = 0; i<arrayLength; i++) {
            array[i] = i+1;
        }
        return array;
    }

    public static void printQ(int[] array) {
        System.out.print("State set of the NFA = {");
        for (int i = 0; i< array.length; i++) {
            if(array.length-1 == i) {
                System.out.println(array[i] + "}");
            } 
            else {
                System.out.print(array[i] + ", ");
            }
        }
    }

    /*** Methods for E ***/
    public static int askForELength() {
        System.out.println("Please enter number of the symbols in the alphabet:");
        int arrayLength = input.nextInt();
        if(arrayLength > 0) 
            return arrayLength;
        while(arrayLength <= 0) {
            System.out.println("Enter number greater than 0");
            arrayLength = input.nextInt();
        }
        return arrayLength;
    }

    //cannot create Alphabet that exceeds "z"
    public static String[] createE(int arrayLength) {
        String[] array = new String[arrayLength];
        int letter = 97;
        for(int i = 0; i<arrayLength; i++) {
            // array[i] = i;
            array[i] = String.valueOf((char)letter);
            letter = letter+1;
        }
        return array;
    }

    public static void printE(String[] array) {
        System.out.print("Alphabet of the NFA = {");
        for (int i = 0; i< array.length; i++) {
            if(array.length-1 == i) {
                System.out.println(array[i] + "}");
            } 
            else {
                System.out.print(array[i] + ", ");
            }
        }
    }

    /*** Methods for Tranistion Function ***/
    public static void askForN() {
            System.out.println("Enter the transition function in set format \"{1, 2, ...}\":");

    }

    public static List<Integer[]> createN(int[] states, String[] alphabet) {
        /* Every time you call input.nextInt() follow it with input.nextLine() so that the end of line token is swallowed. Line 85 addresses this bug.*/
        input.nextLine();
        List<Integer[]> al = new ArrayList<>();
        int statesXalphabet = states.length * (alphabet.length+1); //includes epsilon
        int stateElement = 0;
        int alphabetElement = 0;
        for(int s = 0; s < statesXalphabet; s++) {
            alphabetElement = (s+1) % (alphabet.length+1);
            // System.out.println("yay"+ alphabetElement);
            if(alphabetElement % (alphabet.length+1) == 0) {
                System.out.print("Delta(" + states[stateElement] + ",epsilon) = ");
                al.add(acceptSet());
                alphabetElement = 0;
                stateElement++;
            }
            else {
                System.out.print("Delta(" + states[stateElement] + "," + alphabet[alphabetElement-1] + ") = ");
                al.add(acceptSet());
                alphabetElement = alphabetElement + 1;
            }

        }
        return al;
    }

    public static Integer[] acceptSet() {
        List<Integer> al = new ArrayList<Integer>();
        String transition;
        String number = "";
        int n = 0;
        transition = input.nextLine();
        for (char ch : transition.toCharArray()) {
            //if number found in string, concatonate to number
            if (Character.isDigit(ch)) {
                number = number + String.valueOf(ch);
            }
            else {
                //convert number string into number, then reset number string into ""
                if(!number.isEmpty()) {
                    n = Integer.parseInt(number);
                    al.add(n);
                    number = "";
                }
            }
        }
        Integer[] array = new Integer[al.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = al.get(i);
        }
        return array;
    }
// List<Integer[]> al
    public static void printN(List<Integer[]> al) {
        for (int i = 0; i < al.size(); i++) {
            for (int j = 0; j < al.get(i).length; j++) {
                System.out.println("i" + i + " ----- j" + j);
                System.out.println(al.get(i)[j]);
            }
            System.out.println();
        }
    }

    public static Integer findStartState() {
        System.out.println("Please enter the start state");
        int start = input.nextInt();
        input.nextLine();
        return start;
    }
    
    public static List<Integer[]> findFinalStates() {
        System.out.println("Please enter all final states on one line format \"{1, 2, ... n\":");
        List<Integer[]> fStates = new ArrayList<>();
        fStates.add(acceptSet());
        return fStates;
    }

    // nfaQ, nfaE, nfa, startState
    public static List<Integer[]> nfa2dfa(int[] nfaStates, String[] alphabet, List<Integer[]> nfa, int sState, List<Integer[]> fStates) {
        List<Integer[]> nfaAsDFA = new ArrayList<>();
        List<Integer[]> dfa = new ArrayList<>();
        int epsilonOfStart = alphabet.length+1;
                nfaAsDFA.add(grabStartofDFA(nfa, sState, epsilonOfStart));
                // Successful at grabbing index epsilon of start state. Use line 176
                //to convert epsilon set into an Integer array and return it.

                //returns for dfa // findSetOfDFAState();
                System.out.println("it's time boiz");
                // alphabet.lenth, 3c
                // getNFASet(nfa, 4, 3, 2);

                getNFASet(nfa, 2, 1, 0);

        
        // dfa ex: A = 1+
        //DFA(A, a) = {b}
        //DFA(A, b) = {empty}
        //DFA(B, a) = {B}
        //DFA(B, b) = {C}
        // public static String[] createE(int arrayLength) {
        // String[] array = new String[arrayLength];
        // int letter = 97;
        // for(int i = 0; i<arrayLength; i++) {
        //     // array[i] = i;
        //     array[i] = String.valueOf((char)letter);
        //     letter = letter+1;
        // }
        // return array;
        // }



        //Map DFA's start state

        //Find rest of DFA
        // fStates.add(acceptSet());
        return dfa;
    }

    public static Integer[] grabStartofDFA(List<Integer[]> nfa2dfa, int startState, int epsilon) {
        List<Integer> al = new ArrayList<>();
        int epsilonOfStart = startState * epsilon - 1;
        for (int i = 0; i < nfa2dfa.get(epsilonOfStart).length; i++) {      
            // System.out.println("nfa2dfa.get("+epsilonOfStart+")["+i+"] =  " +nfa2dfa.get(epsilonOfStart)[i]);
            al.add(nfa2dfa.get(epsilonOfStart)[i]);
        }
        al.add(startState);

        List<Integer> listWithoutDuplicates = new ArrayList<> (new HashSet<>(al));
  
        Integer[] startStateSet = new Integer[listWithoutDuplicates.size()];
        startStateSet = listWithoutDuplicates.toArray(startStateSet);
        for (Integer x : startStateSet) 
            System.out.print(x + " "); 

        return startStateSet;
    }

    public static Integer[] getNFASet(List<Integer[]> nfa, int aLength, int state, int alphabetElement) {
        List<Integer> al = new ArrayList<>();
        int alphabetIndex = state * aLength-(aLength-alphabetElement);
        for (int i = 0; i < nfa.get(alphabetIndex).length; i++) {      
            System.out.println("nfa.get("+alphabetIndex+")["+i+"] =  " +nfa.get(alphabetIndex)[i]);
            al.add(nfa.get(alphabetIndex)[i]);
        }

        List<Integer> listWithoutDuplicates = new ArrayList<> (new HashSet<>(al));
  
        Integer[] startStateSet = new Integer[listWithoutDuplicates.size()];
        startStateSet = listWithoutDuplicates.toArray(startStateSet);
        for (Integer x : startStateSet) 
            System.out.print(x + " "); 

        return startStateSet;
    }

    // Integer[]        List<Integer[]> nfa2dfa, int startState, int epsilon
    public static void findSetOfDFAState(Integer[] nfaSetAsDFA, int[] nfaStates, String[] alphabet, List<Integer[]> nfa, int sState, List<Integer[]> fStates) {
        List<Integer> al = new ArrayList<>();
        
        // for(int nfaAsDFACounter = 0; nfaAsDFACounter < nfaSetAsDFA.length; nfaAsDFACounter++) {

        // }
        
        // int epsilonOfStart = startState * epsilon - 1;
        // {1, 3}
        for(int nfaAsDFACounter = 0; nfaAsDFACounter < nfaSetAsDFA.length; nfaAsDFACounter++) {
            for (int i = 0; i < nfa.get(nfaAsDFACounter).length; i++) {      
                // al.add(nfa2dfa.get(epsilonOfStart)[i]);
                System.out.println(nfa.get(nfaSetAsDFA[i])[i]);
            }
        }

        List<Integer> listWithoutDuplicates = new ArrayList<> (new HashSet<>(al));
  
        Integer[] startStateSet = new Integer[listWithoutDuplicates.size()];
        startStateSet = listWithoutDuplicates.toArray(startStateSet);
        // for (Integer x : startStateSet) 
        //     System.out.print(x + " "); 

        
        // return startStateSet;
    }
}