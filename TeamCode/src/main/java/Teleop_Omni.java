import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Teleop_Omni {

    @TeleOp(name="Teleop_Omni", group="Linear Opmode")
    public class Driver extends LinearOpMode {

        // public void Driver() {
        private DcMotor leftDrive;
        private DcMotor rightDrive;

        // private Gamepad gamepad1 = new Gamepad;

        @Override
        public void runOpMode() {
            telemetry.addData("Status", "Initialized");
            telemetry.update();

            leftDrive = hardwareMap.get(DcMotor.class,"left_drive");
            rightDrive = hardwareMap.get(DcMotor.class,"right_drive");

            leftDrive.setDirection(DcMotor.Direction.FORWARD);
            rightDrive.setDirection(DcMotor.Direction.REVERSE);


            // Wait for the game to start (driver presses PLAY)
            waitForStart();

            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {
                double power = gamepad2.right_stick_y;
                double rightmotorpower = gamepad1.right_stick_y;
                double leftmotorpower = gamepad1.left_stick_y;

                leftDrive.setPower(leftmotorpower);
                rightDrive.setPower(rightmotorpower);
                telemetry.addData("Status", "Running");
                telemetry.update();


            }

        }
    }

}
