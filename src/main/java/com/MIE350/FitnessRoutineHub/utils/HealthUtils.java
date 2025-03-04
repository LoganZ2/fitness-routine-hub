package com.MIE350.FitnessRoutineHub.utils;

import com.MIE350.FitnessRoutineHub.controller.exceptions.MissingRequiredValuesException;
import com.MIE350.FitnessRoutineHub.model.entity.HealthProfile;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HealthUtils {
    public static Map<String, Integer> foodCalories() {
        //TODO
        //reads from csv files for food calories information
        throw new NotImplementedException();
    }
    public static Map<String, Integer> exerciseBurn() {
        //TODO
        //reads from csv files or implement a map as code for exercise calories burn
        throw new NotImplementedException();
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
        throw new NotImplementedException();
    }
}
