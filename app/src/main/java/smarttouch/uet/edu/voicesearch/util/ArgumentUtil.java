package smarttouch.uet.edu.voicesearch.util;

import java.util.List;

/**
 * Created by VanHop on 4/15/2016.
 */
public class ArgumentUtil {

    public static boolean hasChannel(List<String> args){

        for(String arg : args){
            if(arg.contains("aname"))
                return true;
        }

        return false;
    }

    public static boolean hasCinema(List<String> args){

        for(String arg : args){
            if(arg.contains("aname"))
                return true;
        }

        return false;
    }

    public static String getArgument(List<String> args, String suffix, List<String> tokens){

        String argument = "";
        for(int i = 0 ; i < args.size() ; i++){
            if(args.get(i).endsWith(suffix)) {
                argument += tokens.get(i) + " ";
                continue;
            }
            if(argument.length() > 0){
                break;
            }
        }

        return argument.trim();
    }

    public static boolean hasTitle(List<String> args){

        for(String arg : args){
            if(arg.contains("title"))
                return true;
        }

        return false;
    }

    public static boolean hasDateTime(List<String> args){

        for(String arg : args){
            if(arg.contains("datetime"))
                return true;
        }

        return false;
    }

}
