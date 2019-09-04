package frc.robot.autonomous;

import frc.robot.chassis.Chassis;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

import java.io.File;
import java.io.IOException;

import static frc.robot.autonomous.AutonomousConstants.*;

public class PathFollower {
    private EncoderFollower leftEncoderFollower, rightEncoderFollower;

    public PathFollower(String pathName) throws IOException {
        File leftTrajFile = new File(ROBORIO_OUTPUT_DIR + pathName + LEFT_TRAJECTORY_EXTENSION);
        File rightTrajFile = new File(ROBORIO_OUTPUT_DIR + pathName + RIGHT_TRAJECTORY_EXTENSION);
        Trajectory leftTrajectory = Pathfinder.readFromCSV(leftTrajFile);
        Trajectory rightTrajectory = Pathfinder.readFromCSV(rightTrajFile);

        leftEncoderFollower = new EncoderFollower(leftTrajectory);
        rightEncoderFollower = new EncoderFollower(rightTrajectory);

        leftEncoderFollower.configurePIDVA(KP, KI, KD, KV, KA);
        rightEncoderFollower.configurePIDVA(KP, KI, KD, KV, KA);

        int leftInitialPosition = Chassis.getInstance().getLeftEncoder();
        int rightInitialPosition = Chassis.getInstance().getRightEncoder();

        leftEncoderFollower.configureEncoder(leftInitialPosition, TICKS_PER_REVOLUTION, WHEEL_DIAMETER);
        rightEncoderFollower.configureEncoder(rightInitialPosition, TICKS_PER_REVOLUTION, WHEEL_DIAMETER);

        leftEncoderFollower.reset();
        rightEncoderFollower.reset();

    }

    public void followPath() {
        int leftPos = Chassis.getInstance().getLeftEncoder();
        int rightPos = Chassis.getInstance().getRightEncoder();

        double leftSpeed = leftEncoderFollower.calculate(leftPos);
        double rightSpeed = rightEncoderFollower.calculate(rightPos);

        double turn = 0.8 * (-1. / 80) * (Pathfinder.boundHalfDegrees(Pathfinder.r2d(rightEncoderFollower.getHeading()))
                - Chassis.getNavX().getYaw());
        Chassis.getInstance().drive(leftSpeed + turn, rightSpeed - turn);
    }

    public boolean isPathFinished() {
        return leftEncoderFollower.isFinished() && rightEncoderFollower.isFinished();
    }
}
