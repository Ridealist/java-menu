package menu.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import menu.repository.FoodRepository;

public class Coach {

    private static final int NAME_LENGTH_LOWER_BOUND = 2;
    private static final int NAME_LENGTH_UPPER_BOUND = 4;
    private static final String INVALID_NAME_MESSAGE = "코치 이름은 2글자 이상 4글자 이하여야 합니다. 다시 입력해주세요.";

    private final String name;
    private final List<Food> cannotEatFoods = new ArrayList<>();

    private final List<Food> thisWeekMenus = new LinkedList<>();

    public Coach(String name) throws IllegalArgumentException {
        validateNameLength(name);
        this.name = name;
    }

    public void updateFoods(List<String> foods) {
        if (foods.size() == 1 && foods.get(0).equals("")) {
            return;
        }
        for (String name : foods) {
            cannotEatFoods.add(FoodRepository.getByName(name));
        }
    }

    public void setThisWeeksMenus(List<Category> thisWeekCategories) {
        for (Category category : thisWeekCategories) {
            thisWeekMenus.add(getRandomMenu(category));
        }
    }

    public List<Food> getThisWeekMenus() {
        return Collections.unmodifiableList(thisWeekMenus);
    }

    private Food getRandomMenu(Category category) {
        List<String> menus = FoodRepository.getAllFoodsByCategory(category);
        String menu = Randoms.shuffle(menus).get(0);
        Food food = FoodRepository.getByName(menu);
        if (validateMenu(food)) {
            return food;
        }
        return getRandomMenu(category);
    }

    private boolean validateMenu(Food food) {
        return validateUniqueMenu(food) && validateNotContainsCannotEatMenus(food);
    }

    private boolean validateUniqueMenu(Food food) {
        return !thisWeekMenus.contains(food);
    }

    private boolean validateNotContainsCannotEatMenus(Food food) {
        return !cannotEatFoods.contains(food);
    }

    private void validateNameLength(String name) {
        if (name.length() < NAME_LENGTH_LOWER_BOUND || name.length() > NAME_LENGTH_UPPER_BOUND) {
            throw new IllegalArgumentException(INVALID_NAME_MESSAGE);
        }
    }

    public String getName() {
        return name;
    }

    public boolean hasSameName(String name) {
        return this.name.equals(name);
    }

    public List<Food> getCannotEatFoods() {
        return Collections.unmodifiableList(cannotEatFoods);
    }
}
