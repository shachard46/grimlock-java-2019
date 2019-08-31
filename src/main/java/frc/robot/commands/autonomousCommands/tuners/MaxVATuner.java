package frc.robot.commands.autonomousCommands.tuners;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.commands.Command;
import frc.robot.subsystems.Chassis;

public class MaxVATuner extends Command {
    private final double MAX_DIST = 5;

    private double currentVelocity, maxVelocity, prevVelocity, currentAcc, maxAcc, prevTime, initDistance, currentDistance;

    public MaxVATuner() {
        super(Chassis.getInstance());
    }

    @Override
    protected void initialize() {
        currentVelocity = 0;
        currentAcc = 0;
        maxVelocity = 0;
        maxAcc = 0;
        prevTime = 0;
        prevVelocity = 0;
        initDistance = Chassis.getInstance().getAverageDistance();
        currentDistance = 0;
    }

    @Override
    protected void execute() {
        currentVelocity = Chassis.getInstance().getAverageVelocity();
        currentAcc = (currentVelocity - prevVelocity) / (Timer.getFPGATimestamp() - prevTime);
        currentDistance = Chassis.getInstance().getAverageDistance() - initDistance;

        if (currentVelocity > maxVelocity) {
            maxVelocity = currentVelocity;
        }

        if (currentAcc > maxAcc) {
            maxAcc = currentAcc;
        }

        prevTime = Timer.getFPGATimestamp();
        prevVelocity = Chassis.getInstance().getAverageVelocity();

        Chassis.getInstance().drive(1, 1);
    }

    @Override
    protected boolean isFinished() {
        return currentDistance >= MAX_DIST;
    }

    @Override
    protected void end() {
        Chassis.getInstance().stopMotors();
        System.out.println("max velocity: " + maxVelocity);
        System.out.println("max acc: " + maxAcc);
    }
}
