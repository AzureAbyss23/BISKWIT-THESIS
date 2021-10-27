package com.example.biskwit.MainDrawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biskwit.Data.Constants;
import com.example.biskwit.databinding.FragmentProfileBinding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    ProgressDialog progressDialog;

    public static final String filename = "idfetch";
    public static final String UserID = "userid";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // dito iniinitialize yung layout niya
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        progressDialog = new ProgressDialog(getContext());

        getData();

        return root;
    }

    private void getData() {

        int id;
        SharedPreferences logger = getActivity().getSharedPreferences(filename,Context.MODE_PRIVATE);
        id = logger.getInt(UserID,0);

        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading your profile details...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = "https://biskwitteamdelete.000webhostapp.com/fetch_profile.php?id="+id;

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
        String child = "";
        String age = "";
        String bday = "";
        String severity = "";
        String parent = "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constants.JSON_ARRAY);
            int length = result.length();
            for(int i = 0; i < length; i++) {
                JSONObject collegeData = result.getJSONObject(i);
                child = collegeData.getString("child");
                age = collegeData.getString("age");
                bday = collegeData.getString("birthday");
                severity = collegeData.getString("severity");
                parent = collegeData.getString("parent");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!child.equals("")){
            binding.Child.setText(child);
            binding.Age.setText(age);
            binding.Bday.setText(bday);
            binding.Severity.setText(severity);
            binding.Parent.setText(parent);
            progressDialog.dismiss();
        } else {
            Toast.makeText(getContext(), "No data", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}