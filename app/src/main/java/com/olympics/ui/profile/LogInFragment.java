package com.olympics.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.olympics.Account;
import com.olympics.GlobalClass;
import com.olympics.R;

import java.util.ArrayList;

public class LogInFragment extends Fragment {

    private ArrayList<Account> accountsList = new ArrayList<>();
    private EditText userName,password;
    private TextView signUp;
    private Button login;
    private GlobalClass globalClass;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        userName = (EditText)root.findViewById(R.id.login_username_edit);
        password = (EditText)root.findViewById(R.id.login_pass_edit);
        login = (Button)root.findViewById(R.id.login_login_button);
        signUp = (TextView) root.findViewById(R.id.login_signup);

        globalClass = (GlobalClass)getContext().getApplicationContext();
        accountsList = globalClass.getAccounts();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInputName = userName.getText().toString();
                String userInputPass = password.getText().toString();
                for (Account acc:accountsList) {
                if (acc.getUserID().equals(userInputName) && acc.getPassword().equals(userInputPass)){
                    globalClass.logIn();
                    acc.logIn();
                    Bundle bundle = new Bundle();
                    bundle.putInt("AccountNO",acc.getAccNo());
                    ProfileFragment profileFragment = new ProfileFragment();
                    profileFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,profileFragment).commit();
                    break;
                }
                else if (acc.getUserID().equals(userInputName) && !(acc.getPassword().equals(userInputPass))){
                    Toast.makeText(getContext().getApplicationContext(),"Incorrect UserName or Password",Toast.LENGTH_SHORT).show();
                }
                }
                if (!globalClass.isLogIn()){Toast.makeText(getContext().getApplicationContext(),"Incorrect UserName or Password",Toast.LENGTH_SHORT).show();}
                password.setText("");

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment signUpFragment = new SignUpFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,signUpFragment).commit();
            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
