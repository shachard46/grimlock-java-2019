package frc.robot.gripper;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import frc.robot.RobotMap;
import frc.robot.Subsystem;

public class Gripper extends Subsystem {
    private static final Gripper instance = new Gripper();
    private final Victor gripperMotor;
    private final DoubleSolenoid gripperModeSolenoid;
    private GripperMode mode = GripperMode.diskMode;

    private Gripper() {
        super();
        gripperMotor = new Victor(RobotMap.CLAW_MOTOR_PORT);
        gripperModeSolenoid = new DoubleSolenoid(RobotMap.CLAW_SOLENOID_PORT_1, RobotMap.CLAW_SOLENOID_PORT_2);
    }

    public static Gripper getInstance() {
        return instance;
    }

    public GripperMode getMode() {
        return mode;
    }

    public void setMode(GripperMode mode) {
        this.mode = mode;
        mode.setMode(gripperModeSolenoid);
    }

    public void rotate(double speed) {
        int modeDirection = mode == GripperMode.ballMode ? 1 : -1;
        gripperMotor.set(modeDirection * speed);
    }

    @Override
    protected void restoreFactoryDefault() {

    }

    @Override
    public void stopMotors() {
        gripperMotor.stopMotor();
    }

    @Override
    protected void invert() {
        gripperMotor.setInverted(false);
    }

    @Override
    protected void initDefaultCommand() {

    }

    public enum GripperMode {
        ballMode(DoubleSolenoid.Value.kForward),
        diskMode(DoubleSolenoid.Value.kReverse);

        private DoubleSolenoid.Value value;

        GripperMode(DoubleSolenoid.Value value) {
            this.value = value;
        }

        public DoubleSolenoid.Value getValue() {
            return value;
        }

        public void setMode(DoubleSolenoid solenoid) {
            solenoid.set(value);
        }
    }
}
