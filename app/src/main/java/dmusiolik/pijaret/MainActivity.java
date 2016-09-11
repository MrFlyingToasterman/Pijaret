/*
Pijaret is just another rotation encryption tool
 */

package dmusiolik.pijaret;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//Pijaret is just another rotation encryption tool

public class MainActivity extends AppCompatActivity {

    //Deklare the crypt_key var
    public static String crypt_key = null;
    //Deklare Version
    public static final String version = "1.6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get object
        TextView keybox = (TextView) findViewById(R.id.editText2);

        //First Time start warning
        if (getPreferenceValue("warn").equals("TheDefaultValueIfNoValueFoundOfThisKey")) {
            //Show Warning
            startup_warning();
        }else{
            //Do nothing
        }

        //Looking for saved key
        if (getPreferenceValue("key").equals("TheDefaultValueIfNoValueFoundOfThisKey")) {
            //Do nothing
        }else{
            keybox.setText(getPreferenceValue("key"));
        }
    }

    //Set up a Contextmenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //Deal with Context
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                //About informations
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK).create();
                alertDialog.setTitle("About");
                alertDialog.setMessage("Pijaret v" + version + "\n(Pijaret is just another rotation encryption tool)\n\nThis is Free (as in freedom) Software written by Darius Musiolik 2k16.\nBut not Copyrighted or anything!\nFeel free to copy, fork or do what ever you want with my code. You don't have to credit me in your fork:\n\nhttps://www.GitHub.com/MrFlyingToasterman"
                                        + "\n\n\nPLEASE NOTICE THIS IS A TOYCRYPTER!\nDO NOT USE FOR SERIOUS CRYPTION!");
                alertDialog.show();
                return true;
            case R.id.make_mrpropper:
                clearup();
                return true;
            case R.id.show_key:
                showkey();
                return true;
            case R.id.hide_key:
                hidekey();
                return true;
            case R.id.input:
                getfc();
                return true;
            case R.id.copy:
                //copy to clipboard
                copytc();

                //done message
                displaytoast("done");
                return true;
            case R.id.save_key:
                //get object
                TextView keybox = (TextView) findViewById(R.id.editText2);

                //set var
                crypt_key = keybox.getText().toString();
                writeToPreference(crypt_key, "key");

                //done message
                displaytoast("Saved key.");
                return true;
            case R.id.delete_key:
                //set var
                crypt_key = "";
                writeToPreference(crypt_key, "key");

                //done message
                displaytoast("Removed key.");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startup_warning() {
        AlertDialog warn;
        warn = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK).create();
        warn.setTitle("WARNING");
        warn.setMessage("Pijaret v" + version + " IS A TOYCRYPTER!\nDO NOT USE FOR SERIOUS CRYPTION!");
        warn.show();

        //Set warn value
        writeToPreference("Startwarning_off", "warn");
    }

    //Method to geting saved values
    public String getPreferenceValue(String myPref) {
        SharedPreferences sp = getSharedPreferences(myPref,0);
        String str = sp.getString("myStore","TheDefaultValueIfNoValueFoundOfThisKey");
        return str;
    }

    //Method to write saved values
    public void writeToPreference(String thePreference, String myPref) {
        SharedPreferences.Editor editor = getSharedPreferences(myPref,0).edit();
        editor.putString("myStore", thePreference);
        editor.commit();
    }

    //Toast function
    public void displaytoast(String msg) {
        Toast.makeText(MainActivity.this,
                msg, Toast.LENGTH_SHORT).show();
    }

    //Warnings
    public void warning() {
        //get objects
        TextView keybox = (TextView) findViewById(R.id.editText2);

        //Display warning
        if (Integer.parseInt(keybox.getText().toString()) <= 99) {
            keybox.setError("YOUR KEY IS VERY VERY VERY WEAK!!");
        }
        //Display warning
        else if (Integer.parseInt(keybox.getText().toString()) <= 999) {
            keybox.setError("Your key is very weak!");
        }
        //Display warning
        else if (Integer.parseInt(keybox.getText().toString()) <= 9999) {
            keybox.setError("Your key is weak!");
        }

    }

    //Show key
    public void showkey() {
        //get object
        TextView keybox = (TextView) findViewById(R.id.editText2);

        //Set InputType
        keybox.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

    }

    //Hide key
    public void hidekey() {
        //get object
        TextView keybox = (TextView) findViewById(R.id.editText2);

        //Set InputType
        keybox.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }

    //Clear function
    public void clearup() {
        //get object
        TextView contextbox = (TextView) findViewById(R.id.editText);

        //set 'nuffin
        contextbox.setText("");

        //done message
        displaytoast("Cleaning up done!");
    }



    public void copytc() {
        //get object
        TextView contextbox = (TextView) findViewById(R.id.editText);

        //set var
        String content = contextbox.getText().toString();

        //Prepare Clipboard
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        //Copy to clipboard
        clipboard.setText(content);

        //done message
        displaytoast("Done!");
    }

    //Get from clipboard
    public void getfc() {
        //get object
        TextView contextbox1 = (TextView) findViewById(R.id.editText);

        //Prepare Clipboard
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        //Get From Clipboard and set in contentbox
        contextbox1.setText(contextbox1.getText().toString() + clipboard.getText());

        //done message
        displaytoast("Text set!");
    }

    //encrypt
    String cipher(String input, int cryptvalue){
        String s = "";
        int len = input.length();
        for(int x = 0; x < len; x++) {
            char c = (char)(input.charAt(x) + cryptvalue);

            s += (char)(input.charAt(x) + cryptvalue);

        }
        return s;
    }

    //deencrypt
    String uncipher(String input, int cryptvalue){
        String s = "";
        int len = input.length();

        for(int i = 0; i < len; i++) {
            char c = (char)(input.charAt(i) - cryptvalue);
            if (c > 'z') {
                s += (char)(input.charAt(i) + (27 + cryptvalue));
            }
            else {
                s += (char)(input.charAt(i) - cryptvalue);
            }
        }
        return s;
    }

    public void clearbtn(View view) {
        clearup();
    }

    //Copy to clipboard btn
    public void copytcbtn(View viw) {
        copytc();
    }

    //Get from clipboard btn
    public void getfcbtn(View view) {
        getfc();
    }

    //encrypt btn
    public void enc_btn(View view) {
        //get objects
        TextView contextbox = (TextView) findViewById(R.id.editText);
        TextView keybox = (TextView) findViewById(R.id.editText2);

        //Check if keybox is valid
        if (keybox.getText().toString().equals("")) {
            //Keybox invalid message
            displaytoast("Keybox invalid");
            return;
        }

        //Check for warnings
        warning();

        //set var's
        String content = contextbox.getText().toString();
        int key = Integer.parseInt(keybox.getText().toString());

        //cipher content
        String enc_text = cipher(content, key);

        //set content in textbox
        contextbox.setText(enc_text);
    }

    //decrypt btn
    public void dec_btn(View view) {
        //get objects
        TextView contextbox = (TextView) findViewById(R.id.editText);
        TextView keybox = (TextView) findViewById(R.id.editText2);

        //Check if keybox is valid
        if (keybox.getText().toString().equals("")) {
            //Keybox invalid message
            displaytoast("Keybox invalid");
            return;
        }

        //set var's
        String content = contextbox.getText().toString();
        int key = Integer.parseInt(keybox.getText().toString());

        //cipher content
        String enc_text = uncipher(content, key);

        //set content in textbox
        contextbox.setText(enc_text);
    }
}
