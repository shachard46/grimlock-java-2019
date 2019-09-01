package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DriverStation;

import static frc.robot.Enums.ArmState;
import static frc.robot.RobotParams.*;

public class Arm extends Subsystem {
    private static final TalonSRX armMasterMotor = new TalonSRX(0);
    private static final VictorSPX armFollowerMotor = new VictorSPX(0);
    public static ArmState armState = ArmState.ballCollect;

    public void moveArm(double speed) {
        armMasterMotor.set(ControlMode.PercentOutput, speed);
    }

    public void AutoMoveArm(int ticks) {
        armMasterMotor.set(ControlMode.MotionMagic, ticks, DemandType.ArbitraryFeedForward, calculateFF());
    }


    public double calculateFF(){
        double angle = (currnt_ticks / ARM_TICKS_PER_REVULATION) * 360;
        double arm_torque = ARM_TORQUE_WITHOOUT_ANGLE * TB_Math::CosInDegrees(angle);

        double leverage_torque = LEVERAGE_TORQUE_WITHOOUT_ANGLE * TB_Math::CosInDegrees(angle -
                TB_Math::ATanInDegrees(LEVERAGE_LENGTH * TB_Math::CosInDegrees(angle) / (DIST_TSIR_TO_SPRING - TB_Math::SinInDegrees(angle) * LEVERAGE_LENGTH)));
        double final_torque = arm_torque - leverage_torque;
        return 1 * (final_torque * INTERNAL_RESISTANCE / EFFECTIVE_KT) / DriverStation.getInstance().getBatteryVoltage();
        return 1 * (final_torque * INTERNAL_RESISTANCE / EFFECTIVE_KT) / DriverStation.getInstance().getBatteryVoltage();
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
