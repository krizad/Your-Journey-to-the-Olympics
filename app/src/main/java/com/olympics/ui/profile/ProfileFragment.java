package com.olympics.ui.profile;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.olympics.Account;
import com.olympics.GlobalClass;
import com.olympics.R;

public class ProfileFragment extends Fragment {

    private ImageView acc_icon,eye;
    private TextView acc_name,acc_pass,acc_card;
    private Button acc_change_card,logOut,addCard;
    private GlobalClass globalClass;
    private Account accountLoggedIn;
    private boolean show;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        globalClass = (GlobalClass)getContext().getApplicationContext();
        acc_icon = (ImageView)root.findViewById(R.id.profile_icon);
        acc_name = (TextView)root.findViewById(R.id.profile_username_show);
        acc_pass = (TextView)root.findViewById(R.id.profile_password_show);
        acc_card = (TextView)root.findViewById(R.id.profile_card_show);
        acc_change_card = (Button)root.findViewById(R.id.profile_show_card);
        eye = (ImageView)root.findViewById(R.id.profile_eye);
        addCard = (Button)root.findViewById(R.id.profile_add_card);
        logOut = (Button)root.findViewById(R.id.profile_logout);
        for (Account acc:globalClass.getAccounts()) {
            if (acc.getAccountStatus() == 1){
                accountLoggedIn = acc;
            }
        }
        String passwordHide = "";
        final String passwordShow = accountLoggedIn.getPassword() ;

        for (int i = 0; i < passwordShow.length() ; i++) {
            passwordHide += "*";
        }

        acc_name.setText(accountLoggedIn.getUserID());
        acc_pass.setText(passwordHide);
        show = false;
        acc_card.setText(accountLoggedIn.getSelectedCardNO());
        final String finalPasswordHide = passwordHide;
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show){
                    acc_pass.setText(finalPasswordHide);
                    eye.setImageResource(R.drawable.closeeyes);
                    show = false;
                }
                else{
                    acc_pass.setText(passwordShow);
                    eye.setImageResource(R.drawable.openeyes);
                    show =true;

                }
            }
        });

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountLoggedIn.hasCard()){
                    Toast.makeText(getContext().getApplicationContext(),"You already have a card.",Toast.LENGTH_SHORT).show();
                }
                else {
                    final Dialog addCard = new Dialog(getContext());
                    addCard.setContentView(R.layout.dialog_add_card);
                    final EditText cardNoInput = (EditText)addCard.findViewById(R.id.dia_cardNO);
                    final EditText csvInput = (EditText)addCard.findViewById(R.id.dia_card_csv);
                    Button add = (Button)addCard.findViewById(R.id.dia_add_card_confirm);
                    Button cancelAdd = (Button)addCard.findViewById(R.id.dia_add_card_cancel);
                    addCard.show();
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (cardNoInput.getText().toString().length() != 16 ){
                                Toast.makeText(getContext().getApplicationContext(),"Incorrect Card Number",Toast.LENGTH_SHORT).show();
                            }
                            else if (csvInput.getText().toString().length() != 3){
                                Toast.makeText(getContext().getApplicationContext(),"Incorrect Card csv",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                accountLoggedIn.addCard(cardNoInput.getText().toString(),csvInput.getText().toString());
                                acc_card.setText(accountLoggedIn.getSelectedCardNO());
                                addCard.cancel();
                            }

                        }
                    });
                    cancelAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addCard.cancel();
                        }
                    });
                }

            }
        });

            acc_change_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (accountLoggedIn.hasCard()){
                        final Dialog addCard = new Dialog(getContext());
                        addCard.setContentView(R.layout.dialog_add_card);
                        TextView title = (TextView)addCard.findViewById(R.id.dia_title);
                        title.setText("Change Card");
                        final EditText cardNoInput = (EditText)addCard.findViewById(R.id.dia_cardNO);
                        final EditText csvInput = (EditText)addCard.findViewById(R.id.dia_card_csv);
                        Button add = (Button)addCard.findViewById(R.id.dia_add_card_confirm);
                        Button cancelAdd = (Button)addCard.findViewById(R.id.dia_add_card_cancel);
                        addCard.show();
                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (cardNoInput.getText().toString().length() != 16 ){
                                    Toast.makeText(getContext().getApplicationContext(),"Incorrect Card Number",Toast.LENGTH_SHORT).show();
                                }
                                else if (csvInput.getText().toString().length() != 3){
                                    Toast.makeText(getContext().getApplicationContext(),"Incorrect Card csv",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    accountLoggedIn.setSelectedCard(cardNoInput.getText().toString(),csvInput.getText().toString());
                                    acc_card.setText(accountLoggedIn.getSelectedCardNO());
                                    addCard.cancel();
                                }

                            }
                        });
                        cancelAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addCard.cancel();
                            }
                        });

                    }
                    else {
                        Toast.makeText(getContext().getApplicationContext(),"Please Add Card",Toast.LENGTH_SHORT).show();
                    }
                    }
            });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalClass.logOut();
                accountLoggedIn.logOut();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new LogInFragment()).commit();
            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }
}
