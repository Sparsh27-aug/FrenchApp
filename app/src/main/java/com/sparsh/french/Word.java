package com.sparsh.french;

public class Word {
    private String mDefaultTranslation;
    private String mFrenchTranslation;
    private int mImageResource=hasimage;
    private static final int hasimage=-1;
    private int mAudioId;

    public Word(String DefaultTranslation, String FrenchTranslation,int ImageResource,int AudioId) {
        this.mDefaultTranslation = DefaultTranslation;
        this.mFrenchTranslation = FrenchTranslation;
        mImageResource=ImageResource;
        mAudioId=AudioId;
    }
    public Word(String DefaultTranslation, String FrenchTranslation,int AudioId) {
        this.mDefaultTranslation = DefaultTranslation;
        this.mFrenchTranslation = FrenchTranslation;
        mAudioId=AudioId;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getFrenchTranslation() {
        return mFrenchTranslation;
    }

    public int getImageResource()
    {
        return mImageResource;
    }

    public boolean hasImage()
    {
        return mImageResource!=hasimage;
    }

    public int getAudioId()
    {
        return mAudioId;
    }
}
