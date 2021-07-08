package com.sparsh.french;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    int mcolorId;
    public WordAdapter(Activity context, ArrayList<Word> words, int colorId) {
        super(context, 0, words);
        mcolorId=colorId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Word currentWord = getItem(position);
        TextView French = (TextView) listItemView.findViewById(R.id.FrenchWord);
        French.setText(currentWord.getFrenchTranslation());

        TextView English = (TextView) listItemView.findViewById(R.id.EnglishWord);
        English.setText(currentWord.getDefaultTranslation());

        ImageView image = (ImageView) listItemView.findViewById(R.id.image);
        if (currentWord.hasImage()) {
            image.setImageResource(currentWord.getImageResource());
            image.setVisibility(View.VISIBLE);
        }
        else
            image.setVisibility(View.GONE);

        View wordLayout=(View)listItemView.findViewById(R.id.words_layout);
        int color= ContextCompat.getColor(getContext(),mcolorId);
        wordLayout.setBackgroundColor(color);
        return listItemView;
    }
}
