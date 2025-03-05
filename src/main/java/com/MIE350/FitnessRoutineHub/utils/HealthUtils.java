package com.MIE350.FitnessRoutineHub.utils;

import com.MIE350.FitnessRoutineHub.controller.exceptions.MissingRequiredValuesException;
import com.MIE350.FitnessRoutineHub.model.entity.HealthProfile;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HealthUtils {
    private static final String FOOD_CALORIES_FILE = "C:\Users\jeff\Desktop\Food and Calories - Sheet1.csv"; // CSV 文件路径
    private static final String EXERCISE_BURN_FILE = "C:\Users\jeff\Desktop\exercise_dataset.csv"; // CSV 文件路径

    public static Map<String, Integer> foodCalories() {
        //TODO
        //reads from csv files for food calories information
        return loadCsvData(FOOD_CALORIES_FILE);
        throw new NotImplementedException();
    }
    public static Map<String, Integer> exerciseBurn() {
        //TODO
        //reads from csv files or implement a map as code for exercise calories burn
        return loadCsvData(EXERCISE_BURN_FILE);
        throw new NotImplementedException();
    }

    private static Map<String, Integer> loadCsvData(String filePath) {
        Map<String, Integer> dataMap = new HashMap<>();
        try (CSVParser parser = new CSVParser(new FileReader(filePath), CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord record : parser) {
                String name = record.get(0);
                Integer calories = Integer.parseInt(record.get(1));
                dataMap.put(name, calories);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + filePath, e);
        }
        return dataMap;
    }

    public static int calculateNetCalories(Integer height, Integer weight, Integer age, HealthProfile.Objective objective) {
        List<String> missingFields = new ArrayList<>();

        if (height == null) missingFields.add("Height");
        if (weight == null) missingFields.add("Weight");
        if (age == null) missingFields.add("Age");
        if (objective == null) missingFields.add("Objective");

        if (!missingFields.isEmpty()) {
            throw new MissingRequiredValuesException(missingFields.toArray(new String[0]));
        }

        //TODO
        //determine daily needs of net calories(positive or negative)
                // 使用通用 BMR 计算公式（男女公式的平均值）
                int bmr = (int) (10 * weight + 6.25 * height - 5 * age - 78);
                // 目标调整
                int adjustment = switch (objective) {
                    case LOSE_WEIGHT -> -500; // 目标减脂，每日减少 500 卡
                    case GAIN_MUSCLE -> 500;  // 目标增肌，每日增加 500 卡
                    case MAINTAIN -> 0;       // 目标维持体重
                };
        
                return bmr + adjustment;
        throw new NotImplementedException();
    }
}
