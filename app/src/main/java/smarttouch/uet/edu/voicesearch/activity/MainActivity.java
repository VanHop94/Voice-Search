package smarttouch.uet.edu.voicesearch.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import smarttouch.uet.edu.voicesearch.R;
import smarttouch.uet.edu.voicesearch.classicfier.action.ClassifiAction;
import smarttouch.uet.edu.voicesearch.classicfier.argument.CommandArgumentClassifier;
import smarttouch.uet.edu.voicesearch.classicfier.feature.DomainClassifier;
import smarttouch.uet.edu.voicesearch.controller.SearchFilmController;
import smarttouch.uet.edu.voicesearch.controller.SearchTVController;
import smarttouch.uet.edu.voicesearch.custom.adapter.MessagesListAdapter;
import smarttouch.uet.edu.voicesearch.entities.CustomObject;
import smarttouch.uet.edu.voicesearch.entities.Message;
import smarttouch.uet.edu.voicesearch.util.FileUtil;
import smarttouch.uet.edu.voicesearch.util.ResponseType;

public class MainActivity extends Activity implements RecognitionListener{

    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private EditText input;
    private Button send;
    private ImageView imgMic;
    private AVLoadingIndicatorView listening;
    private AVLoadingIndicatorView loading;
    private ArrayList<CustomObject> messages;
    private ListView listViewMessages;
    private MessagesListAdapter adapter;
    public static boolean isWithinDialog;
    private String lastCommandOfUser;
    private String lastAction;
    public static String leakArgument;
    private String lastFeature;
    private List<String> lastArguments;
    private DomainClassifier domainClassifier;
    private CommandArgumentClassifier commandArgumentClassifier;
    private boolean isProcessing;

    private void init() {
        isWithinDialog = false;
        isProcessing = true;
        lastCommandOfUser = "";
        lastAction = "";
        lastFeature = "";
        leakArgument = "";
        FileUtil.copyAssetsDataToPhone(getApplicationContext());
        FileUtil.loadDictionary(getApplicationContext());
        domainClassifier = new DomainClassifier("data/data/" + getPackageName() + "/files/feature");
        domainClassifier.init();
        commandArgumentClassifier = new CommandArgumentClassifier("data/data/" + getPackageName() + "/files/argument");
        commandArgumentClassifier.init();
        ClassifiAction.init();
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_main);
        input = (EditText) findViewById(R.id.input);
        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } else {
                    if (speech != null)
                        speech.stopListening();
                    imgMic.setVisibility(View.VISIBLE);
                    listening.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                }
            }
        });
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input.getText().toString().length() < 5)
                    return;
                input.clearFocus();
                process(input.getText().toString());
            }
        });
        imgMic = (ImageView) findViewById(R.id.imageView);
        listening = (AVLoadingIndicatorView) findViewById(R.id.avloadingIndicatorView);
        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        imgMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listening();
            }
        });

        listening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isProcessing = false;
                stopListening();
            }
        });

        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isProcessing = false;
                stopListening();
            }
        });

        listViewMessages = (ListView) findViewById(R.id.list_view_messages);
        messages = new ArrayList<CustomObject>(Arrays.asList(new CustomObject(ResponseType.MESSAGE, new Message("BOT","Xin chào! Bạn muốn hỏi gì nhỉ", false))));
        adapter = new MessagesListAdapter(this, messages);
        listViewMessages.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null)
            speech.stopListening();

    }

    public void keepListening() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listening();
    }

    public void process(String sentence){
        String voiceStr = sentence.toLowerCase();
        if(isWithinDialog){
            voiceStr = lastCommandOfUser + " " + voiceStr;
        } else {
            lastCommandOfUser = sentence;
        }
        messages.add(new CustomObject(ResponseType.MESSAGE, new Message("YOU", sentence, true)));
        adapter.notifyDataSetChanged();

        if (!isWithinDialog) {
            (new ProcessingData()).execute(voiceStr);
        } else {
            String [] tokens = sentence.toLowerCase().split(" ");
            for(int i = 0 ; i < tokens.length ; i ++){
                if(i == 0)
                    lastArguments.add("b-" + leakArgument);
                else
                    lastArguments.add("i-" + leakArgument);
            }
            (new ProcessingData()).execute(voiceStr);
        }
    }

    @Override
    public void onResults(Bundle results) {

        if(!isProcessing)
            return;

        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        process(matches.get(0));
        /*ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String voiceStr = matches.get(0).toLowerCase();
        if(isWithinDialog){
            voiceStr = lastCommandOfUser + " " + voiceStr;
        } else {
            lastCommandOfUser = matches.get(0);
        }
        messages.add(new CustomObject(ResponseType.MESSAGE, new Message("YOU", matches.get(0), true)));
        adapter.notifyDataSetChanged();

        if (!isWithinDialog) {
            (new ProcessingData()).execute(voiceStr);
        } else {
            String [] tokens = matches.get(0).split(" ");
            for(int i = 0 ; i < tokens.length ; i ++){
                if(i == 0)
                    lastArguments.add("b-" + leakArgument);
                else
                    lastArguments.add("i-" + leakArgument);
            }
            (new ProcessingData()).execute(voiceStr);

        }*/
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        //progressBar.setProgress((int) rmsdB);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
    }

    @Override
    public void onEndOfSpeech() {
        stopListening();
    }

    @Override
    public void onError(int error) {
    }

    private void stopListening() {
        if (speech != null)
            speech.stopListening();
        imgMic.setVisibility(View.VISIBLE);
        listening.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        input.setText("");
        input.clearFocus();
    }

    private void listening() {
        isProcessing = true;
        if(speech != null)
            speech.startListening(recognizerIntent);
        imgMic.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        listening.setVisibility(View.VISIBLE);
        input.setText("");
        input.clearFocus();
    }

    private void loading() {
        imgMic.setVisibility(View.GONE);
        listening.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }


    class ProcessingData extends AsyncTask<String, Void, CustomObject> {
        public ProcessingData() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading();
        }

        @Override
        protected CustomObject doInBackground(String... params) {
            if(!isWithinDialog)
                lastFeature = domainClassifier.doDomainClassification(params[0]);
            if (!lastFeature.equals("other")) {
                if(!isWithinDialog)
                    lastArguments = commandArgumentClassifier.doCommandArgumentClassification(params[0]);
                String strClassifiAction = "";
                String [] tokens = params[0].split(" ");
                for(int i = 0 ; i < lastArguments.size() ; i++){
                    if(strClassifiAction.contains(lastArguments.get(i).replace("-","+")))
                        strClassifiAction += tokens[i] + " ";
                    else
                        strClassifiAction += lastArguments.get(i).replace("-","+") + " " + tokens[i] + " ";
                }
                if(lastFeature.equals("tivi"))
                    return SearchTVController.execute(params[0], lastArguments, ClassifiAction.getAction(lastFeature, strClassifiAction.trim()));
                else
                    return SearchFilmController.execute(params[0], lastArguments, ClassifiAction.getAction(lastFeature, strClassifiAction.trim()));
            }else {
                return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Mình không hiểu ý bạn", false));
                //
            }
        }

        @Override
        protected void onPostExecute(CustomObject customObject) {
            super.onPostExecute(customObject);
            messages.add(customObject);
            adapter.notifyDataSetChanged();
            stopListening();
            if(isWithinDialog)
                keepListening();
        }
    }
}
