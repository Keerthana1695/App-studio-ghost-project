package com.example.vincent.ghost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyCustomAdapter extends ArrayAdapter<Player> {

    private TextView rankingTextView, nameTextView, scoreTextView;
    private ImageView medalImageView;

    public MyCustomAdapter(Context context, Player[] values) {
        super(context, R.layout.layout_row_my_custom_adapter, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        // Warning?
        View theView = theInflater.inflate(R.layout.layout_row_my_custom_adapter, parent, false);
        initializeViews(theView);
        Player player = getItem(position);
        int ranking = player.getRanking();
        rankingTextView.setText(String.valueOf(ranking));
        setMedalImage(ranking);
        nameTextView.setText(player.getName());
        scoreTextView.setText(String.valueOf(player.getScore()));
        return theView;
    }

    private void initializeViews(View view) {
        rankingTextView = (TextView) view.findViewById(R.id.player_ranking_row_textView);
        medalImageView = (ImageView) view.findViewById(R.id.medal_row_imageView);
        nameTextView = (TextView) view.findViewById(R.id.player_name_row_textView);
        scoreTextView = (TextView) view.findViewById(R.id.player_score_row_textView);
    }

    private void setMedalImage(int ranking) {
        switch(ranking) {
            case 1:
                medalImageView.setImageResource(R.drawable.gold_medal);
                break;
            case 2:
                medalImageView.setImageResource(R.drawable.silver_medal);
                break;
            case 3:
                medalImageView.setImageResource(R.drawable.bronze_medal);
                break;
        }
    }
}
