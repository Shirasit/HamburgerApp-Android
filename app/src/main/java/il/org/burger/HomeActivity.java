package il.org.burger;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    public static int totalPrice;
    static int numOfDynSpinner;

    ArrayList<BurgerTopItem> BurgerTopsList;

    String price;
    LinearLayout TopLayout;

    EditText pizzaNumET;
    TextView priceTV;
    EditText addressET;
    EditText fullNameET;
    EditText phoneNumberET;

    RadioGroup radioBtnGroupSize;
    RadioGroup radioBtnGroupDelivery;
    RadioButton delivery_Rbtn;
    RadioButton self_Rbtn;

    Button okbtn;
    Button submit_btn;
    CheckBox cheeseExstra;
    CheckBox BulgarianExtra;
    CheckBox PizzaScExtra;
    CheckBox TunaExtra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        totalPrice=0;
        numOfDynSpinner=0;
        initTopsItems();

        // ------------LinearLayout for Dynamic spinners------------------//
        TopLayout = findViewById(R.id.spinnerLayout);

        //---------------------RadioButtons-------------------------------//
        radioBtnGroupDelivery = findViewById(R.id.delivery_pickup_RB);
        radioBtnGroupSize = findViewById(R.id.groupradio);

        delivery_Rbtn = findViewById(R.id.deliveryRB);
        self_Rbtn = findViewById(R.id.selfpickupRB);

        //------------------------Buttons----------------------------//
        okbtn = findViewById(R.id.okBtn); //button for dynamic spinners
        submit_btn = findViewById((R.id.submit_btn)); //button for 'save' the order

        radioBtnGroupDelivery.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.deliveryRB:
                        addressET.setVisibility(View.VISIBLE);
                        fullNameET.setVisibility(View.VISIBLE);
                        phoneNumberET.setVisibility(View.VISIBLE);
                        break;
                    case R.id.selfpickupRB:
                        addressET.setVisibility(View.GONE);
                        fullNameET.setVisibility(View.VISIBLE);
                        phoneNumberET.setVisibility(View.VISIBLE);

                        break;
                }
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((numOfDynSpinner) < 0) { //no top chosen
                    Toast.makeText(HomeActivity.this, R.string.error_Tops_num_hint, Toast.LENGTH_SHORT).show();
                }
                else if (!delivery_Rbtn.isChecked() && !self_Rbtn.isChecked()) { //no type order selected
                    Toast.makeText(HomeActivity.this, R.string.Error_delivery_hint, Toast.LENGTH_SHORT).show();
                }
                else if (delivery_Rbtn.isChecked() && addressET.getText().toString().isEmpty() || phoneNumberET.getText().toString().isEmpty() || fullNameET.getText().toString().isEmpty()) {
                        Toast.makeText(HomeActivity.this, R.string.Error_sent_order, Toast.LENGTH_SHORT).show();
                    }
                else if (self_Rbtn.isChecked() && phoneNumberET.getText().toString().isEmpty() || fullNameET.getText().toString().isEmpty()) {
                            Toast.makeText(HomeActivity.this, R.string.Error_sent_order, Toast.LENGTH_SHORT).show();
                        }
                else if(phoneNumberET.length()!= 10) {
                    Toast.makeText(HomeActivity.this,R.string.error_length_phone_hint,Toast.LENGTH_SHORT).show();
                }

                else {// if type order chosen & all the fields are fill
                        AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
                        alert.setTitle("         :)             ");
                        alert.setMessage(R.string.alertMessageHint);
                        alert.setPositiveButton(R.string.finishAlertBtnHint, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intnet1 = new Intent(HomeActivity.this, HomeActivity.class);
                                startActivity(intnet1); //'restart' the home page
                                finish();
                            }
                        });
                        alert.setNegativeButton(R.string.moveToOrderHint, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(HomeActivity.this, LastActivity.class);
                                intent.putExtra("Full name", fullNameET.getText().toString());

                                if(delivery_Rbtn.isChecked()) { //for delivery the customer need to fill address
                                    intent.putExtra("Address", addressET.getText().toString());
                                }
                                else {
                                    addressET.setText(R.string.no_address_hint);
                                    intent.putExtra("Address", addressET.getText().toString());
                                }
                                intent.putExtra("phone number", phoneNumberET.getText().toString());
                                intent.putExtra("Total price", priceTV.getText().toString());
                                startActivity(intent); //move to next page
                                finish();
                            }
                        });
                        // create and show the alert dialog
                        AlertDialog dialog = alert.create();
                        alert.show();
                    }
                }



        });


        //----------------------Edit Text---------------------------------//
        pizzaNumET = findViewById(R.id.numOfTops);
        fullNameET = findViewById(R.id.fullNameET);
        addressET = findViewById(R.id.addressET);
        phoneNumberET= findViewById(R.id.phoneNumberET);

        //--------------------Dynamic spinners-----------------------------//
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numOfFTops = pizzaNumET.getText().toString();
                if(radioBtnGroupSize.getCheckedRadioButtonId() == -1)
                    Toast.makeText(HomeActivity.this, R.string.Error_size_hint, Toast.LENGTH_SHORT).show();
                else if (!numOfFTops.isEmpty()) {
                    // Closing keyboard //
                    try {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    numOfDynSpinner = Integer.parseInt(numOfFTops);
                    TopLayout.removeAllViews();
                    if(numOfDynSpinner>=7)
                    {
                        Toast.makeText(HomeActivity.this,R.string.too_much_Tops_hint,Toast.LENGTH_SHORT).show();
                    }
                    else{
                    for (int i = 0; i < numOfDynSpinner; i++) {
                        //new spinner
                        Spinner spinner = new Spinner(HomeActivity.this);
                        spinner.setBackgroundResource(R.drawable.spinner_outline_background);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(20, 10, 20, 0);
                        spinner.setLayoutParams(params);
                        spinner.setPadding(10, 10, 10, 10);
                        TopsAdapter topsAdapter = new TopsAdapter(HomeActivity.this, BurgerTopsList);
                        spinner.setPopupBackgroundResource(R.drawable.spinner_outline);
                        topsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(topsAdapter);

                        TopLayout.addView(spinner);
                    }
                }
                } else{
                    Toast.makeText(HomeActivity.this, R.string.Error_fill_top, Toast.LENGTH_SHORT).show(); //localization
                }

            }
        });

        //--TextView--//
        priceTV = findViewById(R.id.orderTotalPrice);


        //--Checkboxes--//
        cheeseExstra = findViewById(R.id.chips_extra);
        BulgarianExtra = findViewById(R.id.onionRings_extra);
        TunaExtra = findViewById(R.id.ketchup);
        PizzaScExtra = findViewById(R.id.mayo);

        //------Weight of ice cream chooser------//
        radioBtnGroupSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            final String price = priceTV.getText().toString();
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.mini_burger:
                        if(totalPrice >= 55){
                            totalPrice -= 55;
                            totalPrice+= Integer.parseInt(price) + 30;
                        } else {
                            totalPrice+= Integer.parseInt(price) + 30;
                        }
                        break;

                    case R.id.big_burger:
                        if(totalPrice >= 30){
                            totalPrice -= 30;
                            totalPrice+= Integer.parseInt(price) + 55;
                        } else {
                            totalPrice+= Integer.parseInt(price) + 55;
                        }

                        break;
                }
                priceTV.setText(totalPrice+"");
            }
        });

        //------Delivery or self pickUp (Visible/Gone)------//



        cheeseExstra.setOnCheckedChangeListener(this);
        BulgarianExtra.setOnCheckedChangeListener(this);
        TunaExtra.setOnCheckedChangeListener(this);
        PizzaScExtra.setOnCheckedChangeListener(this);
    }

    //-------------Update price by checking which checkbox was selected--------------------//
    public void selectItem(View v) {
        price = priceTV.getText().toString();
        //is the view now checked?
        boolean checked = ((CheckBox) v).isChecked();
        //check which checkbox was clicked
        switch (v.getId()) {

            case R.id.chips_extra:
                if (checked)
                    totalPrice += 10;
                else
                    totalPrice -= 10;
                price = Integer.toString(totalPrice);
                priceTV.setText(price+"");

                break;
            case R.id.ketchup:
                if (checked)
                    totalPrice += 2;
                else
                    totalPrice -= 2;
                price = Integer.toString(totalPrice);
                priceTV.setText(price+"");

                break;
            case R.id.mayo:
                if (checked)
                    totalPrice += 2;
                else
                    totalPrice -= 2;
                price = Integer.toString(totalPrice);
                priceTV.setText(price+"");

                break;
            case R.id.onionRings_extra:
                if (checked)
                    totalPrice +=12;
                else
                    totalPrice = totalPrice - 12;
                price = Integer.toString(totalPrice);
                priceTV.setText(price+"");

                break;
        }
    }


    @Override
    public void onClick(View v) {

    }


    private void initTopsItems(){
        BurgerTopsList = new ArrayList<>();
      //  PizzaList.add(new PizzaTopItem(getString(R.string.without_extras_hint), R.drawable.withoutextras));
        BurgerTopsList.add(new BurgerTopItem(getString(R.string.pickle_hint), R.drawable.pickle));
        BurgerTopsList.add(new BurgerTopItem(getString(R.string.mushrooms_hint),R.drawable.mushroom));
        BurgerTopsList.add(new BurgerTopItem(getString(R.string.tomatoes_hint),R.drawable.tomatos));
        BurgerTopsList.add(new BurgerTopItem(getString(R.string.hasa_hint),R.drawable.hasa));
        BurgerTopsList.add(new BurgerTopItem(getString(R.string.onion_hint),R.drawable.onion));
        BurgerTopsList.add(new BurgerTopItem(getString(R.string.hot_pepper_hint),R.drawable.hotpepper));

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}




