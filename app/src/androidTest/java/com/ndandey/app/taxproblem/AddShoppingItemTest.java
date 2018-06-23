package com.ndandey.app.taxproblem;

import com.ndandey.app.taxproblem.addItem.AddItemActivity;
import com.ndandey.app.taxproblem.listitem.view.ListItemActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by ndandey on 6/22/2018.
 */
@RunWith(AndroidJUnit4.class)
public class AddShoppingItemTest {
    public static final String TEA_NAME = "Green Tea";
    
    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly
     * access the activity during the test.
     */
    @Rule
    public ActivityTestRule<ListItemActivity> mActivityTestRule = new ActivityTestRule<>(ListItemActivity.class);
    
    /*
     *  This test verifies that a toast message needs to be displayed when you provide null values to Item Name and Item Price.
     */
    @Test
    public void AddShoppingItem_with_null_values() {
        openOptionsMenu(R.string.add_shopping_item);
        onView((withId(R.id.action_save))).perform(click());
        onView(allOf(withId(R.string.missing_fields), isDisplayed()));
    }
    
    /**
     * This test verifies that when you provide valid values for Item Name and Item Value, the record is successfully added.
     */
    @Test
    public void AddShoppingItem_with_valid_values() {
        addShoppingItem("Book","12.49", Boolean.FALSE, Boolean.TRUE);
        addShoppingItem("Music CD","14.99", Boolean.FALSE, Boolean.FALSE);
        addShoppingItem("chocolate","0.85", Boolean.FALSE, Boolean.TRUE);
        openOptionsMenu(R.string.check_out);
    }
    
    private void addShoppingItem(String itemName, String itemPrice, Boolean isImported, Boolean isExempted) {
        openOptionsMenu(R.string.add_shopping_item);
        onView((withId(R.id.itemName))).perform(typeText(itemName));
        onView((withId(R.id.itemPrice))).perform(typeText(itemPrice));
        if(isImported) {
            onView((withId(R.id.isImported))).perform(click());
        }
        if(isExempted) {
            onView((withId(R.id.isExempted))).perform(click());
        }
        onView((withId(R.id.action_save))).perform(click());
//        onView(withId(R.id.recyclerView)).perform(
//                RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }
    
    private void openOptionsMenu(int resourceId) {
        onView(withContentDescription("More options")).perform(click());
        onView((withText(resourceId))).perform(click());
    }
    
}
