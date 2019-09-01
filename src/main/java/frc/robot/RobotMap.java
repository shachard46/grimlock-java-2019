/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int CHASSIS_LEFT_MOTOR_PORT_1 = 0;
    public static final int CHASSIS_LEFT_MOTOR_PORT_2 = 1;
    public static final int CHASSIS_RIGHT_MOTOR_PORT_1 = 2;
    public static final int CHASSIS_RIGHT_MOTOR_PORT_2 = 3;

    public static final int CHASSIS_SOL_PORT_1 = 0;
    public static final int CHASSIS_SOL_PORT_2 = 1;

    //LIFT ports
    public static final int LIFT_MOTOR_PORT_1 = 6; //TalonSRX //06.02
    public static final int LIFT_MOTOR_PORT_2 = 5; //victorSPX//06.02

    public static final int LIFT_MS_UP_PORT = 1;   //1;
    public static final int LIFT_MS_DOWN_PORT = 2; //1;
    //ARM
    public static final int ARM_MOTOR_PORT_1 = 4; //v //TalonSRX 06.02
    public static final int ARM_MOTOR_PORT_2 = 7; //victorSPX 06.02
    //CLAW
    public static final int JOINT_HORISONTAL_MOTOR_PORT = 9;
    public static final int CLAW_MOTOR_PORT = 8; //v

    public static final int CLAW_SOLENOID_PORT_1 = 4;
    public static final int CLAW_SOLENOID_PORT_2 = 5;

    public static final int CLAW_BALL_COLLECT_IR_PORT = 3;
    public static final int CLAW_DISK_COLLECT_MS_PORT = 4;

    //ball intake ports
    public static final int LEFT_BALL_INTAKE_ARM_PORT = 12;
    public static final int RIGHT_BALL_INTAKE_ARM_PORT = 13;

    public static final int FOLD_MS_PORT = 0;
    public static final int OPEN_MS_PORT = 2;

    public static final int BALL_INTAKE_MOTOR_PORT = 14;

    public static final int BALL_INTAKE_SOLENOID_PORT_1 = 2;
    public static final int BALL_INTAKE_SOLENOID_PORT_2 = 3;

    //floor disk intake ports
    public static final int FLOOR_DISK_INTAKE_SOLENOID_PORT_1 = 7;
    public static final int FLOOR_DISK_INTAKE_SOLENOID_PORT_2 = 8;
    public static final int OPEN_FLOOR_DISK_INTAKE_PORT = 1;

    //climb ports
    public static final int HET_MOTOR_PORT_1 = -1;
    public static final int HET_MOTOR_PORT_2 = -1;

    public static final int LEFT_SHAFT_MOTOR_PORT = -1;
    public static final int RIGHT_SHAFT_MOTOR_PORT = -1;

    public static final int DOWN_MS_PORT = 9;
    public static final int UP_MS_PORT = 5;

    public static final int CLIMB_LIFT_MOTOR_1 = 10; //TALON
    public static final int CLIMB_LIFT_MOTOR_2 = 11;
}
