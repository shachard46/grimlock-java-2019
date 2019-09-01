package frc.robot;


public abstract class Subsystem extends edu.wpi.first.wpilibj.command.Subsystem {
    public Subsystem() {
        restoreFactoryDefault();
        invert();
        follow();
        configSensor();
    }

    protected abstract void restoreFactoryDefault();

    protected abstract void setSensorPhase();

    protected void configSensor() {
        initializeSensorPosition();
        setSensorPhase();
    }

    public abstract void stopMotors();

    protected abstract void initializeSensorPosition();

    protected abstract void invert();

    protected abstract void follow();
}
