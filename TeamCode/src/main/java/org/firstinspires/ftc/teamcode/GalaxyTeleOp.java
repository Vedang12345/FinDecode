package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name="GalaxyBot-TeleOp", group="Opmode")
public class GalaxyTeleOp extends OpMode
{
    private GalaxyBot robot;

    private Gamepad currentGamepad1 = new Gamepad();
    private Gamepad previousGamepad1 = new Gamepad();

    private Gamepad currentGamepad2 = new Gamepad();
    private Gamepad previousGamepad2 = new Gamepad();

    private IMU imu;

    private boolean fieldCentric = false;

    private void mecanumDrive(double botHeading)
    {
        float LIMIT_SPEED = 1.0f;
        float y = -gamepad1.left_stick_y;
        float x = gamepad1.left_stick_x * 1.1f;
        float rx = gamepad1.right_stick_x;

        if(currentGamepad1.right_bumper)
        {
            LIMIT_SPEED = 0.375f;
        }

        // If you want field-centric, use rotX and rotY instead of x and y
        // double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        // double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        double frontLeftPower = (y + x + rx) / denominator * robot.driveDir;
        double backLeftPower = (y - x + rx) / denominator * robot.driveDir;
        double frontRightPower = (y - x - rx) / denominator * robot.driveDir;
        double backRightPower = (y + x - rx) / denominator * robot.driveDir;

        robot.drive(frontRightPower * LIMIT_SPEED, frontLeftPower * LIMIT_SPEED,
                backRightPower * LIMIT_SPEED, backLeftPower * LIMIT_SPEED);

        telemetry.addData("leftFrontPower", frontLeftPower * LIMIT_SPEED);
        telemetry.addData("leftBackPower", backLeftPower * LIMIT_SPEED);
        telemetry.addData("rightFrontPower", frontRightPower * LIMIT_SPEED);
        telemetry.addData("rightBackPower", backRightPower * LIMIT_SPEED);
        telemetry.addData("y", y);
        telemetry.addData("x", x);
        telemetry.addData("rx", rx);
        telemetry.addData("heading", botHeading);
        telemetry.update();
    }

    private void copyGamepad() throws RobotCoreException
    {
        previousGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);

        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);
    }

    @Override
    public void init()
    {
        robot = new GalaxyBot(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters myIMUparameters;
        myIMUparameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );
        imu.initialize(myIMUparameters);
    }

    @Override
    public void loop()
    {
        try {
            copyGamepad();
        } catch (RobotCoreException e) {
            e.printStackTrace();
        }

        // Fixed: was checking currentGamepad1.touchpad twice
        if (currentGamepad1.touchpad && !previousGamepad1.touchpad)
            robot.driveDir *= -1;

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        // robot.updateSense();
        mecanumDrive(botHeading);
    }
}