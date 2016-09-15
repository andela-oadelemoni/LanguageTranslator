package ng.com.tinweb.www.languagetranslator.launcher;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ng.com.tinweb.www.languagetranslator.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by kamiye on 15/09/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FullscreenActivityUITest {

    @Rule
    public ActivityTestRule<FullscreenActivity> activityTestRule =
            new ActivityTestRule<>(FullscreenActivity.class);

    @Before
    public void restartActivity() {
        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityTestRule.getActivity().recreate();
            }
        });
    }

    @Test
    public void testFetchingTextVisible() {
        onView(withId(R.id.fetchStatusTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void testError_and_button_invisible() {
        onView(withId(R.id.errorMessageTextView)).check(doesNotExist());
        onView(withId(R.id.proceedButton)).check(doesNotExist());
    }
}