package com.example.biskwit.MainDrawer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import com.example.biskwit.Content.Lessons.Score;
import com.example.biskwit.Data.Constants;
import com.example.biskwit.Data.Database;
import com.example.biskwit.MainNavMenu;
import com.example.biskwit.databinding.FragmentProfileBinding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    ProgressDialog progressDialog;
    public static final int PICK_IMAGE = 1;
    public static final String filename = "idfetch";
    public static final String UserID = "userid";
    public static final String PROFILE_URL = "https://biskwitteamdelete.000webhostapp.com/update_profile_image.php";


    String encodedImage;

    MainNavMenu frommainnav;

    SharedPreferences logger;
    int id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        frommainnav = (MainNavMenu)getActivity();
        frommainnav.startMusic();

        logger = frommainnav.getSharedPreferences(filename, Context.MODE_PRIVATE);
        id = logger.getInt(UserID,0);

        // dito iniinitialize yung layout niya
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        progressDialog = new ProgressDialog(getContext());

        getData();
        binding.gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        //save image
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(encodedImage != null) {
                    updateProfilePicture();
                }
            }
        });

        return root;
    }
    //insert sa database
    private void updateProfilePicture() {
        class SetProfilePicture extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Database profileUploader = new Database();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Inserting your Picture...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();

                data.put("id", Integer.toString(id));
                data.put("imageholder", encodedImage);

                String result = profileUploader.sendPostRequest(PROFILE_URL, data);

                return result;
            }
        }

        SetProfilePicture profileUploader = new SetProfilePicture();
        profileUploader.execute();
    }

    //from image to byte
    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    //select image
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getActivity().getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            encodedImage = encodeImage(selectedImage);
            System.out.println("IMAGE" + encodedImage);
            binding.profilepic.setImageURI(imageUri);
        }
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
        String imageholder= "";

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
                imageholder = collegeData.getString("imageholder");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!child.equals("")){
            binding.Child.setText(child);
            binding.Age.setText(age);
            binding.Bday.setText(bday);
            binding.Severity.setText(severity);
            //binding.Parent.setText(parent);

            System.out.println(imageholder);
            byte[] decodedString = Base64.decode(imageholder, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            binding.profilepic.setImageBitmap(decodedByte); 
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
}