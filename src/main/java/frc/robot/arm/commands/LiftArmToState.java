package frc.robot.arm.commands;

import frc.robot.Command;
import frc.robot.arm.Arm;

public class LiftArmToState extends Command {
    Arm.ArmState state;

    public LiftArmToState(Arm.ArmState state) {
        super(Arm.getInstance());
    }

    @Override
    protected void initialize() {
        Arm.getInstance().setState(state);
    }

    @Override
    protected void execute() {
        Arm.getInstance().liftToState();
    }

    @Override
    protected boolean isFinished() {
        return Arm.getInstance().isAtState();
    }

    @Override
    protected void end() {
        Arm.getInstance().stopMotors();
    }
}
