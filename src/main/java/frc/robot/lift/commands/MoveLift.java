package frc.robot.lift.commands;

import frc.robot.Command;
import frc.robot.Robot;
import frc.robot.Trigger;
import frc.robot.lift.Lift;

public class MoveLift extends Command {
    public MoveLift() {
        super(Lift.getInstance());
    }

    @Override
    protected void execute() {
        Lift.getInstance().move(Robot.oi.getController().getRawAxis(Trigger.GamepadAxis.rightY.getAxis()));
    }

    @Override
    protected void end() {
        Lift.getInstance().stopMotors();
    }
}
