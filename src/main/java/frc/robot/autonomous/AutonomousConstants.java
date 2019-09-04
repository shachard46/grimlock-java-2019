package frc.robot.autonomous;

public class AutonomousConstants {
    //Trajectory file parameters
    public static final String ROBORIO_HOME_DIR = "/home/lvuser";
    public static final String ROBORIO_DEPLOY_DIR = ROBORIO_HOME_DIR + "/deploy";
    public static final String ROBORIO_OUTPUT_DIR = ROBORIO_DEPLOY_DIR + "/output";
    public static final String LEFT_TRAJECTORY_EXTENSION = "left.pf1.csv";
    public static final String RIGHT_TRAJECTORY_EXTENSION = "right.pf1.csv";


    //Encoder to meter parameters
    public static final double WHEEL_DIAMETER = 0;
    public static final double WHEEL_BASE = 0;
    public static final int TICKS_PER_REVOLUTION = 4096;

    //PIDVA parameters
    public static final double KP = 0;
    public static final double KI = 0;
    public static final double KD = 0;
    public static final double KV = 0;
    public static final double KA = 0;
    public static final double MAX_VELOCITY = 0;
    public static final double MAX_ACC = 0;
}
