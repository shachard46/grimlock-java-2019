package frc.robot.chassis.commands;

import frc.robot.Command;
import frc.robot.chassis.Chassis;
import frc.robot.chassis.Chassis.Gear;

public class Shift extends Command {
    Gear gear;

    public Shift(Gear gear) {
        super();
        this.gear = gear;
    }

    @Override
    protected void initialize() {
        Chassis.getInstance().setGear(gear);
    }
}
