package smarttouch.uet.edu.voicesearch.util;


import java.util.ArrayList;
import java.util.HashMap;

import smarttouch.uet.edu.voicesearch.entities.ResponseFilmDTO;
import smarttouch.uet.edu.voicesearch.entities.ResponseTVDTO;

/**
 * Created by VanHop on 4/9/2016.
 */
public class ShowResults {

    public static void print(ResponseTVDTO responseTVDTO){
        System.out.println(responseTVDTO.getName() + " : " + responseTVDTO.getStartingTime() + " - " + responseTVDTO.getFinshingTime());
    }

    public static void prints(ArrayList<ResponseTVDTO> responseTVDTOs){
        for(ResponseTVDTO responseTVDTO : responseTVDTOs)
            print(responseTVDTO);
    }

    public static void printsHashMap(HashMap<String, ArrayList<ResponseFilmDTO>> results){
        for(String key : results.keySet()){
            System.out.println("===============" + key + "==================");
            ArrayList<ResponseFilmDTO> responseFilmDTOs = results.get(key);
            for(ResponseFilmDTO res : responseFilmDTOs){
                System.out.println(res.getName() + ":" + res.getTimes().toString());
            }
        }
    }

}
