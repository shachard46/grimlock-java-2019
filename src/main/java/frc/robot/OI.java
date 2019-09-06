/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.arm.commands.MoveArm;
import frc.robot.formations.Formation;
import frc.robot.gripper.Gripper;
import frc.robot.gripper.commands.CollectOrEject;
import frc.robot.lift.commands.MoveLift;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    private final Joystick driveController;
    private final Joystick controller;
    // declared buttons
    private final JoystickButton aButton;
    private final JoystickButton bButton;
    private final JoystickButton xButton;
    private final JoystickButton yButton;
    private final JoystickButton leftBumper;
    private final JoystickButton rightBumper;
    private final JoystickButton startButton;
    private final JoystickButton backButton;

    private final Trigger.GameStateTrigger lowBall;
    private final Trigger.GameStateTrigger lowDisk;
    private final Trigger.GameStateTrigger middleFrontBall;
    private final Trigger.GameStateTrigger middleFrontDisk;
    private final Trigger.GameStateTrigger middleBackBall;
    private final Trigger.GameStateTrigger middleBackDisk;
    private final Trigger.GameStateTrigger highBall;
    private final Trigger.GameStateTrigger highDisk;

    private final Trigger.GamepadAxisTrigger leftTrigger;
    private final Trigger.GamepadAxisTrigger rightTrigger;
    private final Trigger.GamepadAxisTrigger leftJoystickY;
    private final Trigger.GamepadAxisTrigger rightJoystickY;

    public OI() {
        driveController = new Joystick(1);
        controller = new Joystick(4);

        aButton = new JoystickButton(controller, Trigger.GamepadButton.btnA.getNumber());
        bButton = new JoystickButton(controller, Trigger.GamepadButton.btnB.getNumber());
        xButton = new JoystickButton(controller, Trigger.GamepadButton.btnX.getNumber());
        yButton = new JoystickButton(controller, Trigger.GamepadButton.btnY.getNumber());
        leftBumper = new JoystickButton(controller, Trigger.GamepadButton.leftBumper.getNumber());
        rightBumper = new JoystickButton(controller, Trigger.GamepadButton.rightBumper.getNumber());
        startButton = new JoystickButton(controller, Trigger.GamepadButton.start.getNumber());
        backButton = new JoystickButton(controller, Trigger.GamepadButton.back.getNumber());

        lowBall = new Trigger.GameStateTrigger(controller,
                Trigger.GamepadButton.btnA, Gripper.GripperMode.ballMode);
        lowDisk = new Trigger.GameStateTrigger(controller,
                Trigger.GamepadButton.btnA, Gripper.GripperMode.diskMode);
        middleFrontBall = new Trigger.GameStateTrigger(controller,
                Trigger.GamepadButton.btnX, Gripper.GripperMode.ballMode);
        middleFrontDisk = new Trigger.GameStateTrigger(controller,
                Trigger.GamepadButton.btnX, Gripper.GripperMode.diskMode);
        middleBackBall = new Trigger.GameStateTrigger(controller,
                Trigger.GamepadButton.btnB, Gripper.GripperMode.ballMode);
        middleBackDisk = new Trigger.GameStateTrigger(controller,
                Trigger.GamepadButton.btnB, Gripper.GripperMode.diskMode);
        highBall = new Trigger.GameStateTrigger(controller,
                Trigger.GamepadButton.btnY, Gripper.GripperMode.ballMode);
        highDisk = new Trigger.GameStateTrigger(controller,
                Trigger.GamepadButton.btnY, Gripper.GripperMode.diskMode);

        leftTrigger = new Trigger.GamepadAxisTrigger(controller, Trigger.GamepadAxis.leftTrigger, 0.1);
        rightTrigger = new Trigger.GamepadAxisTrigger(controller, Trigger.GamepadAxis.rightTrigger, 0.1);
        leftJoystickY = new Trigger.GamepadAxisTrigger(controller, Trigger.GamepadAxis.leftY, 0.1);
        rightJoystickY = new Trigger.GamepadAxisTrigger(controller, Trigger.GamepadAxis.rightY, 0.1);
    }

    public void activate() {
        ballButtons();
        diskButtons();
        collectAndEjectButtons();
        manualControlButtons();
    }

    public void manualControlButtons() {
        leftJoystickY.whileActive(new MoveArm(-controller.getRawAxis(Trigger.GamepadAxis.leftY.getAxis())));
        rightJoystickY.whileActive(new MoveLift(controller.getRawAxis(Trigger.GamepadAxis.rightY.getAxis())));
    }

    private void ballButtons() {
        lowBall.whenActive(new Formation.LowBall());
        middleFrontBall.whenActive(new Formation.FrontMiddleBall());
        middleBackBall.whenActive(new Formation.BackMiddleBall());
        highBall.whenActive(new Formation.HighBall());
        rightBumper.whenActive(new Formation.FeederBallCollect());
    }

    private void diskButtons() {
        lowDisk.whenActive(new Formation.LowDisk());
        middleFrontDisk.whenActive(new Formation.FrontMiddleDisk());
        middleBackDisk.whenActive(new Formation.BackMiddleDisk());
        highDisk.whenActive(new Formation.HighDisk());
        leftBumper.whenActive(new Formation.FeederDiskCollect());
    }

    private void collectAndEjectButtons() {
        leftTrigger.whileActive(new CollectOrEject(1));
        rightTrigger.whileActive(new CollectOrEject(-1));
    }

    public Joystick getJoystick() {
        return driveController;
    }
}
