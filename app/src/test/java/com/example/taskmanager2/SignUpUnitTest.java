package com.example.taskmanager2;

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
    public void test_validateUserCredentials_userDidNotEnterUsernameOrPassword_displayErrorMessage() throws Exception {
        String username = "";
        String password = "";
        signUpFragment.setUsernameAndPassword(username, password);
        signUpFragment.isUserPassEntered();
        Mockito.verify(view).displayUserOrPassCantBeEmptyMessage();
    }

    @Test
    public void test_validateUserCredentials_userEnteredTakenUsernameOrPassword_displayErrorMessage() throws Exception {
        String username = "Jaimeeee";
        String password = "kana";
        signUpFragment.setUsernameAndPassword(username, password);
        signUpFragment.isTakenUserPass();
        Mockito.verify(view).displayUserOrPassIsTakenMessage();
    }

    @Test
    public void test_validateUserCredentials_userEnteredNotTakenUsernameAndPassword_performApiCallToSignInUser() throws Exception {
        String username = "Jaime";
        String password = "kanay";
        signUpFragment.setUsernameAndPassword(username, password);
        signUpFragment.isTakenUserPass();
        Mockito.verify(view).displayWelcomeMessage();
    }

}
