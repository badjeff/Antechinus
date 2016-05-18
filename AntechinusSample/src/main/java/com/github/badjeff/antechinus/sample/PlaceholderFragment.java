package com.github.badjeff.antechinus.sample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jeff on 12/16/15.
 * For Antechinus.
 */
public class PlaceholderFragment extends Fragment {

    public static Fragment getInstance() {
        return new PlaceholderFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick({R.id.button_bold})
    public void onClickBoldButton() {
        Toast.makeText(getActivity(), getActivity().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.button_default})
    public void onClickDefaultButton() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getActivity().getString(R.string.app_name));
        builder.setTitle(getActivity().getString(R.string.app_name));
        builder.setPositiveButton(getActivity().getString(R.string.app_name), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
