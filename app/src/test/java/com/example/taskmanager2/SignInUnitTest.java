package com.example.taskmanager2;

import android.content.Context;

import com.example.taskmanager2.controller.activity.SignInActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SignInUnitTest {


    private static final String FAKE_STRING = "Login was successful";

    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {

        SignInActivity myObjectUnderTest = new SignInActivity(mMockContext);

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.validate("user", "user");

        // ...then the result should be the expected one.
        assertThat(result, is(FAKE_STRING));
    }

}
