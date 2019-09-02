package frc.robot.lift;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.RobotMap;
import frc.robot.Subsystem;
import frc.robot.utils.Utils;

public class Lift extends Subsystem {

    private static final int TOLERANCE = 600;
    private static final Lift instance = new Lift();
    private final TalonSRX liftMasterMotor = new TalonSRX(RobotMap.LIFT_MOTOR_PORT_1);
    private final VictorSPX liftFollowerMotor = new VictorSPX(RobotMap.LIFT_MOTOR_PORT_2);
    private LiftState liftState = LiftState.down;

    public static Lift getInstance() {
        return instance;
    }

    public void move(double speed) {
        liftMasterMotor.set(ControlMode.PercentOutput, speed);
    }

    public void autoMove(int ticks) {
        liftMasterMotor.set(ControlMode.MotionMagic, ticks);
    }

    public void liftToState(LiftState state) {
        autoMove(state.getAngle());
    }

    private boolean isAtTarget(double target) {
        return Utils.isInTolerance(liftMasterMotor.getSelectedSensorPosition(), target, TOLERANCE);
    }

    public void liftToState() {
        autoMove(liftState.getAngle());
    }

    public boolean isAtState() {
        return isAtTarget(liftState.getAngle());
    }

    public double getStateTicks(LiftState liftState) {
        return liftState.getAngle();
    }

    @Override
    protected void restoreFactoryDefault() {
        liftMasterMotor.configFactoryDefault();
        liftFollowerMotor.configFactoryDefault();
    }

    @Override
    protected void setSensorPhase() {
        liftMasterMotor.setSensorPhase(true);
    }

    @Override
    public void stopMotors() {
        liftMasterMotor.set(ControlMode.PercentOutput, 0);
    }

    @Override
    protected void initializeSensorPosition() {
        liftMasterMotor.setSelectedSensorPosition(0);
    }

    @Override
    protected void invert() {
        liftMasterMotor.setInverted(true);
        liftFollowerMotor.setInverted(false);
    }

    @Override
    protected void follow() {
        liftFollowerMotor.follow(liftMasterMotor);
    }

    @Override
    protected void configSensor() {
        liftMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        liftMasterMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 20, 10);

        liftMasterMotor.selectProfileSlot(0, 0);
        liftMasterMotor.config_kP(0, 0.5, 0);
        liftMasterMotor.config_kI(0, 0, 0);
        liftMasterMotor.config_kD(0, 0, 0);

        super.configSensor();
    }

    @Override
    protected void initDefaultCommand() {

    }

    public enum LiftState {
        down(0),
        bottomBall(0),
        bottomDisk(0),
        middleDisk(68131),
        moveIntake(31573),
        moveArm(10000),
        feederBallCollect(63194),
        topDisk(66756),
        middleBallFront(9941),
        up(72105);

        private final int angle;

        LiftState(int angle) {
            this.angle = angle;
        }

        public int getAngle() {
            return angle;
        }
    }
}
