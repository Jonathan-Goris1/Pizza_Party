package com.zybooks.pizzaparty;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public final static int SLICES_PER_PIZZA = 8;
    public final static int COST_PER_PIZZA = 16;
    public final static Double TAXES = 1.53;

    private EditText mNumAttendEditText;
    private EditText mFirstToppingEditText;
    private EditText mSecondToppingEditText;

    private TextView mSlicePizzaTextView;
    private TextView mNumPizzaTextView;
    private TextView mTotalCostTextView;
    private TextView mFoodCostTextView;
    private TextView mTaxesTextView;

    private RadioGroup mHowHungryRadioGroup;
    private RadioGroup mToppingRadioGroup;

    private View mLineDivider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign the widgets to fields
        mNumAttendEditText = findViewById(R.id.num_attend_edit_text);
        mSlicePizzaTextView = findViewById(R.id.total_slice_text_view);
        mNumPizzaTextView = findViewById(R.id.num_pizza_text_view);
        mHowHungryRadioGroup = findViewById(R.id.hungry_radio_group);
        mFirstToppingEditText = findViewById(R.id.topping1_edit_text);
        mSecondToppingEditText = findViewById(R.id.topping2_edit_text);
        mToppingRadioGroup = findViewById(R.id.topping_radio_group);
        mTotalCostTextView = findViewById(R.id.total_cost_text_view);
        mFoodCostTextView = findViewById(R.id.cost_food_text_view);
        mTaxesTextView = findViewById(R.id.taxes_text_view);
        mLineDivider = findViewById(R.id.line_divider);

    }


    public void calculateClick(View view) {
        // Get the text that was typed into the EditText
        String numAttendStr = mNumAttendEditText.getText().toString();
        String firstTopping = mFirstToppingEditText.getText().toString();
        String secondTopping = mSecondToppingEditText.getText().toString();

        int toppingCheckedId = mToppingRadioGroup.getCheckedRadioButtonId();

        if (numAttendStr.isEmpty()) {
            Toast.makeText(this, "Please enter number of people", Toast.LENGTH_SHORT).show();
        } else if (toppingCheckedId == R.id.topping_radio_button && (firstTopping.isEmpty() && secondTopping.isEmpty())) {
            Toast.makeText(this, "Please enter your toppings", Toast.LENGTH_SHORT).show();
        } else {

            // Convert the text into an integer
            int numAttend = Integer.parseInt(numAttendStr);

            // Determine how many slices on average each person will eat
            int slicesPerPerson = 0;
            int hungryCheckedId = mHowHungryRadioGroup.getCheckedRadioButtonId();

            if (hungryCheckedId == R.id.light_radio_button) {
                slicesPerPerson = 2;
            } else if (hungryCheckedId == R.id.medium_radio_button) {
                slicesPerPerson = 3;
            } else if (hungryCheckedId == R.id.ravenous_radio_button) {
                slicesPerPerson = 4;
            }

            // Calculate and show the number of pizzas needed
            int totalSlice = (int) Math.ceil(numAttend * slicesPerPerson / (double) SLICES_PER_PIZZA);
            int foodCost = COST_PER_PIZZA * totalSlice;
            double totalCost = foodCost + TAXES;


            mSlicePizzaTextView.setText("Total slice per person: " + totalSlice);
            mNumPizzaTextView.setText("Number of pizzas: " + totalSlice);
            mFoodCostTextView.setText("Food: $" + foodCost);
            mTaxesTextView.setText("Taxes: $" + TAXES);
            mTotalCostTextView.setText("Total: $" + totalCost);

            mFoodCostTextView.setVisibility(View.VISIBLE);
            mTaxesTextView.setVisibility(View.VISIBLE);
            mLineDivider.setVisibility(View.VISIBLE);
            mTotalCostTextView.setVisibility(View.VISIBLE);
        }
    }

    public void noTopping(View view) {
        mFirstToppingEditText.setVisibility(View.INVISIBLE);
        mSecondToppingEditText.setVisibility(View.INVISIBLE);
    }

    public void wantTopping(View view) {
        mFirstToppingEditText.setVisibility(View.VISIBLE);
        mSecondToppingEditText.setVisibility(View.VISIBLE);
    }

    public void saveClick(View view) {
        Toast.makeText(this, "Order has been saved", Toast.LENGTH_SHORT).show();
    }

    public void checkoutClick(View view) {

        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Thank You for placing an order");

        // Set Alert Title
        builder.setTitle("Checkout");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder.setPositiveButton(
                "Okay",
                (dialog, which) -> {

                    // When the user click yes button
                    // then app will close
                    finish();
                });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton(
                "Cancel",
                (dialog, which) -> {

                    // If user click no
                    // then dialog box is canceled.
                    dialog.cancel();
                });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();

    }
}