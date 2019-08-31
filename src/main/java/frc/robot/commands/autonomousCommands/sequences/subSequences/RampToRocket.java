package frc.robot.commands.autonomousCommands.sequences.subSequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.Command;
import frc.robot.commands.autonomousCommands.FollowPath;

public class RampToRocket extends CommandGroup {
    public RampToRocket(){
        addSequential(new FollowPath("rampToRocket"));
    }
}
