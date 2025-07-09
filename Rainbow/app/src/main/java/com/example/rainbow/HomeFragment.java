package com.example.rainbow;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (!isInEditMode(view)) {
            // Setup Quantity Controls for static cards
            setupQuantityControls(view.findViewById(R.id.plusBtn1_1), view.findViewById(R.id.minusBtn1_1), view.findViewById(R.id.counterText1_1));
            setupQuantityControls(view.findViewById(R.id.plusBtn1_2), view.findViewById(R.id.minusBtn1_2), view.findViewById(R.id.counterText1_2));
            setupQuantityControls(view.findViewById(R.id.plusBtn1_3), view.findViewById(R.id.minusBtn1_3), view.findViewById(R.id.counterText1_3));
            setupQuantityControls(view.findViewById(R.id.plusBtn2_1), view.findViewById(R.id.minusBtn2_1), view.findViewById(R.id.counterText2_1));
            setupQuantityControls(view.findViewById(R.id.plusBtn2_2), view.findViewById(R.id.minusBtn2_2), view.findViewById(R.id.counterText2_2));
            setupQuantityControls(view.findViewById(R.id.plusBtn2_3), view.findViewById(R.id.minusBtn2_3), view.findViewById(R.id.counterText2_3));
            setupQuantityControls(view.findViewById(R.id.plusBtn3_1), view.findViewById(R.id.minusBtn3_1), view.findViewById(R.id.counterText3_1));
            setupQuantityControls(view.findViewById(R.id.plusBtn3_2), view.findViewById(R.id.minusBtn3_2), view.findViewById(R.id.counterText3_2));
            setupQuantityControls(view.findViewById(R.id.plusBtn3_3), view.findViewById(R.id.minusBtn3_3), view.findViewById(R.id.counterText3_3));
            setupQuantityControls(view.findViewById(R.id.plusBtn4_1), view.findViewById(R.id.minusBtn4_1), view.findViewById(R.id.counterText4_1));
            setupQuantityControls(view.findViewById(R.id.plusBtn4_2), view.findViewById(R.id.minusBtn4_2), view.findViewById(R.id.counterText4_2));
            setupQuantityControls(view.findViewById(R.id.plusBtn4_3), view.findViewById(R.id.minusBtn4_3), view.findViewById(R.id.counterText4_3));


            // Start countdown timers
            startCountdown(3 * 24 * 60 * 60 * 1000L,
                    view.findViewById(R.id.flashDays1),
                    view.findViewById(R.id.flashHours1),
                    view.findViewById(R.id.flashMin1),
                    view.findViewById(R.id.flashSec1));

            startCountdown(1 * 24 * 60 * 60 * 1000L,
                    view.findViewById(R.id.flashDays2),
                    view.findViewById(R.id.flashHours2),
                    view.findViewById(R.id.flashMin2),
                    view.findViewById(R.id.flashSec2));

            // Setup Recommended for you static data cards
            setupRecommendedCards(view);
        }

        return view;
    }

    private boolean isInEditMode(View view) {
        return view.isInEditMode();
    }

    private void setupQuantityControls(ImageView plusBtn, ImageView minusBtn, TextView counterText) {
        if (plusBtn == null || minusBtn == null || counterText == null) return;

        final int[] counter = {0};

        plusBtn.setOnClickListener(v -> {
            counter[0]++;
            counterText.setText(String.valueOf(counter[0]));
            counterText.setVisibility(View.VISIBLE);
            minusBtn.setVisibility(View.VISIBLE);
        });

        minusBtn.setOnClickListener(v -> {
            if (counter[0] > 0) {
                counter[0]--;
                counterText.setText(String.valueOf(counter[0]));
                if (counter[0] == 0) {
                    counterText.setVisibility(View.GONE);
                    minusBtn.setVisibility(View.GONE);
                }
            }
        });
    }

    private void fetchProducts() {
        String url = "https://rainbow-three-khaki.vercel.app/api/products";
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        LinearLayout container = getView().findViewById(R.id.dynamicProductContainer);
                        LayoutInflater inflater = LayoutInflater.from(getContext());

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject product = response.getJSONObject(i);

                            View card = inflater.inflate(R.layout.item_product_card, container, false);

                            ImageView image = card.findViewById(R.id.productImage);
                            TextView name = card.findViewById(R.id.productName);
                            TextView price = card.findViewById(R.id.productPrice);
                            ImageView plusBtn = card.findViewById(R.id.plusButton);
                            ImageView minusBtn = card.findViewById(R.id.minusButton);
                            TextView counter = card.findViewById(R.id.quantityCounter);

                            String imageUrl = product.getString("image");
                            String productName = product.getString("name");
                            String productPrice = product.getString("price");

                            name.setText(productName);
                            price.setText(productPrice + " PKR");

                            Glide.with(getContext()).load(imageUrl).into(image);

                            // Counter logic
                            final int[] quantity = {0};

                            plusBtn.setOnClickListener(v -> {
                                quantity[0]++;
                                counter.setText(String.valueOf(quantity[0]));
                                counter.setVisibility(View.VISIBLE);
                                minusBtn.setVisibility(View.VISIBLE);
                            });

                            minusBtn.setOnClickListener(v -> {
                                if (quantity[0] > 0) {
                                    quantity[0]--;
                                    counter.setText(String.valueOf(quantity[0]));
                                    if (quantity[0] == 0) {
                                        counter.setVisibility(View.GONE);
                                        minusBtn.setVisibility(View.GONE);
                                    }
                                }
                            });

                            container.addView(card);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.e("API_ERROR", error.toString()));

        queue.add(jsonArrayRequest);
    }


    private void startCountdown(long timeInMillis, TextView daysText, TextView hoursText, TextView minutesText, TextView secondsText) {
        if (daysText == null || hoursText == null || minutesText == null || secondsText == null) return;

        new CountDownTimer(timeInMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                long totalSeconds = millisUntilFinished / 1000;
                long d = totalSeconds / (24 * 3600);
                totalSeconds %= (24 * 3600);
                long h = totalSeconds / 3600;
                totalSeconds %= 3600;
                long m = totalSeconds / 60;
                long s = totalSeconds % 60;

                daysText.setText(d + " Days");
                hoursText.setText(h + " Hours");
                minutesText.setText(m + " Min");
                secondsText.setText(s + " Sec");
            }

            public void onFinish() {
                daysText.setText("0 Days");
                hoursText.setText("0 Hours");
                minutesText.setText("0 Min");
                secondsText.setText("0 Sec");
            }
        }.start();
    }

    private void setupRecommendedCards(View view) {
        LinearLayout recommendedContainer = view.findViewById(R.id.recommendedLinearLayout1);

        List<Product> staticProducts = new ArrayList<>();
        staticProducts.add(new Product(R.drawable.potato, "Potato 1KG", "80 PKR"));
        staticProducts.add(new Product(R.drawable.onion, "Onion 500G", "50 PKR"));
        staticProducts.add(new Product(R.drawable.tomato, "Tomato 1KG", "70 PKR"));
        staticProducts.add(new Product(R.drawable.carrots, "Carrot 1KG", "120 PKR"));
        staticProducts.add(new Product(R.drawable.lemons, "Lemon 250G", "60 PKR"));

        for (Product product : staticProducts) {
            View cardView = LayoutInflater.from(getContext()).inflate(R.layout.product_card, recommendedContainer, false);

            ImageView imageView = cardView.findViewById(R.id.productImage);
            TextView titleView = cardView.findViewById(R.id.productTitle);
            TextView priceView = cardView.findViewById(R.id.productPrice);
            ImageView plusBtn = cardView.findViewById(R.id.plusBtn);
            ImageView minusBtn = cardView.findViewById(R.id.minusBtn);
            TextView counterText = cardView.findViewById(R.id.counterText);

            imageView.setImageResource(product.imageResId);
            titleView.setText(product.title);
            priceView.setText(product.price);

            setupQuantityControls(plusBtn, minusBtn, counterText);

            recommendedContainer.addView(cardView);
        }
    }
}

