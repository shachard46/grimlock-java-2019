package frc.robot.lift;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystem;
import frc.robot.utils.Utils;

import static frc.robot.RobotMap.LIFT_MOTOR_PORT_1;
import static frc.robot.RobotMap.LIFT_MOTOR_PORT_2;

public class Lift extends Subsystem {

    private static final int TOLERANCE = 600;
    private static final Lift instance = new Lift();
    private final TalonSRX liftMasterMotor;
    private final VictorSPX liftSlaveMotor;
    private LiftState state = LiftState.down;

    private Lift() {
        liftMasterMotor = new TalonSRX(LIFT_MOTOR_PORT_1);
        liftSlaveMotor = new VictorSPX(LIFT_MOTOR_PORT_2);
        initialize();
    }

    public static Lift getInstance() {
        return instance;
    }

    public void move(double speed) {
        liftMasterMotor.set(ControlMode.PercentOutput, speed);
        SmartDashboard.putNumber("LiftSpeed", speed);
    }

    private void autoMove(int ticks) {
        liftMasterMotor.set(ControlMode.MotionMagic, ticks);
    }

    public void setState(LiftState state) {
        this.state = state;
    }

    public void liftToState(LiftState state) {
        autoMove(state.getAngle());
    }

    private boolean isAtTarget(double target) {
        return Utils.isInTolerance(liftMasterMotor.getSelectedSensorPosition(), target, TOLERANCE);
    }

    public void liftToState() {
        autoMove(state.getAngle());
    }

    public boolean isAtState() {
        return isAtTarget(state.getAngle());
    }

    public double getStateTicks(LiftState state) {
        return state.getAngle();
    }

    @Override
    protected void restoreFactoryDefault() {
        liftMasterMotor.configFactoryDefault();
        liftSlaveMotor.configFactoryDefault();
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
        liftSlaveMotor.setInverted(false);
    }

    @Override
    protected void follow() {
        liftSlaveMotor.follow(liftMasterMotor);
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
