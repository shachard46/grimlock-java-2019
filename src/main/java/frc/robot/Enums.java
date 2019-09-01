package frc.robot;

public class Enums {
    public static enum ArmState {
        ballCollect,
        frontDisk,
        frontLowBall,
        feederDisk,
        feederBall,
        frontMidBall,
        middleBackBall,
        highBackBall,
        backDisk,
    }
    public static enum LiftState {
        down,
        bottomBall,
        bottomDisk,
        middleDisk,
        moveIntake,
        moveArm,
        feederBallCollect,
        topDisk,
        middleBallFront,
        up,
    }
}
