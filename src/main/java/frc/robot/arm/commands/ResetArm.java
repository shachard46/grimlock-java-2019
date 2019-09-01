package frc.robot.arm.commands;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Command;
import frc.robot.arm.Arm;

public class ResetArm extends Command {
    public ResetArm() {
        super(Arm.getInstance());
    }

    @Override
    protected void initialize() {
        Arm.getInstance().reset();
    }
}
