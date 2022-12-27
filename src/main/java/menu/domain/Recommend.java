package menu.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Recommend {

    private final List<Category> weekCategories = new LinkedList<>();

    public Recommend() {
        while (weekCategories.size() < 5) {
            Category category = Category.getByNumber(Randoms.pickNumberInRange(1, 5));
            if (validate(category)) {
                weekCategories.add(category);
            }
        }
    }

    private boolean validate(Category newCategory) {
        long count = weekCategories.stream().filter(category -> category.equals(newCategory)).count();
        return count <= 1;
    }

    public List<Category> getWeekCategories() {
        return Collections.unmodifiableList(weekCategories);
    }
}
