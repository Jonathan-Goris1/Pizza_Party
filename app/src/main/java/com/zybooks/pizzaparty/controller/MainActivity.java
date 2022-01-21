package com.zybooks.pizzaparty.controller;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.pizzaparty.R;
import com.zybooks.pizzaparty.databinding.ActivityMainBinding;
import com.zybooks.pizzaparty.model.PizzaCalculator;

public class MainActivity extends AppCompatActivity {


    public final static double TAXES = 1.53;


    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Assign the widgets to fields
        binding.numAttendEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    public void calculateClick(View view) {

        //Get how many are attending the party
        int numAttend;
        try {
            String numAttendStr = binding.numAttendEditText.getText().toString();
            numAttend = Integer.parseInt(numAttendStr);
        } catch (NumberFormatException ex) {
            numAttend = 0;
            Toast.makeText(this, "Please enter number of people", Toast.LENGTH_SHORT).show();
        }

        // Get hunger level selection
        int checkedId = binding.hungryRadioGroup.getCheckedRadioButtonId();
        PizzaCalculator.HungerLevel hungerLevel = PizzaCalculator.HungerLevel.RAVENOUS;
        if (checkedId == R.id.light_radio_button) {
            hungerLevel = PizzaCalculator.HungerLevel.LIGHT;
        } else if (checkedId == R.id.medium_radio_button) {
            hungerLevel = PizzaCalculator.HungerLevel.MEDIUM;
        }

        // Get the number of pizzas needed
        PizzaCalculator calc = new PizzaCalculator(numAttend, hungerLevel);
        int totalPizzas = calc.getTotalPizzas();
        double foodCost = calc.getFoodCost();
        double totalCost = calc.getTotalCost();

        // Place totalPizzas into the string resource and display
        String totalNumPizzaText = getString(R.string.total_pizzas, totalPizzas);
        binding.numPizzaTextView.setText(totalNumPizzaText);

        String foodCostText = getString(R.string.food_text_plus_total, foodCost);
        binding.costFoodTextView.setText(foodCostText);
        binding.costFoodTextView.setVisibility(View.VISIBLE);

        String taxesText = getString(R.string.taxes_text_plus_total, TAXES);
        binding.taxesTextView.setText(taxesText);
        binding.taxesTextView.setVisibility(View.VISIBLE);

        binding.lineDivider.setVisibility(View.VISIBLE);

        String totalText = getString(R.string.total_text_plus_total, totalCost);
        binding.totalCostTextView.setText(totalText);
        binding.totalCostTextView.setVisibility(View.VISIBLE);


        //}
    }


    public void noTopping(View view) {
        binding.topping1EditText.setVisibility(View.INVISIBLE);
        binding.topping2EditText.setVisibility(View.INVISIBLE);
    }

    public void wantTopping(View view) {
        binding.topping1EditText.setVisibility(View.VISIBLE);
        binding.topping2EditText.setVisibility(View.VISIBLE);
    }

    public void saveClick(View view) {
        Toast.makeText(this, "Order has been saved", Toast.LENGTH_SHORT).show();
    }

    public void checkoutClick(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

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

        builder.setPositiveButton("Okay", (dialog, which) -> {

            // When the user click yes button
            // then app will close
            finish();
        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton("Cancel", (dialog, which) -> {

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