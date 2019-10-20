import java.util.*;
public class SubStringFinder
{
    public static void main(String[] args)
    {
        String[] search4 = {"lemon", "red", "mighty", "star", "light", "havoc"};
        String[] list = {"red", "sporty", "starlight", "flag"};
        inArray(search4, list);
    }

    public static String[] inArray(String[] search4, String[] list) {
        // array[1].contains(word);
        ArrayList<String> al = new ArrayList<String>(); 
        for(int sIndex = 0; sIndex < search4.length; sIndex++) {
            for(int lIndex = 0; lIndex < list.length; lIndex++) {
                if(list[lIndex].contains(search4[sIndex]) && !al.contains(search4[sIndex])) {
                    al.add(search4[sIndex]);
                    System.out.println(search4[sIndex]); 
                }
            }
        }
        String[] answer = new String[al.size()];
        answer = al.toArray(answer);

		 return answer;
	}
}