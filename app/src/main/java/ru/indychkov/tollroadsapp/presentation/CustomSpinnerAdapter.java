package ru.indychkov.tollroadsapp.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.indychkov.tollroadsapp.R;

public class CustomSpinnerAdapter extends BaseAdapter {
    private final List<String> data;

    public CustomSpinnerAdapter(List<String> data) {
        this.data = new ArrayList<>(data);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.spinner_item, parent, false);
            Holder holder = new Holder(convertView);
            convertView.setTag(holder);
        }
        String data = getItem(position);
        Holder holder = (Holder) convertView.getTag();
        holder.text.setText(data);
        return convertView;
    }
    private static class Holder {
        private TextView text;

        private Holder(View view) {
            text = view.findViewById(R.id.text1);
        }

    }
}
