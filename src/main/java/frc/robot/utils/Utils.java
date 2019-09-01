package frc.robot.utils;

public class Utils {
    public static double cosInDegrees(double angle){
        return Math.cos(angle * Math.PI / 180);
    }

    public static double sinInDegrees(double angle){
        return Math.sin(angle * Math.PI / 180);
    }

    public static double tanInDegrees(double angle){
        return Math.tan(angle * Math.PI / 180);
    }

    public static boolean isInTolerance(int currentTicks, double target, int tollerance){
        return Math.abs(target - currentTicks) < tollerance;
    }
}
