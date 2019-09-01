package frc.robot.arm.commands;

import frc.robot.Command;
import frc.robot.arm.Arm;

public class MoveArm extends Command {
    private double speed;

    public MoveArm(double speed) {
        super(Arm.getInstance());
        this.speed = speed;
    }

    @Override
    protected void execute() {
        Arm.getInstance().move(speed);
    }

    @Override
    protected void end() {
        Arm.getInstance().stopMotors();
    }
}
