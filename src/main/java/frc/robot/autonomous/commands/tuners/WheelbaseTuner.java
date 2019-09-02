package frc.robot.autonomous.commands.tuners;

import frc.robot.Command;
import frc.robot.chassis.Chassis;

public class WheelbaseTuner extends Command {
    private final int NUM_OF_ROTS = 10;
    private final double ANGLE_TOLERANCE = 5;
    private final double POWER_TO_APPLY = 0.3;
    double overallDistance;
    private double initRightEncoderDist;
    private double initLeftEncoderDist;
    private int rotations;
    private boolean zeroFlag;

    public WheelbaseTuner() {
        super(Chassis.getInstance());

    }

    @Override
    protected void initialize() {
        zeroFlag = false;
        Chassis.getNavX().zeroYaw();
        rotations = 0;
        initLeftEncoderDist = Chassis.getInstance().getLeftDistance();
        initRightEncoderDist = Chassis.getInstance().getRightDistance();
    }

    @Override
    protected void execute() {
        double currentLeftEncoderDist = Math.abs(Chassis.getInstance().getLeftDistance() - initLeftEncoderDist);
        double currentRightEncoderDist = Math.abs(Chassis.getInstance().getRightDistance() - initRightEncoderDist);
        overallDistance = (currentLeftEncoderDist + currentRightEncoderDist) / 2;

        if (Math.abs(Chassis.getNavX().getYaw()) <= ANGLE_TOLERANCE) {
            if (!zeroFlag) {
                rotations++;
                zeroFlag = true;
            }
        } else {
            zeroFlag = false;
        }


        Chassis.getInstance().drive(-POWER_TO_APPLY, POWER_TO_APPLY);
    }

    @Override
    protected boolean isFinished() {
        return rotations == NUM_OF_ROTS;
    }

    @Override
    protected void end() {
        Chassis.getInstance().stopMotors();
        System.out.println(overallDistance / rotations / Math.PI);
    }


}
