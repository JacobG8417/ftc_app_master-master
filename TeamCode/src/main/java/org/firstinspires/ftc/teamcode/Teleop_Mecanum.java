/*
ADB guide can be found at:
https://ftcprogramming.wordpress.com/2015/11/30/building-ftc_app-wirelessly/
*/
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.Arrays;

/**
 * Created by Ethan Schaffer on 10/31/2016.
 */
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Mecanum_Drive", group="TeleOp")
public class Teleop_Mecanum extends OpMode {


    private static final double TRIGGERTHRESHOLD = .2;
    private static final double ACCEPTINPUTTHRESHOLD = .15;
    private static final double SCALEDPOWER = 1; //Emphasis on current controller reading (vs current motor power) on the drive train

    private static DcMotor front_left, back_left, front_right, back_right;
    @Override
    public void init() {
        front_left = hardwareMap .dcMotor.get(UniversalConstants.LEFT1NAME);
        back_left = hardwareMap.dcMotor.get(UniversalConstants.LEFT2NAME);
        front_right = hardwareMap.dcMotor.get(UniversalConstants.RIGHT1NAME);
        back_right = hardwareMap.dcMotor.get(UniversalConstants.RIGHT2NAME);

        front_right.setDirection(DcMotorSimple.Direction.REVERSE);
        front_left.setDirection(DcMotorSimple.Direction.FORWARD);
        back_right.setDirection(DcMotorSimple.Direction.REVERSE);
        back_left.setDirection(DcMotorSimple.Direction.FORWARD);
        // double volts = hardwareMap.voltageSensor.get("Motor Controller 1").getVoltage();
    }

    @Override
    public void loop() {
        double inputY = Math.abs(gamepad1.left_stick_y) > ACCEPTINPUTTHRESHOLD ? gamepad1.left_stick_y : 0;
        double inputX = Math.abs(gamepad1.left_stick_x) > ACCEPTINPUTTHRESHOLD ? -gamepad1.left_stick_x : 0;
        double inputC = Math.abs(gamepad1.right_stick_y)> ACCEPTINPUTTHRESHOLD ? -gamepad1.right_stick_y: 0;

        double BIGGERTRIGGER = gamepad1.left_trigger > gamepad1.right_trigger ? gamepad1.left_trigger : gamepad1.right_trigger;
        //Ternary, the larger trigger value is set to the value BIGGERTRIGGER

            if(BIGGERTRIGGER > TRIGGERTHRESHOLD){ //If we have enough pressure on a trigger
            if( (Math.abs(inputY) > Math.abs(inputX)) && (Math.abs(inputY) > Math.abs(inputC)) ){ //If our forwards motion is the largest motion vector
                inputY /= 5*BIGGERTRIGGER; //slow down our power inputs
                inputX /= 5*BIGGERTRIGGER; //slow down our power inputs
                inputC /= 5*BIGGERTRIGGER; //slow down our power inputs
            } else if( (Math.abs(inputC) > Math.abs(inputX)) && (Math.abs(inputC) > Math.abs(inputY)) ){ //and if our turing motion is the largest motion vector
                inputY /= 4*BIGGERTRIGGER; //slow down our power inputs
                inputX /= 4*BIGGERTRIGGER; //slow down our power inputs
                inputC /= 4*BIGGERTRIGGER; //slow down our power inputs
            } else if( (Math.abs(inputX) > Math.abs(inputY)) && (Math.abs(inputX) > Math.abs(inputC)) ){ //and if our strafing motion is the largest motion vector
                inputY /= 3*BIGGERTRIGGER; //slow down our power inputs
                inputX /= 3*BIGGERTRIGGER; //slow down our power inputs
                inputC /= 3*BIGGERTRIGGER; //slow down our power inputs*/
            }
        }
        //Use the larger trigger value to scale down the inputs.

        arcadeMecanum(inputY, inputX, inputC, front_left, front_right, back_left, back_right);
    }

    // y - forwards
    // x - side
    // c - rotation
    public static void arcadeMecanum(double y, double x, double c, DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight) {
        double leftFrontVal = y + x + c; // Hi...
        double rightFrontVal = y - x - c;
        double leftBackVal = y - x + c;
        double rightBackVal = y + x - c;

        double strafeVel;
        double driveVel;
        double turnVel;
        {
            driveVel = 0;
            strafeVel = 0;
            turnVel = 0;

            double leftFrontVel = -driveVel - strafeVel + turnVel;
            double rightFrontVel = -driveVel - strafeVel - turnVel;
            double leftRearVel = -driveVel - strafeVel + turnVel;
            double rightRearVel = -driveVel - strafeVel - turnVel;
            double[] vels = {leftFrontVel, rightFrontVel, leftRearVel, rightRearVel};
            //double[] vels = {Math.abs(leftFrontVel), Math.abs(rightFrontVel), Math.abs(leftRearVel), Math.abs(rightRearVel)};
            Arrays.sort(vels);
            if (vels[3] > 1) {
                leftFrontVel /= vels[3];
                rightFrontVel /= vels[3];
                leftRearVel /= vels[3];
                rightRearVel /= vels[3];
            }
            frontLeft.setPower(leftFrontVel);
            frontRight.setPower(rightFrontVel);
            backLeft.setPower(leftRearVel);
            backRight.setPower(rightRearVel);
        }

        //Move range to between 0 and +1, if not already
        double[] wheelPowers = {rightFrontVal, leftFrontVal, leftBackVal, rightBackVal};
        Arrays.sort(wheelPowers);
        if (wheelPowers[3] > 1) {
            leftFrontVal /= wheelPowers[3];
            rightFrontVal /= wheelPowers[3];
            leftBackVal /= wheelPowers[3];
            rightBackVal /= wheelPowers[3];
        }
        double scaledPower = SCALEDPOWER;

        front_left.setPower(leftFrontVal*scaledPower+frontLeft.getPower()*(1+scaledPower));
        back_left.setPower(rightFrontVal*scaledPower+backLeft.getPower()*(1+scaledPower));
        front_right.setPower(leftBackVal*scaledPower+frontRight.getPower()*(1-scaledPower));
        back_right.setPower(rightBackVal*scaledPower+backRight.getPower()*(1-scaledPower));
    }

}
/*© 2019 GitHub, Inc.
        Terms
        Privacy
        Security
        Status
        Help
        Contact GitHub
        Pricing
        API
        Training
        Blog
        About

 */
