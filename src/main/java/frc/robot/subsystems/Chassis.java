/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import static frc.robot.RobotParams.TICKS_PER_REVOLUTION;
import static frc.robot.RobotParams.WHEEL_DIAMETER;

/**
 * Add your docs here.
 */
public class Chassis extends Subsystem {
    private static final WPI_TalonSRX leftMotor1 = new WPI_TalonSRX(0);
    private static final WPI_TalonSRX leftMotor2 = new WPI_TalonSRX(1);
    private static final WPI_TalonSRX rightMotor1 = new WPI_TalonSRX(2);
    private static final WPI_TalonSRX rightMotor2 = new WPI_TalonSRX(3);

    private static final SpeedControllerGroup left = new SpeedControllerGroup(leftMotor1, leftMotor2);
    private static final SpeedControllerGroup right = new SpeedControllerGroup(rightMotor1, rightMotor2);

    private static final DifferentialDrive drive = new DifferentialDrive(left, right);

    private static final AHRS navX = new AHRS(I2C.Port.kMXP);

    private static final Chassis instance = new Chassis();

    public Chassis() {
        super();
    }

    public static Chassis getInstance() {
        return instance;
    }

    public static AHRS getNavX() {
        return navX;
    }

    public void drive(double left, double right) {
        drive.tankDrive(left, right);
    }

    public double getLeftVelocity() {
        return (leftMotor1.getSelectedSensorVelocity() / TICKS_PER_REVOLUTION) * WHEEL_DIAMETER * Math.PI;
    }

    public double getRightVelocity() {
        return (rightMotor1.getSelectedSensorVelocity() / TICKS_PER_REVOLUTION) * WHEEL_DIAMETER * Math.PI;
    }

    public double getAverageVelocity() {
        return (getLeftVelocity() + getRightVelocity()) / 2;
    }

    public double getLeftDistance() {
        return (leftMotor1.getSelectedSensorPosition() / TICKS_PER_REVOLUTION) * WHEEL_DIAMETER * Math.PI;
    }

    public double getRightDistance() {
        return (rightMotor1.getSelectedSensorPosition() / TICKS_PER_REVOLUTION) * WHEEL_DIAMETER * Math.PI;
    }

    public double getAverageDistance() {
        return (getLeftDistance() + getRightDistance()) / 2;
    }

    public int getLeftEncoder() {
        return leftMotor1.getSelectedSensorPosition();
    }

    public int getRightEncoder() {
        return rightMotor1.getSelectedSensorPosition();
    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void restoreFactoryDefault() {
        leftMotor1.configFactoryDefault();
        leftMotor2.configFactoryDefault();
        rightMotor1.configFactoryDefault();
        rightMotor2.configFactoryDefault();
    }

    @Override
    public void setSensorPhase() {
        leftMotor1.setSensorPhase(true);
        rightMotor1.setSensorPhase(true);
    }

    @Override
    public void stopMotors() {
        left.set(0);
        right.set(0);
    }

    @Override
    public void setSensorInitialPosition() {
        leftMotor1.setSelectedSensorPosition(0);
        rightMotor1.setSelectedSensorPosition(0);
    }

    @Override
    public void setInverted() {
        left.setInverted(true);
        left.setInverted(false);
    }

    @Override
    public void follow() {
        leftMotor2.follow(leftMotor1);
        rightMotor2.follow(rightMotor1);
    }

    @Override
    public void configSensor() {
        leftMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        rightMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        leftMotor1.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 20, 10);
        rightMotor1.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 20, 10);

        super.configSensor();
    }
}
