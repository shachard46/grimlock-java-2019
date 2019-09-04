package frc.robot.gripper.commands;

import frc.robot.Command;
import frc.robot.gripper.Gripper;

public class SetGameMode extends Command {
    private Gripper.GripperMode mode;
    public SetGameMode(Gripper.GripperMode mode) {
        this.mode = mode;
    }

    @Override
    protected void initialize() {
        Gripper.getInstance().setMode(mode);
    }
}
