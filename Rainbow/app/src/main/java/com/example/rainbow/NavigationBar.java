package com.example.rainbow;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class NavigationBar extends AppCompatActivity {

    ImageView hamburgerIcon, navHome, navFavorites, navCart, navNotification, navProfile;
    TextView pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);

        // Top bar elements
        hamburgerIcon = findViewById(R.id.hamburgerIcon);
        pageTitle = findViewById(R.id.pageTitle);

        // Bottom nav icons
        navHome = findViewById(R.id.navHome);
        navFavorites = findViewById(R.id.navFavorites);
        navCart = findViewById(R.id.navCart);
        navNotification = findViewById(R.id.navNotification);
        navProfile = findViewById(R.id.navProfile);

        // Set click listener for hamburgerIcon to open popup menu
        hamburgerIcon.setOnClickListener(v -> showMenu(v));

        // Set initial fragment to Home
        loadFragment(new HomeFragment());
        pageTitle.setText("Home");

        // Set bottom nav click listeners
        setupBottomNav();
    }

    // Function to show popup menu
    private void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenuInflater().inflate(R.menu.top_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_home) {
                loadFragment(new HomeFragment());
                pageTitle.setText("Home");
                return true;
            } else if (itemId == R.id.menu_favorites) {
                loadFragment(new DescriptionFragment());
                pageTitle.setText("Favorites");
                return true;
            } else if (itemId == R.id.menu_cart) {
                loadFragment(new DescriptionFragment());
                pageTitle.setText("Cart");
                return true;
            } else if (itemId == R.id.menu_notifications) {
                loadFragment(new DescriptionFragment());
                pageTitle.setText("Notifications");
                return true;
            } else if (itemId == R.id.menu_profile) {
                loadFragment(new DescriptionFragment());
                pageTitle.setText("Profile");
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    // Function to load fragments into contentFrame
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentFrame, fragment);
        transaction.commit();
    }

    // Bottom nav icon click handlers
    private void setupBottomNav() {
        navHome.setOnClickListener(v -> {
            loadFragment(new HomeFragment());
            pageTitle.setText("Home");
        });

        navFavorites.setOnClickListener(v -> {
            loadFragment(new DescriptionFragment());
            pageTitle.setText("Favorites");
        });

        navCart.setOnClickListener(v -> {
            loadFragment(new DescriptionFragment());
            pageTitle.setText("Cart");
        });

        navNotification.setOnClickListener(v -> {
            loadFragment(new DescriptionFragment());
            pageTitle.setText("Notifications");
        });

        navProfile.setOnClickListener(v -> {
            loadFragment(new DescriptionFragment());
            pageTitle.setText("Profile");
        });
    }
}
