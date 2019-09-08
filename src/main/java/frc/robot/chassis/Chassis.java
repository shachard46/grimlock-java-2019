/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.chassis;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.Subsystem;
import frc.robot.chassis.commands.DriveByController;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import static frc.robot.RobotMap.*;
import static frc.robot.autonomous.AutonomousConstants.TICKS_PER_REVOLUTION;
import static frc.robot.autonomous.AutonomousConstants.WHEEL_DIAMETER;

/**
 * Add your docs here.
 */
public class Chassis extends Subsystem {
    private static final AHRS navX = new AHRS(I2C.Port.kMXP);
    private final WPI_TalonSRX leftMasterMotor = new WPI_TalonSRX(CHASSIS_LEFT_MOTOR_PORT_1);
    private final WPI_TalonSRX leftSlaveMotor = new WPI_TalonSRX(CHASSIS_LEFT_MOTOR_PORT_2);
    private final WPI_TalonSRX rightMasterMotor = new WPI_TalonSRX(CHASSIS_RIGHT_MOTOR_PORT_1);
    private final WPI_TalonSRX rightSlaveMotor = new WPI_TalonSRX(CHASSIS_RIGHT_MOTOR_PORT_2);
    private final SpeedControllerGroup left = new SpeedControllerGroup(leftMasterMotor, leftSlaveMotor);
    private final SpeedControllerGroup right = new SpeedControllerGroup(rightMasterMotor, rightSlaveMotor);
    private final DifferentialDrive drive = new DifferentialDrive(left, right);
    private final DoubleSolenoid shifterSolenoid = new DoubleSolenoid(CHASSIS_SOL_PORT_1, CHASSIS_SOL_PORT_2);

    private static final Chassis instance = new Chassis();

    private Chassis() {
        super();
    }

    public static Chassis getInstance() {
        return instance;
    }

    public static AHRS getNavX() {
        return navX;
    }

    public void drive(double left, double right) {
        drive.tankDrive(left, right);
    }

    public void cheesyDrive(double forward, double turn) {
        drive.curvatureDrive(forward, turn, false);
    }

    /**
     * @return returns the robot current gear
     */
    public Gear getGear() {
        return Gear.getGear(shifterSolenoid.get());
    }

    public void setGear(Gear gear) {
        gear.shiftGear(shifterSolenoid);
    }

    private double getLeftVelocity() {
        return ((double) leftMasterMotor.getSelectedSensorVelocity() / TICKS_PER_REVOLUTION) * WHEEL_DIAMETER * Math.PI;
    }

    private double getRightVelocity() {
        return ((double) rightMasterMotor.getSelectedSensorVelocity() / TICKS_PER_REVOLUTION) * WHEEL_DIAMETER
                * Math.PI;
    }

    public double getAverageVelocity() {
        return (getLeftVelocity() + getRightVelocity()) / 2;
    }

    public double getLeftDistance() {
        return ((double) leftMasterMotor.getSelectedSensorPosition() / TICKS_PER_REVOLUTION) * WHEEL_DIAMETER * Math.PI;
    }

    public double getRightDistance() {
        return ((double) rightMasterMotor.getSelectedSensorPosition() / TICKS_PER_REVOLUTION) * WHEEL_DIAMETER
                * Math.PI;
    }

    public double getAverageDistance() {
        return (getLeftDistance() + getRightDistance()) / 2;
    }

    public int getLeftEncoder() {
        return leftMasterMotor.getSelectedSensorPosition();
    }

    public int getRightEncoder() {
        return rightMasterMotor.getSelectedSensorPosition();
    }

    @Override
    public void initDefaultCommand() {
        (new DriveByController(Robot.oi.getJoystick())).start();
    }

    @Override
    public void restoreFactoryDefault() {
        leftMasterMotor.configFactoryDefault();
        leftSlaveMotor.configFactoryDefault();
        rightMasterMotor.configFactoryDefault();
        rightSlaveMotor.configFactoryDefault();
    }

    @Override
    public void setSensorPhase() {
        leftMasterMotor.setSensorPhase(true);
        rightMasterMotor.setSensorPhase(true);
    }

    @Override
    public void stopMotors() {
        left.set(0);
        right.set(0);
    }

    @Override
    public void initializeSensorPosition() {
        leftMasterMotor.setSelectedSensorPosition(0);
        rightMasterMotor.setSelectedSensorPosition(0);
    }

    @Override
    public void invert() {
        left.setInverted(true);
        left.setInverted(false);
    }

    @Override
    public void follow() {
        leftSlaveMotor.follow(leftMasterMotor);
        rightSlaveMotor.follow(rightMasterMotor);
    }

    @Override
    public void configSensor() {
        leftMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        rightMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        leftMasterMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 20, 10);
        rightMasterMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 20, 10);

        super.configSensor();
    }

    public enum Gear {
        powerGear(Value.kForward), speedGear(Value.kReverse), neutralGear(Value.kOff);

        private Value solenoidValue;

        Gear(Value solenoidValue) {
            this.solenoidValue = solenoidValue;
        }

        static Gear getGear(Value solenoidValue) {
            for (Gear gear : Gear.values()) {
                if (gear.getSolenoidValue() == solenoidValue) {
                    return gear;
                }
            }
            throw new IllegalArgumentException("Illegal solenoidValue: " + solenoidValue);
        }

        void shiftGear(DoubleSolenoid doubleSolenoid) {
            doubleSolenoid.set(this.solenoidValue);
        }

        Value getSolenoidValue() {
            return solenoidValue;
        }

    }
}
