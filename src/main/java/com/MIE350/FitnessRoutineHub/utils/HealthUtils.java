package com.MIE350.FitnessRoutineHub.utils;

import com.MIE350.FitnessRoutineHub.controller.exceptions.MissingRequiredValuesException;
import com.MIE350.FitnessRoutineHub.model.entity.HealthProfile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HealthUtils {
    private static final String FOOD_CALORIES_FILE = "src/main/resources/Food and Calories.csv";
    private static final String EXERCISE_BURN_FILE = "src/main/resources/exercise_dataset.csv";

    public static Map<String, Double> loadFoodCalories() {
        Map<String, Double> foodCaloriesMap = new HashMap<>();
        Pattern pattern = Pattern.compile("\\((\\d+) g\\)");

        try (BufferedReader br = new BufferedReader(new FileReader(FOOD_CALORIES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                String food = parts[0].trim();
                String serving = parts[1].trim();
                String caloriesText = parts[2].replace(" cal", "").trim();

                Matcher matcher = pattern.matcher(serving);
                if (matcher.find()) {
                    int grams = Integer.parseInt(matcher.group(1));
                    double totalCalories = Double.parseDouble(caloriesText);
                    double caloriesPerGram = totalCalories / grams;
                    foodCaloriesMap.put(food, caloriesPerGram);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading food CSV file: " + FOOD_CALORIES_FILE, e);
        }
        return foodCaloriesMap;
    }

        public static Map<String, Double> loadExerciseCaloriesPerKg() {
            Map<String, Double> exerciseCaloriesMap = new HashMap<>();

            try (BufferedReader br = new BufferedReader(new FileReader(EXERCISE_BURN_FILE))) {
                String line = br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length < 6) continue;

                    String exerciseType = parts[0].trim();
                    double caloriesPerKg = Double.parseDouble(parts[5].trim());

                    exerciseCaloriesMap.put(exerciseType, caloriesPerKg);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error reading exercise CSV file: " + EXERCISE_BURN_FILE, e);
            }
            return exerciseCaloriesMap;
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
        int bmr = (int) (10 * weight + 6.25 * height - 5 * age - 78);
        int adjustment;
        switch (objective) {
            case CUT:
                adjustment = -500;
                break;
            case BULK:
                adjustment = 500;
                break;
            case MAINTAIN:
                adjustment = 0;
                break;
            default:
                throw new IllegalArgumentException();
        }

        int netCalories = bmr + adjustment;
        return bmr + adjustment;
    }

    public static double calculateFoodCalories(String foodName, double grams) {
        Map<String, Double> foodCaloriesMap = loadFoodCalories();

        if (!foodCaloriesMap.containsKey(foodName)) {
            throw new IllegalArgumentException("Food not found: " + foodName);
        }

        double caloriesPerGram = foodCaloriesMap.get(foodName);
        return caloriesPerGram * grams;
    }

    public static double calculateExerciseCalories(String exerciseType, double weight, double duration) {
        Map<String, Double> exerciseCaloriesMap = loadExerciseCaloriesPerKg();

        if (!exerciseCaloriesMap.containsKey(exerciseType)) {
            throw new IllegalArgumentException("Exercise type not found: " + exerciseType);
        }

        double caloriesPerKgPerHour = exerciseCaloriesMap.get(exerciseType);
        return caloriesPerKgPerHour * weight * (duration / 60.0);
    }

    public static double calculateTotalCalories(int weight,
                                                Map<String, Double> foodIntake,
                                                Map<String, Double> exerciseData) {
        double totalFoodCalories = 0;
        for (Map.Entry<String, Double> entry : foodIntake.entrySet()) {
            totalFoodCalories += calculateFoodCalories(entry.getKey(), entry.getValue());
        }

        double totalExerciseCalories = 0;
        for (Map.Entry<String, Double> entry : exerciseData.entrySet()) {
            totalExerciseCalories += calculateExerciseCalories(entry.getKey(), weight, entry.getValue());
        }

        return totalFoodCalories - totalExerciseCalories;
    }
}

