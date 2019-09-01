package frc.robot.autonomous.commands.sequences.subSequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.autonomous.commands.FollowPath;

public class RampToRocket extends CommandGroup {
    public RampToRocket(){
        addSequential(new FollowPath("rampToRocket"));
    }
}
