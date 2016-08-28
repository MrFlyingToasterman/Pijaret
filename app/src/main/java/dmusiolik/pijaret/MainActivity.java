/*
Pijaret is just another rotation encryption tool
 */

package dmusiolik.pijaret;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //encrypt btn
    public void enc_btn(View view) {
        //get objects
        TextView contextbox = (TextView) findViewById(R.id.editText);
        TextView keybox = (TextView) findViewById(R.id.editText2);

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

}
