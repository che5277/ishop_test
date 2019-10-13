package tableshop.ilinkedtech.com.views.sidebar;

import java.util.Comparator;

import tableshop.ilinkedtech.com.beans.main.CarDetailBean;


public class LetterComparator
        implements Comparator<CarDetailBean>
{

    @Override
    public int compare(CarDetailBean l, CarDetailBean r) {
        if (l == null || r == null) {
            return 0;
        }

        String lhsSortLetters = l.pys.substring(0, 1).toUpperCase();
        String rhsSortLetters = r.pys.substring(0, 1).toUpperCase();
        if (lhsSortLetters == null || rhsSortLetters == null) {
            return 0;
        }
        return lhsSortLetters.compareTo(rhsSortLetters);
    }
}