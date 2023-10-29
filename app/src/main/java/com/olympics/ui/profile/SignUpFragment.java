package com.olympics.ui.profile;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.olympics.Account;
import com.olympics.GlobalClass;
import com.olympics.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SignUpFragment extends Fragment {
    private TextInputLayout signUp_userName,signUp_password,signUp_password_confirm,signUp_cardNO,signUp_csv;
    private boolean canSignUp = false;
    private Button signUp;
    private GlobalClass globalClass;
    private Account newAccount;
    private final String a_z = "abcdefghijklmnopqrstuvwxyz";
    private final String A_Z = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String number = "0123456789";
    private final String spacialCha = "! \" # $ % & ' ( ) * + , - . / : ; < = > ? @ [ \\ ] ^ _ ` { | } ~";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        signUp = (Button)root.findViewById(R.id.singup_signup_button);
        signUp_userName = (TextInputLayout)root.findViewById(R.id.singup_username_edit);
        signUp_password = (TextInputLayout)root.findViewById(R.id.singup_pass_edit);
        signUp_password_confirm = (TextInputLayout)root.findViewById(R.id.singup_pass_confirm_edit);
        signUp_cardNO = (TextInputLayout)root.findViewById(R.id.singup_card_edit);
        signUp_csv = (TextInputLayout)root.findViewById(R.id.singup_csv_edit);

        signUp_userName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                canSignUp = isValidUserID(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signUp_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    canSignUp = isValidPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signUp_password_confirm.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                canSignUp = isValidConfirm(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signUp_cardNO.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                canSignUp = isValidCard(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signUp_csv.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                canSignUp = isValidCsv(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signUp_userName.setError(signUp_userName.getEditText().getText().toString());
        globalClass = (GlobalClass)getContext().getApplicationContext();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canSignUp){
                    try {
                        String userName_Input = signUp_userName.getEditText().getText().toString();
                        String password_Input = signUp_password.getEditText().getText().toString();
                        String password_confirm_Input = signUp_password_confirm.getEditText().getText().toString();
                        String cardNO_Input = signUp_cardNO.getEditText().getText().toString();
                        String csv_Input = signUp_csv.getEditText().getText().toString();

                        if ( !userName_Input.isEmpty() && !password_Input.isEmpty() && cardNO_Input.equals("") && csv_Input.equals("")){
                            newAccount = new Account(userName_Input,password_Input);
                        }
                        else {newAccount = new Account(userName_Input,password_confirm_Input,cardNO_Input,csv_Input);}
                        globalClass.getAllLine();
                        globalClass.addAllLine(newAccount.toString());
                        String data = globalClass.getAllLine();
                        FileOutputStream fileOutputStream = getActivity().openFileOutput("input.txt", Context.MODE_PRIVATE);
                        fileOutputStream.write(data.getBytes());
                        fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    globalClass.logIn();
                    newAccount.logIn();
                    globalClass.addAccount(newAccount);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new ProfileFragment()).commit();
                }
                else {Toast.makeText(getContext().getApplicationContext(),"Incorrect input",Toast.LENGTH_SHORT).show();}

            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public boolean isValidUserID(String s){
        boolean isValidUserID = false;
        if (s.length() > 0 && !(a_z+A_Z).contains(s.substring(s.length()-1,s.length()))){
            signUp_userName.setError("UserID must be a-z or A-Z");
            isValidUserID = false;
        }
        else{
            signUp_userName.setError(null);
            if (s.length()>0 && !(s.toString().substring(0,1).equals(s.toString().substring(0,1).toUpperCase()))){
                signUp_userName.setError("First letter muse be uppercase");
                isValidUserID = false;
            }
            else {
                signUp_userName.setError(null);
                isValidUserID = true;}
        }


        if (s.length() == 0) {
            signUp_userName.setError(null);
            isValidUserID = false;
        }
        return isValidUserID;

    }

    public boolean isValidPassword(String s){
        //Uppercase>=1
        //Lowercase>=5
        //Number>=3
        //SpacialCha>=2
        int upperCount = 0;
        int lowerCount = 0;
        int numberCount = 0;
        int specialCount = 0;
            for (int i = 1; i <= s.length() ; i++) {
                if (A_Z.contains(s.substring(i-1,i))){
                    upperCount++;
                }
                else if (a_z.contains(s.substring(i-1,i))){
                    lowerCount++;
                }
                else if (number.contains(s.substring(i-1,i))){
                    numberCount++;
                }
                else if (spacialCha.contains(s.substring(i-1,i))){
                    specialCount++;
                }
            }

        String canUse = a_z+A_Z+number+spacialCha;
        boolean isValidPassword = false;
        if (s.length() == 0){
            signUp_password.setError(null);
            isValidPassword = false;
        }
        else {
            if (s.length()>0 && !(canUse.contains(s.substring(s.length()-1,s.length())))){
                signUp_password.setError("Password can be A-Z, a-z, 0-9 or "+spacialCha);
                isValidPassword = false;
            }
            else {
                if ((upperCount>=1 && lowerCount>=5 && numberCount>=3 && specialCount>=2)){
                    signUp_password.setError(null);
                    isValidPassword = true;
                }
                else {
                    signUp_password.setError("Password need at lest 1 uppercase, 5 lowercase, 3 numbers and 2 special characters");
                    isValidPassword = false;}
            }

        }

        return isValidPassword;

    }

    public boolean isValidConfirm(String s){

        String password = signUp_password.getEditText().getText().toString();

        if (s.length() == 0){
            signUp_password_confirm.setError(null);
            return false;
        }
        else {
            if (s.equals(password)){
                signUp_password_confirm.setError(null);
                return true;
            }
            else {
                signUp_password_confirm.setError("Password do not match");
                return false;
            }
        }

    }

    public boolean isValidCard(String s){
        if (s.length() == 0){
            signUp_cardNO.setError(null);
            return true;
        }
        else if (s.length() != 16){
            signUp_cardNO.setError("Please enter card number 16 digits");
            return false;
        }
        else if (signUp_csv.getEditText().getText().toString().isEmpty()){
            signUp_cardNO.setError(null);
            signUp_csv.setError("Please enter card csv number 3 digits");
            return false;
        }
        else{
            signUp_cardNO.setError(null);
            return true;
        }

    }

    public boolean isValidCsv(String s){
        if (signUp_cardNO.getEditText().getText().toString().length() == 16 && signUp_csv.getEditText().getText().toString().isEmpty()){
            signUp_csv.setError("Please enter card csv number 3 digits");
            return false;
        }
        else if (s.length() == 0 && signUp_cardNO.getEditText().getText().toString().length() == 0){
            signUp_csv.setError(null);
            return true;
        }
        else if (s.length() != 3){
            signUp_csv.setError("Please enter card csv number 3 digits");
            return false;
        }
        else{
            signUp_csv.setError(null);
            return true;
        }

    }
}
