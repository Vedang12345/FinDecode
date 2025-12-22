package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class GalaxyBot {
    private DcMotor leftBackMotor;
    private DcMotor leftFrontMotor;

    int driveDir = 1;

    private DcMotor rightBackMotor;
    private DcMotor rightFrontMotor;

    public DcMotor intake;

    private HardwareMap hwMap;
    private ElapsedTime runtime = new ElapsedTime();

    public GalaxyBot(HardwareMap hwMap) {
        this.hwMap = hwMap;
        map();
    }

    private void map() {
        leftFrontMotor = hwMap.get(DcMotor.class, "left_front");
        rightFrontMotor = hwMap.get(DcMotor.class, "right_front");
        leftBackMotor = hwMap.get(DcMotor.class, "left_back");
        rightBackMotor = hwMap.get(DcMotor.class, "right_back");

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void drive(double speed) {
        leftBackMotor.setPower(speed);
        leftFrontMotor.setPower(speed);
        rightBackMotor.setPower(speed);
        rightFrontMotor.setPower(speed);
    }

    public void drive(double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower) {
        leftBackMotor.setPower(backLeftPower);
        leftFrontMotor.setPower(frontLeftPower);
        rightBackMotor.setPower(backRightPower);
        rightFrontMotor.setPower(frontRightPower);
    }

    public double getElapsedTime() {
        return runtime.time(TimeUnit.MILLISECONDS);
    }

}