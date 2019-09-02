package frc.robot.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Subsystem;
import frc.robot.arm.commands.HoldPosition;
import frc.robot.utils.Utils;

import static frc.robot.RobotMap.ARM_MOTOR_PORT_1;
import static frc.robot.RobotMap.ARM_MOTOR_PORT_2;

public class Arm extends Subsystem {
    private static final int ARM_INITIAL_POSITION = -3840;
    private static final int TOLERANCE = 70; // was 55

    private static final int ARM_TICKS_PER_REVOLUTION = 4096;

    private static final double EFFECTIVE_KT = 0.00529850746 * 2 * 360; //in N*m/V  stalltorqueper1A*2motors*gearratio 3.973
    private static final double INTERNAL_RESISTANCE = 0.0895522388;
    private static final double ARM_TORQUE_WITHOUT_ANGLE = 22.71996;
    private static final double LEVERAGE_TORQUE_WITHOUT_ANGLE = 12.73537339;
    private static final double LEVERAGE_LENGTH = 0.135;
    private static final double DIST_TSIR_TO_SPRING = 0.144;
    private static final Arm instance = new Arm();
    private final TalonSRX armMasterMotor = new TalonSRX(ARM_MOTOR_PORT_1);
    private final VictorSPX armFollowerMotor = new VictorSPX(ARM_MOTOR_PORT_2);
    private ArmState armState = ArmState.ballCollect;

    public static Arm getInstance() {
        return instance;
    }

    public void holdPosition() {
        armMasterMotor.set(ControlMode.PercentOutput, 0, DemandType.ArbitraryFeedForward, calculateFF());

    }

    public ArmState getState() {
        return armState;
    }

    public void setState(ArmState armState) {
        this.armState = armState;
    }

    public void move(double speed) {
        armMasterMotor.set(ControlMode.PercentOutput, speed);
    }

    public void autoMove(double ticks) {
        armMasterMotor.set(ControlMode.MotionMagic, ticks, DemandType.ArbitraryFeedForward, calculateFF());
    }

    private boolean isAtTarget(double target) {
        return Utils.isInTolerance(armMasterMotor.getSelectedSensorPosition(), target, TOLERANCE);
    }

    public void liftToState() {
        autoMove(armState.getAngle());
    }

    public boolean isAtState() {
        return isAtTarget(armState.getAngle());
    }

    public double getStateTicks(ArmState armState) {
        return armState.getAngle();
    }

    private double calculateFF() {
        int current_ticks = armMasterMotor.getSelectedSensorPosition();
        double angle = ((double) current_ticks / ARM_TICKS_PER_REVOLUTION) * 360;
        double arm_torque = ARM_TORQUE_WITHOUT_ANGLE * Utils.cosInDegrees(angle);

        double leverage_torque = LEVERAGE_TORQUE_WITHOUT_ANGLE * Utils.cosInDegrees(angle -
                Utils.tanInDegrees(LEVERAGE_LENGTH * Utils.cosInDegrees(angle) / (DIST_TSIR_TO_SPRING -
                        Utils.sinInDegrees(angle) * LEVERAGE_LENGTH)));
        double final_torque = arm_torque - leverage_torque;
        return 1 * (final_torque * INTERNAL_RESISTANCE / EFFECTIVE_KT) / DriverStation.getInstance().getBatteryVoltage();
    }

    public void reset() {
        armMasterMotor.setSelectedSensorPosition(-3840, 0, 0);
    }

    @Override
    protected void restoreFactoryDefault() {
        armMasterMotor.configFactoryDefault();
        armFollowerMotor.configFactoryDefault();
    }

    @Override
    protected void setSensorPhase() {
        armMasterMotor.setSensorPhase(true);
    }

    @Override
    public void stopMotors() {
        armMasterMotor.set(ControlMode.PercentOutput, 0);
    }

    @Override
    protected void initializeSensorPosition() {
        armMasterMotor.setSelectedSensorPosition(ARM_INITIAL_POSITION);
    }

    @Override
    protected void invert() {
        armMasterMotor.setInverted(false);
        armFollowerMotor.setInverted(false);
    }

    @Override
    protected void follow() {
        armFollowerMotor.follow(armMasterMotor);
    }

    @Override
    protected void configSensor() {
        armMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        armMasterMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 20, 10);

        armMasterMotor.selectProfileSlot(0, 0);
        armMasterMotor.config_kP(0, 0.8, 0);
        armMasterMotor.config_kI(0, 0, 0);
        armMasterMotor.config_kD(0, 0, 0);

        super.configSensor();
    }

    @Override
    protected void initDefaultCommand() {
        (new HoldPosition()).start();
    }

    public enum ArmState {
        ballCollect(-3840),
        frontDisk(-3000),
        frontLowBall(-2600),
        feederDisk(-3516),
        feederBall(-3245),
        frontMidBall(-2506),
        middleBackBall(4856),
        highBackBall(3490),
        backDisk(3552);

        private final int angle;

        ArmState(int angle) {
            this.angle = angle;
        }

        int getAngle() {
            return angle;
        }
    }
}
