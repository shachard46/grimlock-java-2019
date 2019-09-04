package frc.robot.formations;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.arm.Arm;
import frc.robot.arm.commands.LiftArmToState;
import frc.robot.gripper.Gripper;
import frc.robot.gripper.commands.SetGameMode;
import frc.robot.lift.Lift;
import frc.robot.lift.commands.LiftToState;

public abstract class Formation extends CommandGroup {
    Formation(Lift.LiftState liftState, Arm.ArmState armState, Gripper.GripperMode mode) {
        addParallel(new SetGameMode(mode));
        addParallel(new LiftArmToState(armState));
        addSequential(new WaitCommand(0.4));
        addParallel(new LiftToState(liftState));
    }

    public static class LowBall extends Formation {
        public LowBall() {
            super(Lift.LiftState.bottomBall, Arm.ArmState.frontLowBall, Gripper.GripperMode.ballMode);
        }
    }

    public static class FrontMiddleBall extends Formation {
        public FrontMiddleBall() {
            super(Lift.LiftState.middleBallFront, Arm.ArmState.frontMidBall, Gripper.GripperMode.ballMode);
        }
    }

    public static class BackMiddleBall extends Formation {
        public BackMiddleBall() {
            super(Lift.LiftState.bottomBall, Arm.ArmState.middleBackBall, Gripper.GripperMode.ballMode);
        }
    }

    public static class HighBall extends Formation {
        public HighBall() {
            super(Lift.LiftState.up, Arm.ArmState.highBackBall, Gripper.GripperMode.ballMode);
        }
    }

    public static class LowDisk extends Formation {
        public LowDisk() {
            super(Lift.LiftState.bottomDisk, Arm.ArmState.frontDisk, Gripper.GripperMode.ballMode);
        }
    }

    public static class FrontMiddleDisk extends Formation {
        public FrontMiddleDisk() {
            super(Lift.LiftState.middleDisk, Arm.ArmState.frontDisk, Gripper.GripperMode.ballMode);
        }
    }

    public static class BackMiddleDisk extends Formation {
        public BackMiddleDisk() {
            super(Lift.LiftState.bottomDisk, Arm.ArmState.backDisk, Gripper.GripperMode.ballMode);
        }
    }

    public static class HighDisk extends Formation {
        public HighDisk() {
            super(Lift.LiftState.topDisk, Arm.ArmState.backDisk, Gripper.GripperMode.ballMode);
        }
    }
}
