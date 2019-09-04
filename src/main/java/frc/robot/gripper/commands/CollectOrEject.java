package frc.robot.gripper.commands;

import frc.robot.Command;
import frc.robot.gripper.Gripper;

public class CollectOrEject extends Command {
    private double speed;
    public CollectOrEject(double speed) {
        super(Gripper.getInstance());
        this.speed = speed;
    }

    @Override
    protected void execute() {
        Gripper.getInstance().rotate(speed);
    }

    @Override
    protected void end() {
        Gripper.getInstance().stopMotors();
    }
}
