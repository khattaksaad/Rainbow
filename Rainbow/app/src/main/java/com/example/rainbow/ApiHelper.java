package com.example.rainbow;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class ApiHelper {

    private static ApiHelper instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private ApiHelper(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    // This ensures only one instance exists
    public static synchronized ApiHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ApiHelper(context.getApplicationContext());
        }
        return instance;
    }

    // Create and return a single Volley request queue
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    // Method to fetch products from API
    public void fetchProducts(ApiResponseCallback callback) {
        String url = "https://rainbow-three-khaki.vercel.app/api/products";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                callback::onSuccess,
                error -> callback.onError(error.toString())
        );

        getRequestQueue().add(jsonArrayRequest);
    }

    // Interface for callbacks
    public interface ApiResponseCallback {
        void onSuccess(JSONArray response);
        void onError(String error);
    }
}
