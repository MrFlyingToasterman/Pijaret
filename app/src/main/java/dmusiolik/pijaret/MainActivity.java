/*
Pijaret is just another rotation encryption tool
 */

package dmusiolik.pijaret;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get object
        TextView keybox = (TextView) findViewById(R.id.editText2);

        //
        if (getPreferenceValue("key").equals("TheDefaultValueIfNoValueFoundOfThisKey")) {
            //Do nothing
        }else{
            keybox.setText(getPreferenceValue("key"));
        }
    }

    //encrypt btn
    public void enc_btn(View view) {
        //get objects
        TextView contextbox = (TextView) findViewById(R.id.editText);
        TextView keybox = (TextView) findViewById(R.id.editText2);

        //Check if keybox is valid
        if (keybox.getText().toString().equals("")) {
            //Keybox invalid message
            Toast.makeText(MainActivity.this,
                    "Keybox invalid", Toast.LENGTH_LONG).show();
            return;
        }

        //set var's
        String content = contextbox.getText().toString();
        int key = Integer.parseInt(keybox.getText().toString());

        //cipher content
        String enc_text = cipher(content, key);

        //set content in textbox
        contextbox.setText(enc_text);
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

    //decrypt btn
    public void dec_btn(View view) {
        //get objects
        TextView contextbox = (TextView) findViewById(R.id.editText);
        TextView keybox = (TextView) findViewById(R.id.editText2);

        //Check if keybox is valid
        if (keybox.getText().toString().equals("")) {
            //Keybox invalid message
            Toast.makeText(MainActivity.this,
                    "Keybox invalid", Toast.LENGTH_LONG).show();
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

    //Copy to clipboard
    public void ctc(View viw) {
        //get object
        TextView contextbox = (TextView) findViewById(R.id.editText);

        //set var
        String content = contextbox.getText().toString();

        //Prepare Clipboard
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        //Copy to clipboard
        clipboard.setText(content);

        //done message
        Toast.makeText(MainActivity.this,
                "done", Toast.LENGTH_SHORT).show();
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
                alertDialog.setMessage("Pijaret v1.4\n(Pijaret is just another rotation encryption tool)\n\nThis is Free (as in freedom) Software written by Darius Musiolik 2k16.\nBut not Copyrighted or anything!\nFeel free to copy, fork or do what ever you want with my code. You don't have to credit me in your fork:\n\nhttps://www.GitHub.com/MrFlyingToasterman");
                alertDialog.show();
                return true;
            case R.id.make_mrpropper:
                //get object
                TextView contextbox = (TextView) findViewById(R.id.editText);

                //set 'nuffin
                contextbox.setText("");

                /* I'm thinking about makeing a own method for Toast's. But at the moment i don't see why i need it maybe in the Future. */

                //done message
                Toast.makeText(MainActivity.this,
                        "cleaning up done", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.input:
                //get object
                TextView contextbox1 = (TextView) findViewById(R.id.editText);

                //Prepare Clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                //Get From Clipboard and set in contentbox
                contextbox1.setText(clipboard.getText());

                //done message
                Toast.makeText(MainActivity.this,
                        "Text set", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.copy:
                //get object
                TextView contextbox2 = (TextView) findViewById(R.id.editText);

                //set var
                String content = contextbox2.getText().toString();

                //Prepare Clipboard
                ClipboardManager clipboard2 = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                //Copy to clipboard
                clipboard2.setText(content);

                //done message
                Toast.makeText(MainActivity.this,
                        "done", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.save_key:
                //get object
                TextView keybox = (TextView) findViewById(R.id.editText2);

                //set var
                crypt_key = keybox.getText().toString();
                writeToPreference(crypt_key, "key");

                //done message
                Toast.makeText(MainActivity.this,
                        "Saved key", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete_key:
                //set var
                crypt_key = "";
                writeToPreference(crypt_key, "key");

                //done message
                Toast.makeText(MainActivity.this,
                        "Removed key", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

}
