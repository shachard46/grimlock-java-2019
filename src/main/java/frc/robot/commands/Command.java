/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class Command extends edu.wpi.first.wpilibj.command.Command {
  public Command(Subsystem subsystem) {
    requires(subsystem);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run


  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void interrupted() {
    end();
  }
}
