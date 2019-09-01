package frc.robot.chassis.commands;

import frc.robot.Command;
import frc.robot.chassis.Chassis;

public class Drive extends Command {
    private double left;
    private double right;


    public Drive(double left, double right) {
        super(Chassis.getInstance());
        this.left = left;
        this.right = right;
    }

    @Override
    protected void execute() {
        Chassis.getInstance().drive(left, right);
    }


    @Override
    protected void end() {
        Chassis.getInstance().stopMotors();
    }
}