package com.example.biskwit.MainDrawer;

import static com.example.biskwit.MainActivity.MainNavMenu.tapsoundon;
import static com.example.biskwit.MainActivity.MainNavMenu.globalvolume;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biskwit.Data.Constants;
import com.example.biskwit.MainActivity.Contact_Info;
import com.example.biskwit.MainActivity.MainNavMenu;
import com.example.biskwit.databinding.FragmentSettingsBinding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;
    Intent intent;
    MainNavMenu frommainnav;

    ProgressDialog progressDialog;
    public static final String filename = "logger";
    public static final String filename2 = "idfetch";
    public static final String filename3 = "scorer";
    public static final String UserID = "userid";
    SharedPreferences logger = getContext().getSharedPreferences(filename, Context.MODE_PRIVATE);
    SharedPreferences IDFetch = getContext().getSharedPreferences(filename2, Context.MODE_PRIVATE);
    SharedPreferences Scores = getContext().getSharedPreferences(filename2, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = logger.edit();
    SharedPreferences.Editor editor2 = IDFetch.edit();
    SharedPreferences.Editor editor3 = Scores.edit();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        progressDialog = new ProgressDialog(getContext());

        frommainnav = (MainNavMenu)getActivity();
        frommainnav.startMusic();

        binding.seekBar.setProgress((int) (globalvolume*100));

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //galing sa main para makapag change volume
                float volumeScalar = progress/100.0f;
                frommainnav.changeVolume(volumeScalar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if(tapsoundon){
            binding.switch1.setChecked(true);
            binding.switch1.setText("ON");
        }
        else{
            binding.switch1.setText("OFF");
            binding.switch1.setChecked(false);
        }


        binding.switch1.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked){
                compoundButton.setText("ON");
                tapsoundon = true;
            }
            else  {
                compoundButton.setText("OFF");
                tapsoundon = false;
            }
        });

        binding.resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getContext(), Contact_Info.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPause() {
        frommainnav.stopMusic();
        super.onPause();
    }

    @Override
    public void onResume() {
        frommainnav.startMusic();
        super.onResume();
    }

    private void reset() {

        int id;
        SharedPreferences logger = getActivity().getSharedPreferences(filename, Context.MODE_PRIVATE);
        id = logger.getInt(UserID,0);

        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading lesson...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = "https://biskwitteamdelete.000webhostapp.com/reset_progress.php?id="+id;

        StringRequest stringRequest = new StringRequest(url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void showJSONS(String response) {
        int status = 0;

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constants.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            status = collegeData.getInt("status");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(status>0){
            editor.clear(); editor.apply();
            editor2.clear(); editor2.apply();
            editor3.clear(); editor3.apply();
        } else {
            Toast.makeText(getContext(), "Reset failed...", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }
}