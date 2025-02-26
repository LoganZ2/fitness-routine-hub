package com.MIE350.FitnessRoutineHub.utils;

public class UnitUtils {

    public enum HeightUnit {
        CENTIMETERS,
        INCHES,
    }

    public enum WeightUnit {
        KILOGRAMS,
        POUNDS,
        STONE
    }

    public static Integer convertHeight(HeightUnit from, HeightUnit to, Integer value) {
        if (from == to) {
            return value;
        }

        if (from == HeightUnit.CENTIMETERS && to == HeightUnit.INCHES) {
            return (int) Math.round(value / 2.54);
        } else if (from == HeightUnit.INCHES && to == HeightUnit.CENTIMETERS) {
            return (int) Math.round(value * 2.54);
        }

        throw new IllegalArgumentException("Unsupported height conversion");
    }

    public static Integer convertWeight(WeightUnit from, WeightUnit to, Integer value) {
        if (from == to) {
            return value;
        }

        switch (from) {
            case KILOGRAMS:
                if (to == WeightUnit.POUNDS) {
                    return (int) Math.round(value * 2.20462);
                } else if (to == WeightUnit.STONE) {
                    return (int) Math.round(value / 6.35029);
                }
                break;
            case POUNDS:
                if (to == WeightUnit.KILOGRAMS) {
                    return (int) Math.round(value / 2.20462);
                } else if (to == WeightUnit.STONE) {
                    return (int) Math.round(value / 14.0);
                }
                break;
            case STONE:
                if (to == WeightUnit.KILOGRAMS) {
                    return (int) Math.round(value * 6.35029);
                } else if (to == WeightUnit.POUNDS) {
                    return (int) Math.round(value * 14.0);
                }
                break;
        }

        throw new IllegalArgumentException("Unsupported weight conversion");
    }
}
