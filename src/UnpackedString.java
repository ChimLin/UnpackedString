import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnpackedString {
    public static void main(String[] args) throws IOException {
        // Using Scanner for Getting Input from User
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
        String resultOfReading = reader.readLine();

        List<StringBuilder> list = new ArrayList<>();

        //check for the validation of input
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(resultOfReading);
        System.out.println(matcher.find());

        //divide the input into substrings
        StringBuilder sb = new StringBuilder();
        int a = 0, b = 0;
        for(char symbol: resultOfReading.toCharArray()){
            sb.append(symbol);
            if (symbol == '[')
                a += 1;
            if(symbol == ']')
                b += 1;
            if (a != 0 && b != 0 && (a+b)%2==0){
                list.add(new StringBuilder(sb));
                sb.delete(0, sb.length());
                a = 0;
                b = 0;
            }
        }

        //iterate over ArrayList with substrings to get the final result
        StringBuilder end = new StringBuilder();
        for(StringBuilder subString: list){
            end.append(recurse(subString));
        }

        // print the final result out
        System.out.println(end.append(sb));

    }

    // the method uses recursion
    public static StringBuilder recurse(StringBuilder sb){

        int iterate = 0; // to know how many times to print values between []
        StringBuilder front = new StringBuilder(); // for characters standing in front of repeting substring 2[xxx3[ddd]] -> xxx are characters standing before repeting substring

        //final condition to stop recursion
        if(sb.indexOf("[") == -1){
            return  sb;
        }
        LOOP:for(int i = 0; i<sb.length();i++) {
            if (Character.isDigit(sb.charAt(i))) {
                // assign number to iterate to know how many times loops
                iterate = Integer.parseInt(sb.substring(i, sb.indexOf("[")));

                StringBuilder back; // for characters standing after repeting substring "2[xxx3[ddd]sss]" -> sss are characters standing after repeting substring

                // to know whether there are symbols after "]" , for example, 2[xx2[x]zzz] -> zzz are the symbols after "]"
                if(sb.charAt(sb.length()-1)== ']')
                    back = new StringBuilder();
                else
                    back = new StringBuilder(sb.substring(sb.lastIndexOf("]")+1, sb.length()));

                //make recursion to be clear that there is no any inner repeting substring
                StringBuilder recursive = recurse(new StringBuilder(sb.substring(sb.indexOf("[")+1,sb.lastIndexOf("]"))));

                StringBuilder helper_me = new StringBuilder(); //just a variable to simplify my life

                //here, variable "iterate" is used to repeat n-times of substring
                for(int a = 0 ; a<iterate;a++){
                    helper_me.append(recursive.toString());
                }
                front.append(helper_me).append(back);
                break LOOP;
            }else{
                front.append(sb.charAt(i));
            }
        }
        return front;
    }
}
