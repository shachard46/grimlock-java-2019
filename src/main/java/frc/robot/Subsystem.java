package frc.robot;


public abstract class Subsystem extends edu.wpi.first.wpilibj.command.Subsystem {
    public Subsystem(){
        restoreFactoryDefault();
        setInverted();
        follow();
        configSensor();
    }
    public abstract void restoreFactoryDefault();
    public abstract void setSensorPhase();
    public void configSensor(){
        setSensorInitialPosition();
        setSensorPhase();
    }
    public abstract void stopMotors();
    public abstract void setSensorInitialPosition();
    public abstract void setInverted();
    public abstract void follow();
}
