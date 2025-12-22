package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;
public class GalaxyBot {
    private DcMotor rightFrontMotor;
    private DcMotor rightBackMotor;

    int driveDir = 1;

    private DcMotor leftBackMotor;
    private DcMotor leftFrontMotor;
    private DcMotor intake;
    private DcMotor launcherRight;


    NormalizedRGBA clawColors;

    public DcMotorEx leftSlide;
    public DcMotorEx rightSlide;

    // public RevColorSensorV3 clawSense;

    Servo leftIntake;
    Servo rightIntake;

    Servo leftSwing;
    Servo rightSwing;

//  private DcMotor rightSlide;
//   private DcMotor  leftSlide;

    public Servo intakeClaw;
    public Servo twister;

    public boolean swingUp = false;
    public boolean clawOpen = false;
    public boolean intakeClawOpen = false;
    public boolean spinUp = false;
    public boolean clawSampling = false;
    public boolean intakeExtend = false;
    public boolean slideUp = false;
    public boolean twisted = false;

    public static double intakeDetractPos = 0.0;
    public static double intakeExtendPos = 0.25;


    public static int slideUpPos = 3100;
    public static int slideDownPos = 0;
    public static int slideDownFully = 0;

    public boolean redDetected = false;
    public boolean blueDetected = false;
    public boolean yellowDetected = false;

    // public Servo leftSpin;
    public Servo rightSpin;
    private Servo claw;
    public Servo clawSpinner;

    private HardwareMap hwMap;
    private ElapsedTime runtime = new ElapsedTime();

    public GalaxyBot(HardwareMap hwMap) {
        this.hwMap = hwMap;
        map();
    }

