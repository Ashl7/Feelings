package com.autism.mindpower.feeling;

import java.util.ArrayList;

/**
 * Created by Jeff To on 4/21/2016.
 */
public final class Emoji {

    private final int drawableRes;
    private final String name;
    private final String caption;

    public Emoji(int pathToDrawableRes, String inputName, String inputCaption) {
        drawableRes = pathToDrawableRes;
        name = inputName;
        caption = inputCaption;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public String getName() {
        return name;
    }

    public String getCaption() {
        return caption;
    }

    public static ArrayList<Emoji> createEmojiList() {
        ArrayList<Emoji> al = new ArrayList<>();
        al.add(new Emoji(R.drawable.emoji_happy, "Happy", "I feel happy."));
        al.add(new Emoji(R.drawable.emoji_very_happy, "Very happy", "I feel very happy!"));
        al.add(new Emoji(R.drawable.emoji_love, "Love", "I love this!"));
        al.add(new Emoji(R.drawable.emoji_laughing, "Funny", "This is very funny!"));
        al.add(new Emoji(R.drawable.emoji_sad, "Sad", "I feel sad."));
        al.add(new Emoji(R.drawable.emoji_crying, "Very sad", "I feel very sad."));
        al.add(new Emoji(R.drawable.emoji_angry, "Angry", "I feel angry."));
        al.add(new Emoji(R.drawable.emoji_hurt, "Hurt", "I am hurt."));
        al.add(new Emoji(R.drawable.emoji_sick, "Sick", "I feel sick."));
        al.add(new Emoji(R.drawable.emoji_surprised, "Surprised", "I'm surprised!"));
        al.add(new Emoji(R.drawable.emoji_confused, "Confused", "I'm confused."));
        al.add(new Emoji(R.drawable.alert_bell, "Want", "I want something."));
        al.add(new Emoji(R.drawable.alert_food, "Food", "I want food."));
        al.add(new Emoji(R.drawable.alert_drink, "Drink", "I want a drink."));
        al.add(new Emoji(R.drawable.alert_help, "Help!", "I need help!"));
        return al;
    }
}
