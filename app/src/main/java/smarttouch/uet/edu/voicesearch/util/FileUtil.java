package smarttouch.uet.edu.voicesearch.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import smarttouch.uet.edu.voicesearch.classicfier.Dictionary;
import smarttouch.uet.edu.voicesearch.classicfier.argument.CommandArgumentFeatureGenerator;

/**
 * Created by VanHop on 4/14/2016.
 */
public class FileUtil {

    private static final int COPY_MODEL_ARGUMENT = 1;
    private static final int COPY_OPTION_ARGUMENT = 2;
    private static final int COPY_MODEL_FEATURE = 3;
    private static final int COPY_OPTION_FEATURE = 4;

    public static void loadDictionary(Context context){
        Dictionary.channel = getData(context,"dic/channel");
        Dictionary.cinema = getData(context,"dic/cinema");
        Dictionary.day = getData(context,"dic/day");
        Dictionary.program = getData(context,"dic/program");
        Dictionary.time = getData(context,"dic/time");
        Dictionary.actions = getData(context,"action/conjunction");
    }

    public static void copyAssetsDataToPhone(Context context) {

        checkFolder(context);

        // model argument
        if (!checkFile("data/data/" + context.getPackageName() + "/files/argument/model.txt")) {
            copyFile(context, COPY_MODEL_ARGUMENT);
        }
        // option argument
        if (!checkFile("data/data/" + context.getPackageName() + "/files/argument/option.txt")) {
            copyFile(context, COPY_OPTION_ARGUMENT);
        }
        // model feature
        if (!checkFile("data/data/" + context.getPackageName() + "/files/feature/model.txt")) {
            copyFile(context, COPY_MODEL_FEATURE);
        }
        // option feature
        if (!checkFile("data/data/" + context.getPackageName() + "/files/feature/option.txt")) {
            copyFile(context, COPY_OPTION_FEATURE);
        }

    }

    private static void checkFolder(Context context) {
        checkFolder(context, "data/data/" + context.getPackageName() + "/files/argument");
        checkFolder(context, "data/data/" + context.getPackageName() + "/files/feature");
    }

    private static void copyFile(Context context, int code) {

        switch (code) {
            case 1:
                createFile(context, "argument/model.txt", "data/data/" + context.getPackageName() + "/files/argument/model.txt");
                break;
            case 2:
                createFile(context, "argument/option.txt", "data/data/" + context.getPackageName() + "/files/argument/option.txt");
                break;
            case 3:
                createFile(context, "feature/model.txt", "data/data/" + context.getPackageName() + "/files/feature/model.txt");
                break;
            case 4:
                createFile(context, "feature/option.txt", "data/data/" + context.getPackageName() + "/files/feature/option.txt");
                break;
            default:
        }
    }

    private static void createFile(Context context, String fileName, String path) {
        String content = "";
        try {
            File file = new File(path);
            file.createNewFile();


            InputStream inputStream = context.getAssets().open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = null;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            content = stringBuffer.toString();
            OutputStream fo = new FileOutputStream(file);
            fo.write(content.getBytes());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkFolder(Context context, String str) {
        File file = new File(str);
        File folder = new File("data/data/" + context.getPackageName() + "/files");
        if(!folder.exists()){
            folder.mkdir();
        }
        if (!file.exists())
            file.mkdir();
    }

    private static boolean checkFile(String str) {
        File file = new File(str);
        return file.exists();
    }

    public static List<String> getData(Context context, String fileName) {
        InputStream inputStream = null;
        List<String> data = new ArrayList<>();
        try {
            inputStream = context.getAssets().open(fileName);

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                data.add(line);
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


}
