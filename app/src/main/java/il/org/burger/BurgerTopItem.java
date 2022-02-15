package il.org.burger;

public class BurgerTopItem {
    private String mTopName;
    private int mTopImage;

    public BurgerTopItem(String mTopName, int mTopImage) {
        this.mTopName = mTopName;
        this.mTopImage = mTopImage;
    }

    public String getTopName() {
        return mTopName;
    }

    public int getTopImage() {
        return mTopImage;
    }

}
