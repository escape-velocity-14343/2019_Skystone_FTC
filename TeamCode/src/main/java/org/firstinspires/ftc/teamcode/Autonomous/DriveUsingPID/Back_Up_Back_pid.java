package org.firstinspires.ftc.teamcode.Autonomous.DriveUsingPID;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DriveTrainAndPID.FourEncoderDriveTrain;
import org.firstinspires.ftc.teamcode.DriveTrainAndPID.PIDController;


//Back up Auton that goes to the wall side of the bridge, and parks there

@Autonomous (name = "TESTING PID")
//@Disabled
public class Back_Up_Back_pid extends LinearOpMode {
    //initializaing the future variables
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor LFMotor, LBMotor, RFMotor, RBMotor, clawMotor;
    DigitalChannel limitSwitch;
    Servo rotateServo, clawServo;
    FourEncoderDriveTrain drive;
    private PIDController pidDrive;

    //no. of ticks per one revolution of the yellow jacket motors
    int Ticks_Per_Rev = 1316;

    @Override
    public void runOpMode(){
        // Initialize the hardware variables.
        LFMotor  = hardwareMap.get(DcMotor.class, "LF Motor");
        LBMotor  = hardwareMap.get(DcMotor.class, "LB Motor");
        RFMotor  = hardwareMap.get(DcMotor.class, "RF Motor");
        RBMotor  = hardwareMap.get(DcMotor.class, "RB Motor");
        clawMotor = hardwareMap.get(DcMotor.class,"Claw Up Motor");
        limitSwitch = hardwareMap.get(DigitalChannel.class, "Limit Stop");
        rotateServo = hardwareMap.get(Servo.class, "Rotate Servo");
        clawServo = hardwareMap.get(Servo.class, "Claw Servo");

        LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        //Wheels on the robot funtions
        drive = new FourEncoderDriveTrain(LFMotor, LBMotor, RFMotor, RBMotor);
        pidDrive = new PIDController(0.5, 1.2, 0.5);

        pidDrive.setOutputRange(-1, 1);
        pidDrive.setInputRange(-100000, 100000);
        pidDrive.setTolerance(20);
        pidDrive.reset();
        pidDrive.enable();

        //Reverse the right motors to move forward based on their orientation on the robot
        clawMotor.setDirection(DcMotor.Direction.REVERSE);
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);
        rotateServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setDirection(Servo.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Mode", "Init");
        telemetry.update();
        runtime.reset();
        waitForStart();

        //Running the code
        LFMotor.getCurrentPosition();
            while (opModeIsActive()) {
                //for (int i = 0; i == 1000; i ++) {
                    //drive.DriveForwardDistance(1,12);
                    //drive.DriveForwardPID(12);
                    //drive.DriveForward(1);

                    int inches = 12;

                    double setpoint = 1136 / (4 * 3.14159265);

                    pidDrive.setSetpoint(500);

                    /**/
                    do {
                        int encode = (LFMotor.getCurrentPosition() /*+ LBMotor.getCurrentPosition() + RFMotor.getCurrentPosition()*/ + RBMotor.getCurrentPosition()) / 2;

                        double power = pidDrive.performPID(encode);

                        telemetry.addData("encode", encode);
                        telemetry.addData("LF Motor",LFMotor.getCurrentPosition());
                        telemetry.addData("LB Motor",LBMotor.getCurrentPosition());
                        telemetry.addData("RF Motor",RFMotor.getCurrentPosition());
                        telemetry.addData("RB Motor",RBMotor.getCurrentPosition());
                        telemetry.addData("time", runtime);
                        telemetry.addData("power",power);
                        //telemetry.addData("setpoint",pidDrive.getSetpoint());
                        telemetry.log();
                        telemetry.update();

                        //drive.DriveForward(1);

                        /*LFMotor.setPower(pidDrive.performPID(LFMotor.getCurrentPosition()));
                        LBMotor.setPower(pidDrive.performPID(LBMotor.getCurrentPosition()));
                        RFMotor.setPower(pidDrive.performPID(RFMotor.getCurrentPosition()));
                        RBMotor.setPower(pidDrive.performPID(RBMotor.getCurrentPosition()));*/

                        drive.DriveForward(power);
                    } while (LFMotor.isBusy() && LBMotor.isBusy() && RFMotor.isBusy() && RBMotor.isBusy());
                //}
            }
        //}
    }

}