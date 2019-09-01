package frc.robot.arm.commands;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Command;
import frc.robot.arm.Arm;

public class HoldPosition extends Command {
    public HoldPosition() {
        super(Arm.getInstance());
    }

    @Override
    protected void execute() {
        Arm.getInstance().holdPosition();
    }
}
