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
    private static final String FOOD_CALORIES_FILE = "src/main/resources/Food and Calories - Sheet1.csv"; // CSV 文件路径
    private static final String EXERCISE_BURN_FILE = "src/main/resources/exercise_dataset (1).csv"; // CSV 文件路径

    public static Map<String, Double> loadFoodCalories() {
        Map<String, Double> foodCaloriesMap = new HashMap<>();
        Pattern pattern = Pattern.compile("\\((\\d+) g\\)"); // 正则匹配括号内的克数 (xx g)

        try (BufferedReader br = new BufferedReader(new FileReader(FOOD_CALORIES_FILE))) {
            String line = br.readLine(); // 读取表头，跳过第一行
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // 按逗号分割
                if (parts.length < 3) continue; // 确保数据完整

                String food = parts[0].trim();
                String serving = parts[1].trim();
                String caloriesText = parts[2].replace(" cal", "").trim();

                Matcher matcher = pattern.matcher(serving);
                if (matcher.find()) {
                    int grams = Integer.parseInt(matcher.group(1)); // 获取括号内的克数 (xx g)
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
                String line = br.readLine(); // 读取表头，跳过第一行
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(","); // 按逗号分割
                    if (parts.length < 6) continue; // 确保数据完整

                    String exerciseType = parts[0].trim(); // **第一列：包含运动类型 + 速度**
                    double caloriesPerKg = Double.parseDouble(parts[5].trim()); // **第六列：每千克每小时卡路里**

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

        //TODO
        //determine daily needs of net calories(positive or negative)
                // 使用通用 BMR 计算公式（男女公式的平均值）
                int bmr = (int) (10 * weight + 6.25 * height - 5 * age - 78);
                // 目标调整
        int adjustment;
        switch (objective) {
            case CUT:
                adjustment = -500;
                break;
            // 目标减脂，每日减少 500 卡
            case BULK:
                adjustment = 500;
                break;
            // 目标增肌，每日增加 500 卡
            case MAINTAIN:
                adjustment = 0;
                break;       // 目标维持体重

            default:
                throw new IllegalArgumentException();
        }

        return bmr + adjustment;
    }

        /**
     * 计算用户从食物摄入的卡路里
     * @param foodName 食物名称
     * @param grams 食物重量（克）
     * @return 摄入的卡路里
     */
    public static double calculateFoodCalories(String foodName, double grams) {
        Map<String, Double> foodCaloriesMap = loadFoodCalories(); // 读取食物数据

        if (!foodCaloriesMap.containsKey(foodName)) {
            throw new IllegalArgumentException("Food not found: " + foodName);
        }

        double caloriesPerGram = foodCaloriesMap.get(foodName);
        return caloriesPerGram * grams; // 计算总卡路里
    }

    /**
     * 计算用户运动消耗的卡路里
     * @param exerciseType 运动类型（如 "Running, 6 mph"）
     * @param weight 体重（kg）
     * @param duration 运动时间（分钟）
     * @return 消耗的卡路里
     */
    public static double calculateExerciseCalories(String exerciseType, double weight, double duration) {
        Map<String, Double> exerciseCaloriesMap = loadExerciseCaloriesPerKg(); // 读取运动消耗数据

        if (!exerciseCaloriesMap.containsKey(exerciseType)) {
            throw new IllegalArgumentException("Exercise type not found: " + exerciseType);
        }

        double caloriesPerKgPerHour = exerciseCaloriesMap.get(exerciseType);
        return caloriesPerKgPerHour * weight * (duration / 60.0); // 按小时计算总卡路里消耗
    }

    /**
     * 计算用户今日的总卡路里（净卡路里 + 食物摄入 - 运动消耗）
     * @param height 身高（cm）
     * @param weight 体重（kg）
     * @param age 年龄
     * @param objective 目标（增肌、减脂、维持）
     * @param foodIntake 食物摄入（Map<食物名称, 克数>）
     * @param exerciseData 运动数据（Map<运动类型, 运动时间（分钟）>）
     * @return 今日的总卡路里
     */
    public static double calculateTotalCalories(int height, int weight, int age, 
                                                HealthProfile.Objective objective,
                                                Map<String, Double> foodIntake, 
                                                Map<String, Double> exerciseData) {
        // 计算净卡路里
        int netCalories = calculateNetCalories(height, weight, age, objective);

        // 计算总食物摄入
        double totalFoodCalories = 0;
        for (Map.Entry<String, Double> entry : foodIntake.entrySet()) {
            totalFoodCalories += calculateFoodCalories(entry.getKey(), entry.getValue());
        }

        // 计算总运动消耗
        double totalExerciseCalories = 0;
        for (Map.Entry<String, Double> entry : exerciseData.entrySet()) {
            totalExerciseCalories += calculateExerciseCalories(entry.getKey(), weight, entry.getValue());
        }

        // 计算今日的总卡路里
        return netCalories + totalFoodCalories - totalExerciseCalories;
    }
}

