import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Search {
    
    public static final String ALAN = "100000135760088";
    public static final String YAEL = "100001907592606";
    public static final String PHRASE_TO_FIND = "gradeit";

    public static void main(String[] args) throws FileNotFoundException {
        File messages = new File("AllMessages.txt");
        Scanner input = new Scanner(messages);
        Map<String, Integer> result = allWords(input);
        Map<Integer, List<String>> flip = new TreeMap<Integer, List<String>>();
        for(String cur : result.keySet()){
            int amount = result.get(cur);
            if(!flip.containsKey(amount)){
                flip.put(amount, new ArrayList<String>());
            }
            flip.get(amount).add(cur);
        }
        PrintStream find = new PrintStream("words.txt");
        for(int key : flip.keySet()){
            find.println(key + " = " + flip.get(key));
        }
        find.close();
    }
    
    //returns a map of all the words (keys) and how many times they appear (values)
    public static Map<String, Integer> allWords(Scanner input){
        Map<String, Integer> result = new HashMap<String, Integer>();
        while(input.hasNextLine()){
            String line = input.nextLine();
            Scanner perLine = new Scanner(line);
            if(line.startsWith("[")){
                perLine.next();
                perLine.next();
            }
            while(perLine.hasNext()){
                String cur = perLine.next();
                String lower = cur.toLowerCase();
                lower = findKey(lower);
                if(!result.containsKey(lower)){
                    result.put(lower, 0);
                }
                result.put(lower, result.get(lower) + 1);
            }
            perLine.close();
        }
        return result;
    }
    
    //returns the String passed in without punctuation ("hello!!" becomes "hello")
    //if the whole word is punctuation, returns it instead
    private static String findKey(String pass){
        String result = "";
        for(int i = 0; i < pass.length(); i++){
            char cur = pass.charAt(i);
            if((cur >= 'a' && cur <= 'z') || (cur >= '0' && cur <= '9')){
                result += cur;
            }
        }
        if(result.isEmpty()){
            return pass;
        }
        return result;
    }
    
    //prints how many times each person has said the specific word
    public static void findWord(Scanner input){
        String line = input.nextLine();
        for(int i = 0; i < 6; i++){
            line = input.nextLine();
        }
        int phrases = 0;
        int words = 0;
        int yael = 0;
        int alan = 0;
        while(input.hasNextLine()){
            line = input.nextLine();
            Scanner perLine = new Scanner(line);
            if(line.startsWith("[")){
                String id = perLine.next();
                id = perLine.next();
                if(id.startsWith(YAEL)){
                    yael++;
                } else if(id.startsWith(ALAN)){
                    alan++;
                }
                while(perLine.hasNext()){
                    String cur = perLine.next();
                    words++;
                    if(isVariation(cur)){
                        phrases++;
                        System.out.println(cur + " ");
                    }
                }
            } else {
                while(perLine.hasNext()){
                    String cur = perLine.next();
                    words++;
                    if(isVariation(cur)){
                        phrases++;
                        System.out.println(cur + " ");
                    }
                }
            }
            perLine.close();
        }
        System.out.println();
        System.out.println("Number of times \"" + PHRASE_TO_FIND + "\" said: " + phrases);
        System.out.println("Number of words said: " + words);
        double percent = 100*((double) phrases)/words;
        System.out.println("Percentage of words that are \"" + PHRASE_TO_FIND + "\": " + percent);
        System.out.println("Yael: " + yael + ", Alan: " + alan);
    }
    
    private static boolean isVariation(String pass){
        String cur = pass.toLowerCase();
        return cur.contains(PHRASE_TO_FIND);
    }
}
