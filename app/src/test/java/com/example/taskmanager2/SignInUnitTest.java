package com.example.taskmanager2;

import com.example.taskmanager2.controller.fragment.SignInFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SignInUnitTest {

    @Mock
    SignInFragment view;

    private SignInFragment signInFragment;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        signInFragment = new SignInFragment();
    }

    @Test
    public void test_validateUserCredentials_userDidNotEnterUsername_displayErrorMessage() throws Exception {
        String username = "";
        String password = "kana";
        signInFragment.setUserAndPass(username, password);
        signInFragment.isUserPassEntered();

        Mockito.verify(view).displayHaveNotAccountMessage();
    }

    @Test
    public void test_validateUserCredentials_userEnteredWrongUsername_displayErrorMessage() throws Exception {
        String username = "Jaimeeee";
        String password = "kana";
        signInFragment.setUserAndPass(username, password);
        signInFragment.isUsernameTaken();
        Mockito.verify(view).displayHaveNotAccountMessage();
    }

    @Test
    public void test_validateUserCredentials_userEnteredWrongPassword_displayErrorMessage() throws Exception {
        String username = "Jaime";
        String password = "kana";
        signInFragment.setUserAndPass(username, password);
        signInFragment.isMachUsernameAndPassword();
        Mockito.verify(view).displayDontMatchUserAndPassMessage();
    }

    @Test
    public void test_validateUserCredentials_userEnteredMatchedUsernameAndPassword_performApiCallToSignInUser() throws Exception {
        String username = "Jaime";
        String password = "kanay";
        signInFragment.setUserAndPass(username, password);
        signInFragment.isMachUsernameAndPassword();
        Mockito.verify(view).displayWelcomeMessage();
    }

}
