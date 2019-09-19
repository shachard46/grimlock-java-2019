/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.chassis.commands;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Command;
import frc.robot.chassis.Chassis;

public class DriveByController extends Command {
  private Joystick controller;

  public DriveByController(Joystick controller) {
    super(Chassis.getInstance());
    this.controller = controller;
  }

  @Override
  public void execute() {
    if (getThirdRawAxis(1) > 0.1 || getThirdRawAxis(1) < -0.1) {
      if (getTriggerRawAxis(3) > 0.1) {
        Chassis.getInstance().cheesyDrive(getThirdRawAxis(1), getTriggerRawAxis(3));
      } else if (getThirdRawAxis(4) > 0.1) {
        Chassis.getInstance().cheesyDrive(getThirdRawAxis(1), -getTriggerRawAxis(4));
      } else {
        Chassis.getInstance().cheesyDrive(getThirdRawAxis(1), 0);
      }
    } else {
      if (getTriggerRawAxis(3) > 0.1) {
        Chassis.getInstance().drive(getTriggerRawAxis(3), -getTriggerRawAxis(3));
      } else if (getTriggerRawAxis(4) > 0.1) {
        Chassis.getInstance().drive(-getTriggerRawAxis(4), getTriggerRawAxis(4));
      }
    }
  }

  @Override
  public void end() {
    Chassis.getInstance().stopMotors();
  }

  private double getThirdRawAxis(int axis) {
    return Math.pow(controller.getRawAxis(axis), 1);
  }

  private double getTriggerRawAxis(int axis) {
    return Math.pow(controller.getRawAxis(axis), 3) + 0.1;
  }
}
