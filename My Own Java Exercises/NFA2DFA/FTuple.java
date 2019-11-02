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
    public static void nfa2dfa(
        int[] nfaStates, String[] alphabet, 
        List<Integer[]> nfa, 
        int sState, 
        List<Integer[]> fStates) {
        ArrayList<ArrayList<Integer>> dfaState = new ArrayList();
        ArrayList<ArrayList<ArrayList<Integer>>> mergedArray = new ArrayList();
        ArrayList<ArrayList<ArrayList<Integer>>> temp = new ArrayList();
        ArrayList<ArrayList<Integer>> dfa = new ArrayList();
        ArrayList<Integer> zero = new ArrayList<>(Arrays.asList(0));
        int epsilon = alphabet.length+1;

        dfaState.add(epsilonEnclosure(nfa, sState, epsilon));

        int currentDFAState = 0;
        mergedArray.add(mergingArrays(dfaState.get(0), nfa, alphabet.length));
        System.out.println("It's time boiz");System.out.println("It's time boiz");System.out.println("It's time boiz");

        // for(int i=0; i < mergedArray.get(mergedArray.size() - 1).get(mergedArray.size() -1); i++) {
            System.out.println("hello: " + mergedArray.get(mergedArray.size() - 1).get(0));
        // }
        // dfaState.add(mergedArray.get(mergedArray.size() - 1).get(0));
        // mergedArray.add(mergingArrays(dfaState.get(1), nfa, alphabet.length));

        // dfaState.add(mergedArray.get(mergedArray.size() - 1).get(1));


        ArrayList<Integer> save1 = new ArrayList();
        ArrayList<Integer> save2 = new ArrayList();
        boolean complete = false; 
        while(!complete) {
            for(int i = 0; i < dfaState.size(); i++) {
                for(int j = 0; j < mergedArray.get(mergedArray.size() - 1).size(); j++) {
                    System.out.println("j: " + j);
                    System.out.println("mergeSize: " + mergedArray.get(mergedArray.size() - 1).size());
                    System.out.println("dfa" + dfaState.get(i).toString());
                    System.out.println("merged" + mergedArray.get(mergedArray.size() - 1).get(j).toString());
                    if(!dfaState.get(i).equals(mergedArray.get(mergedArray.size() - 1).get(j))
                        && !mergedArray.get(mergedArray.size() - 1).get(j).equals(zero)) {
                        
                        System.out.println("New DFA State");
                        dfaState.add(mergedArray.get(mergedArray.size() - 1).get(j));
                        temp.add(mergingArrays(dfaState.get(dfaState.size() - 1), nfa, alphabet.length));
                    }
                    else {
                        complete = true;
                    }
                }
                for(ArrayList<ArrayList<Integer>> al: temp) {
                    mergedArray.add(al);
                }
            }
            // ArrayList<Integer> noDupes = new ArrayList<>(new HashSet<>(al.get(al.size() - 1)));
        }
        printDFA(dfaState, mergedArray, currentDFAState, alphabet);
        printDFA(dfaState, mergedArray, 1, alphabet);
        printDFA(dfaState, mergedArray, 2, alphabet);
        
    }

    public static ArrayList<Integer> findE(List<Integer[]> nfa, int state, int epsilon) {
        ArrayList<Integer> al = new ArrayList<>();
        int epsilonOfStart = state * epsilon - 1;
        if (state == 0) { //this means the state is going to an empty state (dead state)
            al.add(0);
            return al;
        }
        for (int i = 0; i < nfa.get(epsilonOfStart).length; i++) {      
            al.add(nfa.get(epsilonOfStart)[i]);
        }
        al.add(state);

        Collections.sort(al); 
        ArrayList<Integer> epsilonSet = new ArrayList<> (new HashSet<>(al));
  
        // List to Array Conversion 
        // for (int i =0; i < noDupes.size(); i++) 
        //     epsilonSet[i] = noDupes.get(i);
  
        // System.out.println("find E:"); 
        // for (Integer x : epsilonSet) 
        //     System.out.print(x + " ");
        // System.out.println(); 

        return epsilonSet;
    }

    public static ArrayList<Integer> epsilonEnclosure(
        List<Integer[]> nfa, 
        int state,
        int epsilon) {

        ArrayList<ArrayList<Integer>> al = new ArrayList();
        ArrayList<ArrayList<Integer>> save = new ArrayList();
        ArrayList<ArrayList<Integer>> temp = new ArrayList();

        al.add(findE(nfa, state, epsilon));
        boolean unique = false;
        for (int i = 0; i < al.get(0).size(); i++) {
            if(al.get(0).get(i) != state) {
                unique = true;
            }
        }

        if(!unique) {
            // System.out.println("Enclosure: Not Unique");
            return al.get(0);
        }
        else {
            boolean complete = false;
            while(!complete) {
                //save to compare later
                save.add(al.get(al.size()-1));

                //add all epsilons, and then remove duplicates
                for(int i = 0; i < al.get(al.size()-1).size(); i++) {
                    if(i > 0) {
                        temp.add(findE(nfa, al.get(al.size() - 1).get(i), epsilon));
                        for (int j = 0; j < temp.get(temp.size() - 2).size(); j ++) {
                            temp.get(temp.size() - 1).add(temp.get(temp.size() - 2).get(j));
                        }
                    }
                    else {
                        temp.add(findE(nfa, al.get(al.size() - 1).get(i), epsilon));
                    }
                }
                ArrayList<Integer> noDupes = new ArrayList<>(new HashSet<>(temp.get(temp.size() - 1)));
                al.add(noDupes);

                if(al.get(al.size() - 1).contains(0) && al.get(al.size() - 1).size() > 1) {
                    al.get(al.size() - 1).removeAll(Arrays.asList(0));
                } 
                // // compare saved with new set
                if(al.get(al.size()-1).equals(save.get(save.size()-1))) {
                    complete = true;
                    // System.out.println("Enclosure: Awesome sauce!");
                }
            }
            return al.get(al.size() - 1);
        }
    }

    public static ArrayList<Integer> getSet(List<Integer[]> nfa, int state, int aLength, int alphabetElement) {
        ArrayList<Integer> al = new ArrayList<>();
        int alphabetIndex = ((state-1) * (aLength + 1)) + alphabetElement;
        //alphabetIndex = state * aLength-(aLength-alphabetElement);
        if (state == 0) { //this means the state is going to an empty state (dead state)
            al.add(0);
            return al;
        }
        for (int i = 0; i < nfa.get(alphabetIndex).length; i++) {      
            // System.out.println("nfa.get("+alphabetIndex+")["+i+"] =  " +nfa.get(alphabetIndex)[i]);
            al.add(nfa.get(alphabetIndex)[i]);
        }

        ArrayList<Integer> set = new ArrayList<> (new HashSet<>(al));
  
        // System.out.println("get Set:"); 
        // for (Integer x : set) 
        //     System.out.print(x + " ");
        // System.out.println(); 

        return set;
    }

    public static ArrayList<Integer> getDFASetofAlphabet(Integer state, List<Integer[]> nfa, int aLength, int alphabetElement) {
        ArrayList<ArrayList<Integer>> al = new ArrayList();
        ArrayList<ArrayList<Integer>> save = new ArrayList();
        ArrayList<ArrayList<Integer>> temp = new ArrayList();

        // getSet(nfa, nfaAsDFA.get(nfaAsDFA.size() - 1).get(0), alphabet.length, 0);
        al.add(getSet(nfa, state, aLength, alphabetElement));
        System.out.println("First added: " + al.get(0).get(0));
        System.out.println("state: " + state);
        boolean unique = false;
        for (int i = 0; i < al.get(0).size(); i++) {
            if(al.get(0).get(0) != state) {
                unique = true;
            }
        }

        if(!unique) {
            // System.out.println("Alphabet: Not Unique");
            return al.get(0);
        }
        else {
            boolean complete = false;
            while(!complete) {
                //save to compare later
                save.add(0, new ArrayList(al.get(al.size()-1)));


                //add all alphabetElement, and then remove duplicates
                for(int i = 0; i < al.get(al.size()-1).size(); i++) {
                    if(i > 0) {
                        temp.add(getSet(nfa, al.get(al.size() - 1).get(i), aLength, alphabetElement));
                        // System.out.println("temp.get(size() - 1)" + temp.get(temp.size() - 1));
                        // System.out.println("temp.get(size() - 2)" + temp.get(temp.size() - 2));
                        for (int j = 0; j < temp.get(temp.size() - 2).size(); j ++) {
                            temp.get(temp.size() - 1).add(temp.get(temp.size() - 2).get(j));
                        }
                    }
                    else {
                        temp.add(new ArrayList(getSet(nfa, al.get(al.size() - 1).get(i), aLength, alphabetElement)));
                        // for(int k = 0; k < temp.get(temp.size() - 2).size(); k++) {
                        //     temp.get(temp.size() - 1).add(temp.get(temp.size() - 2).get(k));
                        // }
                    }
                }
                
                for(int i = 0; i < temp.get(temp.size() - 1).size(); i++) {
                    al.get(al.size() - 1).add(temp.get(temp.size() -1 ).get(i));
                }
                ArrayList<Integer> noDupes = new ArrayList<>(new HashSet<>(al.get(al.size() - 1)));

                al.add(new ArrayList(noDupes));

                Collections.sort(al.get(al.size() - 1));

                Collections.sort(save.get(0));

                // // // compare saved with new set
                // System.out.println("al.get(size() - 1)" + al.get(al.size() - 1));
                // System.out.println("save.get(size() - 1)" + save.get(0));
                if(al.get(al.size() - 1).size() == save.get(0).size()) {
                    for(int i = 0; i<al.get(al.size() - 1).size(); i++) {
                        if(al.get(al.size() - 1).get(i) == save.get(0).get(i)) {
                            complete = true;
                        }
                    }
                }
                if(al.get(al.size()-1).equals(save.get(0))) {
                    if(al.get(al.size() - 1).contains(0) && al.get(al.size() - 1).size() > 1) {
                        al.get(al.size() - 1).removeAll(Arrays.asList(0));
                    } 
                    System.out.println("final al" + al.get(al.size() - 1));
                    complete = true;
                    // System.out.println("Alphabet: Awesome sauce!");
                }
            }
            return al.get(al.size() - 1);
        }
    }

    public static ArrayList<ArrayList<Integer>> mergingArrays(ArrayList<Integer> states, List<Integer[]> nfa, int alphabetLength) {
        ArrayList<ArrayList<Integer>> al = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> save = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < alphabetLength; i++) {
            for(int j = 0; j < states.size(); j++) {
                // System.out.println("state[" + i +"]: " + states.get(j));
                // System.out.println("alphabet: " + i);
                al.add(new ArrayList<Integer>(getDFASetofAlphabet(states.get(j), nfa, alphabetLength, i)));
                if(j > 0) {
                    al.get(al.size() - 1).addAll(al.get(al.size() - 2));
                }
            }
            // System.out.println("testing 1");
                ArrayList<Integer> noDupes = new ArrayList<>(new LinkedHashSet<>(al.get(al.size()-1)));
                // System.out.println("testing 2");
                save.add(new ArrayList(noDupes));
                if(save.get(save.size() - 1).contains(0) && save.get(save.size() - 1).size() > 1) {
                    save.get(save.size() - 1).removeAll(Arrays.asList(0));
                } 
            al.clear();
            // Collections.sort(save.get(save.size() - 1));
        }
        // for (int i = 0; i<save.size(); i++) {
        //     System.out.println("save["+ i +"]: " + save.get(i).toString());
        // }
        return save;
    }

    public static void printDFA(ArrayList<ArrayList<Integer>> dfaState, ArrayList<ArrayList<ArrayList<Integer>>> mergedArray, int currentDFAState,String[] alphabet) {
        for(int alphabetIndex = 0; alphabetIndex < alphabet.length; alphabetIndex++) {
            System.out.print("Delta'({"); 
            // System.out.print("Delta'({" + dfaState.get(1).get(0) + "," + dfaState.get(1).get(1)+ dfaState.get(1).get(2) + "}," + alphabet[0] + ") = {");
            for(int dfaStateIndex = 0; dfaStateIndex < dfaState.get(currentDFAState).size(); dfaStateIndex++){
                System.out.print(dfaState.get(currentDFAState).get(dfaStateIndex));
                if(dfaState.get(currentDFAState).size() - 1 == dfaStateIndex) {
                    System.out.print("}) = ");
                }
                else {
                    System.out.print(",");
                }
            }
            System.out.print("{");
            for(int stateIndex = 0; stateIndex < mergedArray.get(currentDFAState).get(alphabetIndex).size(); stateIndex++) {
                System.out.print(mergedArray.get(currentDFAState).get(alphabetIndex).get(stateIndex));
                if(mergedArray.get(currentDFAState).get(alphabetIndex).size() - 1 == stateIndex) {
                    System.out.print("}");
                }
                else {
                    System.out.print(",");
                }
            }
            System.out.println();
        }
    }
}

// System.out.print("Delta'({" + dfaState.get(1).get(0) + "," + dfaState.get(1).get(1)+ dfaState.get(1).get(2) + "}," + alphabet[0] + ") = {");
        // for(int i = 0; i < mergedArray.get(1).get(0).size(); i++) {
        //     System.out.print(mergedArray.get(0).get(0).get(i));
        //     if(mergedArray.get(0).get(0).size() == i) {
        //         System.out.print("}");
        //     }
        //     else {
        //         System.out.print(",");
        //     }
        // }
        // System.out.println();