    private void map() {
        swingUp = false;
        clawOpen = false;
        intakeClawOpen = false;
        spinUp = false;
        clawSampling = false;
        intakeExtend = false;
        slideUp = false;
        twisted = false;
        leftFront = hwMap.get(DcMotor.class, "left_front");
        rightFront = hwMap.get(DcMotor.class, "right_front");
        leftBack = hwMap.get(DcMotor.class, "left_back");
        rightBack = hwMap.get(DcMotor.class, "right_back");

        leftIntake = hwMap.get(Servo.class, "left_intake");
        rightIntake = hwMap.get(Servo.class, "right_intake");

        // clawSense = hwMap.get(RevColorSensorV3.class, "claw_sense");

        leftSwing = hwMap.get(Servo.class, "left_swing");
        rightSwing = hwMap.get(Servo.class, "right_swing");

        twister = hwMap.get(Servo.class, "rotater");

        rightSlide = hwMap.get(DcMotorEx.class,"right_slide");
        leftSlide = hwMap.get(DcMotorEx.class,"left_slide");
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlide.setTargetPositionTolerance(50);
        leftSlide.setTargetPositionTolerance(50);
// Cristiano Ronaldo
        // rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        intakeClaw = hwMap.get(Servo.class, "intake_claw");

        // leftSpin = hwMap.get(Servo.class,"left_spin");
        rightSpin = hwMap.get(Servo.class, "right_spin");

        claw = hwMap.get(Servo.class, "claw");
        clawSpinner = hwMap.get(Servo.class, "claw_spinner");

        //clawSense.setGain(5);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        rightSpin.setDirection(Servo.Direction.REVERSE);

        rightIntake.setDirection(Servo.Direction.REVERSE);
        rightSwing.setDirection(Servo.Direction.REVERSE);

        leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void drive(double speed) {
        leftBack.setPower(speed);
        leftFront.setPower(speed);
        rightBack.setPower(speed);
        rightFront.setPower(speed);
    }

    public void drive(double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower) {
        leftBack.setPower(backLeftPower);
        leftFront.setPower(frontLeftPower);
        rightBack.setPower(backRightPower);
        rightFront.setPower(frontRightPower);
    }
    //

//    public void updateSense()
//    {
//        float[] hsv = new float[3];
//        clawColors = clawSense.getNormalizedColors();
//
//        Color.colorToHSV(clawColors.toColor(), hsv);
//        if (hsv[0] > 200 && hsv[0] < 240)
//        {
//            blueDetected = true;
//            redDetected = false;
//            yellowDetected = false;
//        }
//        else if (hsv[0] > 60 && hsv[0] < 90)
//        {
//            blueDetected = false;
//            redDetected = false;
//            yellowDetected = true;
//        }
//        else if (hsv[0] > 10 && hsv[0] < 40)
//        {
//            blueDetected = false;
//            redDetected = true;
//            yellowDetected = false;
//        }
//        else
//        {
//            blueDetected = false;
//            redDetected = false;
//            yellowDetected = false;
//        }
//    }

    public static double slideSpeed = 1.0;
//    public void slide()
//    {
//        slideUp = !slideUp;
//
//        if (slideUp)
//        {
//            leftSlide.setTargetPosition(slideUpPos);
//            rightSlide.setTargetPosition(slideUpPos);
//
//        }
//        else
//        {
//            leftSlide.setTargetPosition(slideDownPos);
//            rightSlide.setTargetPosition(slideDownPos);
//
//        }
//        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        leftSlide.setPower(slideSpeed);
//        rightSlide.setPower(slideSpeed);
//    }

    public void slideDown()
    {
        slideUp = false;

        leftSlide.setTargetPosition(slideDownPos);
        rightSlide.setTargetPosition(slideDownPos);

        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftSlide.setPower(slideSpeed);
        rightSlide.setPower(slideSpeed);
    }
    public void doslideDownFully() {
        leftSlide.setTargetPosition(slideDownFully);
        rightSlide.setTargetPosition(slideDownFully);
    }
    //
    //
    public void slideUp()
    {
        slideUp = true;

        leftSlide.setTargetPosition(slideUpPos);
        rightSlide.setTargetPosition(slideUpPos);

        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftSlide.setPower(slideSpeed);
        rightSlide.setPower(slideSpeed);
    }


    public static int slideUpSpecimenPos = 2350;
    public static int slideDownSpecimenPos = 1500;

    public static int slideUpSpecimenPickupPos = 135;

    public void slideUpSpecimen()
    {
        slideUp = true;

        leftSlide.setTargetPosition(slideUpSpecimenPos);
        rightSlide.setTargetPosition(slideUpSpecimenPos);

        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftSlide.setPower(slideSpeed);
        rightSlide.setPower(slideSpeed);
    }


    public void slideUpSpecimenPickup()
    {
        slideUp = true;

        leftSlide.setTargetPosition(slideUpSpecimenPickupPos);
        rightSlide.setTargetPosition(slideUpSpecimenPickupPos);

        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftSlide.setPower(slideSpeed);
        rightSlide.setPower(slideSpeed);
    }

    public void slideDownSpecimen()
    {
        slideUp = true;

        leftSlide.setTargetPosition(slideDownSpecimenPos);
        rightSlide.setTargetPosition(slideDownSpecimenPos);

        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftSlide.setPower(slideSpeed);
        rightSlide.setPower(slideSpeed);
    }



    public void intakeExtend() {
        intakeExtend = true;
        leftIntake.setPosition(intakeExtendPos);
        rightIntake.setPosition(intakeExtendPos);
    }

    public void intakeDetract() {
        intakeExtend = false;
        leftIntake.setPosition(intakeDetractPos);
        rightIntake.setPosition(intakeDetractPos);
    }

    public void intakePreciseExtend(float amount)
    {
        leftIntake.setPosition(leftIntake.getPosition() + amount);
        rightIntake.setPosition(rightIntake.getPosition() + amount);
    }

    public void intakePreciseDetract(float amount)
    {
        leftIntake.setPosition(leftIntake.getPosition() - amount);
        rightIntake.setPosition(rightIntake.getPosition() - amount);
    }

    public static double unTwistPos = 0.27;
    public static double twistPos = 0.60;
    public static double halfTwistPos = 0.45;

    public void doTwist()
    {
        twisted = true;
        twister.setPosition(twistPos);
    }

    public void doUnTwist()
    {
        twisted = false;
        twister.setPosition(unTwistPos);
    }
    public void doHalfTwist()
    {
        twister.setPosition(halfTwistPos);
    }

    public static double clawOpenPos = 0.8;
    public static double clawClosedPos = 0.985;

    public void claw()
    {
        clawOpen = !clawOpen;

        if (clawOpen)
            doClawOpen();
        else
            doClawClose();
    }

    public void doClawOpen()
    {
        clawOpen = true;
        claw.setPosition(clawOpenPos);
    }

    public void doClawClose()
    {
        clawOpen = false;
        claw.setPosition(clawClosedPos);
    }

    public static double intakeClawOpenPos = 0;
    public static double intakeClawClosePos = 0.31;
    public void intakeClaw()
    {
        intakeClawOpen = !intakeClawOpen;

        if (intakeClawOpen)
            intakeClawOpen();
        else
            intakeClawClose();
    }

    public void intakeClawOpen()
    {
        intakeClawOpen = true;
        intakeClaw.setPosition(intakeClawOpenPos);
    }

    public void intakeClawClose()
    {
        intakeClawOpen = false;
        intakeClaw.setPosition(intakeClawClosePos);
    }

    public static double swingUpPos = 0.6575;
    public static double swingAutoInitPos = 0.12;
    public static double swingDownPos = 0.05;
    public void swing()
    {
        swingUp = !swingUp;
        if (swingUp)
        {
            doSwingUp();
        }
        else
        {
            doSwingDown();
        }
    }

    public void doSwingUp()
    {
        swingUp = true;
        leftSwing.setPosition(swingUpPos);
        rightSwing.setPosition(swingUpPos);
    }

    public void doSwingDown()
    {
        swingUp = false;
        leftSwing.setPosition(swingDownPos);
        rightSwing.setPosition(swingDownPos);
    }
    public void doInitAutoPos()
    {
        leftSwing.setPosition(swingAutoInitPos);
        rightSwing.setPosition(swingAutoInitPos);
    }

    public static double swingUpAuto = 0.3;
    public static double swingDownAuto = 0.03;

    public void doAutoUpPos() {
        leftSwing.setPosition(swingUpAuto);
        rightSwing.setPosition(swingUpAuto);
    }

    public void doAutoDownPos() {
        leftSwing.setPosition(swingDownAuto);
        rightSwing.setPosition(swingDownAuto);
    }

    public static double spinUpPos = 0.965;
    public static double spinDownPos = 0.0;
    public void spin()
    {
        spinUp = !spinUp;
        if (spinUp) {
            spinUp();
        }
        else
        {
            spinDown();
        }
    }

    public void spinDown()
    {
        spinUp = false;
        rightSpin.setPosition(spinDownPos);
    }

    public void spinUp()
    {
        spinUp = true;
        rightSpin.setPosition(spinUpPos);
    }

    public static double clawSpinSamplePos = 0.90;
    public static double clawSpinSpecimenPos = 0.292;
    public static double swingSamplePos = 0.4;

    public void clawSpinSample() {
        clawSampling = true;
        clawSpinner.setPosition(clawSpinSamplePos);
    }
    public void clawFromAuto() throws InterruptedException {
        clawSpinSample();
        doClawClose();
        leftSwing.setPosition(swingSamplePos);
        rightSwing.setPosition(swingSamplePos);
        Thread.sleep(1000);
        clawSpinner.setPosition(clawSpinSamplePos);
        Thread.sleep(1000);
        doSwingDown();
    }

    public void clawSpinSpecimen()
    {
        clawSampling = false;
        clawSpinner.setPosition(clawSpinSpecimenPos);
    }

    public double getElapsedTime() {
        return runtime.time(TimeUnit.MILLISECONDS);
    }

}