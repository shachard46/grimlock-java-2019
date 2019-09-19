package frc.robot.arm.commands;

import frc.robot.Command;
import frc.robot.Robot;
import frc.robot.Trigger;
import frc.robot.arm.Arm;

public class MoveArm extends Command {

    public MoveArm() {
        super(Arm.getInstance());    }

    @Override
    protected void execute() {
        Arm.getInstance().move(-Robot.oi.getController().getRawAxis(Trigger.GamepadAxis.leftY.getAxis()));
    }

    @Override
    protected void end() {
        Arm.getInstance().stopMotors();
    }
}
