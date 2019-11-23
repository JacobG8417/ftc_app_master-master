import android.view.animation.GridLayoutAnimationController;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Teleop_Omni", group="TeleOp")
public class Teleop_Omni extends LinearOpMode {

        @Override
        public void runOpMode() throws InterruptedException {
            waitForStart();
            while(opModeIsActive()){
                double power = gamepad1.right_stick_y;
                double rightmotorpower = gamepad1.right_stick_y;
                double leftmotorpower = gamepad1.left_stick_y;
                this.telemetry.update();

                telemetry.addData("Status", "Initialized");

                DcMotor front_left = hardwareMap.get(DcMotor.class,"front_left");
                DcMotor front_right = hardwareMap.get(DcMotor.class, "front_right");
                DcMotor back_left = hardwareMap.get(DcMotor.class, "back_left");
                DcMotor back_right = hardwareMap.get(DcMotor.class, "back_right");


            front_right.setDirection(DcMotorSimple.Direction.REVERSE);
            front_left.setDirection(DcMotorSimple.Direction.FORWARD);
            back_right.setDirection(DcMotorSimple.Direction.REVERSE);
            back_left.setDirection(DcMotorSimple.Direction.FORWARD);


            // Wait for the game to start (driver presses PLAY)
            waitForStart();

            // run until the end of the match (driver presses STOP)

                telemetry.addData("Status", "Running");
                final boolean update = telemetry.update();


            }

        }
    }


