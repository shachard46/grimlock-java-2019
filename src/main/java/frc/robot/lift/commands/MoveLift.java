package frc.robot.lift.commands;

import frc.robot.Command;
import frc.robot.lift.Lift;

public class MoveLift extends Command {
    private double speed;
    public MoveLift(double speed) {
        super(Lift.getInstance());
        this.speed = speed;
    }

    @Override
    protected void execute() {
        Lift.getInstance().move(speed);
    }

    @Override
    protected void end() {
        Lift.getInstance().stopMotors();
    }
}
