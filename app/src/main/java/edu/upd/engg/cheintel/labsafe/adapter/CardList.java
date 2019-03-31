package edu.upd.engg.cheintel.labsafe.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.upd.engg.cheintel.labsafe.R;

public class CardList extends ArrayAdapter<String> {

    public CardList(Context context, ArrayList<String> stepsList) {
        super(context, 0, stepsList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        String list = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_list, parent,false);
        }

        TextView textView = convertView.findViewById(R.id.text1);
        String step = this.getItem(position);
        textView.setText(step);

        return convertView;
    }
}
