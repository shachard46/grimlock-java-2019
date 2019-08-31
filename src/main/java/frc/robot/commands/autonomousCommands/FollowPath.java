package frc.robot.commands.autonomousCommands;

import edu.wpi.first.wpilibj.Notifier;
import frc.robot.RobotParams;
import frc.robot.autonomous.PathFollower;
import frc.robot.commands.Command;
import frc.robot.subsystems.Chassis;

import java.io.File;
import java.io.IOException;

public class FollowPath extends Command {
    private String name;
    private PathFollower follower;
    private Notifier notifier;

    public FollowPath(String name) {
        super(Chassis.getInstance());
        this.name = name;
    }

    @Override
    protected void initialize() {
        try {
            follower = new PathFollower(name);
        }catch (IOException e){
            throw new RuntimeException("Could not initialize PathFollower", e);
        }
        notifier = new Notifier(new Thread(() -> follower.followPath()));
        notifier.startPeriodic(0.02);
    }

    @Override
    protected boolean isFinished() {
        return follower.isPathFinished();
    }

    @Override
    protected void end() {
        notifier.stop();
        Chassis.getInstance().stopMotors();
    }
}
