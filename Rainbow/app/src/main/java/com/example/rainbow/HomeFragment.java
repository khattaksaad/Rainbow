package com.example.rainbow;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;



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
    LinearLayout recommendedLayout;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recommendedLayout = view.findViewById(R.id.recommendedLinearLayout1);
        loadRecommendedProducts();


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


    private void startCountdown(long timeInMillis, TextView daysText, TextView hoursText, TextView minutesText, TextView secondsText) {
        if (daysText == null || hoursText == null || minutesText == null || secondsText == null)
            return;

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
    private void loadRecommendedProducts() {
        String url = "https://rainbow-three-khaki.vercel.app/api/products";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d("API_RESPONSE", response.toString());
                    recommendedLayout.removeAllViews();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            String name = obj.getString("name");
                            int price = obj.getInt("price");
                            String imageUrl = obj.getString("image");

                            View card = LayoutInflater.from(getContext())
                                    .inflate(R.layout.card_recommended, recommendedLayout, false);

                            TextView nameView = card.findViewById(R.id.productName);
                            TextView priceView = card.findViewById(R.id.productPrice);
                            ImageView imageView = card.findViewById(R.id.productImage);

                            nameView.setText(name);
                            priceView.setText(price + " PKR");
                            Picasso.get().load(imageUrl).into(imageView);

                            recommendedLayout.addView(card);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("API_ERROR", error.toString());
                    error.printStackTrace();
                }
        );

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(jsonArrayRequest);
    }


}
