package com.example.taskmanager2;

import com.example.taskmanager2.controller.fragment.SignInFragment;
import com.example.taskmanager2.controller.fragment.SignUpFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SignUpUnitTest {

    @Mock
    SignUpFragment view;

    private SignUpFragment signUpFragment;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        signUpFragment = new SignUpFragment();
    }

    @Test
    public void test_validateUserCredentials_userDidNotEnterUsername_displayErrorMessage() throws Exception {
        String username = "";
        String password = "kana";
        signUpFragment.setUsernameAndPassword(username, password);
        signUpFragment.isUserPassEntered();

        Mockito.verify(view).displayHaveNotAccountMessage();
    }

    @Test
    public void test_validateUserCredentials_userEnteredWrongUsername_displayErrorMessage() throws Exception {
        String username = "Jaimeeee";
        String password = "kana";
        signUpFragment.setUsernameAndPassword(username, password);
        signUpFragment.isUsernameTaken();
        Mockito.verify(view).displayHaveNotAccountMessage();
    }

    @Test
    public void test_validateUserCredentials_userEnteredWrongPassword_displayErrorMessage() throws Exception {
        String username = "Jaime";
        String password = "kana";
        signUpFragment.setUsernameAndPassword(username, password);
        signUpFragment.isMachUsernameAndPassword();
        Mockito.verify(view).displayDontMatchUserAndPassMessage();
    }

    @Test
    public void test_validateUserCredentials_userEnteredMatchedUsernameAndPassword_performApiCallToSignInUser() throws Exception {
        String username = "Jaime";
        String password = "kanay";
        signUpFragment.setUsernameAndPassword(username, password);
        signUpFragment.isMachUsernameAndPassword();
        Mockito.verify(view).displayDontMatchUserAndPassMessage();
    }

}
