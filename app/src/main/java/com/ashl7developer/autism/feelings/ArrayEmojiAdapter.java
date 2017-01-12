package com.ashl7developer.autism.feelings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Jeff To on 4/21/2016.
 */
public class ArrayEmojiAdapter extends ArrayAdapter<Emoji> {

    public ArrayEmojiAdapter(Context context, int resource, List<Emoji> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Emoji e = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_emoji, parent, false);
        }
        // Lookup view for data population
        ImageView ivEmoji = (ImageView) convertView.findViewById(R.id.ivItemEmoji);
        // Populate the data into the template view using the data object
        ivEmoji.setImageResource(e.getDrawableRes());
        // Return the completed view to render on screen
        return convertView;
    }
}
