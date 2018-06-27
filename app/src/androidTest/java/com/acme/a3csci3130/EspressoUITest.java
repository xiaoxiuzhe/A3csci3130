package com.acme.a3csci3130;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class EspressoUITest{

    private DatabaseReference mDatabase;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);
    private MainActivity mainActivity;

    /**
     * This class is to clear the database, so every
     * testcases will start with a empty database.
     */
    @Before
    public void setUp() throws InterruptedException {
        mainActivity = mActivityRule.getActivity();

        mDatabase = FirebaseDatabase.getInstance().getReference("contacts");
        mDatabase.removeValue();
    }

    /**
     * Start with an empty database, store an object inside.
     * Has the size of the list increased?
     */
    @Test
    public void store_an_object() throws InterruptedException {
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.name)).perform(typeText("shawn"));
        onView(withId(R.id.Business_number)).perform(typeText("123456789"), closeSoftKeyboard());
        Thread.sleep(500);
        //leave everything else empty
        onView(withId(R.id.submitButton)).perform(click());
        Thread.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).check(matches(isDisplayed()));
    }

    /**
     * Start with an empty database, store an object inside.
     * Read it back from the database. Is it the object the
     * same one as the one I stored?
     */
    @Test
    public void match_data_is_the_same_as_I_stored() throws InterruptedException {
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.name)).perform(typeText("shawn"));
        onView(withId(R.id.Business_number)).perform(typeText("123456789"), closeSoftKeyboard());
        onView(withId(R.id.Primary_business)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Distributor"))).perform(click());
        onView(withId(R.id.Address)).perform(typeText("123 Unitvisty Rd"), closeSoftKeyboard());
        onView(withId(R.id.Province)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("NS"))).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        onView(withId(R.id.name)).check(matches(withText("shawn")));
        onView(withId(R.id.Business_number)).check(matches(withText("123456789")));
        onView(withId(R.id.Primary_business)).check(matches(withSpinnerText(containsString("Distributor"))));
        onView(withId(R.id.Address)).check(matches(withText("123 Unitvisty Rd")));
        onView(withId(R.id.Province)).check(matches(withSpinnerText(containsString("NS"))));

    }

    /**
     * Start with an empty database, store an object inside.
     * Read it back from the database, edit it, save it back
     * on the database, read from it. Is the object the same
     * as the one I edited and saved?
     */
    @Test
    public void match_data_is_the_same_as_I_edit() throws InterruptedException {
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.name)).perform(typeText("shawn"));
        onView(withId(R.id.Business_number)).perform(typeText("123456789"), closeSoftKeyboard());
        onView(withId(R.id.Primary_business)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Distributor"))).perform(click());
        onView(withId(R.id.Address)).perform(typeText("123 Unitvisty Rd"), closeSoftKeyboard());
        onView(withId(R.id.Province)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("NS"))).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        onView(withId(R.id.name)).perform(replaceText("bob"));
        onView(withId(R.id.Business_number)).perform(replaceText("987654321"), closeSoftKeyboard());
        onView(withId(R.id.Primary_business)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Fish Monger"))).perform(click());
        onView(withId(R.id.Address)).perform(replaceText("321 Unitvisty Rd"), closeSoftKeyboard());
        onView(withId(R.id.Province)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("BC"))).perform(click());
        onView(withId(R.id.updateButton)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        onView(withId(R.id.name)).check(matches(withText("bob")));
        onView(withId(R.id.Business_number)).check(matches(withText("987654321")));
        onView(withId(R.id.Primary_business)).check(matches(withSpinnerText(containsString("Fish Monger"))));
        onView(withId(R.id.Address)).check(matches(withText("321 Unitvisty Rd")));
        onView(withId(R.id.Province)).check(matches(withSpinnerText(containsString("BC"))));
    }

    /**
     * Start with an empty database, store an object inside.
     * Delete the object. Is the size of the list view zero?
     */
    @Test
    public void delete_an_object() throws InterruptedException {
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.name)).perform(typeText("shawn"));
        onView(withId(R.id.Business_number)).perform(typeText("123456789"), closeSoftKeyboard());
        Thread.sleep(500);
        //leave everything else empty
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.listView)).check(matches(isDisplayed()));
        Thread.sleep(1000);

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        onView(withId(R.id.deleteButton)).perform(click());

        onView(withId(R.id.listView)).check(matches(not(isDisplayed())));
    }

}