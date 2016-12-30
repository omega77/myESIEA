package moi.myesiea;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Fragment;

public class RATPFragment extends Fragment {

    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private  View v;
    private static String url = "https://api-ratp.pierre-grimaud.fr/v2/metros/7/stations/298?destination=19";
    private ArrayList<HashMap<String, String>> horaireList;
    private HashMap<String, String> horaire = new HashMap<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        horaireList = new ArrayList<>();
        v = inflater.inflate(R.layout.list,container);
        lv = (ListView) v.findViewById(R.id.list);
        Button button= (Button) container.findViewById(R.id.refresh_ivry);
        new getHoraire().execute();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                horaireList.removeAll(horaireList);
                new getHoraire().execute();
            }
        });
        return inflater.inflate(R.layout.list, null);
    }

    @Nullable
    @Override
    public  void onDestroyView() {
        Button button= (Button) getActivity().findViewById(R.id.refresh_ivry);
        ViewGroup layout = (ViewGroup) button.getParent();
        if(null!=layout) {
            layout.removeAllViews();
        }
        horaireList.removeAll(horaireList);
        getFragmentManager().beginTransaction().remove(this).commit();
        super.onDetach();
    }



    private class getHoraire extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject t = jsonObj.getJSONObject("response");
                    JSONArray horaires  = t.getJSONArray("schedules");
                      for (int i = 0; i < horaires.length(); i++) {
                        JSONObject h = horaires.getJSONObject(i);
                        String destination = h.getString("destination");
                        String message = h.getString("message");
                        horaire = new HashMap<>();
                        horaire.put("destination", destination);
                        horaire.put("message", message);
                        horaireList.add(horaire);
                    }
                } catch (final JSONException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "JSON ERROR: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
               getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Server Not Responding !",
                            Toast.LENGTH_LONG)
                            .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(),
                    horaireList,
                    R.layout.ratp_layout,
                    new String[]{"destination", "message"}, new int[]{R.id.destination, R.id.horaire});
            lv.setAdapter(adapter);
        }
    }
}

