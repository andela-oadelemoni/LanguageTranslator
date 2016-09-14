package ng.com.tinweb.www.languagetranslator.translator;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ng.com.tinweb.www.languagetranslator.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by kamiye on 14/09/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityUITest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

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
    public void testUIVisible() {
        onView(withId(R.id.fromInputEditText)).check(matches(isDisplayed()));

        onView(withId(R.id.translateButton)).check(matches(isDisplayed()));

        onView(withId(R.id.toOutputTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void testDefaultTranslateAction() {
        onView(withId(R.id.fromInputEditText)).perform(typeText("i am"));

        onView(withId(R.id.translateButton)).perform(click());

        onView(withId(R.id.toOutputTextView)).check(matches(withText("je suis")));
    }

}