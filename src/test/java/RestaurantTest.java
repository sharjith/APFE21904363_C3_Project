import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    //DONE REFACTOR ALL THE REPEATED LINES OF CODE
    LocalTime openingTime;
    LocalTime closingTime;
    int initialMenuSize;

    Restaurant spiedRestaurant;

    @BeforeEach
    public void initializeRestaurant() {
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        initialMenuSize = restaurant.getMenu().size();

        spiedRestaurant = Mockito.spy(restaurant);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        //DONE WRITE UNIT TEST CASE HERE

        // Between working hours
        LocalTime betweenWorkingHours = LocalTime.of(11, 30, 0);
        // Edge case - exact opening time
        LocalTime exactOpeningTime = LocalTime.of(10, 30, 0);

        // Mock the current time
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(betweenWorkingHours, exactOpeningTime);

        // Between working hours
        assertTrue(spiedRestaurant.isRestaurantOpen());
        // Edge case - exact opening time
        assertTrue(spiedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        //DONE WRITE UNIT TEST CASE HERE

        // Before opening hours
        LocalTime beforeOpeningHours = LocalTime.of(9, 30, 0);
        // After closing hours
        LocalTime afterClosingHours = LocalTime.of(23, 0, 0);
        // Edge case - exact closing time
        LocalTime exactClosingTime = LocalTime.of(22, 0, 0);

        // Mock the current time
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(beforeOpeningHours, afterClosingHours, exactClosingTime);

        // Before opening hours
        assertFalse(spiedRestaurant.isRestaurantOpen());
        // After closing hours
        assertFalse(spiedRestaurant.isRestaurantOpen());
        // Edge case - exact closing time
        assertFalse(spiedRestaurant.isRestaurantOpen());
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {

        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>TOTAL PRICE<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void selecting_items_from_menu_should_return_the_total_price() {
        List<String> selectedItems = new ArrayList<String>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");

        int expectedTotal = 119 + 269;
        int totalPrice = restaurant.getOrderValue(selectedItems);
        assertEquals(expectedTotal, totalPrice);
    }

    @Test
    public void selecting_no_items_from_menu_should_return_zero_total_price(){
        List<String> selectedItems = new ArrayList<String>();
        int totalPrice = restaurant.getOrderValue(selectedItems);
        assertEquals(0, totalPrice);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}