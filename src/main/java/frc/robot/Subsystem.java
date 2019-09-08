package frc.robot;


public abstract class Subsystem extends edu.wpi.first.wpilibj.command.Subsystem {
    protected void initialize(){
        restoreFactoryDefault();
        invert();
        follow();
        configSensor();
    }
    protected abstract void restoreFactoryDefault();

    protected  void setSensorPhase(){}

    protected void configSensor() {
        initializeSensorPosition();
        setSensorPhase();
    }

    public abstract void stopMotors();

    protected void initializeSensorPosition(){}

    protected abstract void invert();

    protected void follow(){}
}
