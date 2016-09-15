package ng.com.tinweb.www.languagetranslator.translator;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ng.com.tinweb.www.languagetranslator.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

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

    @Test
    public void testDropDownSelection() {
        onView(withId(R.id.fromSelectorSpinner)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("French"))).perform(click());

        onView(withId(R.id.toSelectorSpinner)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("English"))).perform(click());

        onView(withId(R.id.fromSelectorSpinner)).check(matches(withSpinnerText(containsString("French"))));
        onView(withId(R.id.toSelectorSpinner)).check(matches(withSpinnerText(containsString("English"))));
    }

    @Test
    public void testDropDownSelection_withTranslateAction() {

        // do selection
        onView(withId(R.id.fromSelectorSpinner)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("French"))).perform(click());

        onView(withId(R.id.toSelectorSpinner)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("English"))).perform(click());

        // do translation action
        onView(withId(R.id.fromInputEditText)).perform(typeText("je suis"));

        onView(withId(R.id.translateButton)).perform(click());

        onView(withId(R.id.toOutputTextView)).check(matches(withText("I am")));

    }

    @Test
    public void testTranslationSpinnerSwitch() {
        onView(withId(R.id.switchTranslationImageView)).perform(click());

        onView(withId(R.id.fromSelectorSpinner)).check(matches(withSpinnerText(containsString("French"))));
        onView(withId(R.id.toSelectorSpinner)).check(matches(withSpinnerText(containsString("English"))));

    }

}