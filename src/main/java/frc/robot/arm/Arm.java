package frc.robot.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Subsystem;
import frc.robot.utils.Utils;

import static frc.robot.arm.ArmConstants.*;

public class Arm extends Subsystem {
    private static final TalonSRX armMasterMotor = new TalonSRX(0);
    private static final VictorSPX armFollowerMotor = new VictorSPX(0);
    private static ArmState armState = ArmState.ballCollect;

    public static ArmState getArmState() {
        return armState;
    }

    public void setArmState(ArmState armState) {
        this.armState = armState;
    }

    public void moveArm(double speed) {
        armMasterMotor.set(ControlMode.PercentOutput, speed);
    }

    public void autoMoveArm(double ticks) {
        armMasterMotor.set(ControlMode.MotionMagic, ticks, DemandType.ArbitraryFeedForward, calculateFF());
    }

    public boolean isAtTarget(double target) {
        return Utils.isInTolerance(armMasterMotor.getSelectedSensorPosition(), target, TOLERANCE);
    }

    public void armToState() {
        switch (armState) {
            case ballCollect:
                autoMoveArm(BALL_COLLECT_ARM_ANGLE);
                break;
            case frontDisk:
                autoMoveArm(FRONT_DISK_ARM_ANGLE);
                break;
            case frontLowBall:
                autoMoveArm(FRONT_LOW_BALL_ARM_ANGLE);
                break;
            case feederDisk:
                autoMoveArm(FEEDER_DISK_COLLECT);
                break;
            case feederBall:
                autoMoveArm(FEEDER_BALL_COLLECT);
                break;
            case frontMidBall:
                autoMoveArm(FRONT_MIDDLE_BALL_ARM_ANGLE);
                break;
            case middleBackBall:
                autoMoveArm(BACK__MIDDLE_BALL_ARM_ANGLE);
                break;
            case highBackBall:
                autoMoveArm(BACK_HIGH_BALL_ARM_ANGLE);
                break;
            case backDisk:
                autoMoveArm(BACK_DISK_ARM_ANGLE);
                break;
        }
    }

    public void isArmAtState() {
        switch (armState) {
            case ballCollect:
                isAtTarget(BALL_COLLECT_ARM_ANGLE);
                break;
            case frontDisk:
                isAtTarget(FRONT_DISK_ARM_ANGLE);
                break;
            case frontLowBall:
                isAtTarget(FRONT_LOW_BALL_ARM_ANGLE);
                break;
            case feederDisk:
                isAtTarget(FEEDER_DISK_COLLECT);
                break;
            case feederBall:
                isAtTarget(FEEDER_BALL_COLLECT);
                break;
            case frontMidBall:
                isAtTarget(FRONT_MIDDLE_BALL_ARM_ANGLE);
                break;
            case middleBackBall:
                isAtTarget(BACK__MIDDLE_BALL_ARM_ANGLE);
                break;
            case highBackBall:
                isAtTarget(BACK_HIGH_BALL_ARM_ANGLE);
                break;
            case backDisk:
                isAtTarget(BACK_DISK_ARM_ANGLE);
                break;
        }
    }

    public double getStateVal(ArmState armState) {
        switch (armState) {
            case ballCollect:
                return BALL_COLLECT_ARM_ANGLE;
            case frontDisk:
                return FRONT_DISK_ARM_ANGLE;
            case frontLowBall:
                return FRONT_LOW_BALL_ARM_ANGLE;
            case frontMidBall:
                return FRONT_MIDDLE_BALL_ARM_ANGLE;
            case highBackBall:
                return BACK_HIGH_BALL_ARM_ANGLE;
            case middleBackBall:
                return BACK__MIDDLE_BALL_ARM_ANGLE;
            case backDisk:
                return BACK_DISK_ARM_ANGLE;
            case feederBall:
                return FEEDER_BALL_COLLECT;
        }
        return 0;
    }

    public double calculateFF() {
        int current_ticks = armMasterMotor.getSelectedSensorPosition();
        double angle = (current_ticks / ARM_TICKS_PER_REVULATION) * 360;
        double arm_torque = ARM_TORQUE_WITHOOUT_ANGLE * Utils.cosInDegrees(angle);

        double leverage_torque = LEVERAGE_TORQUE_WITHOOUT_ANGLE * Utils.cosInDegrees(angle -
                Utils.tanInDegrees(LEVERAGE_LENGTH * Utils.cosInDegrees(angle) / (DIST_TSIR_TO_SPRING -
                        Utils.sinInDegrees(angle) * LEVERAGE_LENGTH)));
        double final_torque = arm_torque - leverage_torque;
        return 1 * (final_torque * INTERNAL_RESISTANCE / EFFECTIVE_KT) / DriverStation.getInstance().getBatteryVoltage();
    }

    public void zeroArm() {
        armMasterMotor.setSelectedSensorPosition(-3840, 0, 0);
    }

    @Override
    public void restoreFactoryDefault() {
        armMasterMotor.configFactoryDefault();
        armFollowerMotor.configFactoryDefault();
    }

    @Override
    public void setSensorPhase() {
        armMasterMotor.setSensorPhase(true);
    }

    @Override
    public void stopMotors() {
        armMasterMotor.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void setSensorInitialPosition() {
        armMasterMotor.setSelectedSensorPosition(ARM_INITIAL_POSITION);
    }

    @Override
    public void setInverted() {
        armMasterMotor.setInverted(false);
        armFollowerMotor.setInverted(false);
    }

    @Override
    public void follow() {
        armFollowerMotor.follow(armMasterMotor);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
