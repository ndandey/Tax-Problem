package com.ndandey.app.taxproblem;

import com.ndandey.app.taxproblem.addItem.AddItemActivity;
import com.ndandey.app.taxproblem.listitem.view.ListItemActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

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
public class TaxProblemTest {
    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly
     * access the activity during the test.
     */
    @Rule public ActivityTestRule<ListItemActivity> mActivityTestRule = new ActivityTestRule<>(ListItemActivity.class);
    
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
     * This test verifies the input1 and output1.
     * Input 1: 1 book at 12.49
     * 1 music CD at 14.99
     * 1 chocolate bar at 0.85
     * Output 1:
     * 1 book : 12.49
     * 1 music CD: 16.49
     * 1 chocolate bar: 0.85
     * Sales Taxes: 1.50
     * Total: 29.83
     */
    @Test
    public void testInput1() {
        addShoppingItem("Book", "12.49", Boolean.FALSE, Boolean.TRUE, 0, 1);
        addShoppingItem("Music CD", "14.99", Boolean.FALSE, Boolean.FALSE, 1, 1);
        addShoppingItem("Chocolate Bar", "0.85", Boolean.FALSE, Boolean.TRUE, 2, 1);
        openOptionsMenu(R.string.check_out);
        onView((withId(R.id.totalSalesTaxValueTextView))).check(matches(withText("1.50")));
        onView((withId(R.id.totalPriceValueTextView))).check(matches(withText("29.83")));
    }
    
    /**
     * This test verifies the input2 and output2.
     * Input 2:
     * 1 imported box of chocolates at 10.00
     * 2 imported bottles of perfume at 47.25
     * <p>
     * Output 2:
     * 1 imported box of chocolates: 10.50
     * 2 imported bottles of perfume: 108.70
     * <p>
     * Sales Taxes: 14.70
     * Total: 119.20
     */
    @Test
    public void testInput2() {
        addShoppingItem("imported chocolates", "10.00", Boolean.TRUE, Boolean.TRUE, 0, 1);
        addShoppingItem("imported perfume", "47.25", Boolean.TRUE, Boolean.FALSE, 1, 2);
        openOptionsMenu(R.string.check_out);
        onView((withId(R.id.totalSalesTaxValueTextView))).check(matches(withText("14.70")));
        onView((withId(R.id.totalPriceValueTextView))).check(matches(withText("119.20")));
    }
    
    /**
     * This test verifies the input3 and output3.
     * Input 3:
     * 1 imported bottle of perfume at 27.99
     * 1 bottle of perfume at 18.99
     * 1 packet of headache pills at 9.75
     * 1 box of imported chocolates at 11.25
     * <p>
     * Output 3:
     * 1 imported bottle of perfume: 32.19
     * 1 bottle of perfume: 20.89
     * 1 packet of headache pills: 9.75
     * 1 imported box of chocolates: 11.85
     * <p>
     * Sales Taxes: 6.70
     * Total: 74.68
     */
    @Test
    public void testInput3() {
        addShoppingItem("imported perfume", "27.99", Boolean.TRUE, Boolean.FALSE, 0, 1);
        addShoppingItem("perfume bottle", "18.99", Boolean.FALSE, Boolean.FALSE, 1, 1);
        addShoppingItem("headache pills", "9.75", Boolean.FALSE, Boolean.TRUE, 2, 1);
        addShoppingItem("imported chocolates", "11.25", Boolean.TRUE, Boolean.TRUE, 3, 1);
        
        openOptionsMenu(R.string.check_out);
        onView((withId(R.id.totalSalesTaxValueTextView))).check(matches(withText("6.70")));
        onView((withId(R.id.totalPriceValueTextView))).check(matches(withText("74.68")));
    }
    
    private void addShoppingItem(String itemName, String itemPrice, Boolean isImported, Boolean isExempted, int childPosition, int qty) {
        openOptionsMenu(R.string.add_shopping_item);
        onView((withId(R.id.itemName))).perform(typeText(itemName));
        onView((withId(R.id.itemPrice))).perform(typeText(itemPrice));
        if (isImported) {
            onView((withId(R.id.isImported))).perform(click());
        }
        if (isExempted) {
            onView((withId(R.id.isExempted))).perform(click());
        }
        onView((withId(R.id.action_save))).perform(click());
        for (int i = 0; i < qty; i++) {
            clickOnImageViewAtRow(childPosition);
        }
    }
    
    private void openOptionsMenu(int resourceId) {
        onView(withContentDescription("More options")).perform(click());
        onView((withText(resourceId))).perform(click());
    }
    
    public void clickOnImageViewAtRow(int position) {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(position, new ClickOnImageView()));
    }
    
    public class ClickOnImageView implements ViewAction {
        android.support.test.espresso.ViewAction click = click();
        
        @Override
        public Matcher<View> getConstraints() {
            return click.getConstraints();
        }
        
        @Override
        public String getDescription() {
            return " click on custom image view";
        }
        
        @Override
        public void perform(UiController uiController, View view) {
            click.perform(uiController, view.findViewById(R.id.cart_plus_img));
        }
    }
    
}
