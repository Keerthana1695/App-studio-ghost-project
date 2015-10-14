/*
 * MyCustomAdapter.java
 *
 * This class implements a custom ArrayAdapter for the ListView in the highscores Activity (see
 * Highscores.java). The custom ArrayAdapter works with an array with Player objects (see
 * Player.java) as data.
 *
 * Author: Vincent Erich
 * Version: October, 2015
 */

package com.example.vincent.ghost;

/*
 * Necessary import statements.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCustomAdapter extends ArrayAdapter<Player> {

    /*
     * Properties of the class.
     */
    private TextView rankingTextView, nameTextView, scoreTextView;
    private ImageView medalImageView;

    /*
     * Constructor of the class.
     */
    public MyCustomAdapter(Context context, Player[] values) {
        super(context, R.layout.layout_row_my_custom_adapter, values);
    }

    /*
     * Gets a View that displays the data at the specified position in the data set.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater theInflater = LayoutInflater.from(getContext());
            convertView = theInflater.inflate(R.layout.layout_row_my_custom_adapter, parent, false);
        }
        initializeViews(convertView);
        setViewsContent(position);
        return convertView;
    }

    /*
     * Initializes all the views that are used in a ListView row.
     */
    private void initializeViews(View view) {
        rankingTextView = (TextView) view.findViewById(R.id.player_ranking_row_textView);
        medalImageView = (ImageView) view.findViewById(R.id.medal_row_imageView);
        nameTextView = (TextView) view.findViewById(R.id.player_name_row_textView);
        scoreTextView = (TextView) view.findViewById(R.id.player_score_row_textView);
    }

    /*
     * Sets the content of the views that are used in a ListView row.
     */
    private void setViewsContent(int position) {
        Player player = getItem(position);
        int ranking = player.getRanking();
        rankingTextView.setText(String.valueOf(ranking));
        setMedalImageSource(ranking);
        nameTextView.setText(player.getName());
        scoreTextView.setText(String.valueOf(player.getScore()));
    }

    /*
     * Sets the source of the ImageView 'medalImageView' based on a ranking that is
     * passed as an argument.
     */
    private void setMedalImageSource(int ranking) {
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