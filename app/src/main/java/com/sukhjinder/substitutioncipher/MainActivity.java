package com.sukhjinder.substitutioncipher;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    static private Button encryptButton;
    static private Button decryptButton;
    static private NumberPicker oddNP;
    static private NumberPicker evenNP;
    static private EditText userText;
    static private TextView output;

    static String userString = null;
    static String finalOutput;
    static int oddShift = 0;
    static int evenShift = 0;
    static String oddChar = "";
    static String evenChar = "";
    static ArrayList<String> letters = new ArrayList<String>();
    static ArrayList<String> userInput;
    static ArrayList<String> oddCipher;
    static ArrayList<String> evenCipher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", output.getText());
                clipboard.setPrimaryClip(clip);
                userText.setText(output.getText());

                Snackbar.make(view, "Copied to Clipboard", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        Collections.addAll(letters, "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "-");

        oddNP = (NumberPicker) findViewById(R.id.OddShift);
        oddNP.setMinValue(0);
        oddNP.setMaxValue(53);

        evenNP = (NumberPicker) findViewById(R.id.Even_Shift);
        evenNP.setMinValue(0);
        evenNP.setMaxValue(53);

        userText = (EditText) findViewById(R.id.userInput);
        output = (TextView) findViewById(R.id.output);

        encryptButton = (Button) findViewById(R.id.encrypt_button);
        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oddShift = oddNP.getValue();
                evenShift = evenNP.getValue();
                userString = (userText.getText().toString());
                shift(oddShift, evenShift);
                encrypt(userString);
            }
        });


        decryptButton = (Button) findViewById(R.id.decrypt_button);
        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oddShift = oddNP.getValue();
                evenShift = evenNP.getValue();
                userString = (userText.getText().toString());
                shift(oddShift, evenShift);
                decrypt(userString);
            }
        });
    }

    private static void encrypt(String userString) {

//		Puts the users input into an arraylist
        String[] words = userString.split("");
        userInput = new ArrayList<String>(Arrays.asList(words));
        ArrayList<String> encryptedText = new ArrayList<String>();

        for (int i = 0; i < userInput.size(); i++) {
            if (userInput.get(i).equals(" ")) {
                userInput.set(i, "-");
            }
        }

        userInput.remove(0);
        for (int i = 0; i < userInput.size(); i++) {
            oddChar = userInput.get(i);
            encryptedText.add(oddCipher.get((letters.indexOf(oddChar))));

            if (i == userInput.size() - 1) {
                break;
            } else {
                evenChar = userInput.get(i + 1);
                encryptedText.add(evenCipher.get((letters.indexOf(evenChar))));
            }
            i++;
        }
        finalOutput = encryptedText.toString().replace(",", "")
                .replace(" ", "")
                .replace("[", "")
                .replace("]", "")
                .trim();
        output.setText(finalOutput);
    }


    private static void decrypt(String userString) {
//		Puts the users input into an arraylist
        String[] words = userString.split("");
        userInput = new ArrayList<String>(Arrays.asList(words));

        ArrayList<String> decryptedText = new ArrayList<String>();
        userInput.remove(0);
        for (int i = 0; i < userInput.size(); i++) {
            if (userInput.get(i).equals(" ")) {
                userInput.set(i, "-");
            }
        }

        for (int i = 0; i < userInput.size(); i++) {
            oddChar = userInput.get(i);
            decryptedText.add(letters.get((oddCipher.indexOf(oddChar))));

            if (i == userInput.size() - 1) {
                break;
            } else {
                evenChar = userInput.get(i + 1);
                decryptedText.add(letters.get((evenCipher.indexOf(evenChar))));
            }

            i++;
        }

        finalOutput = decryptedText.toString().replace(",", "")
                .replace(" ", "")
                .replace("[", "")
                .replace("]", "")
                .trim();
        output.setText(finalOutput);

    }


    private static void shift(int oddShift, int evenShift) {

        //		Copies original cipher text into two arrays one for the odd cipher and the other for even cipher
        oddCipher = new ArrayList<String>();
        evenCipher = new ArrayList<String>();
        for (int i = 0; i < letters.size(); i++) {
            String element;
            element = letters.get(i);
            oddCipher.add(element);
            evenCipher.add(element);
        }

//		Shifts the letters for the oddCipher text
        String element = null;
        while (oddShift > 0) {
            element = oddCipher.remove(0);
            oddCipher.add(oddCipher.size(), element);
            oddShift--;
        }
        //	Shifts the letters for the evenCipher text
        while (evenShift > 0) {
            element = evenCipher.remove(0);
            evenCipher.add(evenCipher.size(), element);
            evenShift--;
        }
    }
}