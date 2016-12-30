package moi.myesiea;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class EmergencyFragment extends Fragment {

    String tel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.emergency_layout, null);
        Button bIvry = (Button) view.findViewById(R.id.bIvry);
        bIvry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tel = getString(R.string.nIvry);
                alertCall();

             }
        });
        Button bParis = (Button) view.findViewById(R.id.bParis);
        bParis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tel = getString(R.string.nParis);
                alertCall();

            }
        });
        Button bPolice = (Button) view.findViewById(R.id.bPolice);
        bPolice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tel = getString(R.string.nPolice);
                alertCall();

            }
        });
        Button bSAMU = (Button) view.findViewById(R.id.bSamu);
        bSAMU.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tel = getString(R.string.nSAMU);
                alertCall();

            }
        });
        Button bPompier = (Button) view.findViewById(R.id.bPompier);
        bPompier.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tel = getString(R.string.nPompier);
                alertCall();

            }
        });

        return view;
    }
    private void call(String tel) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + tel));
        startActivity(callIntent);
    }
    private void alertCall() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(R.string.tCall);
        alertDialogBuilder
                .setMessage(R.string.confirme)
                .setCancelable(false)
                .setPositiveButton(R.string.y,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        call(tel);
                    }
                })
                .setNegativeButton(R.string.n,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

