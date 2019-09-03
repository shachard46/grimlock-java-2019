package frc.robot.lift.commands;

import frc.robot.Command;
import frc.robot.lift.Lift;

public class LiftToState extends Command {
    private Lift.LiftState state;

    public LiftToState(Lift.LiftState state) {
        super(Lift.getInstance());
        this.state = state;
    }

    @Override
    protected void initialize() {
        Lift.getInstance().setState(state);
    }

    @Override
    protected void execute() {
        Lift.getInstance().liftToState();
    }

    @Override
    protected boolean isFinished() {
        return Lift.getInstance().isAtState();
    }

    @Override
    protected void end() {
        Lift.getInstance().stopMotors();
    }
}
