package core;

import java.util.Comparator;

public class DrawableZIndexComparator implements Comparator<Drawable> {
    @Override
    public int compare(Drawable d1, Drawable d2) {
        return d1.getZIndex() - d2.getZIndex();
    }
}
