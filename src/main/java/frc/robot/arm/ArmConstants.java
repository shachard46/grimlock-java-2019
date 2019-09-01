package frc.robot.arm;

public class ArmConstants {
    public static final int ARM_INITIAL_POSITION = -3840;
    public static final int TOLERANCE = 70; // was 55

    public static final int ARM_TICKS_PER_REVULATION = 4096;
    public static final double STARTING_ANGLE = -90;

    public static final double EFFECTIVE_KT = 0.00529850746 * 2 * 360; //in N*m/V  stalltorqueper1A*2motors*gearratio 3.973
    public static final double INTERNAL_RESISTANCE = 0.0895522388;
    public static final double ARM_TORQUE_WITHOOUT_ANGLE = 22.71996;
    public static final double LEVERAGE_TORQUE_WITHOOUT_ANGLE = 12.73537339;
    public static final double LEVERAGE_LENGTH = 0.135;
    public static final double DIST_TSIR_TO_SPRING = 0.144;

    public static final double BALL_COLLECT_ARM_ANGLE = -3840;
    public static final double FRONT_DISK_ARM_ANGLE = -3000;        //UPDATED
    public static final double FRONT_MIDDLE_BALL_ARM_ANGLE = -2506; //UPDATED
    public static final double FRONT_LOW_BALL_ARM_ANGLE = -2600;    //UPDATED
    public static final double FEEDER_BALL_COLLECT = -3245;
    public static final double FEEDER_DISK_COLLECT = -3516; //-3481
    public static final double BACK_BALL_ARM_ANGLE = 3490;  //UPDATE         //4152;
    public static final double BACK_DISK_ARM_ANGLE = 3552;  //UPDATED          //3720;  //3855; //4152;
    public static final double FRONT_LOW_BALL_SIDE = 0;
    public static final double FRONT_MIDDLE_BALL_SIDE = 0;
    public static final double BACK_LOW_BALL_SIDE = 0;
    public static final double BACK_MIDDLE_BALL_SIDE = 0;
    public static final double BACK__MIDDLE_BALL_ARM_ANGLE = 4856;
    public static final double BACK_HIGH_BALL_ARM_ANGLE = 3490;
    public static final double FRONT_LOW_DISK_SIDE = 0;
    public static final double FRONT_MIDDLE_DISK_SIDE = 0;
    public static final double BACK_LOW_DISK_SIDE = 0;
    public static final double BACK_MIDDLE_DISK_SIDE = 0;

}
