package com.example.biskwit;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.biskwit.databinding.ActivityMainNavMenuBinding;

import java.sql.SQLOutput;

public class MainNavMenu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainNavMenuBinding binding;

    public static MediaPlayer bgsong;
    public static float globalvolume = 0.3f;
    public static Boolean tapsoundon = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bgsong = MediaPlayer.create(this, R.raw.bg_music);

        //Dito dinedeclare saka sineset yung Drawer Layout natin
        binding = ActivityMainNavMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainNavMenu.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_start, R.id.nav_gallery, R.id.nav_settings,R.id.nav_aboutus,R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        //eto yung nagcocontrol ng paglipat lipat ng pages
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_nav_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }
    //pang start ng music sa lahat ng activity itawag lang ito
    public void startMusic(){
        if(!bgsong.isPlaying()) {
//            bgsong.prepareAsync();

            bgsong.setVolume(globalvolume, globalvolume);
            bgsong.start();
            bgsong.setLooping(true);
        }
    }
    //pang stop ito kapag may important sounds na gagawin
    public void stopMusic(){
        if(bgsong.isPlaying()) {
            bgsong.pause();
        }
    }

    //magchachange nung volume function
    public void changeVolume(float volume) {
        globalvolume = volume;
        bgsong.setVolume(volume,volume);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // some codes needed for the Navigation
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_nav_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}