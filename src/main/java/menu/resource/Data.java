package menu.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import menu.domain.Category;
import menu.domain.Food;
import menu.repository.FoodRepository;

public class Data {
    private static final String rawData = "일식: 규동, 우동, 미소시루, 스시, 가츠동, 오니기리, 하이라이스, 라멘, 오코노미야끼\n"
            + "한식: 김밥, 김치찌개, 쌈밥, 된장찌개, 비빔밥, 칼국수, 불고기, 떡볶이, 제육볶음\n"
            + "중식: 깐풍기, 볶음면, 동파육, 짜장면, 짬뽕, 마파두부, 탕수육, 토마토 달걀볶음, 고추잡채\n"
            + "아시안: 팟타이, 카오 팟, 나시고렝, 파인애플 볶음밥, 쌀국수, 똠얌꿍, 반미, 월남쌈, 분짜\n"
            + "양식: 라자냐, 그라탱, 뇨끼, 끼슈, 프렌치 토스트, 바게트, 스파게티, 피자, 파니니";

    private static final int CATEGORY_INDEX = 0;
    private static final int FOODS_INDEX = 1;
    private static final String RAW_DATA_DELIMITER = "\n";
    private static final String CATEGORY_DELIMITER = ":";
    private static final String FOOD_DELIMITER = ",";

    public static void loadData() {
        String[] foods = splitFoodByCategory(rawData);
        for (String categoryFoods : foods) {
            Category category = parsingFoodCategory(categoryFoods);
            for (String foodName : parsingFoodName(categoryFoods)) {
                FoodRepository.addFood(new Food(foodName, category));
            }
        }
    }

    private static String[] splitFoodByCategory(String rawData) {
        return rawData.split(RAW_DATA_DELIMITER);
    }

    private static Category parsingFoodCategory(String rawData) {
        List<String> data = Arrays.asList(rawData.split(CATEGORY_DELIMITER));
        String categoryName = data.get(CATEGORY_INDEX);
        return Category.getByName(categoryName);
    }

    private static List<String> parsingFoodName(String rawData) {
        List<String> data = Arrays.asList(rawData.split(CATEGORY_DELIMITER));
        List<String> foods = Arrays.asList(data.get(FOODS_INDEX).split(FOOD_DELIMITER));
        return foods.stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
