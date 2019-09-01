package frc.robot.autonomous.commands.tuners;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Command;
import frc.robot.chassis.Chassis;
import frc.robot.utils.SimpleCSVLogger;

public class KvTuner extends Command {

    private final double MAX_DISTANCE = 5.1; // The maximum distance the robot is allowed to travel. (meters)
    private final double DELTA_PERCENT = 0.05; // The percent to add between each measurement. (%)
    private final double ACC_TOLERANCE = 1E-4; // The accelration under which a measurement is taken. (meters per second
    // per second)

    private double currentOutput, prevVelocity, prevTime, initDistance, distance;
    private int logCount;

    private SimpleCSVLogger logger;

    public KvTuner() {
        super(Chassis.getInstance());
        logger = new SimpleCSVLogger("KvTuningLog");
        logger.init(new String[]{"vel", "output"}, new String[]{"m/s", "%"});
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        currentOutput = 0;
        prevVelocity = 0;
        prevTime = 0;
        initDistance = 0;
        logCount = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        logCount++;
        double currentVelocity = Chassis.getInstance().getAverageVelocity();
        double currentTime = Timer.getFPGATimestamp();
        double currentAcc = (currentVelocity - prevVelocity) / (currentTime - prevTime);
        distance = Chassis.getInstance().getAverageDistance() - initDistance;

        if (currentAcc < ACC_TOLERANCE) {
            if (Chassis.getInstance().getAverageVelocity() <= 1E-3) {
                currentOutput += 0.001;

            } else {
                if (logCount > 4) {
                    logCount = 0;
                    logger.writeData(currentVelocity, currentOutput);
                    currentOutput += DELTA_PERCENT;
                }
            }
        }

        Chassis.getInstance().drive(currentOutput, currentOutput);


        // Update vars.
        prevVelocity = currentVelocity;
        prevTime = currentTime;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return distance >= MAX_DISTANCE;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        logger.close();
        Chassis.getInstance().stopMotors();
    }

}
