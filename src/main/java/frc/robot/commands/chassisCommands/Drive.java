package frc.robot.commands.chassisCommands;

import frc.robot.commands.Command;
import frc.robot.subsystems.Chassis;

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