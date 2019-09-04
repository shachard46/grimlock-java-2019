package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.gripper.Gripper;

public abstract class Trigger extends edu.wpi.first.wpilibj.buttons.Trigger {

    final Joystick joystick;

    Trigger(Joystick joystick) {
        this.joystick = joystick;
    }

    public enum GamepadAxis {
        leftX(0),
        rightY(1),
        leftTrigger(2),
        rightTrigger(3),
        rightX(4),
        leftY(5);

        private final int axis;

        GamepadAxis(int axis) {
            this.axis = axis;
        }

        public int getAxis() {
            return axis;
        }
    }

    public enum GamepadButton {
        btnA(1),
        btnB(2),
        btnX(3),
        btnY(4),
        leftBumper(5),
        rightBumper(6),
        back(7),
        start(8),
        leftJoystick(9),
        rightJoystick(10);


        private final int number;

        GamepadButton(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }

    public static class GamepadAxisTrigger extends Trigger {
        private final GamepadAxis axis;
        private final double thresholdValue;

        public GamepadAxisTrigger(Joystick joystick, GamepadAxis axis, double thresholdValue) {
            super(joystick);
            this.axis = axis;
            this.thresholdValue = thresholdValue;
        }


        @Override
        public boolean get() {
            return Math.abs(joystick.getRawAxis(axis.getAxis())) > thresholdValue;
        }
    }

    public static class GamepadButtonTrigger extends Trigger {
        private final GamepadButton button;

        public GamepadButtonTrigger(Joystick joystick, GamepadButton button) {
            super(joystick);
            this.button = button;
        }

        @Override
        public boolean get() {
            return joystick.getRawButton(button.getNumber());
        }
    }

    public static class GameStateTrigger extends GamepadButtonTrigger {
        private final Gripper.GripperMode mode;

        public GameStateTrigger(Joystick joystick, GamepadButton button, Gripper.GripperMode mode) {
            super(joystick, button);
            this.mode = mode;
        }

        @Override
        public boolean get() {
            return super.get() && mode == Gripper.getInstance().getMode();
        }
    }
}
