/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Add your docs here.
 */
public class Chassis extends Subsystem{
  private static final WPI_TalonSRX leftMotor1 = new WPI_TalonSRX(0);
  private static final WPI_TalonSRX leftMotor2 = new WPI_TalonSRX(1);
  private static final WPI_TalonSRX rightMotor1 = new WPI_TalonSRX(2);
  private static final WPI_TalonSRX rightMotor2 = new WPI_TalonSRX(3);

  private static final SpeedControllerGroup left = new SpeedControllerGroup(leftMotor1, leftMotor2);
  private static final SpeedControllerGroup right = new SpeedControllerGroup(rightMotor1, rightMotor2);

  private static final DifferentialDrive drive = new DifferentialDrive(left, right);

  private static final Chassis instance = new Chassis();

  public Chassis() {
    super();
  }

  public static Chassis getInstance(){
    return instance;
  }

  public void drive(double left, double right){
    drive.tankDrive(left, right);
  }

  public double getRightVelocity(){
    return rightMotor1.getSelectedSensorVelocity() / 4096 /*ticks per revolution*/ * 1 /*wheel base*/;
  }



  @Override
  public void initDefaultCommand() {

  }

  @Override
  public void restoreFactoryDefult() {
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
